package DesignPatterns.TemplateMethod;

/**
 * 大话设计模式 - 模板方法模式
 * 模板
 * */
//模板方法抽象类
abstract class AbstractClass {
    //模板方法
    public void templateMethod() {
        //写一些可以被子类共享的代码
        this.primitiveOperation1();
        this.primitiveOperation2();
    }
    public abstract void primitiveOperation1(); //子类个性的行为，放到子类去实现
    public abstract void primitiveOperation2(); //子类个性的行为，放到子类去实现
}
//模板方法具体类A
class ConcreteClassA extends AbstractClass {
    public void primitiveOperation1(){ System.out.println("具体类 A，方法1 的实现"); }
    public void primitiveOperation2(){ System.out.println("具体类 A，方法2 的实现"); }
}
//模板方法具体类B
class ConcreteClassB extends AbstractClass {
    public void primitiveOperation1(){ System.out.println("具体类 B，方法1 的实现"); }
    public void primitiveOperation2(){ System.out.println("具体类 B，方法2 的实现"); }
}
public class MyTemplateMethodTemplate {
    public static void main(String[] args){
        AbstractClass classA = new ConcreteClassA();
        classA.templateMethod();
        AbstractClass classB = new ConcreteClassB();
        classB.templateMethod();
    }
}
