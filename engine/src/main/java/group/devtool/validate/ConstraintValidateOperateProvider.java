package group.devtool.validate;

import java.lang.annotation.Annotation;

/**
 * 根据注解类型提供约束校验方法
 */
public interface ConstraintValidateOperateProvider {

  /**
   * 根据注解类型提供约束校验方法
   * 
   * @param annotationType 注解类型
   * @return 约束校验方法
   */
  ConstraintValidateOperate resolve(Annotation annotationType);

}
