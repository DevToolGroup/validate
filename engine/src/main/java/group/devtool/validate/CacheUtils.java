/*
 * Validate, declare and validate application constraints
 *
 * License: GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 * See the license.txt file in the root directory or see <http://www.gnu.org/licenses/>.
 */

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
