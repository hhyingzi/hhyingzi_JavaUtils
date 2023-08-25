package DesignPatterns.Observer;

import java.util.Observable;
import java.util.Observer;

// //jdk中Observer代码
// public interface Observer {
//     void update(Observable o, Object arg);
// }

// //jdk中Observable代码
// public class Observable {
//     private boolean changed = false;
//     private Vector obs;
//     public Observable() {
//         obs = new Vector();
//     }
//     public synchronized void addObserver(Observer o) {
//         if (o == null)
//             throw new NullPointerException();
//         if (!obs.contains(o)) {
//             obs.addElement(o);
//         }
//     }
//     public synchronized void deleteObserver(Observer o) {
//         obs.removeElement(o);
//     }
//     public void notifyObservers() {
//         notifyObservers(null);
//     }
//     public void notifyObservers(Object arg) {
//         Object[] arrLocal;

//         synchronized (this) {
//             if (!changed)
//                 return;
//             arrLocal = obs.toArray();
//             clearChanged();
//         }

//         for (int i = arrLocal.length-1; i>=0; i--)
//             ((Observer)arrLocal[i]).update(this, arg);
//     }
//     public synchronized void deleteObservers() {
//         obs.removeAllElements();
//     }
//     protected synchronized void setChanged() {
//         changed = true;
//     }
//     protected synchronized void clearChanged() {
//         changed = false;
//     }
//     public synchronized boolean hasChanged() {
//         return changed;
//     }
//     public synchronized int countObservers() {
//         return obs.size();
//     }
// }

//Subject2
class Subject2 extends java.util.Observable{
    protected String name;
    private String action;

    public Subject2(String name){ this.name = name; }
    //得到状态
    public String getAction(){ return this.action; }
    //设置状态（就是设置具体通知的话）
    public void setAction(String value){
        this.action = value;
        setChanged();
        notifyObservers();
    }
}
//老板
class Boss2 extends Subject2 {
    public Boss2(String name){ super(name); }
}
//看股票同事类
class StockObserver2 implements java.util.Observer{
    protected String name;
    public StockObserver2(String name){ this.name = name; }
    public void update(Observable o, Object arg){
        Subject2 b=(Subject2)o;
        System.out.println(b.name+"："+b.getAction()+"！"+this.name+"，请关闭股票行情，赶紧工作。");
    }
}
//看NBA同事类
class NBAObserver2 implements java.util.Observer{
    protected String name;
    public NBAObserver2(String name){ this.name = name; }
    public void update(Observable o, Object arg){
        Subject2 b=(Subject2)o;
        System.out.println(b.name+"："+b.getAction()+"！"+this.name+"，请关闭NBA直播，赶紧工作。");
    }
}
public class MyLazyEmployerWithJDKObserver {
    public static void main(String[] args){
        //老板胡汉三
        Boss2 boss2 = new Boss2("胡汉三");
        //看股票的同事
        java.util.Observer employee1 = new StockObserver2("魏关姹");
        java.util.Observer employee2 = new StockObserver2("易管查");
        java.util.Observer employee3 = new NBAObserver2("兰秋幂");
        //老板登记下三个同事
        boss2.addObserver(employee1);
        boss2.addObserver(employee2);
        boss2.addObserver(employee3);
        boss2.deleteObserver(employee1); //魏关姹其实没有被通知到，减去
        //老板回来
        boss2.setAction("我胡汉三回来了");
    }
}
