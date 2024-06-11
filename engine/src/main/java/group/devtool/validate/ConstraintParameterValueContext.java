package group.devtool.validate;

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
