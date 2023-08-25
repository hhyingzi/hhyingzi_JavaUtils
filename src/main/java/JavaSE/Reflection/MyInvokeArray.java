package JavaSE.Reflection;

import java.lang.reflect.Array;

public class MyInvokeArray {
    public static void main(String[] args){
        //一维数组 String[10]
        try {
            Object arr = Array.newInstance(String.class, 10);
            Array.set(arr, 5, "HHyingzi");  //给数组赋值
            Array.set(arr, 6, "nihao");
            Object book1 = Array.get(arr , 5);  //获取数组的值
            Object book2 = Array.get(arr , 6);
            System.out.println(book1);
            System.out.println(book2);
        }
        catch (Exception e) { e.printStackTrace(); }
        System.out.println("==============");

        /* 创建一个三维数组。 三维数组只不过是数组元素是二维数组的一维数组， 因此可以认为arr是长度为3的一维数组 */
        Object arr = Array.newInstance(String.class, 3, 4, 10);  //三维数组
        // 获取arr数组中index为2的元素，该元素应该是二维数组
        Object arrObj = Array.get(arr, 2);
        // 使用Array为二维数组的数组元素赋值。二维数组的数组元素是一维数组，所以传入Array的set()方法的第三个参数是一维数组。
        Array.set(arrObj , 2 , new String[]{"hhyingzi", "Hello"});
        // 获取arrObj数组中index为3的元素，该元素应该是一维数组。
        Object anArr  = Array.get(arrObj, 3);
        Array.set(anArr , 8  , "你好");
        // 将arr强制类型转换为三维数组
        String[][][] cast = (String[][][])arr;
        // 获取cast三维数组中指定元素的值
        System.out.println(cast[2][3][8]);
        System.out.println(cast[2][2][0]);
        System.out.println(cast[2][2][1]);
    }
}
