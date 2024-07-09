package group.devtool.validate.documentation;

import group.devtool.validate.engine.ConstraintValidateFailure;
import group.devtool.validate.engine.ParameterValidateService;
import group.devtool.validate.engine.ParameterValidateServiceImpl;

import java.lang.reflect.Executable;
import java.util.List;

public class ValidateDependenceExample {

	public static void main(String[] args) throws Exception {
		ValidateDependenceExample validateCollection = new ValidateDependenceExample();
		validateCollection.validate();
	}

	public void validate() throws Exception {
		// 初始化校验服务，在Spring的上下文中可以作为单例使用
		ParameterValidateService service = new ParameterValidateServiceImpl(true);

		// 获取需要校验参数的方法
		Executable method = service.get(DependenceExample.class, "validate");

		// 1. 执行方法验证元素级联约束
		// 1.1 验证大陆
		DependenceExample example1 = new DependenceExample();
		example1.setIdType("ID");
		example1.setIdNo("daLu"); // 不符合ID的规则

		List<ConstraintValidateFailure> cFailures1 = service.validate(method, new Object[]{example1});
		System.out.println(cFailures1);

		// 1.2 验证香港
		DependenceExample example2 = new DependenceExample();
		example2.setIdType("HK");
		example2.setIdNo("123"); // 不符合HK的规则

		List<ConstraintValidateFailure> cFailures2 = service.validate(method, new Object[]{example2});
		System.out.println(cFailures2);

		// 1.3 验证澳门
		DependenceExample example3 = new DependenceExample();
		example3.setIdType("MC");
		example3.setIdNo("123adc"); // 不符合ID的规则

		List<ConstraintValidateFailure> cFailures3 = service.validate(method, new Object[]{example3});
		System.out.println(cFailures3);
	}
}
