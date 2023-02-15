package JavaSE.Class;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * https://zhuanlan.zhihu.com/p/90815478
 */
public class MyLambda {
    /* Lambda 用 forEach 遍历集合 */
    public void myLambdaForEach(){
        //ArrayList 的 forEach 遍历
        ArrayList<String> strings = new ArrayList<>();
        strings.add("List1"); strings.add("List2"); strings.add("List3");
        strings.forEach(item -> System.out.println(item));

        //TreeMap 的 forEach 遍历
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "Map1"); map.put(2, "Map2"); map.put(3, "Map3");
        map.forEach((index, string) -> System.out.println(index + ": " + string));
    }

    public static void main(String[] args){
        MyLambda myLambda = new MyLambda();
        myLambda.myLambdaForEach();
    }
}
