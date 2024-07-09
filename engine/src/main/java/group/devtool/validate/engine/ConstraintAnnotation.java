/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate.engine;

import java.util.List;

/**
 * 约束注解
 */
public interface ConstraintAnnotation {

  /**
   * @return 约束的元素
   */
  ConstrainedElement getElement();

  /**
   * 执行约束校验
   * 
   * @param context         包含元素值相关内容的上下文
   * @param validateContext 校验上下文
   * @return 失败约束集合
   * @throws Exception 未知异常
   */
  List<ConstraintValidateFailure> validate(ConstraintValueContext context, ParameterValidateContext validateContext)
      throws Exception;

}
