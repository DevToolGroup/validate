[参数校验组件]
ifndef::sourcedir[:sourcedir: ../../main/java/group/devtool/validate/documentation/]
= 参数校验组件

[[背景]]
== 背景
在日常的Java项目开发过程中是参数校验一项非常基本的任务，同时也是一项比较耗时的任务，每个项目都需要写一些相同的校验逻辑，对于单一字段的参数校验可以通过注解的方式，但是对于依赖字段的参数校验，只能通过硬编码的方式实现。

例如：
* 证件类型是身份证，证件编号必须满足身份证要求，证件类型是企业信用编码，证件编号必须满足统一信用代码的样式。
* 联系方式为邮箱时，号码必须满足邮箱格式，联系方式为手机时，号码必须满足手机号格式。
* 开始时间需要小于等于结束时间。
* 其他场景等。

基于以上场景，开发设计了 *validate* 参数校验组件，希望通过Java Annotation注解的方式，在实现单一字段参数校验的基础上，实现依赖的参数校验。
[[快速开始]]
== 快速开始
[[环境准备]]
=== 环境准备
* Java版本： JDK 8
* http://maven.apache.org/[Apache Maven]
  [[依赖配置]]

=== 依赖配置
在项目的 *pom.xml* 文件中增加以下maven配置
[source, XML]
----
<dependencies>
  <dependency>
    <groupId>group.devtool.validate.engine</groupId>
    <artifactId>engine</artifactId>
    <version>1.0-SNAPSHOT</version>
    <scope>compile</scope>
  </dependency>
</dependencies>
----

[[参数校验]]
=== 普通参数校验

示例程序：
[source, JAVA, indent=0, ]
----
include::{sourcedir}/SimpleExample.java[]
----
`@Cascade` 注解声明参数需要级联校验，`@NotNull` 注解声明字段不能为空，`@Pattern` 注解声明字段内容满足正则表达式要求，`@Min` 注解声明字段的最小值。

初始化ParameterValidateService对象验证参数约束，具体方法如下：
[source, JAVA, indent=0]
----
include::{sourcedir}/ValidateSimpleExample.java[]
----

验证结果：
[source]
----
[
{message: 不可以为空, annotation: NotNull,  location: arg0.<Simple>[name]},
{message: 内容不符合要求, annotation: Pattern, location: arg0.<Simple>[sex]},
{message: 参数小于最小值, annotation: Min, location: arg0.<Simple>[age]}
]
----
验证结果说明：

* 首先，验证结果对应一个列表，列表中的每项对应一个不满足条件的约束。
* 其次，验证结果的每一项分为三部分：
  ** 1、message字段表示约束注解的message()方法的返回结果，
  ** 2、annotation字段对应约束注解的名称，
  ** 3、location对应约束作用的字段的路径

=== 容器类型参数校验
容器类参数主要是指类似List<E>，Collection<E>, Set<E>, Map<K, V>这样结构的参数，显然，这种类型的参数校验是不需要对容器本身校验的，更多的是对容器中的元素校验。
另外，容器类型的参数校验分为两种：

1. 元素本身，例如：元素不能为空、元素只能是邮箱格式的字符串等；
2. 元素级联校验，即：将元素对象展开、校验其包含的字段是否满足约束；

示例程序：
[source, JAVA, indent=0]
----
include::{sourcedir}/CollectionExample.java[]
----
`@Spread` 注解声明展开 __容器__，对容器中的元素进行校验，`@Cascade` 注解声明展开 __容器元素__，并进行校验，`@NotNull` 注解声明字段不能为空，
`@Size` 注解声明字段的长度满足要求，`@Min` 注解声明字段的最小值。

__ `特别说明：对于容器类型的参数校验时，容器元素的类型可能是容器元素本身及其子类，因此，在处理@Cascade注解时，使用了动态解析注解的方式，即：根据实际的参数类型确定元素包含的注解` __

同样，初始化ParameterValidateService对象验证参数约束，具体方法如下：
[source, JAVA, indent=0]
----
include::{sourcedir}/ValidateCollectionExample.java[]
----

验证结果：
[source]
----
// 1. 场景1: 容器元素为null
[
{message: 不可以为空,annotation: NotNull,location: arg0.<List.E>[0]}
]

// 2. 场景2: 容器元素的字段不满足约束
[
{message: 不可以为空,annotation: NotNull,location: arg0.<List.E>[0].<Collection>[name]},
{message: 内容不符合要求,annotation: Pattern,location: arg0.<List.E>[0].<Collection>[sex]},
{message: 参数小于最小值,annotation: Min,location: arg0.<List.E>[0].<Collection>[age]}
]
----

