package group.devtool.validate.documentation;

import group.devtool.validate.engine.Cascade;
import group.devtool.validate.engine.Equals;
import group.devtool.validate.engine.Pattern;

public class DependenceExample {

	@Equals(name = "id", value = "ID", isCondition = true)
	@Equals(name = "hk", value = "HK", isCondition = true)
	@Equals(name = "mc", value = "MC", isCondition = true)
	private String idType;

	@Pattern(useCondition = "id", regexp = "\\d+")
	@Pattern(useCondition = "hk", regexp = "\\[a-z]+")
	@Pattern(useCondition = "mc", regexp = "^[a-z].*\\d$")
	private String idNo;

	public void validate(@Cascade DependenceExample example) {

	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
}
