/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate.engine;

import java.lang.reflect.Type;

/**
 * 提供容器展开方法的接口
 */
public interface ConstrainedValueSpreadProvider {

  /**
   * 根据容器的类型选择展开方式
   * 
   * @param containerType 容器类型
   * @return 容器类约束对象展开接口
   */
  ConstrainedValueSpreader resolve(Type containerType);

}
