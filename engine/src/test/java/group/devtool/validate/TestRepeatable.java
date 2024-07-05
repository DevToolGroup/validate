/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.junit.Test;

public class TestRepeatable {

  @Equals(isCondition = true, value = "ID", name = "id")
  @Equals(isCondition = true, value = "MO", name = "mo")
  @Equals(isCondition = true, value = "HK", name = "hk")
  public String name;
  
  @Test
  public void test() throws NoSuchFieldException, SecurityException {
    Field field = TestRepeatable.class.getDeclaredField("name");
    Annotation[] annotations = field.getAnnotations();
  }

}
