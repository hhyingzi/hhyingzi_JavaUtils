package JavaSE.Reflection;

import java.lang.reflect.*;

interface Dog {
    void info();
    void run();
}
class GunDog implements Dog {
    public void info() { System.out.println("我是一只猎狗"); }
    public void run() { System.out.println("我奔跑迅速"); }
}
class DogUtil {
    // 第一个拦截器方法
    public void method1() { System.out.println("=====模拟第一个通用方法====="); }
    // 第二个拦截器方法
    public void method2() { System.out.println("=====模拟通用方法二====="); }
}
class MyInvokationHandler implements InvocationHandler {
    private Object target;  // 需要被代理的对象
    public void setTarget(Object target) { this.target = target; }

    // 执行动态代理对象的所有方法时，都会被替换成执行如下的invoke方法
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        DogUtil du = new DogUtil();
        du.method1();
        Object result = method.invoke(target , args);
        du.method2();
        return result;
    }
}
class MyProxyFactory {
    // 为指定target生成动态代理对象
    public static Object getProxy(Object target) throws Exception {
        MyInvokationHandler handler = new MyInvokationHandler();  // 创建一个MyInvokationHandler对象
        handler.setTarget(target);  // 为MyInvokationHandler设置target对象
        // 创建、并返回一个动态代理
        Object result =  Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces() , handler);
        return result;
    }
}
public class MyProxyInvokeAOP {
    public static void main(String[] args){
        try{
            Dog target = new GunDog();
            Dog dog = (Dog)MyProxyFactory.getProxy(target);  //创建动态代理
            //被代理以后的 dog，执行所有方法都会被替换成代理类的方法，而代理类的方法采用反射调用 dog 的原来方法，但是在执行前后的切面节点上，插入了自己的方法。
            dog.info();
            dog.run();
        }catch (Exception e){e.printStackTrace();}
    }
}
