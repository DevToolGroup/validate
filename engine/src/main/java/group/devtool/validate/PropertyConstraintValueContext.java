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
