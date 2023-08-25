package DesignPatterns.Bridge;

/**
 * 大话设计模式 - 桥接模式
 * 模板示例
 */
//某功能 Implementor 的实现接口
abstract class Implementor{
    public abstract void operation();
}
// Implementor 的实现类 A
class ConcreteImplementorA extends Implementor{
    public void operation(){ System.out.println("具体实现A的方法执行"); }
}
// Implementor 的实现类 B
class ConcreteImplementorB extends Implementor{
    public void operation(){ System.out.println("具体实现B的方法执行"); }
}
//具体事物的实体抽象类 Abstraction，该事物会用到功能 Implementor 的实现类
abstract class Abstraction{
    protected Implementor implementor;
    public void setImplementor(Implementor implementor){ this.implementor = implementor; }
    public abstract void operation();
}
//具体的事物
class RefinedAbstraction extends Abstraction{
    public void operation(){
        System.out.print("具体的Abstraction");
        implementor.operation();
    }
}
//主程序入口
public class MyBridgeTemplate {
    public static void main(String[] args) {
        Abstraction ab = new RefinedAbstraction();

        ab.setImplementor(new ConcreteImplementorA());  //功能的具体实现方式 A
        ab.operation();

        ab.setImplementor(new ConcreteImplementorB());  //功能的具体实现方式 B
        ab.operation();
    }
}
