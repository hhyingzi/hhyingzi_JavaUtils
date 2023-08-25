package DesignPatterns.Observer;

/**
 * 大话设计模式 - 观察者模式
 * 模板示例
 */
import java.util.ArrayList;

//观察者接口（因为观察者可能是风马牛不相及的类，所以需要用接口来做顶层抽象）
interface Observerable{ public void update();}
//抽象观察者（具体的观察者抽象类）
abstract class Observer implements Observerable { public abstract void update();}
//具体观察者类
class ConcreteObserver extends Observer {
    private String name;
    private Subject sub;
    public ConcreteObserver(String name,Subject sub){
        this.name = name;
        this.sub = sub;
    }
    public void update(){ System.out.println("观察者"+this.name+"的新状态是"+this.sub.getSubjectState()); }
}

//通知者抽象类
abstract class Subject{
    private ArrayList<Observerable> list = new ArrayList<Observerable>();//观察者数组，用接口抽象化以减少耦合
    public void attach(Observerable observer){ list.add(observer); }  //增加观察者
    public void detach(Observerable observer){ list.remove(observer); }  //减少观察者
    public void notifyObserver(){ for(Observerable item : list) item.update(); } //通知观察者
    //通知者自身状态变化设置
    protected String subjectState;
    public void setSubjectState(String value){ this.subjectState = value; }
    public String getSubjectState(){ return this.subjectState; }
}
//具体通知者
class ConcreteSubject extends Subject{ }
//主程序入口
public class MyObserverTemplate {
    public static void main(String[] args){
        Subject subject = new ConcreteSubject();
        subject.attach(new ConcreteObserver("NameX",subject));
        subject.attach(new ConcreteObserver("NameY",subject));
        subject.attach(new ConcreteObserver("NameZ",subject));
        subject.setSubjectState("ABC");
        subject.notifyObserver();  //通知观察者
    }
}
