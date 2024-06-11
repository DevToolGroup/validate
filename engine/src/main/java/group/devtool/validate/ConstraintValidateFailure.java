package group.devtool.validate;


/**
 * 校验失败信息
 */
public interface ConstraintValidateFailure {

  String getTemplateMessage();

  String getLocation();

}
