package DesignPatterns.Mediator;

/**
 * 大话设计模式 - 中介者模式
 * 模板代码示例
 */
//抽象同事类
abstract class Colleague {
    protected Mediator mediator;  //中介者对象
    public Colleague(Mediator mediator){ this.mediator = mediator; } //构造方法，得到中介者对象
}
//同事实现类 ConcreteColleague1
class ConcreteColleague1 extends Colleague {
    public ConcreteColleague1(Mediator mediator) { super(mediator); }
    public void send(String message) { this.mediator.send(message, this); }
    public void notify(String message) { System.out.println("同事1得到信息:" + message); }
}
//同事实现类 ConcreteColleague2
class ConcreteColleague2 extends Colleague {
    public ConcreteColleague2(Mediator mediator) { super(mediator); }
    public void send(String message) { this.mediator.send(message, this); }
    public void notify(String message){ System.out.println("同事2得到信息:" + message); }
}

//中介者抽象类
abstract class Mediator{
    public abstract void send(String message,Colleague colleague); //中介者接收 colleague 同事的消息 message，然后转发给其他同事
}
//中介者实现类
class ConcreteMediator extends Mediator{
    private ConcreteColleague1 colleague1;  //同事1的实例
    private ConcreteColleague2 colleague2;  //同事2的实例
    public void setColleague1(ConcreteColleague1 value) { this.colleague1 = value; }
    public void setColleague2(ConcreteColleague2 value) { this.colleague2 = value; }
    public void send(String message, Colleague colleague) {  //中介者接收某同事 colleague 的消息 message，并转发给别人
        if (colleague == colleague1) colleague2.notify(message);
        else colleague1.notify(message);
    }
}
public class MyMediatorTemplate {
    public static void main(String[] args) {
        ConcreteMediator m = new ConcreteMediator();

        ConcreteColleague1 c1 = new ConcreteColleague1(m);
        ConcreteColleague2 c2 = new ConcreteColleague2(m);

        m.setColleague1(c1);
        m.setColleague2(c2);

        c1.send("吃过饭了吗?");
        c2.send("没有呢，你打算请客？");
    }
}

