/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

package group.devtool.validate;

import java.lang.reflect.Type;

/**
 * 容器类约束对象展开接口
 */
public interface ConstrainedValueSpreader {

  /**
   * @return 容器类型
   */
  public Type getContainerType();

  /**
   * 调用{@method SpreadConstrainedValueReceiver}的receive方法，校验容器对象中的每个元素。
   * 
   * @param value
   * @param receiver
   * @throws Exception
   */
  public void spread(Object value, SpreadConstrainedValueReceiver receiver) throws Exception;

  /**
   * 容器元素处理接口
   */
  public interface SpreadConstrainedValueReceiver {

    /**
     * 接收容器展开接口返回的元素
     * 
     * @param indexTypeVariable 元素参数在容器中的位置，
     *                          例如：List<E> 中的E代表元素参数，其位置为0；Map<K,
     *                          V>中的K，V代表元素参数，其位置分别为0， 1
     * @param value             容器元素值。
     * @param index             元素值在容器中位置，主要应用List，Array等结构。
     * @throws Exception
     */
    public void receive(Integer indexTypeVariable, Object value, Integer index) throws Exception;

  }
}
