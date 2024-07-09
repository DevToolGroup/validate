/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate.engine;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedArrayType;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link ConstraintAnnotationProvider} 实现类
 */
public class ConstraintAnnotationProviderImpl implements ConstraintAnnotationProvider {

  private ConstrainedValueSpreadProvider spreadProvider;

  private ConstraintValidateOperateProvider operateProvider;

  private ConcurrentHashMap<Executable, List<List<ConstraintAnnotation>>> CONSTRAINTS = new ConcurrentHashMap<>();

  private ConcurrentHashMap<Class<?>, List<ConstrainedElement>> REFLECT = new ConcurrentHashMap<>();

  private ConcurrentHashMap<ConstrainedElement, List<ConstraintAnnotation>> ELEMENTS = new ConcurrentHashMap<>();

  private Boolean openCache;

  public ConstraintAnnotationProviderImpl(ConstrainedValueSpreadProvider spreadProvider,
      ConstraintValidateOperateProvider operateProvider,
      Boolean openCache) {
    this.spreadProvider = spreadProvider;
    this.operateProvider = operateProvider;
    this.openCache = openCache;
  }

  @Override
  public List<List<ConstraintAnnotation>> resolveAnnotations(Executable executable) throws Exception {
    return CacheUtils.doMapCache(CONSTRAINTS, executable, (exec) -> {
      List<List<ConstraintAnnotation>> constraints = new ArrayList<>();
      Parameter[] parameters = executable.getParameters();
      for (int i = 0; i < parameters.length; i++) {
        ConstrainedElement element = new ConstrainedParameter(parameters[i]);
        constraints.add(doResolveConstraintAnnotations(element));
      }
      return constraints;
    }, openCache);
  }

  private List<ConstraintAnnotation> doResolveConstraintAnnotations(ConstrainedElement element) throws Exception {
    return CacheUtils.doMapCache(ELEMENTS, element, (ele) -> {
      List<ConstraintAnnotation> constraints = new ArrayList<>();
      if (element.isAnnotationPresent(Spread.class)) {
        doResolveSpreadConstraintAnnotation(element, constraints);
      }
      if (element.isAnnotationPresent(Cascade.class)) {
        doResolveCascadeConstraintAnnotation(element, constraints);
      }
      doResolveGenericConstraintAnnotation(element, constraints);
      return constraints;
    }, openCache);
  }

  private void doResolveSpreadConstraintAnnotation(ConstrainedElement element, List<ConstraintAnnotation> constraints)
      throws Exception {
    AnnotatedType annotatedType = element.getAnnotatedType();
    if (annotatedType instanceof AnnotatedArrayType) {
      AnnotatedArrayType annotatedArrayType = (AnnotatedArrayType) annotatedType;

      ConstrainedArrayElement arrayElement = new ConstrainedArrayElement(element,
          annotatedArrayType.getAnnotatedGenericComponentType());
      SpreadArrayConstraintAnnotation constraint = new SpreadArrayConstraintAnnotation(element,
          doResolveConstraintAnnotations(arrayElement));
      constraints.add(constraint);

    } else if (annotatedType instanceof AnnotatedParameterizedType) {
      AnnotatedParameterizedType annotatedParameterizedType = (AnnotatedParameterizedType) annotatedType;

      ParameterizedType parameterizedType = (ParameterizedType) annotatedParameterizedType.getType();
      Class<?> containerType = (Class<?>) parameterizedType.getRawType();
      TypeVariable<?>[] variables = containerType.getTypeParameters();

      List<ConstraintAnnotation> spreads = new ArrayList<>();
      AnnotatedType[] annotatedActualTypeArguments = annotatedParameterizedType.getAnnotatedActualTypeArguments();
      for (int index = 0; index < annotatedActualTypeArguments.length; index++) {
        AnnotatedType annotatedActualTypeArgument = annotatedActualTypeArguments[index];
        ConstrainedTypeArgument typeArgument = new ConstrainedTypeArgument(element,
            containerType,
            index,
            variables[index],
            annotatedActualTypeArgument);
        spreads.addAll(doResolveConstraintAnnotations(typeArgument));

      }
      ConstrainedValueSpreader spread = spreadProvider.resolve(containerType);
      constraints.add(new SpreadTypeArgumentConstraintAnnotation(element, spreads, spread));
    } else {
      // do nothing
    }

  }

