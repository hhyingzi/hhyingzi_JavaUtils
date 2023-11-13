package MySolutions.InterviewProgramming.JavaSE;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//票务系统一共 60张票，有 A B C 三个窗口，他们一直卖票直到卖完为止。
public class SaleTicketDemo01_MultiThread {
    class Ticket{
        //使用lock锁来控制线程进行
        Lock lock = new ReentrantLock();
        private int ticketNumber = 60;
        public void sale() {
            lock.lock();//先加锁
            try {
                if(ticketNumber > 0) {
                    --ticketNumber;
                    System.out.println(Thread.currentThread().getName() + "卖出了" + (60-ticketNumber) + "张票，剩余：" + ticketNumber + "张票");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();//后解锁
            }
        }
    }

    public static void main(String[] args) {
        SaleTicketDemo01_MultiThread.Ticket ticket = new SaleTicketDemo01_MultiThread().new Ticket();

        for(int i=0; i<1000; i++){
            new Thread(() -> { ticket.sale(); }, "A").start();
            new Thread(() -> { ticket.sale(); }, "B").start();
            new Thread(() -> { ticket.sale(); }, "C").start();
        }
    }
}

