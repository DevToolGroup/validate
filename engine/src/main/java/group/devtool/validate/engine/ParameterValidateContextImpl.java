/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate.engine;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParameterValidateContextImpl implements ParameterValidateContext {

  private Executable executable;
  
  private Object[] parameters;

  private Map<String, Object> result;

  private List<String> conditions;

  private Map<String, Object> refers;

  private List<String> references;

  public ParameterValidateContextImpl(Executable executable, Object[] parameters) {
    this.executable = executable;
    this.parameters = parameters;
    this.result = new HashMap<>();
    this.conditions = new ArrayList<>();
    this.refers = new HashMap<>();
    this.references = new ArrayList<>();
  }

  public Executable getExecutable() {
    return executable;
  }
  
  public Object[] getParameters() {
    return parameters;
  }

  @Override
  public boolean hasConstraintValueContext(ConstraintValueContext context) {
    return false;
  }

  @Override
  public void validatedConstraintValueContext(ConstraintValueContext context) {
    
  }

  @Override
  public void addConstraintResult(String name, Object success) {
    result.put(name, success);
  }

  @Override
  public Object getConstraintResult(String name) {
    return result.get(name);
  }

  @Override
  public void addUseConditionConstraint(String name) {
    conditions.add(name);
  }

  @Override
  public void deleteUseConditionConstraint(String name) {
    conditions.remove(name);
  }

  @Override
  public boolean hasConditionConstraint() {
    return conditions.size() > 0;
  }

  @Override
  public void addReferValue(String name, Object value) {
    refers.put(name, value);
  }

  @Override
  public Object getReferValue(String name) {
    return refers.get(name);
  }

  @Override
  public void addWaitReferenceConstraint(String name) {
    references.add(name);
  }

  @Override
  public void deleteWaitReferenceConstraint(String name) {
    references.remove(name);
  }

  @Override
  public boolean hasWaitReferenceConstraint() {
    return references.size() > 0;
  }

  @Override
  public void clear() {
    result.clear();
    references.clear();
    refers.clear();
    conditions.clear();
  }

}
