/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate;

import java.lang.reflect.Executable;
import java.util.List;


/**
 * 参数约束注解解析器
 */
public interface ConstraintAnnotationProvider {

  /**
   * 解析方法参数包含的约束注解
   * 
   * @param executable 可执行方法
   * @return 约束注解
   * @throws Exception 
   */
  List<List<ConstraintAnnotation>> resolveAnnotations(Executable executable) throws Exception;

}
