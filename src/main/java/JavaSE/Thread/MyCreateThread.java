package JavaSE.Thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

// 通过继承Thread类来创建线程类
public class MyCreateThread{
    class MyThread1 extends Thread{
        @Override
        public void run() { System.out.println(this.getName()); }
    }

    class MyThread2 implements Runnable{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }

    class MyThread3 implements Callable<String>{
        @Override
        public String call(){
            return Thread.currentThread().getName();
        }
    }

    public static void main(String[] args){
        MyCreateThread myCreateThread = new MyCreateThread();

        //通过继承 Thread 创建线程 Thread-0
        myCreateThread.new MyThread1().start();

        //通过 Runnable 接口创建线程 Thread-1
        new Thread(myCreateThread.new MyThread2()).start();

        //通过 Callable 接口创建线程 Thread-2
        FutureTask<String> futureTask = new FutureTask<>(myCreateThread.new MyThread3());
        new Thread(futureTask).start();
        try{
            System.out.println(futureTask.get());
        }catch (Exception e){e.printStackTrace();}

        /* =====Lambda 方式===== */
        //通过继承 Thread 创建线程 Thread-3
        new Thread(){
            @Override
            public void run(){
                System.out.println(this.getName());
            }
        }.start();

        //通过 Runnable 接口创建线程 Thread-4
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }).start();

        //通过 Callable 接口创建线程 Thread-5
        FutureTask<String> futureTask1 = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return Thread.currentThread().getName();
            }
        });
        new Thread(futureTask1).start();
        try{
            System.out.println(futureTask1.get());
        }catch (Exception e){e.printStackTrace();}
    }
}
