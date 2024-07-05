/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate;

import java.lang.annotation.*;

import group.devtool.validate.AssertTrue.List;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.PARAMETER,
    ElementType.TYPE_USE,
    ElementType.FIELD
})
@Repeatable(List.class)
@Valid(operate = AssertTrue.TrueConstraintValidateOperate.class)
public @interface AssertTrue {

  String name() default "";

  String message() default "Boolean值必须为真";

  boolean isCondition() default false;

  String useCondition() default "";

  public static class TrueConstraintValidateOperate implements ConstraintValidateOperate {

    @Override
    public boolean isValid(Object value, Annotation annotation, ParameterValidateContext validateContext) {
      return null != value && (value instanceof Boolean && (Boolean) value);
    }

  }

  @Target({ ElementType.PARAMETER,
      ElementType.TYPE_USE,
      ElementType.FIELD
  })
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  public @interface List {
    AssertTrue[] value();
  }
}
