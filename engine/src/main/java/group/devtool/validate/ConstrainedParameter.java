package group.devtool.validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

public class ConstrainedParameter implements ConstrainedElement {

  private Parameter parameter;

  public ConstrainedParameter(Parameter parameter) {
    this.parameter = parameter;
  }

  @Override
  public Annotation[] getAnnotations() {
    return parameter.getAnnotations();
  }

  @Override
  public AnnotatedType getAnnotatedType() {
    return parameter.getAnnotatedType();
  }

  @Override
  public boolean isAnnotationPresent(Class<? extends Annotation> annotation) {
    return parameter.isAnnotationPresent(annotation);
  }

  @Override
  public Type getType() {
    return parameter.getType();
  }

  @Override
  public boolean isArray() {
    return parameter.getClass().isArray();
  }

  @Override
  public ConstrainedElementLocation getLocation() {
    return new ConstrainedParameterLocation(parameter.getName());
  }

  public static class ConstrainedParameterLocation implements ConstrainedElementLocation {

    private String name;

    public ConstrainedParameterLocation(String name) {
      this.name = name;
    }

    @Override
    public String location(ConstraintValueContext context) {
      return name;
    }

  }

}
