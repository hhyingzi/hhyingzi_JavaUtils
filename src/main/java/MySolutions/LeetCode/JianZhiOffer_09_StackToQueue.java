package MySolutions.LeetCode;

import java.util.ArrayDeque;

/** 用两个栈实现一个队列。队列的声明如下，请实现它的两个函数 appendTail 和 deleteHead ，分别完成在队列尾部插入整数和在队列头部删除整数的功能。
 * Your CQueue object will be instantiated and called as such:
 * CQueue obj = new CQueue();
 * obj.appendTail(value);
 * int param_2 = obj.deleteHead();
 */
//用两个栈实现队列
public class JianZhiOffer_09_StackToQueue {
    public static void main(String[] args){
        CQueue cQueue = new CQueue();
        cQueue.appendTail(1);cQueue.appendTail(2);cQueue.appendTail(3); //入队列 1 2 3
        System.out.println(cQueue.deleteHead()); //出队列 1
        cQueue.appendTail(4);cQueue.appendTail(5);  //入队列 4 5
        for(int i=0; i<5; i++)
            System.out.println(cQueue.deleteHead()); //出队列 2 3 4 5 -1
    }
}

class CQueue {
    //栈1，入队列，只需要入队
    private ArrayDeque<Integer> stack1;
    //栈2，出队列，出队只从这里提取，如果全出完了，再从栈1倒过来即可
    private ArrayDeque<Integer> stack2;
    public CQueue() {
        stack1 = new ArrayDeque<>();
        stack2 = new ArrayDeque<>();
    }

    //入队列：新元素插入栈1即可
    public void appendTail(int value) {
        stack1.push(value);
    }

    //出队列：从栈2出栈即可，栈2出完了继续从栈1倒进来。
    public int deleteHead() {
        if(stack1.isEmpty() && stack2.isEmpty()) return -1;
        if(!stack2.isEmpty()) return stack2.pop();
        while(!stack1.isEmpty()) stack2.push(stack1.pop());
        return stack2.pop();
    }
}