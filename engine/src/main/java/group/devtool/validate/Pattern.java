/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate;

import java.lang.annotation.*;
import java.util.regex.Matcher;
import group.devtool.validate.Pattern.List;


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.PARAMETER,
    ElementType.TYPE_USE,
    ElementType.FIELD
})
@Repeatable(List.class)
@Valid(operate = Pattern.PatternConstraintValidateOperate.class)
public @interface Pattern {

  String name() default "";

  String message() default "内容不符合要求";

  boolean isCondition() default false;

  String useCondition() default "";

  String regexp();

  public static class PatternConstraintValidateOperate implements ConstraintValidateOperate {

    @Override
    public boolean isValid(Object value, Annotation annotation, ParameterValidateContext validateContext) {
      if (null == value) {
        return false;
      }
      if (!(value instanceof CharSequence)) {
        return false;
      }

      Pattern pattern = (Pattern) annotation;
      java.util.regex.Pattern compilePattern = java.util.regex.Pattern.compile(pattern.regexp());
      Matcher matcher = compilePattern.matcher((CharSequence) value);
      return matcher.matches();
    }

  }

  @Target({ ElementType.PARAMETER,
      ElementType.TYPE_USE,
      ElementType.FIELD
  })
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  public @interface List {
    Pattern[] value();
  }
}
