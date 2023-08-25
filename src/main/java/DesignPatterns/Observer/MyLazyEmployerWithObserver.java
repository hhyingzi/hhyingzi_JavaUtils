package DesignPatterns.Observer;

/**
 * 大话设计模式 - 观察者模式
 * 员工偷懒买股票，看NBA。前台或老板负责通知。
 */

import java.util.ArrayList;

//抽象通知者
abstract class Subject1{
    protected String name;
    public Subject1(String name){ this.name = name; }
    private ArrayList<Observer1> list = new ArrayList<>();  //同事列表，针对抽象的Observer编程
    public void attach(Observer1 observer1){ list.add(observer1); }  //增加同事（有几个同事需要秘书通知，就增加几个对象）
    public void detach(Observer1 observer1){ list.remove(observer1); }  //减少同事
    public void notifyEmployee(){ for(Observer1 item : list) item.update(); }  //给所有登记过的观察者同事发通知
    //通知者自身的状态变化
    private String action;
    public void setAction(String value){ this.action = value; }  //设置状态（就是设置具体通知的话）
    public String getAction(){ return this.action; }  //通知
}
//老板
class Boss extends Subject1{
    public Boss(String name){ super(name); }
    //拥有自己的方法和属性
}
//前台类
class Secretary extends Subject1{
    public Secretary(String name){ super(name); }
    //拥有自己的方法和属性
}

//抽象观察者
abstract class Observer1{
    protected String name;
    protected Subject1 sub;
    public Observer1(String name,Subject1 sub){
        this.name = name;
        this.sub = sub;
    }
    public abstract void update();
}
//看股票同事类
class StockObserver extends Observer1{
    public StockObserver(String name,Subject1 sub){ super(name, sub); }
    public void update(){ System.out.println(super.sub.name+"："+super.sub.getAction()+"！"+super.name+"，请关闭股票行情，赶紧工作。"); }
}
//看NBA同事类
class NBAObserver extends Observer1{
    public NBAObserver(String name,Subject1 sub){ super(name,sub); }
    public void update(){ System.out.println(super.sub.name+"："+super.sub.getAction()+"！"+super.name+"，请关闭NBA直播，赶紧工作。"); }
}
//程序主入口
public class MyLazyEmployerWithObserver {
    public static void main(String[] args){
        //老板胡汉三
        Subject1 boss1 = new Boss("胡汉三");

        //看股票的同事
        Observer1 employee1 = new StockObserver("魏关姹",boss1);
        Observer1 employee2 = new StockObserver("易管查",boss1);
        //看NBA的同事
        Observer1 employee3 = new NBAObserver("兰秋幂",boss1);

        //老板登记下三个同事
        boss1.attach(employee1);
        boss1.attach(employee2);
        boss1.attach(employee3);

        boss1.detach(employee1); //魏关姹其实没有被通知到，所有减去

        //老板回来
        boss1.setAction("我胡汉三回来了");
        //通知两个同事
        boss1.notifyEmployee();
    }
}