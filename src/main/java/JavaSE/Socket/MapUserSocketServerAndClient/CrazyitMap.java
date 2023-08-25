package JavaSE.Socket.MapUserSocketServerAndClient;

import java.util.*;
// 通过组合HashMap对象来实现CrazyitMap，CrazyitMap要求value也不可重复
// <客户名字, 对应输出流>
public class CrazyitMap<K,V> {
    // 创建一个线程安全的HashMap
    public Map<K ,V> dataMap = Collections.synchronizedMap(new HashMap<K,V>());

    //增加键值对，要求不允许value重复
    public synchronized V put(K key,V value) {
        // 遍历所有value组成的集合。如果某个value与试图放入集合的value相同，则抛出一个RuntimeException异常
        for (V val : valueSet() ) {
            if (val.equals(value) && val.hashCode() == value.hashCode()) throw new RuntimeException("MyMap实例中不允许有重复value!");
        }
        return dataMap.put(key , value);
    }

    // 根据 value（IO通道），删除该元素
    public synchronized void removeByValue(Object value) {
        for (Object key : dataMap.keySet()) {
            if (dataMap.get(key) == value) {
                dataMap.remove(key);
                break;
            }
        }
    }

    // 获取所有 value ，返回为 HashSet
    public synchronized Set<V> valueSet() {
        Set<V> result = new HashSet<V>();
        dataMap.forEach((key , value) -> result.add(value));
        return result;
    }

    // 根据 value 查找 key 。
    public synchronized K getKeyByValue(V val) {
        // 遍历所有key组成的集合
        for (K key : dataMap.keySet()) {
            // 如果指定key对应的value与被搜索的value相同，则返回对应的key
            if (dataMap.get(key) == val || dataMap.get(key).equals(val)) return key;
        }
        return null;
    }
}
