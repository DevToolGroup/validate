/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate.engine;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericConstraintAnnotation implements ConstraintAnnotation {

  private Annotation annotation;

  private ConstrainedElement element;

  private ConstraintValidateOperate operate;

  private Map<String, Object> attributes;

  public GenericConstraintAnnotation(Annotation annotation, ConstrainedElement element,
      ConstraintValidateOperate operator) {
    this.annotation = annotation;
    this.element = element;
    this.operate = operator;
    this.attributes = getAttributes();
  }

  @Override
  public List<ConstraintValidateFailure> validate(ConstraintValueContext valueContext,
      ParameterValidateContext validateContext) {
    List<ConstraintValidateFailure> failures = new ArrayList<>();

    if (validateContext.hasConstraintValueContext(valueContext)) {
      return failures;
    }

    String name = getAnnotationName();

    // 判断是否是条件约束
    Boolean success = null;
    if (!useCondition()) {
      success = doValid(valueContext, validateContext);
    } else {
      validateContext.addUseConditionConstraint(name);
    }

    if (useConditionIsTrue(validateContext)) {
      success = doValid(valueContext, validateContext);
      validateContext.deleteUseConditionConstraint(name);
    } else if (useConditionIsFalse(validateContext)) {
      validateContext.deleteUseConditionConstraint(name);
    } else {
      // if useCondition is null, loop validate
    }

    if (isCondition()) {
      validateContext.addConstraintResult(name, success);
    } else {
      addFailures(success, valueContext, failures);
    }

    validateContext.validatedConstraintValueContext(valueContext);

    return failures;
  }

  private Boolean doValid(ConstraintValueContext valueContext, ParameterValidateContext validateContext) {
    // 判断reference依赖是否存在，由于reference
    String reference = getReference();
    if (!"".equals(reference) && notExistReferenceValue(validateContext, reference)) {
      validateContext.addWaitReferenceConstraint(reference);
      return null;
    }
    // 删除reference依赖
    validateContext.deleteWaitReferenceConstraint(reference);
    return operate.isValid(valueContext.getValue(), annotation, validateContext);
  }

  private boolean notExistReferenceValue(ParameterValidateContext validateContext, String reference) {
    return null == validateContext.getReferValue(reference);
  }

  private String getReference() {
    Object reference = attributes.get("reference");
    return null != reference ? (String)reference : "";
  }

  private void addFailures(Boolean success, ConstraintValueContext valueContext,
      List<ConstraintValidateFailure> failures) {
    if (Boolean.FALSE.equals(success)) {
      failures.add(buildConstraintValidateFailure(valueContext));
    }
  }

  private boolean useConditionIsFalse(ParameterValidateContext validateContext) {
    String conditionName = (String) attributes.get("useCondition");
    return Boolean.FALSE.equals(validateContext.getConstraintResult(conditionName));
  }

  private boolean useConditionIsTrue(ParameterValidateContext validateContext) {
    String conditionName = (String) attributes.get("useCondition");
    return Boolean.TRUE.equals(validateContext.getConstraintResult(conditionName));
  }

  private boolean isCondition() {
    Object isCondition = attributes.get("isCondition");
    return null != isCondition && (Boolean) isCondition;
  }

  private boolean useCondition() {
    Object useCondition = attributes.get("useCondition");
    return null != useCondition && !"".equals((String) useCondition);
  }

  private String getAnnotationName() {
    return (String) attributes.get("name");
  }

  private ConstraintValidateFailure buildConstraintValidateFailure(ConstraintValueContext valueContext) {
    return new ConstraintValidateFailureImpl((String) attributes.get("message"), annotation, element, valueContext);
  }

  private Map<String, Object> getAttributes() {
    final Method[] declaredMethods = annotation.annotationType().getDeclaredMethods();
    Map<String, Object> attributes = new HashMap<>();

    for (Method m : declaredMethods) {
      if (m.isSynthetic()) {
        continue;
      }

      m.setAccessible(true);

      String attributeName = m.getName();

      try {
        attributes.put(attributeName, m.invoke(annotation));
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        throw new ParameterValidateException("注解：" + annotation.getClass() + " 属性: " + attributeName);
      }
    }
    return attributes;
  }

  @Override
  public ConstrainedElement getElement() {
    return element;
  }

}
