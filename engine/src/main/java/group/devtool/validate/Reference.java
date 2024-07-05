/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate;

import java.lang.annotation.*;
import group.devtool.validate.Reference.List;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.PARAMETER,
    ElementType.TYPE_USE,
    ElementType.FIELD
})
@Repeatable(List.class)
@Valid(operate = Reference.ReferConstraintValidateOperate.class)
public @interface Reference {

  String name();

  String message() default "必须为空";

  public static class ReferConstraintValidateOperate implements ConstraintValidateOperate {

    @Override
    public boolean isValid(Object value, Annotation annotation, ParameterValidateContext validateContext) {
      Reference refer = (Reference) annotation;
      String name = refer.name();
      validateContext.addReferValue(name, value);
      return true;
    }

  }

  @Target({ ElementType.PARAMETER,
      ElementType.TYPE_USE,
      ElementType.FIELD
  })
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  public @interface List {
    Reference[] value();
  }
}