  private void doResolveCascadeConstraintAnnotation(ConstrainedElement element,
      List<ConstraintAnnotation> constraints) {
    if (element.isArray()) {
      return;
    }
    constraints.add(new CascadeConstraintAnnotation(element, (parameter) -> {
      List<ConstraintAnnotation> dynamicConstraints = new ArrayList<>();
      for (ConstrainedElement property : doResolveProperty(parameter.getClass(), element)) {
        dynamicConstraints.addAll(doResolveConstraintAnnotations(property));
      }
      return dynamicConstraints;
    }));
  }

  private List<ConstrainedElement> doResolveProperty(Class<? extends Object> clazz, ConstrainedElement parent)
      throws Exception {
    return CacheUtils.doMapCache(REFLECT, clazz, (clz) -> {
      List<ConstrainedElement> elements = new ArrayList<>();
      Class<?> superClass = clazz;
      do {
        Field[] fields = superClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
          Field field = fields[i];
          if (field.isSynthetic() || Modifier.isStatic(field.getModifiers())) {
            continue;
          }
          if (hasConstraintAnnotation(field.getAnnotations())) {
            elements.add(new ConstrainedProperty(parent, field));
          }
          if (hasMultiConstraintAnnotation(field.getAnnotations())) {
            elements.add(new ConstrainedProperty(parent, field));
          }
        }
        superClass = superClass.getSuperclass();
      } while (null != superClass);
      return elements;
    }, openCache);
  }

  private void doResolveGenericConstraintAnnotation(ConstrainedElement element,
      List<ConstraintAnnotation> constraints) {
    Annotation[] annotations = element.getAnnotations();
    for (Annotation annotation : annotations) {
      if (isConstraintAnnotation(annotation.annotationType())) {
        ConstraintValidateOperate operate = operateProvider.resolve(annotation);
        constraints.add(new GenericConstraintAnnotation(annotation, element, operate));
      }
      if (isMultiConstraintAnnotation(annotation.annotationType())) {
        constraints.addAll(resolveMultiConstraintAnnotation(annotation, element));
      }
    }
  }

  private List<ConstraintAnnotation> resolveMultiConstraintAnnotation(Annotation annotation, ConstrainedElement element) {
    List<ConstraintAnnotation> constraints = new ArrayList<>();
    try {
      Method method = annotation.annotationType().getDeclaredMethod("value");
      Annotation[] composes = (Annotation[]) method.invoke(annotation);
      for (Annotation simple : composes) {
        ConstraintValidateOperate operate = operateProvider.resolve(simple);
        constraints.add(new GenericConstraintAnnotation(simple, element, operate));
      }
    } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException e) {
      return constraints;
    }
    return constraints;
  }

  private boolean hasConstraintAnnotation(Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			if (isConstraintAnnotation(annotation.annotationType())
							|| annotation instanceof Spread
							|| annotation instanceof Cascade) {
				return true;
			}
		}
    return false;
  }

  private boolean hasMultiConstraintAnnotation(Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			if (isMultiConstraintAnnotation(annotation.annotationType())) {
				return true;
			}
		}
    return false;
  }

  private boolean isConstraintAnnotation(Class<? extends Annotation> annotationType) {
    return annotationType.isAnnotationPresent(Valid.class);
  }

  private boolean isMultiConstraintAnnotation(Class<? extends Annotation> annotationType) {
    Method method = null;
    try {
      method = annotationType.getDeclaredMethod("value");
    } catch (NoSuchMethodException | SecurityException | IllegalArgumentException e) {
      return false;
    }
    Class<?> returnType = method.getReturnType();
    if (!returnType.isArray() || !returnType.getComponentType().isAnnotation()) {
      return false;
    }
    @SuppressWarnings("unchecked")
    Class<? extends Annotation> componentType = (Class<? extends Annotation>) returnType.getComponentType();
    if (isConstraintAnnotation(componentType)) {
      return true;
    } else {
      return false;
    }
  }

}
