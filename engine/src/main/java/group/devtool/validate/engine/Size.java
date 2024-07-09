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

import group.devtool.validate.engine.Size.List;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.PARAMETER,
    ElementType.TYPE_USE,
    ElementType.FIELD
})
@Repeatable(List.class)
@Valid(operate = Size.SizeConstraintValidateOperate.class)
public @interface Size {

  String name() default "";

  String message() default "大小不符合要求";

  boolean isCondition() default false;

  String useCondition() default "";

  int min() default 0;

  int max() default Integer.MAX_VALUE;

  public static class SizeConstraintValidateOperate implements ConstraintValidateOperate {

    @Override
    public boolean isValid(Object value, Annotation annotation, ParameterValidateContext validateContext) {
      if (null == value) {
        return true;
      }
      Size size = (Size) annotation;

      Integer length;

      if (value.getClass().isArray()) {
        length = ((Object[]) value).length;
      } else if (value instanceof Map) {
        length = ((Map<?, ?>) value).size();
      } else if (value instanceof Collection) {
        length = ((Collection<?>) value).size();
      } else if (value instanceof CharSequence) {
        length = ((CharSequence) value).length();
      } else {
        length = null;
      }
      return length != null && length >= size.min() && length <= size.max();
    }

  }

  @Target({ ElementType.PARAMETER,
      ElementType.TYPE_USE,
      ElementType.FIELD
  })
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  public @interface List {
    Size[] value();
  }
}
