package group.devtool.validate.documentation;

import group.devtool.validate.engine.ConstraintValidateFailure;
import group.devtool.validate.engine.ParameterValidateService;
import group.devtool.validate.engine.ParameterValidateServiceImpl;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.List;

public class ValidateArrayExample {

	public static void main(String[] args) throws Exception {
		ValidateArrayExample validateCollection = new ValidateArrayExample();
		validateCollection.validate();
	}

	public void validate() throws Exception {
		// 初始化校验服务，在Spring的上下文中可以作为单例使用
		ParameterValidateService service = new ParameterValidateServiceImpl(true);

		// 获取需要校验参数的方法
		Executable method = service.get(ArrayExample.class, "validate");

		// 1. 执行方法验证元素不能为null的约束
		List<ConstraintValidateFailure> elFailures = service.validate(method, new Object[]{new ArrayExample[]{null}});
		System.out.println(elFailures);

		// 2. 执行方法验证元素级联约束
		ArrayExample example = new ArrayExample();
		example.setAge(0); // 年龄不符合
		example.setName(null); // 名字不符合
		example.setSex("N"); // 性别不符合


		List<ConstraintValidateFailure> cFailures = service.validate(method, new Object[]{new ArrayExample[]{example}});
		System.out.println(cFailures);
	}
}
