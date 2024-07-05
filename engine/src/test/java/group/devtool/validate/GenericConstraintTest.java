/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 验证普通注解逻辑是否有效
 */
public class GenericConstraintTest {

  private ParameterValidateServiceImpl service;

  @Before
  public void initService() {
    service = new ParameterValidateServiceImpl(false);
  }

  /**
   * 验证 @Null注解
   * 
   * @throws Exception
   * @throws SecurityException
   * @throws NoSuchMethodException
   */
  @Test
  public void testNullConstraint() throws NoSuchMethodException, SecurityException, Exception {
    List<ConstraintValidateFailure> failures = service.validate(GenericValidation.validateNull(),
        new String[] { "123" });
    Assert.assertEquals(1, failures.size());
    Assert.assertEquals("arg0", failures.get(0).getLocation());
  }

  /**
   * 验证 @NotNull注解
   * 
   * @throws Exception
   * @throws SecurityException
   * @throws NoSuchMethodException
   */
  @Test
  public void testNotNullConstraint() throws NoSuchMethodException, SecurityException, Exception {
    List<ConstraintValidateFailure> failures = service.validate(GenericValidation.validateNotNull(),
        new String[] { null });
    Assert.assertEquals(1, failures.size());
    Assert.assertEquals("arg0", failures.get(0).getLocation());
  }

  /**
   * 验证 @NotBlank注解
   * 
   * @throws Exception
   * @throws SecurityException
   * @throws NoSuchMethodException
   */
  @Test
  public void testNotBlankConstraint() throws NoSuchMethodException, SecurityException, Exception {
    List<ConstraintValidateFailure> failures = service.validate(GenericValidation.validateNotBlank(),
        new String[] { "" });
    Assert.assertEquals(1, failures.size());
    Assert.assertEquals("arg0", failures.get(0).getLocation());
  }

  /**
   * 验证 @NotEmpty注解
   * 
   * @throws Exception
   * @throws SecurityException
   * @throws NoSuchMethodException
   */
  @Test
  public void testNotEmptyConstraint() throws NoSuchMethodException, SecurityException, Exception {
    List<ConstraintValidateFailure> failures = service.validate(GenericValidation.validateNotEmpty(),
        new String[] { null });
    Assert.assertEquals(1, failures.size());
    Assert.assertEquals("arg0", failures.get(0).getLocation());
  }

  /**
   * 验证 @NotMin注解
   * 
   * @throws Exception
   * @throws SecurityException
   * @throws NoSuchMethodException
   */
  @Test
  public void testMinConstraint() throws NoSuchMethodException, SecurityException, Exception {
    List<ConstraintValidateFailure> failures = service.validate(GenericValidation.validateMin(), new Integer[] { 9 });
    Assert.assertEquals(1, failures.size());
    Assert.assertEquals("arg0", failures.get(0).getLocation());
  }

  /**
   * 验证 @NotMax注解
   * 
   * @throws Exception
   * @throws SecurityException
   * @throws NoSuchMethodException
   */
  @Test
  public void testMaxConstraint() throws NoSuchMethodException, SecurityException, Exception {
    List<ConstraintValidateFailure> failures = service.validate(GenericValidation.validateMax(), new Integer[] { 11 });
    Assert.assertEquals(1, failures.size());
    Assert.assertEquals("arg0", failures.get(0).getLocation());
  }

  /**
   * 验证 @AssertTrue注解
   * 
   * @throws Exception
   * @throws SecurityException
   * @throws NoSuchMethodException
   */
  @Test
  public void testAssertTrueConstraint() throws NoSuchMethodException, SecurityException, Exception {
    List<ConstraintValidateFailure> failures = service.validate(GenericValidation.validateAssertTrue(),
        new Boolean[] { false });
    Assert.assertEquals(1, failures.size());
    Assert.assertEquals("arg0", failures.get(0).getLocation());
  }

  /**
   * 验证 @AssertFalse注解
   * 
   * @throws Exception
   * @throws SecurityException
   * @throws NoSuchMethodException
   */
  @Test
  public void testAssertFalseConstraint() throws NoSuchMethodException, SecurityException, Exception {
    List<ConstraintValidateFailure> failures = service.validate(GenericValidation.validateAssertFalse(),
        new Boolean[] { true });
    Assert.assertEquals(1, failures.size());
    Assert.assertEquals("arg0", failures.get(0).getLocation());
  }

  /**
   * 验证 @Size注解
   * 
   * @throws Exception
   * @throws SecurityException
   * @throws NoSuchMethodException
   */
  @Test
  public void testSizeConstraint() throws NoSuchMethodException, SecurityException, Exception {
    List<ConstraintValidateFailure> failures = service.validate(GenericValidation.validateSize(),
        new String[] { "testSizeConstraint" });
    Assert.assertEquals(1, failures.size());
    Assert.assertEquals("arg0", failures.get(0).getLocation());
  }

