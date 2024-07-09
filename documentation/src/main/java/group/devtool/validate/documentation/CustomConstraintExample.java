package group.devtool.validate.documentation;

import group.devtool.validate.engine.*;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.PARAMETER,
				ElementType.TYPE_USE,
				ElementType.FIELD
})
@Valid(operate = CustomConstraintExample.CustomConstraintValidateOperate.class)
public @interface CustomConstraintExample {

	String name() default "";

	String message() default "Boolean值必须为假";

	boolean isCondition() default false;

	String useCondition() default "";

	public static class CustomConstraintValidateOperate implements ConstraintValidateOperate {

		@Override
		public boolean isValid(Object value, Annotation annotation, ParameterValidateContext validateContext) {
			System.out.println("--custom constraint--");
			return true;
		}

	}

}
