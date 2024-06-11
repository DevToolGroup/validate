package group.devtool.validate;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = (ElementType.ANNOTATION_TYPE))
@Constraint
public @interface Valid {
  
  Class<? extends ConstraintValidateOperate> operate();

}
