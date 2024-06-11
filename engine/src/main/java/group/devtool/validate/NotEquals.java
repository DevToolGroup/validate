package group.devtool.validate;

import java.lang.annotation.*;

import group.devtool.validate.NotEquals.List;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.PARAMETER,
    ElementType.TYPE_USE,
    ElementType.FIELD
})
@Repeatable(List.class)
@Valid(operate = NotEquals.NotEqualsConstraintValidateOperate.class)
public @interface NotEquals {

  String name() default "";

  String message() default "长度不能为空";

  boolean isCondition() default false;

  String useCondition() default "";

  String reference() default "";

  public static class NotEqualsConstraintValidateOperate implements ConstraintValidateOperate {

    @Override
    public boolean isValid(Object value, Annotation annotation, ParameterValidateContext validateContext) {
      if (null == value) {
        return false;
      }
      NotEquals eqAnnotation = (NotEquals) annotation;
      if (!"".equals(eqAnnotation.reference())) {
        Object eqVal = validateContext.getReferValue(eqAnnotation.reference());
        return !eqVal.equals(value);
      }
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
    NotEquals[] value();
  }
}