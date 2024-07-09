package group.devtool.validate.documentation;

import group.devtool.validate.engine.*;

import java.lang.reflect.Executable;
import java.util.List;

public class ValidateSimpleExample {

	public static void main(String[] args) throws Exception {
		ValidateSimpleExample validateSimple = new ValidateSimpleExample();
		validateSimple.validate();
	}

	public void validate() throws Exception {
		// 初始化校验服务，在Spring的上下文中可以作为单例使用
		ParameterValidateService service = new ParameterValidateServiceImpl(true);

		// 获取需要校验参数的方法
		Executable method = service.get(SimpleExample.class, "validate");

		SimpleExample simple = new SimpleExample();

		simple.setAge(0); // 年龄不符合
		simple.setName(null); // 名字不符合
		simple.setSex("N"); // 性别不符合

		// 执行方法参数校验
		List<ConstraintValidateFailure> failures = service.validate(method, new Object[]{simple});
		System.out.println(failures);
	}

}
