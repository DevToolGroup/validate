/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate.engine;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;

public interface ConstrainedElement {

  Annotation[] getAnnotations();

  AnnotatedType getAnnotatedType();

  public boolean isAnnotationPresent(Class<? extends Annotation> annotation);

  Type getType();

  boolean isArray();

  ConstrainedElementLocation getLocation();

}
