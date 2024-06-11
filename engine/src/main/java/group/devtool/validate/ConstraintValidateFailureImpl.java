package group.devtool.validate;

import java.lang.annotation.Annotation;

/**
 * {@code ConstraintValidateFailure}
 */
public class ConstraintValidateFailureImpl implements ConstraintValidateFailure {

  private String message;

  private Annotation annotation;

  private ConstrainedElement element;

  private ConstraintValueContext valueContext;

  public ConstraintValidateFailureImpl(String message, Annotation annotation, ConstrainedElement element,
      ConstraintValueContext valueContext) {
    this.message = message;
    this.annotation = annotation;
    this.element = element;
    this.valueContext = valueContext;
  }

  @Override
  public String getTemplateMessage() {
    return message;
  }

  public Annotation annotation() {
    return annotation;
  }

  public ConstrainedElement element() {
    return element;
  }

  @Override
  public String getLocation() {
    return element.getLocation().location(valueContext);
  }

  @Override
  public String toString() {
    return "{"
        + "message: " + message + ","
        + "annotation: " + annotation.annotationType().getSimpleName() + ","
        + "location: " + getLocation()
        + "}";
  }
}
