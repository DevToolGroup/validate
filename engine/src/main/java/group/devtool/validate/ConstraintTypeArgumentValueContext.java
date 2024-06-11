package group.devtool.validate;

public class ConstraintTypeArgumentValueContext implements ConstraintValueContext {

  private Object value;

  private Integer index;

  private Integer indexTypeParameter;

  private ConstraintValueContext parent;

  public ConstraintTypeArgumentValueContext(Object value, Integer index, Integer indexTypeParameter, ConstraintValueContext parent) {
    this.value = value;
    this.index = index;
    this.indexTypeParameter = indexTypeParameter;
    this.parent = parent;
  }

  @Override
  public Object getValue() {
    return value;
  }

  public Integer getIndex() {
    return index;
  }

  public Integer getIndexTypeParameter() {
    return indexTypeParameter;
  }

  @Override
  public ConstraintValueContext getParent() {
    return parent;
  }

}
