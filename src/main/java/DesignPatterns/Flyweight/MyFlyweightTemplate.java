package DesignPatterns.Flyweight;

/**
 * 大话设计模式 - 享元模式
 * 模板代码
 */
import java.util.Hashtable;
//享元抽象类 Flyweight
abstract class Flyweight {
    public abstract void operation(int extrinsicstate);
}
//需要共享的具体Flyweight子类
class ConcreteFlyweight extends Flyweight {
    public void operation(int extrinsicstate){ System.out.println("具体Flyweight:"+extrinsicstate); }
}
//不需要共享的Flyweight子类
class UnsharedConcreteFlyweight extends Flyweight {
    public void operation(int extrinsicstate){ System.out.println("不共享的具体Flyweight:"+extrinsicstate); }
}
//享元工厂
class FlyweightFactory {
    private Hashtable<String,Flyweight> flyweights = new Hashtable<>();  //享元数组<享元id，享元实例Flyweight>
    public FlyweightFactory(){  //事先创建好所有享元实例，用的时候就不用新建了
        flyweights.put("X", new ConcreteFlyweight());
        flyweights.put("Y", new ConcreteFlyweight());
        flyweights.put("Z", new ConcreteFlyweight());
    }
    public Flyweight getFlyweight(String key) { return (Flyweight)flyweights.get(key); }  //直接从Map中获取对应id为 key 的享元实例
}
public class MyFlyweightTemplate {
    public static void main(String[] args) {
        int extrinsicstate = 22;
        FlyweightFactory f = new FlyweightFactory();  //享元工厂

        Flyweight fx = f.getFlyweight("X");  //获取并创建一个享元实例 “X”
        fx.operation(--extrinsicstate);  //享元操作外部状态（共享状态） extrinsicstate减一

        Flyweight fy = f.getFlyweight("Y");
        fy.operation(--extrinsicstate);

        Flyweight fz = f.getFlyweight("Z");
        fz.operation(--extrinsicstate);

        Flyweight uf = new UnsharedConcreteFlyweight();

        uf.operation(--extrinsicstate);
    }
}
