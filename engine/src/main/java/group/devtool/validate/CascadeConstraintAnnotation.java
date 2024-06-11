package group.devtool.validate;

import java.util.ArrayList;
import java.util.List;

public class CascadeConstraintAnnotation implements ConstraintAnnotation {

  private ConstrainedElement element;

  private CascadeConstraintSupplier<Object, List<ConstraintAnnotation>> constraints;

  public CascadeConstraintAnnotation(ConstrainedElement element,
      CascadeConstraintSupplier<Object, List<ConstraintAnnotation>> constraints) {
    this.element = element;
    this.constraints = constraints;
  }

  @Override
  public List<ConstraintValidateFailure> validate(ConstraintValueContext context,
      ParameterValidateContext validateContext) throws Exception {
    List<ConstraintValidateFailure> failures = new ArrayList<>();

    if (null == context.getValue()) {
      return failures;
    }

    if (validateContext.hasConstraintValueContext(context)) {
      return failures;
    }

    for (ConstraintAnnotation actualConstraint : constraints.apply(context.getValue())) {
      failures.addAll(doValidate(actualConstraint, context, validateContext));
    }

    validateContext.validatedConstraintValueContext(context);
    return failures;
  }

  private List<ConstraintValidateFailure> doValidate(ConstraintAnnotation actualConstraint,
      ConstraintValueContext context,
      ParameterValidateContext validateContext) throws Exception {

    ConstrainedElement element = actualConstraint.getElement();
    if (!(element instanceof ConstrainedProperty)) {
      throw new ParameterValidateException("校验元素不一致");
    }
    ConstrainedProperty property = (ConstrainedProperty) element;
    return actualConstraint.validate(new PropertyConstraintValueContext(property, context), validateContext);
  }

  @Override
  public ConstrainedElement getElement() {
    return element;
  }

  @FunctionalInterface
  public interface CascadeConstraintSupplier<T, R> {
    
    public R apply(T t) throws Exception;
    
  }

}
