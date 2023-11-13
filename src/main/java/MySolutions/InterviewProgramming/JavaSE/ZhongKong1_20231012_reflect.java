package MySolutions.InterviewProgramming.JavaSE;

import java.lang.reflect.Method;

//中控笔试题1：2023-10-12
//题目：已知存在 add,sub,mul,div 四个静态计算方法。
//要求补充 solution(String methodName, int a, int b) 函数，根据传入的 methodName 和参数，调用对应的方法。
public class ZhongKong1_20231012_reflect {
    public static int add(int a, int b){return a+b;}
    public static int sub(int a, int b){return a-b;}
    public static int mul(int a, int b){return a*b;}
    public static int div(int a, int b){return a/b;}

    //题目要求，完成 solution(String methodName, int a, int b)，能够根据传入的函数名 methodName ，调用 Test 类的对应函数并获取返回值。
    public int solution(String methodName, int a, int b) throws Exception{
        try{
            //实例化一个类
            Class<?> clazzA = ZhongKong1_20231012_reflect.class;

            //根据传入字符串，获得方法
            Class<?>[] parameterTypes = { int.class, int.class };
            Method methodA = clazzA.getMethod(methodName, parameterTypes);

            //通过反射调用该方法，获取返回值并输出
            int result = (int)methodA.invoke(clazzA, a, b);  //静态方法传入类 clazzA，参数
            return result;
        }catch (NoSuchMethodException e){
            throw new NoSuchMethodException();  //题目要求：方法不存在时抛这个异常
        }
    }

    public static void main(String[] args)throws Exception {
        ZhongKong1_20231012_reflect test = new ZhongKong1_20231012_reflect();

        //测试对 add(3, 5) 的调用
        System.out.println("add(3, 5): " + test.solution("add", 3, 5));

        //测试对 mul(3, 5) 的调用
        System.out.println("mul(3, 5): " + test.solution("mul", 3, 5));
    }
}
