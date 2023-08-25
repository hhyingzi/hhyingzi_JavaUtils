package JavaSE.Thread.ConcurrentUtils;

import java.util.concurrent.Flow.*;
import java.util.*;
import java.util.concurrent.*;

/* 发布者-订阅者模式，核心原理就是使用一个集合 BufferedSubscription<T> clients 来存储所有的订阅者类，当发布消息的时候遍历这个集合，并调用集合中的每一个订阅者类的通知方法 */
class PubSubTest {
    public static void doPublish() {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();  // 创建一个发布者
        MySubscriber<String> subscriber = new MySubscriber<>();  // 创建订阅者
        publisher.subscribe(subscriber);  //向发布者 publisher 注册订阅者 subscriber
        // 发布几个数据项
        System.out.println("开发发布数据...");
        List.of("Java", "Kotlin", "Go", "Erlang", "Swift", "Lua").forEach(im -> {
            publisher.submit(im);  // 提交数据
            try { Thread.sleep(500); } catch (Exception e) { e.printStackTrace(); }
        });
        publisher.close();  // 发布结束
        // 发布结束后，为了让发布者线程不会死亡，暂停线程
        synchronized ("fkjava") {
            try { "fkjava".wait(); } catch (Exception e){ e.printStackTrace(); }
        }
    }
}

// 创建订阅者
class MySubscriber<T> implements Subscriber<T> {
    private Subscription subscription;  // 发布者与订阅者之间的纽带
    @Override  // 订阅时触发该方法
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);  // 开始请求数据
    }
    @Override  // 接收到数据时触发该方法
    public void onNext(T item) {
        System.out.println("获取到数据: " + item);
        subscription.request(1);  // 请求下一条数据
    }
    @Override // 订阅出错时触发该方法
    public void onError(Throwable t) {
        t.printStackTrace();
        synchronized ("fkjava") { "fkjava".notifyAll(); }
    }
    @Override  // 订阅结束时触发该方法
    public void onComplete() {
        System.out.println("订阅结束");
        synchronized ("fkjava") { "fkjava".notifyAll(); }
    }
}

public class PublisherSubscriber {
    public static void main(String args[]) {
        PubSubTest.doPublish();
    }
}