package MySolutions.LeetCode;

import java.util.ArrayDeque;
import java.util.Arrays;

/**
 * 输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。
 * 输入：head = [1,3,2]
 * 输出：[2,3,1]
 */
// 我的方法，空间复杂度 O(n)，时间复杂度 O(n)
// 更好的方法：反转链表。空间复杂度O(1)，时间复杂度 O(n)。
class Solution_06 {
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public int[] reversePrint(ListNode head) {
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        while(head != null){
            stack.push(head.val);
            head = head.next;
        }
        int[] arr = new int[stack.size()];
        for(int i=0; i<arr.length; i++){
            arr[i] = stack.pop();
        }
        return arr;
    }
}
public class JianZhiOffer_06_LinkedlistToArrayReverse {
    public static void main(String[] args){
        Solution_06 solution06 = new Solution_06();
        Solution_06.ListNode head;
        Solution_06.ListNode now = new Solution_06.ListNode(1);  head = now;
        now.next = new Solution_06.ListNode(3); now = now.next;
        now.next = new Solution_06.ListNode(2); now = now.next;
        System.out.println(Arrays.toString(solution06.reversePrint(head)));
    }
}
