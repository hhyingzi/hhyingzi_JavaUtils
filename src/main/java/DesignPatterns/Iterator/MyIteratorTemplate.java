package DesignPatterns.Iterator;

/**
 * 大话设计模式 - 迭代器模式
 * 模板示例
 */
import java.util.ArrayList;

//聚集抽象类
abstract class Aggregate{
    public abstract Iterator createIterator(); //创建迭代器
}
//具体聚集类，继承Aggregate
class ConcreteAggregate extends Aggregate{
    private ArrayList<Object> items = new ArrayList<Object>(); //声明一个ArrayList泛型变量，用于存放聚合对象
    public Iterator createIterator(){ return new ConcreteIterator(this); }
    public int getCount(){ return items.size(); } //返回聚集总个数
    public void add(Object object){ items.add(object); } //增加新对象
    public Object getCurrentItem(int index){ return items.get(index); } //得到指定索引对象
}
//迭代器抽象类
abstract class Iterator{
    public abstract Object first();         //第一个
    public abstract Object next();          //下一个
    public abstract boolean isDone();       //是否到最后
    public abstract Object currentItem();   //当前对象
}
//具体迭代器类，继承Iterator
class ConcreteIterator extends Iterator{
    private ConcreteAggregate aggregate;
    private int current = 0;

    public ConcreteIterator(ConcreteAggregate aggregate){ this.aggregate = aggregate; } //初始化时将具体的聚集对象传入
    public Object first(){ return aggregate.getCurrentItem(0); } //得到第一个对象
    public Object next() { //得到下一个对象
        Object ret = null;
        current++;
        if (current < aggregate.getCount()) ret = aggregate.getCurrentItem(current);
        return ret;
    }
    public boolean isDone(){ return current >= aggregate.getCount() ? true : false; } //判断当前是否遍历到结尾，到结尾返回true
    public Object currentItem(){ return aggregate.getCurrentItem(current); } //返回当前的聚集对象
}
//具体迭代器类(倒序），继承Iterator
class ConcreteIteratorDesc extends Iterator{
    private ConcreteAggregate aggregate;
    private int current = 0;
    public ConcreteIteratorDesc(ConcreteAggregate aggregate){
        this.aggregate = aggregate;
        current = aggregate.getCount()-1;
    }
    //第一个对象
    public Object first(){ return aggregate.getCurrentItem(aggregate.getCount()-1); }
    //下一个对象
    public Object next() {
        Object ret = null;
        current--;
        if (current >= 0) ret = aggregate.getCurrentItem(current);
        return ret;
    }
    //判断当前是否遍历到结尾，到结尾返回true
    public boolean isDone(){ return current <0 ? true : false; }
    //返回当前的聚集对象
    public Object currentItem(){ return aggregate.getCurrentItem(current); }
}
//主程序入口
public class MyIteratorTemplate {
    public static void main(String[] args){
        ConcreteAggregate bus = new ConcreteAggregate();
        bus.add("大鸟");
        bus.add("小菜");
        bus.add("行李");
        bus.add("老外");
        bus.add("公交内部员工");
        bus.add("小偷");

        //正序迭代器
        //Iterator conductor = new ConcreteIterator(bus);
        //倒序迭代器
        Iterator conductor = new ConcreteIteratorDesc(bus);

        conductor.first();
        while (!conductor.isDone()) {
            System.out.println(conductor.currentItem() + "，请买车票!");
            conductor.next();
        }
    }
}