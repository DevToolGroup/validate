/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate.engine;

import java.util.ArrayList;
import java.util.List;

public class SpreadArrayConstraintAnnotation implements ConstraintAnnotation {

  private ConstrainedElement element;

  private List<ConstraintAnnotation> constraints;

  public SpreadArrayConstraintAnnotation(ConstrainedElement element, List<ConstraintAnnotation> constraints) {
    this.element = element;
    this.constraints = constraints;
  }

  @Override
  public List<ConstraintValidateFailure> validate(ConstraintValueContext context,
      ParameterValidateContext validateContext) throws Exception {
    List<ConstraintValidateFailure> failures = new ArrayList<>();

    if (validateContext.hasConstraintValueContext(context)) {
      return failures;
    }

    Object[] values = (Object[]) context.getValue();
    for (int i = 0; i < values.length; i++) {
      for (ConstraintAnnotation constraint : constraints) {
        failures.addAll(constraint.validate(new ConstraintArrayValueContext(values[i], i, context), validateContext));
      }
    }

    validateContext.validatedConstraintValueContext(context);

    return failures;
  }

  @Override
  public ConstrainedElement getElement() {
    return element;
  }

}
