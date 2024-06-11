package group.devtool.validate;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 方法/构造器参数校验服务
 * 
 * 具体实例：
 * 代码：
 * <code>
 * 
 * class User { @NotNull String name; }
 * 
 * public void validate(@NotNull List<@NotNull Map<@NotNull String, @NotNull @Valid User>> arg0, ...) {}
 * 
 * public void validate(@Cascade User arg0, ...) {}
 * 
 * </code>
 * 
 * validate方法校验结果如下：
 * 1. 如果args参数为空， 返回：arg0 不能为空
 * 2. 如果args参数中存在为空的元素， 返回：arg0.<list.E> 不能为空
 * 3. 如果args参数中元素的键为空， 返回：arg0.<list.E>[0].<map.K> 不能为空
 * 4. 如果args参数中元素的值为空， 返回：arg0.<list.E>[0].<map.V> 不能为空
 * 5. 如果args参数中元素值User对象name字段为空， 返回：arg0.<list.E>[0].<map.V>.<user>[name]不能为空
 * 
 */
public interface ParameterValidateService {

  default Executable get(Class<?> clazz, String methodName, String... annotationName)
      throws ParameterValidateException {

    String targetName = null;
    if (null != annotationName && annotationName.length != 0) {
      if (annotationName.length != 1) {
        throw new IllegalArgumentException("仅支持一个executableAnnotationName参数");
      }
      targetName = annotationName[0];
    }

    Executable target = null;

    Method[] methods = clazz.getDeclaredMethods();
    for (Method method : methods) {
      if (!method.getName().equals(methodName)) {
        continue;
      }
      if (null == targetName) {
        if (null != target) {
          throw new HadSameNameValidateMethodException(methodName);
        }
        target = method;
        continue;
      }
      ValidateExecutable annotation = method.getAnnotation(ValidateExecutable.class);
      if (null != annotation && targetName.equals(annotation.name())) {
        if (null != target) {
          throw new HadSameAnnotationValidateMethodException(methodName, targetName);
        }
        target = method;
      }
    }
    if (null == target) {
      throw new NotFoundValidateMethodException(methodName);
    }
    return target;
  }

  /**
   * 校验方法/构造器中参数定义的约束
   * 
   * @param executable 验证方法
   * @param parameters 参数
   * @return 校验失败
   * @throws Exception
   */
  public List<ConstraintValidateFailure> validate(Executable executable, Object[] parameters) throws Exception;

}
