/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate;

public class PropertyConstraintValueContext implements ConstraintValueContext {

  private ConstrainedProperty property;

  private ConstraintValueContext parent;

  public PropertyConstraintValueContext(ConstrainedProperty property, ConstraintValueContext parent) {
    this.property = property;
    this.parent = parent;
  }

  @Override
  public Object getValue() {
    return property.getValue(parent.getValue());
  }

  @Override
  public ConstraintValueContext getParent() {
    return parent;
  }

}
