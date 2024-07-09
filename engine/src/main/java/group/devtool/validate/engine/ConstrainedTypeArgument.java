/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate.engine;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedArrayType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;

public class ConstrainedTypeArgument implements ConstrainedElement {

  private ConstrainedElement element;

  private Type containerType;

  private AnnotatedType annotatedType;

  private int parameterTypeIndex;

  private Type variable;

  public ConstrainedTypeArgument(ConstrainedElement element,
      Type containerType,
      int parameterTypeIndex,
      Type variable,
      AnnotatedType annotatedType) {
    this.element = element;
    this.containerType = containerType;
    this.parameterTypeIndex = parameterTypeIndex;
    this.variable = variable;
    this.annotatedType = annotatedType;
  }

  @Override
  public Annotation[] getAnnotations() {
    if (annotatedType instanceof AnnotatedArrayType) {
      return ((AnnotatedArrayType) annotatedType).getAnnotatedGenericComponentType().getAnnotations();
    }
    return annotatedType.getAnnotations();
  }

  @Override
  public AnnotatedType getAnnotatedType() {
    return annotatedType;
  }

  @Override
  public boolean isAnnotationPresent(Class<? extends Annotation> annotation) {
    if (annotatedType instanceof AnnotatedArrayType) {
      return ((AnnotatedArrayType) annotatedType).getAnnotatedGenericComponentType().isAnnotationPresent(annotation);
    }
    return annotatedType.isAnnotationPresent(annotation);
  }

  public int getIndexTypeParameter() {
    return parameterTypeIndex;
  }

  @Override
  public Type getType() {
    return annotatedType.getType();
  }

  @Override
  public boolean isArray() {
    return annotatedType instanceof AnnotatedArrayType;
  }

  @Override
  public ConstrainedElementLocation getLocation() {
    return new ConstrainedTypeArgumentLocation(containerType, variable, element.getLocation());
  }

  public static class ConstrainedTypeArgumentLocation implements ConstrainedElementLocation {

    private Type containerType;

    private Type variable;

    private ConstrainedElementLocation parent;

    public ConstrainedTypeArgumentLocation(Type containerType, Type variable,
        ConstrainedElementLocation parent) {
      this.containerType = containerType;
      this.variable = variable;
      this.parent = parent;
    }

    @Override
    public String location(ConstraintValueContext context) {
      String typeName = containerType.getTypeName();
      String location = parent.location(context.getParent())
          + "."
          + "<"
          + typeName.substring(typeName.lastIndexOf(".") + 1)
          + "." + variable.getTypeName()
          + ">";

      if (context instanceof ConstraintTypeArgumentValueContext) {
        ConstraintTypeArgumentValueContext argumentContext = (ConstraintTypeArgumentValueContext) context;
        Integer index = argumentContext.getIndex();
        if (null != index) {
          location += "["
              + index
              + "]";
        }
      }
      return location;
    }

  }

}
