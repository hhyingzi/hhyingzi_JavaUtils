package DesignPatterns.Observer;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/** 重构自《大话设计模式 Java溢彩版 - “老板回来，我不知道”观察者模式》
 * jdk “发布者-订阅者工具”。具体类名：发布者 SubmissionPublisher 类，订阅者实现 Subscriber 接口
 * 故事：员工偷懒买股票，看NBA，老板即将到场。前台先通知偷懒员工（实现前台发通知事件），短暂延迟过后老板亲自到场（实现老板发通知事件）。
 * 使用效果存疑：通知可以正常发送接收，但是取消通知的操作不知道怎么实现，subscription.cancel() 为何运行时提示空指针异常。
 */
/* 发布者 */
abstract class MyPublisher{
    protected String name;
    protected SubmissionPublisher<String> submissionPublisher = new SubmissionPublisher<>();
    public void subscribe(MySubscriber subscriber){ submissionPublisher.subscribe(subscriber);}  //增加订阅者
    public void notifyEmployee(String value){ submissionPublisher.submit(value); }  //给所有登记过的观察者同事发通知
}
//前台秘书提前通知员工
class MySecretaryPublisher extends MyPublisher{
    public MySecretaryPublisher(String name){ this.name = name; }
}
//老板来了
class MyBossPublisher extends MyPublisher{
    public MyBossPublisher(String name){ this.name = name; }
}
/* 订阅者 */
abstract class MySubscriber implements Flow.Subscriber {
    protected String name;
    protected Flow.Subscription subscription ;
    MySubscriber(String name){ this.name = name;}
    public void subscribe(MyPublisher myPublisher){ myPublisher.subscribe(this); }
    public void unSubscribe(){ this.subscription.cancel();}

    @Override  // 订阅时触发该方法，自动接收一个 subscription，这里将其存储为类变量。
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);  // 开始请求数据
    }
    @Override  // 接收到数据时触发该方法
    public abstract void onNext(Object item);
    @Override
    public void onError(Throwable throwable) {System.out.println("订阅失败"); }
    @Override
    public void onComplete() { System.out.println("订阅关闭");}
}
//看股票同事类
class MyStockObserver extends MySubscriber{
    public MyStockObserver(String name){ super(name); }
    @Override
    public void onNext(Object item){
        System.out.println(super.name + ", " + item + "，请关闭股票行情，赶紧工作。");
        subscription.request(1);  // 请求下一条数据
    }
}
//看NBA同事类
class MyNBAObserver extends MySubscriber{
    public MyNBAObserver(String name){ super(name); }
    @Override
    public void onNext(Object item){
        System.out.println(super.name + ", " + item + "，请关闭NBA直播，赶紧工作。");
        subscription.request(1);  // 请求下一条数据
    }
}

public class MyLazyEmployerWithJDKSubmissionPublisherAndSubscriber {
    public static void main(String[] args){
        //发布者
        MyPublisher boss = new MyBossPublisher("大老板");
        MyPublisher secretary = new MySecretaryPublisher("前台");
        //订阅者
        MySubscriber observer1 = new MyStockObserver("股票选手");
        observer1.subscribe(boss); observer1.subscribe(secretary);

        MySubscriber observer2 = new MyNBAObserver("NBA选手");
        observer2.subscribe(boss); observer2.subscribe(secretary);
        //observer2.unSubscribe();  //NBA 选手取消订阅消息怎么做？如果只取消接收前台消息，但是仍旧接收老板消息怎么做？

        secretary.notifyEmployee("老板下车啦");  //秘书先通知
        secretary.notifyEmployee("老板上楼啦");  //秘书先通知
        try { Thread.sleep(100); } catch (Exception e) { e.printStackTrace(); }
        System.out.println("=====");
        boss.notifyEmployee("我是老板");  //老板后抵达
        try { Thread.sleep(100); } catch (Exception e) { e.printStackTrace(); }
    }
}
