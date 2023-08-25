package JavaSE.Thread;

/**
 * 演示死锁
 */
//线程 Runnable
public class MyDeadLock implements Runnable {
    //共享资源类 A 和 B
    class A {
        public synchronized void work(B b ){  //主调线程，先请求对对象 B b 的锁
            System.out.println(Thread.currentThread().getName() + " ---> A.work(b) ---> 已获取 b 的锁");
            try { Thread.sleep(200); } catch (InterruptedException ex) { ex.printStackTrace(); }
            b.last();  //再请求对对象 b 的锁
        }
        public synchronized void last() { System.out.println(Thread.currentThread().getName() + " ---> A.last() ---> 获取 a 的锁"); }
    }

    class B {
        public synchronized void work(A a ) {  //主调线程，先请求对对象 A a 的锁
            System.out.println(Thread.currentThread().getName() + " ---> B.work(a) ---> 已获取 a 的锁");
            try { Thread.sleep(200); } catch (InterruptedException ex) { ex.printStackTrace(); }
            a.last();  //再请求对对象 B this 的锁
        }
        public synchronized void last() { System.out.println(Thread.currentThread().getName() + " ---> B.last() ---> 获取 b 的锁"); }
    }

    A a;
    B b;

    public MyDeadLock(){ this.a = new A(); this.b = new B();}
    public void init() {
        System.out.println("进入线程：" + Thread.currentThread().getName());  //Thread-0
        a.work(b);
    }
    @Override
    public void run(){
        System.out.println("进入线程：" + Thread.currentThread().getName());  //main
        b.work(a);
    }
    public static void main(String[] args) {
        MyDeadLock myDeadLock = new MyDeadLock();
        //注意，要产生一个死锁，一定要在本线程请求共享资源的语句运行之前，先把线程 Thread-0 打开。
        new Thread(myDeadLock).start();  //先持有 a 对象的锁，再请求 b 对象的锁。
        myDeadLock.init();  //先持有 b 对象的锁，再请求 a 对象的锁。
    }
}
