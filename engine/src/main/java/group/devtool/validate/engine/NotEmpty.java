/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate.engine;

import java.lang.annotation.*;
import java.util.Collection;
import java.util.Map;

import group.devtool.validate.engine.NotEmpty.List;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.PARAMETER,
    ElementType.TYPE_USE,
    ElementType.FIELD
})
@Repeatable(List.class)
@Valid(operate = NotEmpty.NotEmptyConstraintValidateOperate.class)
public @interface NotEmpty {

  String name() default "";

  String message() default "长度不能为空";

  boolean isCondition() default false;

  String useCondition() default "";

  public static class NotEmptyConstraintValidateOperate implements ConstraintValidateOperate {

    @Override
    public boolean isValid(Object value, Annotation annotation, ParameterValidateContext validateContext) {
      if (null == value) {
        return false;
      }
      if (value.getClass().isArray()) {
        return ((Object[]) value).length != 0;
      }
      if (value instanceof Map) {
        return ((Map<?, ?>) value).size() != 0;
      }
      if (value instanceof Collection) {
        return ((Collection<?>) value).size() != 0;
      }
      if (value instanceof CharSequence) {
        return ((CharSequence) value).length() != 0;
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
    NotEmpty[] value();
  }
}
