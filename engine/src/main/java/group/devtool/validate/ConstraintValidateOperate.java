/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate;

import java.lang.annotation.Annotation;

public interface ConstraintValidateOperate {

  boolean isValid(Object value, Annotation annotation, ParameterValidateContext validateContext);

}
