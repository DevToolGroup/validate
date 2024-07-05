/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate;

public class HadSameNameValidateMethodException extends ParameterValidateException {

  public HadSameNameValidateMethodException(String methodName) {
    super("存在多个名称为：" + methodName + "的方法，请使用@ValidateExecutable(name=\"name\")指定具体的方法");
  }

}
