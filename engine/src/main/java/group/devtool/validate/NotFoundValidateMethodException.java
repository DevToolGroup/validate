package group.devtool.validate;


public class NotFoundValidateMethodException extends ParameterValidateException {

  public NotFoundValidateMethodException(String methodName) {
    super("类内部不存在名称为" + methodName + "的方法");
  }

}
