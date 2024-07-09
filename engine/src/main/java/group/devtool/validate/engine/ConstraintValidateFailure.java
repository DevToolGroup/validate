/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate.engine;


/**
 * 校验失败信息
 */
public interface ConstraintValidateFailure {

  String getTemplateMessage();

  String getLocation();

}
