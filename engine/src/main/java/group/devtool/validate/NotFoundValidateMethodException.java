/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate;


public class NotFoundValidateMethodException extends ParameterValidateException {

  public NotFoundValidateMethodException(String methodName) {
    super("类内部不存在名称为" + methodName + "的方法");
  }

}
