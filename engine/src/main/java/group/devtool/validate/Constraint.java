/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = (ElementType.ANNOTATION_TYPE))
public @interface Constraint {
  
}
