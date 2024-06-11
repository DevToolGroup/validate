package group.devtool.validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;

public interface ConstrainedElement {

  Annotation[] getAnnotations();

  AnnotatedType getAnnotatedType();

  public boolean isAnnotationPresent(Class<? extends Annotation> annotation);

  Type getType();

  boolean isArray();

  ConstrainedElementLocation getLocation();

}
