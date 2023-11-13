package MySolutions.InterviewProgramming.JavaSE;

//num 初始值为 0，要求实现两个线程，一个每次对 num +3，另一个每次对 num -2。
//要点：对运算方法 add() sub() 加锁，否则 num 会出现一致性问题。
public class MinTai_20230430_MultiThread {
    private int num;
    public MinTai_20230430_MultiThread(){
        num = 0;
        System.out.println("num = " + num);
    }

    public synchronized void add(){
        num = num + 3;
        System.out.println("num + 3 = " + this.num);
    }
    public synchronized void sub(){
        num = num - 2;
        System.out.println("num - 2 = " + this.num);
    }

    class T1 implements Runnable{
        @Override
        public void run() {
            for(int i=0; i<10; i++){
                try{ Thread.sleep(100); }catch (Exception e){ e.printStackTrace();}
                add();
            }
        }
    }

    class T2 implements Runnable{
        @Override
        public void run() {
            for(int i=0; i<10; i++){
                try{ Thread.sleep(100); }catch (Exception e){ e.printStackTrace();}
                sub();
            }
        }
    }

    public static void main(String[] args)throws Exception{
        MinTai_20230430_MultiThread test = new MinTai_20230430_MultiThread();

        MinTai_20230430_MultiThread.T1 t1 = test.new T1();
        MinTai_20230430_MultiThread.T2 t2 = test.new T2();

        new Thread(t1).start();
        new Thread(t2).start();
    }
}
