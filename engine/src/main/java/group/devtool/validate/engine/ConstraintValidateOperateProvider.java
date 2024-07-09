/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate.engine;

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
