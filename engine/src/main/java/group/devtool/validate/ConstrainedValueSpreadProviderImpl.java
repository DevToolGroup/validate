package group.devtool.validate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提供容器展开方法的接口
 */
public class ConstrainedValueSpreadProviderImpl implements ConstrainedValueSpreadProvider {

  private static List<ConstrainedValueSpreader> BUILTIN = new ArrayList<>();

  static {
    BUILTIN.add(new ConstrainedListSpreader());
    BUILTIN.add(new ConstrainedMapSpreader());
  }

  private Map<Type, ConstrainedValueSpreader> spreads = new HashMap<>();

  public ConstrainedValueSpreadProviderImpl(List<ConstrainedValueSpreader> customs) {
    // 加载内置展开器
    for (ConstrainedValueSpreader spread : BUILTIN) {
      spreads.put(spread.getContainerType(), spread);
    }

    // 加载自定义展开器
    if (null != customs) {
      for (ConstrainedValueSpreader spread : customs) {
        spreads.put(spread.getContainerType(), spread);
      }
    }
  }

  /**
   * 根据容器的类型选择展开方式
   * 
   * @param containerType 容器类型
   * @return 容器类约束对象展开接口
   */
  @Override
  public ConstrainedValueSpreader resolve(Type containerType) {
    ConstrainedValueSpreader spreader = spreads.get(containerType);
    if (null == spreader) {
      throw new ParameterValidateException("不存在容器类型为：" + getSimpleName(containerType) + "的容器展开器");
    }
    return spreader;
  }

  private String getSimpleName(Type containerType) {
    String typeName = containerType.getTypeName();
    return typeName.substring(typeName.lastIndexOf(",") + 1);
  }

  public static class ConstrainedListSpreader implements ConstrainedValueSpreader {

    public ConstrainedListSpreader() {

    }

    @Override
    public Type getContainerType() {
      return List.class;
    }

    @Override
    public void spread(Object value, SpreadConstrainedValueReceiver receiver) throws Exception {
      if (!(value instanceof List)) {
        throw new ParameterValidateException("仅支持List类型的参数");
      }
      @SuppressWarnings("unchecked")
      List<Object> values = (List<Object>) value;
      for (int i = 0; i < values.size(); i++) {
        receiver.receive(0, values.get(i), i);
      }
    }

  }

  public static class ConstrainedMapSpreader implements ConstrainedValueSpreader {

    public ConstrainedMapSpreader() {

    }

    @Override
    public Type getContainerType() {
      return Map.class;
    }

    @Override
    public void spread(Object value, SpreadConstrainedValueReceiver receiver) throws Exception {
      if (!(value instanceof Map)) {
        throw new ParameterValidateException("仅支持List类型的参数");
      }
      @SuppressWarnings("unchecked")
      Map<Object, Object> mapValue = (Map<Object, Object>) value;
      for (Map.Entry<Object, Object> entry : mapValue.entrySet()) {
        receiver.receive(0, entry.getKey(), null);
        receiver.receive(1, entry.getValue(), null);
      }
    }

  }

}
