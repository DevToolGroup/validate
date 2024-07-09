/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate.engine;

import java.lang.annotation.*;

import group.devtool.validate.engine.Equals.List;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.PARAMETER,
    ElementType.TYPE_USE,
    ElementType.FIELD
})
@Valid(operate = Equals.EqualsConstraintValidateOperate.class)
@Repeatable(List.class)
public @interface Equals {

  String name() default "";

  String message() default "对象相等";

  boolean isCondition() default false;

  String useCondition() default "";

  String reference() default "";

  String value() default "";

  public static class EqualsConstraintValidateOperate implements ConstraintValidateOperate {

    @Override
    public boolean isValid(Object value, Annotation annotation, ParameterValidateContext validateContext) {
      if (null == value) {
        return false;
      }
      Equals eqAnnotation = (Equals) annotation;
      if (!eqAnnotation.reference().isEmpty()) {
        Object eqVal = validateContext.getReferValue(eqAnnotation.reference());
        return eqVal.equals(value);
      }
      if (value instanceof String && !eqAnnotation.value().isEmpty()) {
        return ((String)value).equals(eqAnnotation.value());
      }
      return false;
    }

  }

  @Target({ ElementType.PARAMETER,
      ElementType.TYPE_USE,
      ElementType.FIELD
  })
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  public @interface List {
    Equals[] value();
  }

}
