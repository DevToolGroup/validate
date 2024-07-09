package group.devtool.validate.documentation;

import group.devtool.validate.engine.*;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.List;

public class ValidateCollectionExample {

	public static void main(String[] args) throws Exception {
		ValidateCollectionExample validateCollection = new ValidateCollectionExample();
		validateCollection.validate();
	}

	public void validate() throws Exception {
		// 初始化校验服务，在Spring的上下文中可以作为单例使用
		ParameterValidateService service = new ParameterValidateServiceImpl(true);

		// 获取需要校验参数的方法
		Executable method = service.get(CollectionExample.class, "validate");

		// 1. 执行方法验证元素不能为null的约束
		List<CollectionExample> param1 = new ArrayList<>();
		param1.add(null);
		List<ConstraintValidateFailure> elFailures = service.validate(method, new Object[]{param1});
		System.out.println(elFailures);

		// 2. 执行方法验证元素级联约束
		CollectionExample collection = new CollectionExample();
		collection.setAge(0); // 年龄不符合
		collection.setName(null); // 名字不符合
		collection.setSex("N"); // 性别不符合

		List<CollectionExample> param2 = new ArrayList<>();
		param2.add(collection);
		List<ConstraintValidateFailure> cFailures = service.validate(method, new Object[]{param2});
		System.out.println(cFailures);
	}
}
