/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate;

import java.lang.annotation.*;
import java.math.BigDecimal;

import group.devtool.validate.Max.List;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.PARAMETER,
    ElementType.TYPE_USE,
    ElementType.FIELD
})
@Repeatable(List.class)
@Valid(operate = Max.MaxConstraintValidateOperate.class)
public @interface Max {

  String name() default "";

  String message() default "参数大于最大值";

  boolean isCondition() default false;

  String useCondition() default "";

  String value() default "0";

  public static class MaxConstraintValidateOperate implements ConstraintValidateOperate {

    @Override
    public boolean isValid(Object value, Annotation annotation, ParameterValidateContext validateContext) {
      if (value == null) {
        return true;
      }
      Max max = (Max) annotation;
      try {
        return new BigDecimal(value.toString()).compareTo(new BigDecimal(max.value())) != 1;
      } catch (NumberFormatException nfe) {
        return false;
      }
    }

  }

  @Target({ ElementType.PARAMETER,
      ElementType.TYPE_USE,
      ElementType.FIELD
  })
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  public @interface List {
    Max[] value();
  }
}
