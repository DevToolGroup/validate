package group.devtool.validate;

import java.lang.reflect.Executable;

public interface ParameterValidateContext {

  public Executable getExecutable();

  public Object[] getParameters();

  public void addConstraintResult(String name, Object success);

  public Object getConstraintResult(String name);

  boolean hasConstraintValueContext(ConstraintValueContext context);

  void validatedConstraintValueContext(ConstraintValueContext context);

  public void addUseConditionConstraint(String name);

  public void deleteUseConditionConstraint(String name);

  public boolean hasConditionConstraint();

  public void addReferValue(String name, Object value);

  public Object getReferValue(String name);

  public void addWaitReferenceConstraint(String name);

  public void deleteWaitReferenceConstraint(String name);

  public boolean hasWaitReferenceConstraint();

  public void clear();
}
