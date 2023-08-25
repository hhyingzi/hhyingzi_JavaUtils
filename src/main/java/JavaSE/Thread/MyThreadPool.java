package JavaSE.Thread;

import java.util.concurrent.*;

public class MyThreadPool {
    class MyRunnable implements Runnable{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            try{Thread.sleep(500);}catch (Exception e){e.printStackTrace();}
        }
    }

    class MyCallable implements Callable<String>{
        @Override
        public String call() throws Exception {
            try{Thread.sleep(500);}catch (Exception e){e.printStackTrace();}
            return Thread.currentThread().getName();
        }
    }

    public static void main(String[] args){
        MyThreadPool myThreadPool = new MyThreadPool();
        //1-CachedThreadPool
        ExecutorService executor1 = Executors.newCachedThreadPool();
        for(int i=0; i<5; i++) executor1.execute(myThreadPool.new MyRunnable());
        executor1.shutdown();
        //1 Callable
        ExecutorService executor1_1 = Executors.newCachedThreadPool();
        for(int i=0; i<5; i++){
            Future<String> futureTask1_1 = executor1_1.submit(myThreadPool.new MyCallable());
            try{
                System.out.println(futureTask1_1.get());
            } catch (Exception e){e.printStackTrace();}
        }
        executor1_1.shutdown();

        //2-FixedThreadPool
        ExecutorService executor2 = Executors.newFixedThreadPool(3);  //固定运行5个，满了等待前面运行完然后复用
        for(int i=0; i<5; i++) executor2.execute(myThreadPool.new MyRunnable());
        executor2.shutdown();

        //3-ScheduledThreadPool

        //4 SingleThreadExecutor
        ExecutorService executor4 = Executors.newSingleThreadExecutor();
        for(int i=0; i<5; i++) executor4.execute(myThreadPool.new MyRunnable());
        executor4.shutdown();

        //5 ScheduledThreadPool
        ScheduledExecutorService executor5 = Executors.newScheduledThreadPool(2);
        for(int i=0; i<5; i++) executor5.schedule(myThreadPool.new MyRunnable(), 3, TimeUnit.SECONDS);
        executor5.shutdown();

        //5_1 SingleThreadScheduledExecutor
        ScheduledExecutorService executor5_1 = Executors.newSingleThreadScheduledExecutor();
        for(int i=0; i<5; i++) executor5_1.schedule(myThreadPool.new MyRunnable(), 3, TimeUnit.SECONDS);
        executor5_1.shutdown();

        //6 WorkStealingPool  ,stealing:窃取
        ExecutorService excutor6 = Executors.newWorkStealingPool();  //默认根据 CPU 数量 return new ForkJoinPool(Runtime.getRuntime().availableProcessors(), ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);
        for(int i=0; i<5; i++) excutor6.execute(myThreadPool.new MyRunnable());
        excutor6.shutdown();
        try{Thread.sleep(500);}catch (Exception e){e.printStackTrace();}  //必须要 sleep，因为 WorkStealingPool 是后台线程。

        //7-ForkJoinPool, 参考：https://www.cnblogs.com/zhangmingda/p/14710284.html，执行计算任务
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        for(int i=0; i<5; i++) forkJoinPool.submit(myThreadPool.new MyRunnable());
        forkJoinPool.shutdown();
        try{Thread.sleep(500);}catch (Exception e){e.printStackTrace();}
    }
}
