/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate;

import java.lang.annotation.*;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

import group.devtool.validate.Future.List;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.PARAMETER,
    ElementType.TYPE_USE,
    ElementType.FIELD
})
@Repeatable(List.class)
@Valid(operate = Future.FutureConstraintValidateOperate.class)
public @interface Future {

  String name() default ""; // 在条件模式下，name返回结果不能为空

  String message() default "参数必须为相同日期类型（date/calendar/instant），且日期必须在refer日期之后";

  boolean isCondition() default false;

  String useCondition() default "";

  String reference();

  public static class FutureConstraintValidateOperate implements ConstraintValidateOperate {

    @Override
    public boolean isValid(Object value, Annotation annotation, ParameterValidateContext validateContext) {
      if (!isTime(value)) {
        return false;
      }
      Future future = (Future) annotation;
      Object referValue = validateContext.getReferValue(future.reference());
      if (!isTime(referValue)) {
        return false;
      }

      if (isDate(value, referValue)) {
        Date dv = (Date) value;
        Date dr = (Date) referValue;
        return dv.compareTo(dr) > 0;

      } else if (isCalendar(value, referValue)) {
        Calendar cv = (Calendar) value;
        Calendar cr = (Calendar) referValue;
        return cv.compareTo(cr) > 0;

      } else if (isInstant(value, referValue)) {
        Instant cv = (Instant) value;
        Instant cr = (Instant) referValue;
        return cv.compareTo(cr) > 0;

      } else {
        return false;
      }
    }

    private boolean isInstant(Object value, Object referValue) {
      return value instanceof Instant && referValue instanceof Instant;
    }

    private boolean isCalendar(Object value, Object referValue) {
      return value instanceof Calendar && referValue instanceof Calendar;

    }

    private boolean isDate(Object value, Object referValue) {
      return value instanceof Date && referValue instanceof Date;
    }

    private boolean isTime(Object value) {
      return null != value && (value instanceof Date
          || value instanceof Calendar
          || value instanceof Instant);
    }

  }

  @Target({ ElementType.PARAMETER,
      ElementType.TYPE_USE,
      ElementType.FIELD
  })
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  public @interface List {
    Future[] value();
  }
}
