### Validate 参数校验组件
[![](https://img.shields.io/badge/官网-DevTool-green)](http://devtoolgroup.github.io)
![](https://img.shields.io/badge/语言-Java-blue)
![](https://img.shields.io/badge/许可证-GPL-red)
![](https://img.shields.io/badge/版本-1.0_SNAPSHOT-orange)
![](https://img.shields.io/badge/代码-3.7K-green)

validate 是一个基于Java注解的方法参数校验框架，相较于优秀的Hibernate Validator，增加了规则依赖校验，主要用来解决以下场景：
* 证件类型是身份证，证件编号必须满足身份证要求，证件类型是企业信用编码，证件编号必须满足统一信用代码的样式。
* 联系方式为邮箱时，号码必须满足邮箱格式，联系方式为手机时，号码必须满足手机号格式。
* 开始时间需要小于等于结束时间。
* 其他场景等。



### 简要说明
#### 许可证
    GPL 3.0

#### Java版本
    Java 8+

#### 功能特点
validate 支持的校验类型主要分三大类：
- 单字段校验
- 规则依赖校验
- 字段依赖校验

### 快速开始
当前版本处于SNAPSHOT版本，可直接clone代码，本地打包使用。

有问题可以进入[官网](http://devtoolgroup.github.io)加入交流群。

如果使用了麻烦给个star以示认可，万分感谢。

### 使用示例
#### 普通参数校验

示例程序：

```java
public class SimpleExample {

	@NotNull
	private String name;

	@Pattern(regexp = "F|M")
	private String sex;

	@Min(value = "1")
	private Integer age;

	// 待校验方法
	public void validate(@Cascade SimpleExample simple) {

	}
	// setter, getter
}
```
`@Cascade` 注解声明参数需要级联校验，`@NotNull` 注解声明字段不能为空，`@Pattern` 注解声明字段内容满足正则表达式要求，`@Min` 注解声明字段的最小值。

定义如下异常参数
```java
SimpleExample simple = new SimpleExample();
simple.setAge(0); // 年龄不符合
simple.setName(null); // 名字不符合
simple.setSex("N"); // 性别不符合

```
初始化ParameterValidateService对象验证参数约束，具体方法如下：
```java
// 初始化校验服务，在Spring的上下文中可以作为单例使用
ParameterValidateService service = new ParameterValidateServiceImpl(true);

// 获取需要校验参数的方法
Executable method = service.get(SimpleExample.class, "validate");

 // 性别不符合

// 执行方法参数校验
List<ConstraintValidateFailure> failures = service.validate(method, new Object[]{simple});
System.out.println(failures);
```

验证结果：
```json
[
  {message: 不可以为空, annotation: NotNull,  location: arg0.<Simple>[name]},
  {message: 内容不符合要求, annotation: Pattern, location: arg0.<Simple>[sex]},
  {message: 参数小于最小值, annotation: Min, location: arg0.<Simple>[age]}
]
```

验证结果说明：

* 首先，验证结果对应一个列表，列表中的每项对应一个不满足条件的约束。
* 其次，验证结果的每一项分为三部分：
  * message字段表示约束注解的message()方法的返回结果，
  * annotation字段对应约束注解的名称，
  * location对应约束作用的字段的路径
  
其中location字段是每个约束对应的字段在整个对象中的位置，其格式为：<方法参数名>.<对象类型>[属性名/索引]。

- 方法参数名：arg0， arg1， arg2的形式
- 对象类型：List，Set，Map，ObjectClassName（对象类型）的形式
- 属性名/索引：分别对应普通对象的字段名，容器对象的索引下标

#### 依赖参数校验
示例代码
```java
public class DependenceExample {

	@Equals(name = "id", value = "ID", isCondition = true)
	@Equals(name = "hk", value = "HK", isCondition = true)
	@Equals(name = "mc", value = "MC", isCondition = true)
	private String idType;

	@Pattern(useCondition = "id", regexp = "^id.*")
	@Pattern(useCondition = "hk", regexp = "^hk.*")
	@Pattern(useCondition = "mc", regexp = "^mc.*")
	private String idNo;

	public void validate(@Cascade DependenceExample example) {

	}
	// setter, getter
}
```
注解说明：
- 当 `idType` 的值等于 `ID`（中国大陆）时， `idNo` 的值必须满足以 `id`开头
- 当 `idType` 的值等于 `HK`（中国香港）时， `idNo` 的值必须满足以 `hk`开头
- 当 `idType` 的值等于 `MC`（中国澳门）时， `idNo` 的值必须满足以 `mc`开头

定义以下三类数据

证件类型为ID的
```java
DependenceExample example1 = new DependenceExample();
example1.setIdType("ID");
example1.setIdNo("daLu"); // 不符合ID的规则
```

证件类型为HK的
```java
DependenceExample example2 = new DependenceExample();
example2.setIdType("HK");
example2.setIdNo("123"); // 不符合HK的规则
```

证件类型为MC的
```java
DependenceExample example3 = new DependenceExample();
example3.setIdType("MC");
example3.setIdNo("123adc"); 
```

验证结果如下：
 ```json
// 1. 验证大陆
[{message: 内容不符合要求,annotation: Pattern,location: arg0.<DependenceExample>[idNo]}]

// 2 验证香港
[{message: 内容不符合要求,annotation: Pattern,location: arg0.<DependenceExample>[idNo]}]

// 3 验证澳门
[{message: 内容不符合要求,annotation: Pattern,location: arg0.<DependenceExample>[idNo]}]
 
 ```

### 沟通交流
[***交流地址***](http://devtoolgroup.github.io)

如果你也是一名热爱代码的朋友，非常非常欢迎你的加入一起讨论学习，作者也是一名热爱代码的小白，期待你的加入。

### 后续迭代
重点任务：
1. 循环嵌套问题解决
2. 性能验证

