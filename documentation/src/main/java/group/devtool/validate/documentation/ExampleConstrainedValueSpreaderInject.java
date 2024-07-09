package group.devtool.validate.documentation;

import group.devtool.validate.engine.*;

import java.util.Arrays;
import java.util.List;

public class ExampleConstrainedValueSpreaderInject {

	public static void main(String[] args) {
		// 初始化容器解析器
		List<ConstrainedValueSpreader> custom = Arrays.asList(new ExampleConstrainedValueSpreader());

		// 注入默认的容器解析器
		ConstrainedValueSpreadProviderImpl provider = new ConstrainedValueSpreadProviderImpl(custom);

		// 初始化参数校验服务
		ParameterValidateService service = new ParameterValidateServiceImpl(provider, null, true);
		// ... 执行校验 ...
	}
}
