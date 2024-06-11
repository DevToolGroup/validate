package group.devtool.validate;

import java.util.ArrayList;
import java.util.List;

import group.devtool.validate.ConstrainedValueSpreader.SpreadConstrainedValueReceiver;

public class SpreadTypeArgumentConstraintAnnotation implements ConstraintAnnotation {

  private ConstrainedElement element;

  private List<ConstraintAnnotation> constraints;

  private ConstrainedValueSpreader spreader;

  public SpreadTypeArgumentConstraintAnnotation(ConstrainedElement element,
      List<ConstraintAnnotation> spreads, ConstrainedValueSpreader spreader) {
    this.element = element;
    this.constraints = spreads;
    this.spreader = spreader;
  }

  @Override
  public List<ConstraintValidateFailure> validate(ConstraintValueContext context,
      ParameterValidateContext validateContext) throws Exception {
    List<ConstraintValidateFailure> failures = new ArrayList<>();

    if (validateContext.hasConstraintValueContext(context)) {
      return failures;
    }

    spreader.spread(context.getValue(), new SpreadConstrainedValueReceiver() {

      @Override
      public void receive(Integer indexTypeParameter, Object value, Integer index) throws Exception {
        for (ConstraintAnnotation constraint : constraints) {
          if (matchTypeParameter(indexTypeParameter, constraint)) {
            failures.addAll(constraint.validate(new ConstraintTypeArgumentValueContext(value, index, indexTypeParameter, context), validateContext));
          }
        }
      }

      private boolean matchTypeParameter(int indexTypeParameter, ConstraintAnnotation constraint) {
        ConstrainedTypeArgument argument = (ConstrainedTypeArgument) constraint.getElement();
        return argument.getIndexTypeParameter() == indexTypeParameter;
      }
    });

    validateContext.validatedConstraintValueContext(context);

    return failures;
  }

  @Override
  public ConstrainedElement getElement() {
    return element;
  }

}
