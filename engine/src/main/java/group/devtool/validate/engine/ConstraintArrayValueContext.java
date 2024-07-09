/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate.engine;

public class ConstraintArrayValueContext implements ConstraintValueContext {

  private Object value;

  private ConstraintValueContext parent;

  private Integer index;

  public ConstraintArrayValueContext(Object value, Integer index, ConstraintValueContext parent) {
    this.value = value;
    this.parent = parent;
    this.index = index;
  }

  @Override
  public Object getValue() {
    return value;
  }

  @Override
  public ConstraintValueContext getParent() {
    return parent;
  }

  public Integer getIndex() {
    return index;
  }

}