  /**
   * 验证 @Pattern注解
   * 
   * @throws Exception
   * @throws SecurityException
   * @throws NoSuchMethodException
   */
  @Test
  public void testPatternConstraint() throws NoSuchMethodException, SecurityException, Exception {
    List<ConstraintValidateFailure> failures = service.validate(GenericValidation.validatePattern(),
        new String[] { "testPatternConstraint" });
    Assert.assertEquals(1, failures.size());
    Assert.assertEquals("arg0", failures.get(0).getLocation());
  }

  /**
   * 验证 @Email注解
   * 
   * @throws Exception
   * @throws SecurityException
   * @throws NoSuchMethodException
   */
  @Test
  public void testEmailConstraint() throws NoSuchMethodException, SecurityException, Exception {
    List<ConstraintValidateFailure> failures = service.validate(GenericValidation.validateEmail(),
        new String[] { "testEmailConstraint" });
    Assert.assertEquals(1, failures.size());
    Assert.assertEquals("arg0", failures.get(0).getLocation());
  }

  /**
   * 验证 @Equals 注解
   * 
   * @throws Exception
   * @throws SecurityException
   * @throws NoSuchMethodException
   */
  @Test
  public void testEqualsConstraint() throws NoSuchMethodException, SecurityException, Exception {
    List<ConstraintValidateFailure> failures = service.validate(GenericValidation.validateEquals(),
        new String[] { "key", "value" });
    Assert.assertEquals(1, failures.size());
    Assert.assertEquals("arg1", failures.get(0).getLocation());
  }

  /**
   * 验证 @NotEquals 注解
   * 
   * @throws Exception
   * @throws SecurityException
   * @throws NoSuchMethodException
   */
  @Test
  public void testNotEqualsConstraint() throws NoSuchMethodException, SecurityException, Exception {
    List<ConstraintValidateFailure> failures = service.validate(GenericValidation.validateNotEquals(),
        new String[] { "key", "key" });
    Assert.assertEquals(1, failures.size());
    Assert.assertEquals("arg1", failures.get(0).getLocation());
  }

  /**
   * 验证 @More 注解
   * 
   * @throws Exception
   * @throws SecurityException
   * @throws NoSuchMethodException
   */
  @Test
  public void testMoreConstraint() throws NoSuchMethodException, SecurityException, Exception {
    List<ConstraintValidateFailure> failures = service.validate(GenericValidation.validateMore(),
        new Integer[] { 2, 1 });
    Assert.assertEquals(1, failures.size());
    Assert.assertEquals("arg1", failures.get(0).getLocation());
  }

  /**
   * 验证 @Less 注解
   * 
   * @throws Exception
   * @throws SecurityException
   * @throws NoSuchMethodException
   */
  @Test
  public void testLessConstraint() throws NoSuchMethodException, SecurityException, Exception {
    List<ConstraintValidateFailure> failures = service.validate(GenericValidation.validateLess(),
        new Integer[] { 1, 2 });
    Assert.assertEquals(1, failures.size());
    Assert.assertEquals("arg1", failures.get(0).getLocation());
  }

