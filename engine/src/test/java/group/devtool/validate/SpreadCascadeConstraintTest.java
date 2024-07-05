/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class SpreadCascadeConstraintTest {

  private ParameterValidateService service;

  @Before
  public void initService() {
    service = new ParameterValidateServiceImpl(false);
  }

  @Test
  public void testSimpleCascadeValidate() throws Exception {
    Executable method = service.get(SimpleCascadeValidation.class, "validate");
    List<ConstraintValidateFailure> result = service.validate(method,
        new Object[] { new SimpleCascadeValidation("email") });
    assertEquals(0, result.size());
  }

  @Test
  public void testSimpleCascadeValidateException() throws Exception {
    Executable method = service.get(SimpleCascadeValidation.class, "validate");
    List<ConstraintValidateFailure> result = service.validate(method,
        new Object[] { new SimpleCascadeValidation(null) });
    assertEquals(1, result.size());
    assertEquals("arg0.<SimpleCascadeValidation>[email]", result.get(0).getLocation());
    assertEquals("不可以为空", result.get(0).getTemplateMessage());
  }

  @Test
  public void testComplexCascadeValidate() throws Exception {
    Executable method = service.get(ComplexCascadeValidation.class, "validate");

    List<ConstraintValidateFailure> result = service.validate(method,
        new Object[] { new ComplexCascadeValidation(new SimpleCascadeValidation("email")) });
    assertEquals(0, result.size());
  }

  @Test
  public void testComplexCascadeException() throws Exception {
    Executable method = service.get(ComplexCascadeValidation.class, "validate");

    List<ConstraintValidateFailure> result = service.validate(method,
        new Object[] { new ComplexCascadeValidation(new SimpleCascadeValidation(null)) });

    assertEquals(1, result.size());
    assertEquals("arg0.<ComplexCascadeValidation>[simple].<SimpleCascadeValidation>[email]",
        result.get(0).getLocation());
    assertEquals("不可以为空", result.get(0).getTemplateMessage());
  }

  @Test
  public void testContainerComplexCascadeValidate() throws Exception {
    Executable method = service.get(ContainerComplexCascadeValidation.class, "validate");

    ContainerComplexCascadeValidation validation = new ContainerComplexCascadeValidation(
        new SimpleCascadeValidation("email"));

    List<ConstraintValidateFailure> result = service.validate(method, new Object[] { Arrays.asList(validation) });
    assertEquals(0, result.size());

  }

  @Test
  public void testContainerComplexCascadeValidateException() throws Exception {
    Executable method = service.get(ContainerComplexCascadeValidation.class, "validate");

    ContainerComplexCascadeValidation validation = new ContainerComplexCascadeValidation(
        new SimpleCascadeValidation(null));

    List<ConstraintValidateFailure> result = service.validate(method, new Object[] { Arrays.asList(validation) });
    assertEquals(1, result.size());
    assertEquals("arg0.<List.E>[0].<ContainerComplexCascadeValidation>[simple].<SimpleCascadeValidation>[email]",
        result.get(0).getLocation());
    assertEquals("不可以为空", result.get(0).getTemplateMessage());
  }

  @Test
  public void testNoContainerElementValidateException() throws Exception {
    Executable method = service.get(ContainerComplexCascadeValidation.class, "validate");

    ContainerComplexCascadeValidation validation = null;
    List<ConstraintValidateFailure> result = service.validate(method, new Object[] { Arrays.asList(validation) });
    assertEquals(1, result.size());
    assertEquals("arg0.<List.E>[0]", result.get(0).getLocation());
    assertEquals("不可以为空", result.get(0).getTemplateMessage());
  }

  @Test
  public void testMapValueContainerComplexCascadeValidate() throws Exception {
    ContainerComplexCascadeValidation validation = new ContainerComplexCascadeValidation(
        new SimpleCascadeValidation("email"));

    HashMap<ContainerComplexCascadeValidation, ContainerComplexCascadeValidation> maps = new HashMap<>();
    maps.put(validation, validation);

    Executable method = service.get(ContainerComplexCascadeValidation.class, "validateMap");

    List<ConstraintValidateFailure> result = service.validate(method, new Object[] { Arrays.asList(maps) });
    assertEquals(0, result.size());

  }

  @Test
  public void testMapKeyContainerComplexCascadeValidateException() throws Exception {
    ContainerComplexCascadeValidation key = new ContainerComplexCascadeValidation(
        new SimpleCascadeValidation(null));

    ContainerComplexCascadeValidation value = new ContainerComplexCascadeValidation(
        new SimpleCascadeValidation("email"));

    HashMap<ContainerComplexCascadeValidation, ContainerComplexCascadeValidation> maps = new HashMap<>();
    maps.put(key, value);

    Executable validateMap = service.get(ContainerComplexCascadeValidation.class, "validateMap");

    List<ConstraintValidateFailure> result = service.validate(validateMap, new Object[] { Arrays.asList(maps) });
    assertEquals(1, result.size());
    assertEquals("arg0.<List.E>.<Map.K>.<ContainerComplexCascadeValidation>[simple].<SimpleCascadeValidation>[email]",
        result.get(0).getLocation());
    assertEquals("不可以为空", result.get(0).getTemplateMessage());
  }

  @Test
  public void testMapValueContainerComplexCascadeValidateException() throws Exception {
    ContainerComplexCascadeValidation key = new ContainerComplexCascadeValidation(
        new SimpleCascadeValidation(null));

    ContainerComplexCascadeValidation value = new ContainerComplexCascadeValidation(
        new SimpleCascadeValidation(null));

    HashMap<ContainerComplexCascadeValidation, ContainerComplexCascadeValidation> maps = new HashMap<>();
    maps.put(key, value);

    Executable validateMap = service.get(ContainerComplexCascadeValidation.class, "validateMap");

    List<ConstraintValidateFailure> result = service.validate(validateMap, new Object[] { Arrays.asList(maps) });

    assertEquals(2, result.size());

    assertEquals("arg0.<List.E>.<Map.V>.<ContainerComplexCascadeValidation>[simple].<SimpleCascadeValidation>[email]",
        result.get(1).getLocation());
    assertEquals("不可以为空", result.get(1).getTemplateMessage());

    assertEquals("arg0.<List.E>.<Map.K>.<ContainerComplexCascadeValidation>[simple].<SimpleCascadeValidation>[email]",
        result.get(0).getLocation());
    assertEquals("不可以为空", result.get(0).getTemplateMessage());
  }

  @Test
  public void testNoContainerComplexCascadeValidate() throws Exception {
    ContainerComplexCascadeValidation validation = new ContainerComplexCascadeValidation(
        new SimpleCascadeValidation("email"));

    Executable noCascadeValidate = service.get(ContainerComplexCascadeValidation.class, "noCascadeValidate");

    List<ConstraintValidateFailure> result = service.validate(noCascadeValidate, new Object[] {
        Arrays.asList(validation) });

    assertEquals(0, result.size());
  }

  @Test
  public void testArrayContainerComplexCascadeValidate() throws Exception {
    ContainerComplexCascadeValidation validation = new ContainerComplexCascadeValidation(
        new SimpleCascadeValidation("email"));

    Executable validateArray = service.get(ContainerComplexCascadeValidation.class, "validateArray");

    List<ConstraintValidateFailure> result = service.validate(validateArray,
        new Object[] { new ContainerComplexCascadeValidation[] { validation } });
    assertEquals(0, result.size());
  }

  @Test
  public void testArrayContainerComplexCascadeValidateException() throws Exception {
    ContainerComplexCascadeValidation validation = new ContainerComplexCascadeValidation(
        new SimpleCascadeValidation(null));

    Executable validateArray = service.get(ContainerComplexCascadeValidation.class, "validateArray");

    List<ConstraintValidateFailure> result = service.validate(validateArray,
        new Object[] { new ContainerComplexCascadeValidation[] { validation } });

    assertEquals(1, result.size());
    assertEquals("arg0.<array>[0].<ContainerComplexCascadeValidation>[simple].<SimpleCascadeValidation>[email]",
        result.get(0).getLocation());
    assertEquals("不可以为空", result.get(0).getTemplateMessage());
  }

  @Test
  public void testListArrayComplexCascadeValidate() throws Exception {
    ContainerComplexCascadeValidation validation = new ContainerComplexCascadeValidation(
        new SimpleCascadeValidation("email"));

    Executable validateArray = service.get(ContainerComplexCascadeValidation.class, "validateListArray");

    List<ContainerComplexCascadeValidation[]> as = new ArrayList<>();
    as.add(new ContainerComplexCascadeValidation[] { validation });

    List<ConstraintValidateFailure> result = service.validate(validateArray,
        new Object[] { as });

    assertEquals(0, result.size());

  }

  @Test
  public void testListArrayComplexCascadeValidateException() throws Exception {
    ContainerComplexCascadeValidation validation = new ContainerComplexCascadeValidation(
        new SimpleCascadeValidation(null));

    Executable validateArray = service.get(ContainerComplexCascadeValidation.class, "validateListArray");

    List<ContainerComplexCascadeValidation[]> as = new ArrayList<>();
    as.add(new ContainerComplexCascadeValidation[] { validation });

    List<ConstraintValidateFailure> result = service.validate(validateArray, new Object[] { as });
    System.out.println(result);

    assertEquals(1, result.size());
    assertEquals(
        "arg0.<List.E>[0].<array>[0].<ContainerComplexCascadeValidation>[simple].<SimpleCascadeValidation>[email]",
        result.get(0).getLocation());
    assertEquals("不可以为空", result.get(0).getTemplateMessage());
  }

  @Test
  public void testArrayListComplexCascadeValidateException() throws Exception {
    ContainerComplexCascadeValidation validation = new ContainerComplexCascadeValidation(
        new SimpleCascadeValidation(null));

    Executable validateArray = service.get(ContainerComplexCascadeValidation.class, "validateArrayList");

    @SuppressWarnings("unchecked")
    List<ContainerComplexCascadeValidation>[] as = new List[1];
    as[0] = Arrays.asList(validation);

    List<ConstraintValidateFailure> result = service.validate(validateArray, new Object[] { as });
    assertEquals(1, result.size());
    assertEquals(
        "arg0.<array>[0].<List.E>[0].<ContainerComplexCascadeValidation>[simple].<SimpleCascadeValidation>[email]",
        result.get(0).getLocation());
    assertEquals("不可以为空", result.get(0).getTemplateMessage());

  }

  @Test
  public void testArrayArrayComplexCascadeValidateException() throws Exception {
    ContainerComplexCascadeValidation validation = new ContainerComplexCascadeValidation(
        new SimpleCascadeValidation(null));

    Executable validateArray = service.get(ContainerComplexCascadeValidation.class, "validateArrayArray");

    @SuppressWarnings("unchecked")
    List<ContainerComplexCascadeValidation>[][] as = new List[1][1];
    as[0][0] = Arrays.asList(validation);

    List<ConstraintValidateFailure> result = service.validate(validateArray, new Object[] { as });
    assertEquals(1, result.size());
    assertEquals(
        "arg0.<array>[0].<array>[0].<List.E>[0].<ContainerComplexCascadeValidation>[simple].<SimpleCascadeValidation>[email]",
        result.get(0).getLocation());
    assertEquals("不可以为空", result.get(0).getTemplateMessage());
  }

  @Test
  public void testVariableCascadeValidate() throws Exception {
    Executable method = service.get(VariableCascadeValidation.class, "validate");

    List<ConstraintValidateFailure> result = service.validate(method,
        new Object[] { new SimpleCascadeValidation("email") });

    assertEquals(0, result.size());

  }

  @Test
  public void testVariableCascadeValidateException() throws Exception {
    Executable method = service.get(VariableCascadeValidation.class, "validate");

    List<ConstraintValidateFailure> result = service.validate(method,
        new Object[] { new SimpleCascadeValidation(null) });

    assertEquals(1, result.size());
    assertEquals(
        "arg0.<SimpleCascadeValidation>[email]",
        result.get(0).getLocation());
    assertEquals("不可以为空", result.get(0).getTemplateMessage());
  }

  @Test
  public void testVariableContainerComplexCascadeValidate() throws Exception {
    SimpleCascadeValidation validation = new SimpleCascadeValidation("123");

    Executable method = service.get(VariableContainerComplexCascadeValidation.class, "validate");

    List<ConstraintValidateFailure> result = service.validate(method, new Object[] { Arrays.asList(validation) });
    assertEquals(0, result.size());

  }

  @Test
  public void testVariableContainerComplexCascadeValidateException() throws Exception {
    SimpleCascadeValidation validation = new SimpleCascadeValidation(null);

    Executable method = service.get(VariableContainerComplexCascadeValidation.class, "validate");

    List<ConstraintValidateFailure> result = service.validate(method, new Object[] { Arrays.asList(validation) });

    assertEquals(0, result.size());
    assertEquals(
        "arg0.<List.E>[0].<SimpleCascadeValidation>[email]",
        result.get(0).getLocation());
    assertEquals("不可以为空", result.get(0).getTemplateMessage());
  }

  @Test
  public void testWildcardContainerComplexCascadeValidateException() throws Exception {
    SimpleCascadeValidation validation = new SimpleCascadeValidation(null);
    Method method = WildcardContainerComplexCascadeValidation.class.getMethod("validate", List.class);

    List<ConstraintValidateFailure> result = service.validate(method, new Object[] { Arrays.asList(validation) });

    assertEquals(1, result.size());
    assertEquals(
      "arg0.<List.E>[0].<SimpleCascadeValidation>[email]",
      result.get(0).getLocation());
  assertEquals("不可以为空", result.get(0).getTemplateMessage());
  }


  @FunctionalInterface
  public interface SupplierWithException<R> {

    public R get() throws Exception;

  }

  class SimpleValidation {

    public <T> void validate(@NotNull T name) {

    }

  }

  // 简单级联校验
  static class SimpleCascadeValidation {

    private @NotNull String email;

    public SimpleCascadeValidation(String email) {
      this.email = email;
    }

    public void validate(@Cascade SimpleCascadeValidation validation) {

    }
  }

  // 嵌套级联校验
  static class ComplexCascadeValidation {

    public @Cascade SimpleCascadeValidation simple;

    public ComplexCascadeValidation(SimpleCascadeValidation simple) {
      this.simple = simple;
    }

    public void validate(@Cascade ComplexCascadeValidation validation) {

    }
  }

  // 容器级联校验
  public static class ContainerComplexCascadeValidation {

    private @Cascade SimpleCascadeValidation simple;

    public ContainerComplexCascadeValidation(SimpleCascadeValidation simple) {
      this.simple = simple;
    }

    public void validate(@Spread List<@NotNull @Cascade ContainerComplexCascadeValidation> validation) {

    }

    public void noCascadeValidate(List<@NotNull @Cascade ContainerComplexCascadeValidation> validation) {

    }

    public void validateMap(
        @Spread List<@NotNull @Spread Map<@Cascade ContainerComplexCascadeValidation, @Cascade ContainerComplexCascadeValidation>> validation) {

    }

    public void validateArray(@Spread @Cascade ContainerComplexCascadeValidation[] abc) {

    }

    public void validateListArray(@Spread List<@Cascade @Spread ContainerComplexCascadeValidation[]> abc) {

    }

    public void validateArrayList(@NotNull @Spread List<@Cascade ContainerComplexCascadeValidation>[] args) {

    }

    public void validateArrayArray(@NotNull @Spread List<@Cascade ContainerComplexCascadeValidation>[][] args) {

    }

  }

  static class VariableCascadeValidation {

    public @Cascade SimpleCascadeValidation simple;

    public VariableCascadeValidation(SimpleCascadeValidation simple) {
      this.simple = simple;
    }

    public <T> void validate(@Cascade T t) {

    }

  }

  // 变量类型容器级联校验
  static class VariableContainerComplexCascadeValidation {

    private @Cascade SimpleCascadeValidation simple;

    public VariableContainerComplexCascadeValidation(SimpleCascadeValidation simple) {
      this.simple = simple;
    }

    public <T extends SimpleCascadeValidation> void validate(@Spread List<@NotNull @Cascade T> validation) {

    }

  }

  // 通配符类型容器级联校验
  static class WildcardContainerComplexCascadeValidation {

    public void validate(@Spread List<@NotNull @Cascade ? extends SimpleCascadeValidation> validation) {

    }

  }

}
