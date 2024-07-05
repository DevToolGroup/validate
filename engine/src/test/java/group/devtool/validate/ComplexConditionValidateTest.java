/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import group.devtool.validate.GenericConstraintTest.GenericValidation;

public class ComplexConditionValidateTest {

  private ParameterValidateServiceImpl service;

  @Before
  public void initService() {
    service = new ParameterValidateServiceImpl(false);
  }

  @Test
  public void validateIDSuccess() throws NoSuchMethodException, SecurityException {
    Method executable = ComplexConditionValidateTest.class.getMethod("validate", Condition.class);

    Condition condition = new Condition();
    condition.setCardType("ID");
    condition.setCardNo("id123");
    try {
      List<ConstraintValidateFailure> failures = service.validate(executable, new Condition[] { condition });
      assertTrue(failures.isEmpty());
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void validateIDError() throws NoSuchMethodException, SecurityException {
    Method executable = ComplexConditionValidateTest.class.getMethod("validate", Condition.class);

    Condition condition = new Condition();
    condition.setCardType("ID");
    condition.setCardNo("hk123");
    try {
      List<ConstraintValidateFailure> failures = service.validate(executable, new Condition[] { condition });
      assertTrue(!failures.isEmpty());
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  public static void validate(@Cascade Condition condition) {

  }

  public static class Condition {

    @Equals(isCondition = true, value = "ID", name = "id")
    @Equals(isCondition = true, value = "MO", name = "mo")
    @Equals(isCondition = true, value = "HK", name = "hk")
    private String cardType;

    @Pattern(useCondition = "id", regexp = "^id.+")
    @Pattern(useCondition = "mo", regexp = "^mo.+")
    @Pattern(useCondition = "hk", regexp = "^hk.+")
    private String cardNo;

    public String getCardType() {
      return cardType;
    }

    public void setCardType(String cardType) {
      this.cardType = cardType;
    }

    public String getCardNo() {
      return cardNo;
    }

    public void setCardNo(String cardNo) {
      this.cardNo = cardNo;
    }

  }

}
