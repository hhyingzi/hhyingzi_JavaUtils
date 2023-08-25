package DesignPatterns.Builder;

/**
 * 大话设计模式 - 建造者模式
 * 模板代码
 */
import java.util.ArrayList;

//产品类，假设本案例要求一个完整的产品 Product ，一定是由两个字符串 partA, partB 组成的。
class Product{
    ArrayList<String> parts = new ArrayList<String>();  //产品部件列表
    public void add(String part){ parts.add(part); }  //增加一个部件，生成一个完整产品需要增加两次。
    public void show(){ for(String part : parts) System.out.println(part); }  //列举所有产品部件
}

//抽象的建造者类：建造者知道一定要增加两个部件 partA, pargB，才能得到产品 Product ，那么这两个部件各抽象出一个生成方法，子类必须全都实现。
abstract class Builder {
    public abstract void buildPartA();      //建造部件A
    public abstract void buildPartB();      //建造部件B
    public abstract Product getResult();    //得到产品
}
//具体建造者1
class ConcreteBuilder1 extends Builder {
    private Product product = new Product();
    public void buildPartA(){ product.add("部件A"); }
    public void buildPartB(){ product.add("部件B"); }
    public Product getResult(){ return product; }
}
//具体建造者2
class ConcreteBuilder2 extends Builder {
    private Product product = new Product();
    public void buildPartA(){ product.add("部件X"); }
    public void buildPartB(){ product.add("部件Y"); }
    public Product getResult(){ return product; }
}
//指挥者
class Director{
    //指挥者来负责创建一个完整产品 Product
    public void construct(Builder builder){
        builder.buildPartA();  //指挥者知道要创建A
        builder.buildPartB();  //指挥者知道要创建B
    }
}
public class MyBuilderTemplate {
    public static void main(String[] args){
        Director director = new Director();
        Builder b1 = new ConcreteBuilder1();
        Builder b2 = new ConcreteBuilder2();

        //指挥者用ConcreteBuilder1的方法来建造产品
        director.construct(b1); //创建的是产品A和产品B
        Product p1 = b1.getResult();
        p1.show();

        //指挥者用ConcreteBuilder2的方法来建造产品
        director.construct(b2); //创建的是产品X和产品Y
        Product p2 = b2.getResult();
        p2.show();
    }
}