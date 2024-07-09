/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate.engine;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class ConstrainedProperty implements ConstrainedElement {

  private Field field;

  private ConstrainedElement element;

  public ConstrainedProperty(ConstrainedElement element, Field field) {
    this.element = element;
    this.field = field;
  }

  @Override
  public Annotation[] getAnnotations() {
    return field.getAnnotations();
  }

  public Object getValue(Object parameter) {
    field.setAccessible(true);
    try {
      return field.get(parameter);
    } catch (IllegalArgumentException | IllegalAccessException e) {
      throw new ParameterValidateException("利用反射获取字段参数异常");
    }
  }

  @Override
  public AnnotatedType getAnnotatedType() {
    return field.getAnnotatedType();
  }

  @Override
  public boolean isAnnotationPresent(Class<? extends Annotation> annotation) {
    return field.isAnnotationPresent(annotation);
  }

  @Override
  public Type getType() {
    return field.getType();
  }

  @Override
  public boolean isArray() {
    return field.getClass().isArray();
  }

  @Override
  public ConstrainedElementLocation getLocation() {
    return new ConstrainedPropertyLocation(field, element.getLocation());
  }

  public static class ConstrainedPropertyLocation implements ConstrainedElementLocation {

    private Field field;

    private ConstrainedElementLocation parent;

    public ConstrainedPropertyLocation(Field field, ConstrainedElementLocation parent) {
      this.field = field;
      this.parent = parent;

    }

    @Override
    public String location(ConstraintValueContext context) {
      return parent.location(context.getParent()) 
      + "." 
      + "<" + field.getDeclaringClass().getSimpleName() + ">" 
      + "[" + field.getName() + "]";
    }

  }

}
