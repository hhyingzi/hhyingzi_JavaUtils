package JavaSE.Thread;

import java.util.ArrayDeque;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基于 Lock 的线程锁通信，Condition.await(), Condition.signal()
 */
class MyLockAwaitSignal implements Runnable{
    final ArrayDeque<Object> apples = new ArrayDeque();  //一个苹果队列，必须放了苹果，才能取出苹果
    final int MAX_COUNT = 3;  // apples 队列最多储存3个

    final Lock lock = new ReentrantLock();
    final Condition fullCondition = lock.newCondition();  //放满了 MAX_COUNT 个，则不能继续放，必须 fullCondition.await()
    final Condition emptyCondition = lock.newCondition();  //0个，则不能继续取出，必须 emptyCondition.await()

    //放入苹果，如果苹果满了，则必须阻塞，等待取出。
    public void putApple() throws InterruptedException {
        lock.lock();
        System.out.print(Thread.currentThread().getName() + " -> putApple()：" + apples.size());
        try {
            if (apples.size() == MAX_COUNT){
                System.out.println(" -----> 已经满 " + MAX_COUNT + " 个了。等待取出。");
                fullCondition.await(1, TimeUnit.SECONDS);
                //如果线程阻塞超时，则取消该动作
                if(apples.size() == MAX_COUNT){
                    System.out.println("放弃 putApple() 操作。执行下一个随机动作。");
                    return;
                }
            }
            apples.add(new Object());  //放入一个苹果
            System.out.println(Thread.currentThread().getName() + " -> putApple() 成功， " + " -----> 剩余 " + apples.size() + "个");
            emptyCondition.signal();  //释放 takeApple() 线程
        } finally { lock.unlock(); }
    }
    //取出苹果，如果苹果数为0，则必须阻塞等待放入。
    public void takeApple() throws InterruptedException {
        lock.lock();
        System.out.print(Thread.currentThread().getName() + " -----> takeApple()：" + apples.size());
        try {
            if (apples.size() == 0){
                System.out.println(" -----> 没有苹果了。等待添加。");
                fullCondition.await(1, TimeUnit.SECONDS);
                //如果线程阻塞超时，则取消该动作
                if(apples.size() == 0){
                    System.out.println("放弃 takeApple() 操作。执行下一个随机动作。");
                    return;
                }
            }
            apples.removeFirst();  //取出一个苹果
            System.out.println(Thread.currentThread().getName() + " -> takeApple() 成功，" + " -----> 剩余 " + apples.size() + "个");
            fullCondition.signal();  //取出苹果，可以释放 putApple() 线程
        } finally { lock.unlock(); }
    }

    @Override
    public void run(){
        try {
            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                Thread.sleep(20);
                if (random.nextBoolean()) putApple();
                else takeApple();
            }
        }catch (InterruptedException interruptedException){ return; }
    }
    public static void main(String[] args) {
        MyLockAwaitSignal myLockAwaitSignal = new MyLockAwaitSignal();
        new Thread(myLockAwaitSignal).start();
        new Thread(myLockAwaitSignal).start();
        new Thread(myLockAwaitSignal).start();
    }
}