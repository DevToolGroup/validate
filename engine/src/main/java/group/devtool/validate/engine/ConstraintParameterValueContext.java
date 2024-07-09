/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate.engine;

public class ConstraintParameterValueContext implements ConstraintValueContext {

  private Object object;

  private ConstraintValueContext parent;

  public ConstraintParameterValueContext(Object object, ConstraintValueContext parent) {
    this.object = object;
    this.parent = parent;
  }

  @Override
  public Object getValue() {
    return object;
  }

  @Override
  public ConstraintValueContext getParent() {
    return parent;
  }

}