  /**
   * 验证 @Future 注解
   * 
   * @throws Exception
   * @throws SecurityException
   * @throws NoSuchMethodException
   */
  @Test
  public void testFutureConstraint() throws NoSuchMethodException, SecurityException, Exception {
    List<ConstraintValidateFailure> failures = service.validate(GenericValidation.validateFuture(),
        new Date[] { new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() - 60000) });
    Assert.assertEquals(1, failures.size());
    Assert.assertEquals("arg1", failures.get(0).getLocation());
  }

  /**
   * 验证 @Past 注解
   * 
   * @throws Exception
   * @throws SecurityException
   * @throws NoSuchMethodException
   */
  @Test
  public void testPastConstraint() throws NoSuchMethodException, SecurityException, Exception {
    List<ConstraintValidateFailure> failures = service.validate(GenericValidation.validatePast(),
        new Date[] { new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + 60000) });
    Assert.assertEquals(1, failures.size());
    Assert.assertEquals("arg1", failures.get(0).getLocation());
  }

  @Test
  public void testConditionConstraint() throws NoSuchMethodException, SecurityException, Exception {
    List<ConstraintValidateFailure> noFailures = service.validate(GenericValidation.validateNotNullCondition(),
        new String[] { null, null });
    Assert.assertEquals(0, noFailures.size());

    List<ConstraintValidateFailure> failures = service.validate(GenericValidation.validateNotNullCondition(),
        new String[] { "true", null });
    Assert.assertEquals(1, failures.size());
    Assert.assertEquals("arg1", failures.get(0).getLocation());
  }

  public static class GenericValidation {

    public void validateNull(@Null String name) {
    }

    public static Method validateNull() throws NoSuchMethodException, SecurityException {
      return GenericValidation.class.getMethod("validateNull", String.class);
    }

    public void validateNotNull(@NotNull String name) {
    }

    public static Method validateNotNull() throws NoSuchMethodException, SecurityException {
      return GenericValidation.class.getMethod("validateNotNull", String.class);
    }

    public void validateNotBlank(@NotBlank String name) {
    }

    public static Method validateNotBlank() throws NoSuchMethodException, SecurityException {
      return GenericValidation.class.getMethod("validateNotBlank", String.class);
    }

    public void validateNotEmpty(@NotEmpty String name) {
    }

    public static Method validateNotEmpty() throws NoSuchMethodException, SecurityException {
      return GenericValidation.class.getMethod("validateNotEmpty", String.class);
    }

    public void validateMin(@Min(value = "10") Integer value) {
    }

    public static Method validateMin() throws NoSuchMethodException, SecurityException {
      return GenericValidation.class.getMethod("validateMin", Integer.class);
    }

    public void validateMax(@Max(value = "10") Integer value) {
    }

    public static Method validateMax() throws NoSuchMethodException, SecurityException {
      return GenericValidation.class.getMethod("validateMax", Integer.class);
    }

    public void validateAssertTrue(@AssertTrue Boolean value) {
    }

    public static Method validateAssertTrue() throws NoSuchMethodException, SecurityException {
      return GenericValidation.class.getMethod("validateAssertTrue", Boolean.class);
    }

    public void validateAssertFalse(@AssertFalse Boolean value) {
    }

    public static Method validateAssertFalse() throws NoSuchMethodException, SecurityException {
      return GenericValidation.class.getMethod("validateAssertFalse", Boolean.class);
    }

    public void validateSize(@Size(min = 1, max = 10) String value) {
    }

    public static Method validateSize() throws NoSuchMethodException, SecurityException {
      return GenericValidation.class.getMethod("validateSize", String.class);
    }

    public void validatePattern(@Pattern(regexp = "^a$") String value) {
    }

    public static Method validatePattern() throws NoSuchMethodException, SecurityException {
      return GenericValidation.class.getMethod("validatePattern", String.class);
    }

    public void validateEmail(@Email String name) {
    }

    public static Method validateEmail() throws NoSuchMethodException, SecurityException {
      return GenericValidation.class.getMethod("validateEmail", String.class);
    }

    public void validateEquals(@Reference(name = "key") String key, @Equals(reference = "key") String value) {
    }

    public static Method validateEquals() throws NoSuchMethodException, SecurityException {
      return GenericValidation.class.getMethod("validateEquals", String.class, String.class);
    }

    public void validateNotEquals(@Reference(name = "key") String key, @NotEquals(reference = "key") String value) {
    }

    public static Method validateNotEquals() throws NoSuchMethodException, SecurityException {
      return GenericValidation.class.getMethod("validateNotEquals", String.class, String.class);
    }

    public void validateMore(@Reference(name = "key") Integer value, @More(reference = "key") Integer more) {
    }

    public static Method validateMore() throws NoSuchMethodException, SecurityException {
      return GenericValidation.class.getMethod("validateMore", Integer.class, Integer.class);
    }

    public void validateLess(@Reference(name = "key") Integer value, @Less(reference = "key") Integer more) {
    }

    public static Method validateLess() throws NoSuchMethodException, SecurityException {
      return GenericValidation.class.getMethod("validateLess", Integer.class, Integer.class);
    }

    public void validateFuture(@Reference(name = "key") Date value, @Future(reference = "key") Date future) {
    }

    public static Method validateFuture() throws NoSuchMethodException, SecurityException {
      return GenericValidation.class.getMethod("validateFuture", Date.class, Date.class);
    }

    public void validatePast(@Reference(name = "key") Date value, @Past(reference = "key") Date past) {
    }

    public static Method validatePast() throws NoSuchMethodException, SecurityException {
      return GenericValidation.class.getMethod("validatePast", Date.class, Date.class);
    }

    public void validateNotNullCondition(@NotNull(name = "key", isCondition = true) String value,
        @NotNull(useCondition = "key") String useValue) {
    }

    public static Method validateNotNullCondition() throws NoSuchMethodException, SecurityException {
      return GenericValidation.class.getMethod("validateNotNullCondition", String.class, String.class);
    }
  }

}
