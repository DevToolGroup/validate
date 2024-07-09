package group.devtool.validate.documentation;

import group.devtool.validate.engine.*;

import java.util.List;

public class ArrayExample {

	@NotNull
	private String name;

	@Pattern(regexp = "F|M")
	private String sex;

	@Min(value = "1")
	private Integer age;

	public void validate(@NotNull @Spread @Cascade ArrayExample[] argument) {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}
