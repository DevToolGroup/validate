package group.devtool.validate;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Executable;

import org.junit.Before;
import org.junit.Test;

public class ParameterValidateServiceTest {

  private ParameterValidateService service;

  @Before
  public void initService() {
    service = new ParameterValidateServiceImpl(false);
  }

  @Test
  public void testGet() {
    try {
      Executable method = service.get(MethodParameterValidate.class, "validatedMethod");
      assertNotNull(method);
    } catch (ParameterValidateException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testGetSameMethod() {
    try {
      service.get(MethodParameterValidate.class, "validatedMethodWithAnnotation");
    } catch (ParameterValidateException e) {
      assertTrue(e instanceof HadSameNameValidateMethodException);
    }
  }

  @Test
  public void testGetMethodWithAnnotation() {
    try {
      Executable method = service.get(MethodParameterValidate.class, "validatedMethodWithAnnotation",
          "annotation");
      assertNotNull(method);
    } catch (ParameterValidateException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testGetSameMethodWithAnnotation() {
    try {
      service.get(MethodParameterValidate.class, "validatedMethodWithAnnotation2",
          "annotation2");
    } catch (ParameterValidateException e) {
      assertTrue(e instanceof HadSameAnnotationValidateMethodException);
    }
  }

  @Test
  public void testGetNotExistMethod() {
    try {
      service.get(MethodParameterValidate.class, "validate");
    } catch (ParameterValidateException e) {
      assertTrue(e instanceof NotFoundValidateMethodException);
    }
  }

  public static class MethodParameterValidate {

    public void validatedMethod() {

    }

    @ValidateExecutable(name = "annotation")
    public void validatedMethodWithAnnotation() {

    }

    public void validatedMethodWithAnnotation(String name) {

    }

    @ValidateExecutable(name = "annotation2")
    public void validatedMethodWithAnnotation2() {

    }

    @ValidateExecutable(name = "annotation2")
    public void validatedMethodWithAnnotation2(String name) {

    }

  }
}
