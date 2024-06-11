package group.devtool.validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@code ConstraintValidateOperateProvider}
 */
public class ConstraintValidateOperateProviderImpl implements ConstraintValidateOperateProvider {

  private static List<Class<? extends Annotation>> BUILTIN;

  static {
    BUILTIN = new ArrayList<>();

    BUILTIN.add(Null.class);
    BUILTIN.add(NotNull.class);
    BUILTIN.add(NotEmpty.class);
    BUILTIN.add(NotBlank.class);
    BUILTIN.add(AssertFalse.class);
    BUILTIN.add(AssertTrue.class);
    BUILTIN.add(Min.class);
    BUILTIN.add(Max.class);
    BUILTIN.add(Size.class);
    BUILTIN.add(Pattern.class);
    BUILTIN.add(Email.class);
    BUILTIN.add(Future.class);
    BUILTIN.add(Past.class);
    BUILTIN.add(More.class);
    BUILTIN.add(Less.class);
    BUILTIN.add(Equals.class);
    BUILTIN.add(NotEquals.class);
    BUILTIN.add(Reference.class);
  }

  private Map<Class<? extends Annotation>, ConstraintValidateOperate> operates = new HashMap<>();

  public ConstraintValidateOperateProviderImpl(List<Class<? extends Annotation>> annotations) {
    register(BUILTIN);
    if (null != annotations) {
      register(annotations);
    }
  }

  private void register(List<Class<? extends Annotation>> annotations) {
    for (Class<? extends Annotation> annotationType : annotations) {
      Valid valid = annotationType.getAnnotation(Valid.class);
      operates.put(annotationType, newInstance(valid.operate()));
    }
  }

  private ConstraintValidateOperate newInstance(Class<? extends ConstraintValidateOperate> operate) {
    Constructor<? extends ConstraintValidateOperate> constructor;
    try {
      constructor = operate.getDeclaredConstructor();
      constructor.setAccessible(true);
      return constructor.newInstance();
    } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException e) {
      throw new ParameterValidateException("初始化约束校验方法失败，请检查是否校验方式是否包含无参构造器");
    }
  }

  @Override
  public ConstraintValidateOperate resolve(Annotation annotation) {
    return operates.get(annotation.annotationType());
  }

}
