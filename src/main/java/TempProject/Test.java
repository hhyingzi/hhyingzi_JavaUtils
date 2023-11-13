package TempProject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;




public class Test {

    public static void main(String[] args)throws Exception{
        Test test = new Test();
        HashMap<String, Integer> map = new HashMap<>();
        map.put(null, 123);
        map.put("aaa", 123);

        for(Map.Entry<String, Integer> entryItem: map.entrySet()){
            System.out.println("Key: " + entryItem.getKey() + ", Value: " + entryItem.getValue());
        }

        ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("aaa", 123);
        concurrentHashMap.put("bbb", 456);
        for(Map.Entry<String, Integer> entryItem: concurrentHashMap.entrySet()){
            System.out.println("ConKey: " + entryItem.getKey() + ", ConValue: " + entryItem.getValue());
        }
    }

}


