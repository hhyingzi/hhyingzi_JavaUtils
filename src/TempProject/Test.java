package TempProject;

import java.lang.reflect.Method;

public class Test {
    public static void main(String[] args) throws Exception{
        Test obj = new Test();
        Method m = obj.getClass().getMethod("func", String.class, String[].class);

        Object temp = m.invoke(obj, new String(), new String[1]);
        System.out.println(temp);
    }

    public void func(String key, String[] args){
        System.out.println((args==null)?"null":args.length);
    }
}