package group.devtool.validate;

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