=== 数组类型参数校验
这里把数组类型的参数校验单独出来，是因为相较于容器类型，无法通过注解的形式声明数组元素是否需要级联校验。
例如：<<public void validate(@NotNull @Spread @Cascade Collection[] param)>>
在该方法中的 `@NotNull` 注解约束，可以是针对 *param* 也可能针对 *CollectionExample* ，
因此，对于数组类型的参数校验，在出现 `@Spread` 时，`@NotNull` 约束既作用于 *param*，又作用于 *CollectionExample*。

示例程序
[source, JAVA, indent=0]
----
include::{sourcedir}/ArrayExample.java[]
----
同样，初始化ParameterValidateService对象验证参数约束，具体方法如下：
[source, JAVA, indent=0]
----
include::{sourcedir}/ValidateArrayExample.java[]
----
验证结果：
[source]
----
// 1. 场景1: 容器元素为null
[
{message: 不可以为空, annotation: NotNull, location: arg0.<array>[0]}
]

// 2. 场景2: 容器元素的字段不满足约束
[
{message: 不可以为空,annotation: NotNull,location: arg0.<array>[0].<ArrayExample>[name]},
{message: 内容不符合要求,annotation: Pattern,location: arg0.<array>[0].<ArrayExample>[sex]},
{message: 参数小于最小值,annotation: Min,location: arg0.<array>[0].<ArrayExample>[age]}
]

----
[[依赖参数校验]]
=== 依赖参数校验
[source, JAVA, indent=0]
----
include::{sourcedir}/DependenceExample.java[]
----
注解说明：
1. 当 `idType` 的值等于 `ID`（中国大陆）时， `idNo` 的值必须满足纯数字
2. 当 `idType` 的值等于 `HK`（中国香港）时， `idNo` 的值必须满足纯字母
3. 当 `idType` 的值等于 `MC`（中国澳门）时， `idNo` 的值必须满足字符开头，数字结尾
   初始化ParameterValidateService对象验证参数约束，具体方法如下：
   [source, JAVA, indent=0]
----
include::{sourcedir}/ValidateDependenceExample.java[]
----
验证结果：
[source]
----
// 1. 验证大陆
[{message: 内容不符合要求,annotation: Pattern,location: arg0.<DependenceExample>[idNo]}]

// 2 验证香港
[{message: 内容不符合要求,annotation: Pattern,location: arg0.<DependenceExample>[idNo]}]

// 3 验证澳门
[{message: 内容不符合要求,annotation: Pattern,location: arg0.<DependenceExample>[idNo]}]
----

[[程序扩展]]
== 程序扩展

[[自定义约束]]
=== 自定义约束
当前组件默认支持的约束，包括：
* `@AsserFalse`，验证字段为null或者Boolean值为假
* `@AsseTrue`，验证字段不为null或者Boolean值为真
* `@Email`，验证字段是否符合邮箱格式
* `@Equals`，验证字段是否于注解值相等或者refer值
* `@Future`，验证日期字段在refer日期之后
* `@Past`，验证日期字段在refer日期之前
* `@Less`，验证数值字段小于refer代表的值
* `@More`，验证数值字段大于refer代表的值
* `@Max`，验证字段值小于最大值
* `@Min`，验证字段值大于最小值
* `@NotBlank`，验证字段值字符串不能为空
* `@NotEmpty`，验证字段长度不能为空
* `@NotEquals`，验证字段不等于refer
* `@NotNull`，验证字段不能为空
* `@Null`，验证字段值为空
* `@Pattern`，验证字段内容是否符合表达式要求
* `@Size`，验证字段大小是否符合要求
* `@Reference`，字段值依赖

如果以上组件不满足要求，可以通过自定义注解实现特定需求，实现方法：
[source, JAVA, indent=0]
----
include::{sourcedir}/CustomConstraintExample.java[]
----
说明：

* 自定义注解需要增加 `@Valid` 注解，并实现 `ConstraintValidateOperate` 接口，例如: <<@Valid(operate = CustomConstraintExample.CustomConstraintValidateOperate.class) >>
* 自定义注解需要增加 `String name()` , `String message()`, `boolean isCondition()`, `String useCondition()` 方法

[[容器解析]]
=== 容器解析

[[失败结果]]
=== 失败结果


