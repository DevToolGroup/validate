package group.devtool.validate;

import java.util.Map;

public class CacheUtils {

  public static <I, T> T doMapCache(Map<I, T> cache, I i, CacheLoader<I, T> load, Boolean open) throws Exception {
    if (!open) {
      return load.apply(i);
    }
    if (!cache.containsKey(i)) {
      cache.putIfAbsent(i, load.apply(i));
    }
    return cache.get(i);
  }

  @FunctionalInterface
  public interface CacheLoader<T, R> {
  
    public R apply(T t) throws Exception;
    
  }

}
