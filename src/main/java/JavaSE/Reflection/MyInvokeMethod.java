package JavaSE.Reflection;

import java.lang.reflect.Method;

class B{
    public B(){}
    void work0(){}
    public void work(){ System.out.println("B Object work."); }
    public void work(String str){ System.out.println("B Object work with str: " + str); }
    public String work2(String str){ return str;}
    private static void work3(){ System.out.println("B Object work3.");  }
    public static int add(int a, int b){ return a+b; }
}

public class MyInvokeMethod {
    public static void main(String[] args){
        try{
            //实例化一个类
            Class<?> clazz = Class.forName("JavaSE.Reflection.B");

            //调用构造函数，实例化一个对象
            Object b = clazz.getConstructor().newInstance();

            //包作用域 work()
            Method method0 = clazz.getDeclaredMethod("work");
            method0.invoke(b);  //非静态类，需要传入实例 b，以及函数参数（本例函数无参）

            //public work()
            Method method1 = clazz.getMethod("work");
            method1.invoke(b);

            //public work(String str)
            Method method2 = clazz.getMethod("work", String.class);
            method2.invoke(b, "hhyingzi");

            //public String work2(String str)
            Method method3 = clazz.getMethod("work2", String.class);
            System.out.println(method3.invoke(b, "nihao"));

            //private static void work3(){}
            Method method4 = clazz.getDeclaredMethod("work3");
            method4.setAccessible(true);  //private 必须先设置为不检查执行权限，才能执行
            method4.invoke(b);

            //public static int add(int a, int b)
            Class<?>[] parameterTypes = {int.class, int.class};
            Method methodAdd = clazz.getMethod("add", parameterTypes);
            int result = (int) methodAdd.invoke(clazz, 5, 3); //静态类直接调用类
            System.out.println("add(int, int): " + result);
        }catch (Exception e){e.printStackTrace();}
    }
}
