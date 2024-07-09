package group.devtool.validate.documentation;

import group.devtool.validate.engine.ParameterValidateService;
import group.devtool.validate.engine.ParameterValidateServiceImpl;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

public class ExampleConstraintValidateOperateProviderInject {

	public static void main(String[] args) {
		// 初始化自定义约束注解
		List<Class<? extends Annotation>> annotations = Arrays.asList(CustomConstraintExample.class);

		// 注入自定义约束，初始化参数校验服务
		ParameterValidateService service = new ParameterValidateServiceImpl(true, null, annotations);
		// ... 执行校验 ...
	}

}
