package group.devtool.validate;

public class HadSameAnnotationValidateMethodException extends ParameterValidateException {

  public HadSameAnnotationValidateMethodException(String methodName, String annotationName) {
    super("存在多个注解名称为：" + annotationName + "，方法名称为：" + annotationName + " 的方法，请确保类内部方法的注解@ValidateExecutable名称唯一");
  }

}
