package group.devtool.validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedArrayType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;

public class ConstrainedArrayElement implements ConstrainedElement {

  private ConstrainedElement element;

  private AnnotatedType annotatedType;

  public ConstrainedArrayElement(ConstrainedElement element, AnnotatedType annotatedType) {
    this.element = element;
    this.annotatedType = annotatedType;
  }

  @Override
  public Annotation[] getAnnotations() {
    AnnotatedType componentType = annotatedType;
    while (componentType instanceof AnnotatedArrayType) {
      componentType = ((AnnotatedArrayType) annotatedType).getAnnotatedGenericComponentType();
    }
    return componentType.getAnnotations();
  }

  @Override
  public AnnotatedType getAnnotatedType() {
    return annotatedType;
  }

  @Override
  public boolean isAnnotationPresent(Class<? extends Annotation> annotation) {
    AnnotatedType componentType = annotatedType;
    while (componentType instanceof AnnotatedArrayType) {
      componentType = ((AnnotatedArrayType) annotatedType).getAnnotatedGenericComponentType();
    }
    return componentType.isAnnotationPresent(annotation);
  }

  @Override
  public Type getType() {
    return annotatedType.getType();
  }

  @Override
  public boolean isArray() {
    return annotatedType instanceof AnnotatedArrayType;
  }

  @Override
  public ConstrainedElementLocation getLocation() {
    return new ConstrainedArrayElementLocation(element);
  }

  public static class ConstrainedArrayElementLocation implements ConstrainedElementLocation {

    private ConstrainedElement parent;

    public ConstrainedArrayElementLocation(ConstrainedElement element) {
      this.parent = element;
    }

    @Override
    public String location(ConstraintValueContext context) {
      String location = "<array>";
      if (null != context.getParent()) {
        location = parent.getLocation().location(context.getParent()) + "." + location;
      }
      if (context instanceof ConstraintArrayValueContext) {
        ConstraintArrayValueContext arrayContext = (ConstraintArrayValueContext) context;
        location += "[" + arrayContext.getIndex() + "]";
      }
      return location;
    }

  }

}
