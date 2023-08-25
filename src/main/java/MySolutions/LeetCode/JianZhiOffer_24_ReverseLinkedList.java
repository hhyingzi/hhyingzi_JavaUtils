package MySolutions.LeetCode;

/**
 * 定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点。
 * 输入: 1->2->3->4->5->NULL
 * 输出: 5->4->3->2->1->NULL
 */

class Solution_24 {
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
    //head遍历旧链表，创建新链表，用头插法插入每个节点
    //newHead 代表新节点头
    //temp 暂存head
    public ListNode reverseList(ListNode head) {
        ListNode newHead = null, temp;
        while(head != null){
            temp = head;
            head = head.next;
            temp.next = newHead;
            newHead = temp;
        }
        return newHead;
    }
}
public class JianZhiOffer_24_ReverseLinkedList {
    public static void main(String[] args){
        Solution_24 solution = new Solution_24();
        Solution_24.ListNode head = new Solution_24.ListNode(1);
        Solution_24.ListNode curr = head;
        for(int i=2; i<=5; i++){
            Solution_24.ListNode temp = new Solution_24.ListNode(i);
            curr.next = temp;
            curr = temp;
        }
        Solution_24.ListNode newHead = solution.reverseList(head);
        while(newHead != null){
            System.out.println(newHead.val);
            newHead = newHead.next;
        }
    }
}
