package MySolutions.LeetCode;

import java.util.ArrayDeque;
import java.util.Deque;

/** 定义栈的数据结构，请在该类型中实现一个能够得到栈的最小元素的 min 函数在该栈中，调用 min、push 及 pop 的时间复杂度都是 O(1)。
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.min();
 */
public class JianZhiOffer_30_MinOfStack {
    public static void main(String[] args){
        MinStack minStack = new MinStack();
        minStack.push(1); minStack.push(-3);minStack.push(5); // 1, -3, 5
        System.out.println(minStack.min());  //-3
        minStack.pop();
        System.out.println(minStack.min());  //-3
        minStack.pop();
        System.out.println(minStack.min());  //1
    }
}
    /**
     * 设置一个辅助栈，入栈一个元素，则计算当其出栈时，对应的最小元素是多少，给入辅助栈中，最小元素就从辅助栈中取
     */
    class MinStack {
        Deque<Integer> data = new ArrayDeque();
        Deque<Integer> minData = new ArrayDeque();

        /** initialize your data structure here. */
        public MinStack() {
            data = new ArrayDeque();
            minData = new ArrayDeque();
        }

        public void push(int x) {
            data.push(x);
            if(minData.isEmpty()) minData.push(x);
            else if(minData.peek() < x) minData.push(minData.peek());
            else minData.push(x);
        }

        public void pop() {
            data.pop();
            minData.pop();
        }

        public int top() {
            return data.peek();
        }

        public int min() {
            return minData.peek();
        }
    }


