package JavaSE.Reflection;

import java.lang.reflect.Field;
import java.util.Arrays;

/* 通过反射调用嵌套类，以及其中的 private 成员 */
public class MyInvokeInnerClass {
    public class InA{
        private int age = 18;
        private void work(){System.out.println("Work is done.");}  //Method 不再举例了，原理和 Field 一样。
    }

    public class InB{
        private void work2(){  //访问 InA 的 private 变量
            //正常方式访问 InA 的 private 成员，完全没问题
            InA ina = new InA(); ina.age=20; System.out.println("InA's age=" + ina.age);

            //java11 以前，InB 无法通过反射访问 InA 的 private 成员，但是 Java11 以后，可以了
            try{
                Field field = InA.class.getDeclaredField("age");
                field.set(ina, 21);
                System.out.println("InB invoke Ina's age=" + field.getInt(ina));
            }catch (Exception e){e.printStackTrace();}
        }
    }

    public static void main(String[] args){
        try{
            MyInvokeInnerClass myInvokeInnerClass = new MyInvokeInnerClass();
            myInvokeInnerClass.new InB().work2();

            //java11 以前，外部类无法通过反射访问 InA 的 private 成员，但是 Java11 以后，可以了
            InA ina = myInvokeInnerClass.new InA();
            Field field = InA.class.getDeclaredField("age");
            field.set(ina, 80);
            System.out.println("OuterClass invoke Ina's age=" + field.getInt(ina));

            System.out.println("=====");
            //Java11 新增的几个 Class 的方法，注意嵌套类表示为“MyInvokeInnerClass$InA”
            System.out.println(MyInvokeInnerClass.class.getNestHost());  //返回属主类，对外部类而言就是返回自身
            System.out.println(Class.forName("JavaSE.Reflection.MyInvokeInnerClass$InA").getNestHost());  //对 InA 而言，属主类就是外部类。
            System.out.println(Arrays.toString(MyInvokeInnerClass.class.getNestMembers()));  //返回所有嵌套成员（包括自身）
            System.out.println(Class.forName("JavaSE.Reflection.MyInvokeInnerClass$InA").isNestmateOf(Class.forName("JavaSE.Reflection.MyInvokeInnerClass$InB")));  //InA 是否是 InB 的嵌套同伴
        }catch (Exception e){e.printStackTrace();}
    }
}
