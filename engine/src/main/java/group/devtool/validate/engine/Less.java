/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate.engine;

import java.lang.annotation.*;
import java.math.BigDecimal;

import group.devtool.validate.engine.Less.List;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.PARAMETER,
    ElementType.TYPE_USE,
    ElementType.FIELD
})
@Repeatable(List.class)
@Valid(operate = Less.LessConstraintValidateOperate.class)
public @interface Less {

  String name() default ""; // 在条件模式下，name返回结果不能为空

  String message() default "数值类型小于refer代表的值";

  boolean isCondition() default false;

  String useCondition() default "";

  String reference();

  public static class LessConstraintValidateOperate implements ConstraintValidateOperate {

    @Override
    public boolean isValid(Object value, Annotation annotation, ParameterValidateContext validateContext) {
      if (!(value instanceof Number)) {
        return false;
      }
      Less less = (Less) annotation;
      Object referValue = validateContext.getReferValue(less.reference());
      if (null == referValue || !(referValue instanceof Number)) {
        return false;
      }
      Number nv = (Number) value;
      Number nrv = (Number) referValue;
      return new BigDecimal(nv.toString()).compareTo(new BigDecimal(nrv.toString())) < 0;
    }

  }

  @Target({ ElementType.PARAMETER,
      ElementType.TYPE_USE,
      ElementType.FIELD
  })
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  public @interface List {
    Less[] value();
  }
}
