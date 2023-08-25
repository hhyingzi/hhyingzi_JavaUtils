package JavaSE.Reflection;

import java.lang.reflect.Field;

class C{
    public C(){}
    public String str = "Hello";
    private int a = 100;
}

public class MyInvokeFields {
    public static void main(String[] args){
        try{
            Class<?> clazz = Class.forName("JavaSE.Reflection.C");
            Object obj = clazz.getConstructor().newInstance();

            Field fieldStr = clazz.getField("str");
            System.out.println((String) fieldStr.get(obj));

            Field fieldInt = clazz.getDeclaredField("a");
            fieldInt.setAccessible(true);  //private 在反射时要授权
            System.out.println(fieldInt.getInt(obj));

        }catch (Exception e){e.printStackTrace();}
    }
}
