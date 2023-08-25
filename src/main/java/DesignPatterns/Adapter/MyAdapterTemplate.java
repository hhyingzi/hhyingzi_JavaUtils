package DesignPatterns.Adapter;

/**
 * 大话设计模式 - 适配器模式
 * 模板示例
 */
//需要适配的类
class Adaptee {
    public void specificRequest(){ System.out.println("特殊请求！"); }
}
//客户期待的接口
class Target {
    public void request(){ System.out.println("普通请求！"); }
}
//适配器类
class Adapter extends Target {
    private Adaptee adaptee = new Adaptee(); //建立一个私有的Adaptee对象
    public void request(){ adaptee.specificRequest(); } //这样就可以把表面上调用request()方法，变成实际调用specificRequest()
}
//主程序入口
public class MyAdapterTemplate {
    public static void main(String[] args){
        Target target = new Adapter();
        target.request();
    }
}
