package group.devtool.validate;

import java.lang.annotation.*;
import group.devtool.validate.NotBlank.List;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.PARAMETER,
    ElementType.TYPE_USE,
    ElementType.FIELD
})
@Repeatable(List.class)
@Valid(operate = NotBlank.NotBlankConstraintValidateOperate.class)
public @interface NotBlank {

  String name() default "";

  String message() default "字符串不能为空";

  boolean isCondition() default false;

  String useCondition() default "";

  public static class NotBlankConstraintValidateOperate implements ConstraintValidateOperate {

    @Override
    public boolean isValid(Object value, Annotation annotation, ParameterValidateContext validateContext) {
      if (null == value) {
        return false;
      }
      if (value instanceof CharSequence) {
        return ((CharSequence) value).toString().trim().length() != 0;
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
    NotBlank[] value();
  }
}