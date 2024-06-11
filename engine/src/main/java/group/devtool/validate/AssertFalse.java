package group.devtool.validate;

import java.lang.annotation.*;

import group.devtool.validate.AssertFalse.List;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.PARAMETER,
    ElementType.TYPE_USE,
    ElementType.FIELD
})
@Repeatable(List.class)
@Valid(operate = AssertFalse.FalseConstraintValidateOperate.class)
public @interface AssertFalse {

  String name() default "";

  String message() default "Boolean值必须为假";

  boolean isCondition() default false;

  String useCondition() default "";

  public static class FalseConstraintValidateOperate implements ConstraintValidateOperate {

    @Override
    public boolean isValid(Object value, Annotation annotation, ParameterValidateContext validateContext) {
      return null == value && (value instanceof Boolean && !(Boolean) value);
    }

  }

  @Target({ ElementType.PARAMETER,
      ElementType.TYPE_USE,
      ElementType.FIELD
  })
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  public @interface List {
    AssertFalse[] value();
  }
}
