/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate.documentation;

import group.devtool.validate.engine.ConstrainedValueSpreader;

import java.lang.reflect.Type;

/**
 * 提供容器展开方法的接口
 */
public class ExampleConstrainedValueSpreader implements ConstrainedValueSpreader {


  @Override
  public Type getContainerType() {
    return "custom".getClass();
  }

  @Override
  public void spread(Object value, SpreadConstrainedValueReceiver receiver) throws Exception {
    // 根据实际情况传参
    receiver.receive(0, value, 0);
  }
}
