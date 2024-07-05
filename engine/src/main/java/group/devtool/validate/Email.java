/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate;

import java.lang.annotation.*;
import java.util.regex.Matcher;

import group.devtool.validate.Email.List;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.PARAMETER,
    ElementType.TYPE_USE,
    ElementType.FIELD
})
@Repeatable(List.class)
@Valid(operate = Email.EmailPatternConstraintValidateOperate.class)
public @interface Email {

  String name() default "";

  String message() default "内容不符合邮箱格式";

  boolean isCondition() default false;

  String useCondition() default "";

  String regexp() default "";

  public static class EmailPatternConstraintValidateOperate implements ConstraintValidateOperate {

    private static final String LOCAL_PART_ATOM = "[a-z0-9!#$%&'*+/=?^_`{|}~\u0080-\uFFFF-]";
    private static final String LOCAL_PART_INSIDE_QUOTES_ATOM = "(?:[a-z0-9!#$%&'*.(),<>\\[\\]:;  @+/=?^_`{|}~\u0080-\uFFFF-]|\\\\\\\\|\\\\\\\")";
    private static final java.util.regex.Pattern LOCAL_PART_PATTERN = java.util.regex.Pattern.compile(
        "(?:" + LOCAL_PART_ATOM + "+|\"" + LOCAL_PART_INSIDE_QUOTES_ATOM + "+\")" +
            "(?:\\." + "(?:" + LOCAL_PART_ATOM + "+|\"" + LOCAL_PART_INSIDE_QUOTES_ATOM + "+\")" + ")*");

    @Override
    public boolean isValid(Object value, Annotation annotation, ParameterValidateContext validateContext) {
      if (null == value) {
        return false;
      }
      if (!(value instanceof CharSequence)) {
        return false;
      }

      Matcher localMatcher = LOCAL_PART_PATTERN.matcher((CharSequence) value);
      if (!localMatcher.matches()) {
        return false;
      }

      Email email = (Email) annotation;
      if (email.regexp().length() == 0) {
        return true;
      }

      java.util.regex.Pattern compilePattern = java.util.regex.Pattern.compile(email.regexp());
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
    Email[] value();
  }
}
