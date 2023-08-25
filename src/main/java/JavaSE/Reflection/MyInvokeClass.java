package JavaSE.Reflection;

import java.lang.reflect.Constructor;

class A{
    public A(){ System.out.println("Constructing A");}
    public A(String str){ System.out.println("Construction with String: " + str);}
}

public class MyInvokeClass {
    public static void main(String[] args){
        try{
            //使用默认构造器创建对象
            Class<?> clazz = Class.forName("JavaSE.Reflection.A");
            Object obj = clazz.getConstructor().newInstance();;
            System.out.println(obj);

            //使用指定的构造器来创建对象
            Class<?> clazz2 = Class.forName("JavaSE.Reflection.A");
            Constructor constructor = clazz2.getConstructor(String.class);
            Object obj2 = constructor.newInstance("Hello, hhyingzi!");
            System.out.println(obj2);
        }catch (Exception e){e.printStackTrace();}
    }
}
