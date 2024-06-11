package group.devtool.validate;

import java.lang.annotation.Annotation;

public interface ConstraintValidateOperate {

  boolean isValid(Object value, Annotation annotation, ParameterValidateContext validateContext);

}
