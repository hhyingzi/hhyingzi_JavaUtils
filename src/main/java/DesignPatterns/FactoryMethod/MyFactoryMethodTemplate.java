package DesignPatterns.FactoryMethod;

//产品抽象类 Product
abstract class Product {
    public abstract void make();
}
//A 产品类：ConcreteProductA
class ConcreteProductA extends Product {
    public void make(){ System.out.println("产品A制造"); }
}
//B 产品类：ConcreteProductB
class ConcreteProductB extends Product {
    public void make(){ System.out.println("产品B制造"); }
}
//工厂抽象类 Creator
abstract class Creator {
    public abstract Product factoryMethod();
}
//生产 A 的工厂：ConcreteCreatorA
class ConcreteCreatorA extends Creator{
    public Product factoryMethod(){ return new ConcreteProductA(); }
}
//生产 B 的工厂：ConcreteCreatorB
class ConcreteCreatorB extends Creator{
    public Product factoryMethod(){ return new ConcreteProductB(); }
}
//主程序入口
public class MyFactoryMethodTemplate {
    public static void main(String[] args){
        Creator[] creators = new Creator[2];  //工厂类
        creators[0] = new ConcreteCreatorA();  //A 的工厂
        creators[1] = new ConcreteCreatorB();  //B 的工厂
        for(Creator item : creators){
            Product product = item.factoryMethod();  //用工厂们生产产品
            product.make();
        }
    }
}
