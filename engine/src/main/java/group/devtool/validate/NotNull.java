package group.devtool.validate;

import java.lang.annotation.*;

import group.devtool.validate.NotNull.List;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.PARAMETER,
    ElementType.TYPE_USE,
    ElementType.FIELD
})
@Repeatable(List.class)
@Valid(operate = NotNull.NotNullConstraintValidateOperate.class)
public @interface NotNull {

  String name() default "";

  String message() default "不可以为空";

  boolean isCondition() default false;

  String useCondition() default "";

  public static class NotNullConstraintValidateOperate implements ConstraintValidateOperate {

    @Override
    public boolean isValid(Object value, Annotation annotation, ParameterValidateContext validateContext) {
      return null != value;
    }

  }

  @Target({ ElementType.PARAMETER,
      ElementType.TYPE_USE,
      ElementType.FIELD
  })
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  public @interface List {
    NotNull[] value();
  }
}