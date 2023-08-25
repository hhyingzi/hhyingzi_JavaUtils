package DesignPatterns.Prototype;
/*原型模式模板*/
//原型类
abstract class Prototype implements Cloneable {
    private String id;
    public Prototype(String id){ this.id=id; }
    public String getID(){ return this.id; }
    //原型模式的关键就是有这样一个clone方法
    @Override
    public Object clone(){
        Object object = null;
        try { object = super.clone(); }
        catch(CloneNotSupportedException exception){ System.err.println("Clone异常。"); }
        return object;
    }
}
//具体原型类
class ConcretePrototype extends Prototype{
    public ConcretePrototype(String id){ super(id); }
}
public class MyPrototypeTemplate {
    public static void main(String[] args){
        ConcretePrototype p1 = new ConcretePrototype("编号123456");
        System.out.println("原ID:"+ p1.getID());

        ConcretePrototype c1 = (ConcretePrototype)p1.clone();
        System.out.println("克隆ID:"+ c1.getID());
    }
}
