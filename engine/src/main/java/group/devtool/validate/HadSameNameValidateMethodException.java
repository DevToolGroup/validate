package group.devtool.validate;

public class HadSameNameValidateMethodException extends ParameterValidateException {

  public HadSameNameValidateMethodException(String methodName) {
    super("存在多个名称为：" + methodName + "的方法，请使用@ValidateExecutable(name=\"name\")指定具体的方法");
  }

}
