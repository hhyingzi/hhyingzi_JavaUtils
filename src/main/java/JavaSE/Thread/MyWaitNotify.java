package JavaSE.Thread;

/**
 * 基于同步监视器 synchronized 的线程锁的通信
 */
public class MyWaitNotify implements Runnable{
    //共享资源类 A 和 B
    class A {
        boolean isLocked = false;
        public synchronized void work(B b ){
            b.isLocked = true;
            System.out.println(Thread.currentThread().getName() + " ---> A.work(b) ---> 已获取 b 的锁");
            try{
                Thread.sleep(200);
                if(b.isLocked) wait(2000);  //如果 b 锁正在被持有，则当前线程阻塞，暂不请求 b 的锁。
                if(b.isLocked){
                    System.out.println(Thread.currentThread().getName() + " 等待超时，自动结束线程。");
                    return;  //执行 finally，并结束运行
                }
                else b.last();  //再请求对对象 b 的锁
            }catch (Exception e){e.printStackTrace();}
            finally {  //释放 this 对象的锁。
                this.isLocked = false;
//                notifyAll(); //方法完成，唤醒所有 wait()
                this.notify();
            }
        }
        public synchronized void last() { System.out.println(Thread.currentThread().getName() + " ---> A.last() ---> 获取 a 的锁，运行成功"); }
    }

    class B {
        boolean isLocked = false;
        public synchronized void work(A a ){
            a.isLocked = true;
            System.out.println(Thread.currentThread().getName() + " ---> B.work(a) ---> 已获取 a 的锁");
            try{
                Thread.sleep(200);
                if(a.isLocked) wait(3000);  //如果 a 锁正在被持有，则当前线程阻塞，暂不请求 a 的锁。
                if(a.isLocked){
                    System.out.println(Thread.currentThread().getName() + " 等待超时，自动结束线程。");
                    return;
                }
                else a.last();  //再请求对对象 a this 的锁
            }catch (Exception e){e.printStackTrace();}
            finally {
                this.isLocked = false;
//                notifyAll(); //方法完成，唤醒所有 wait()
                this.notify();
            }
        }
        public synchronized void last() { System.out.println(Thread.currentThread().getName() + " ---> B.last() ---> 获取 b 的锁，运行成功"); }
    }
    public A a;
    public B b;
    public MyWaitNotify(){ a = new A(); b = new B();}

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
        MyWaitNotify myWaitNotify = new MyWaitNotify();
        //注意，要产生一个死锁，一定要在本线程请求共享资源的语句运行之前，先把线程 Thread-0 打开。
        new Thread(myWaitNotify).start();  //Thread-0，获取锁的顺序，先 A 后 B。
        myWaitNotify.init();  //main，获取锁的顺序，先 B 后 A。
    }
}
