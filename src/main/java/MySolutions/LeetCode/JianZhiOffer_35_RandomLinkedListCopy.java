package MySolutions.LeetCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * 请实现 copyRandomList 函数，复制一个复杂链表。在复杂链表中，每个节点除了有一个 next 指针指向下一个节点，还有一个 random 指针指向链表中的任意节点或者 null。
 */
public class JianZhiOffer_35_RandomLinkedListCopy {
    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }
    class Solution {
        //笨办法，由于最后一个 for 循环中，使用 oldList.indexOf() 寻找元素的索引，造成整体时间复杂度是 O(N^2)。
        public Node copyRandomList(Node head) {
            if(head==null) return null;
            ArrayList<Node> oldList = new ArrayList<>();
            ArrayList<Node> list = new ArrayList<>();
            Node curr = head;
            //将旧节点装进 oldList，创建新节点（只初始化了val值）装进 list
            while(curr != null){
                oldList.add(curr);
                Node newNode = new Node(curr.val);
                list.add(newNode);
                curr = curr.next;
            }
            //找到oldList每个节点的 random节点的索引号，用该索引找到 list 中相应节点的 random 对应节点
            for(int i=0; i<list.size(); i++){
                //Node temp = list.get(i);

                if(i < list.size()-1) list.get(i).next = list.get(i+1);
                else list.get(i).next = null;

                Node oldRandomNode = oldList.get(i).random;
                if(oldRandomNode == null) list.get(i).random = null;
                else list.get(i).random = list.get(oldList.indexOf(oldRandomNode));
            }
            return list.get(0);
        }
        //好办法，用 HashMap 建立旧节点和新节点之间的对应
        public Node copyRandomList2(Node head) {
            if(head==null) return null;
            HashMap<Node, Node> map = new HashMap<>();
            Node curr = head;
            while(curr != null){
                map.put(curr, new Node(curr.val));
                curr = curr.next;
            }
            for(Node key: map.keySet()){
                Node currNode = map.get(key);
                currNode.next = map.get(key.next);
                currNode.random = map.get(key.random);
            }
            return map.get(head);
        }
    }

    public static void main(String[] args){
        JianZhiOffer_35_RandomLinkedListCopy outerClass = new JianZhiOffer_35_RandomLinkedListCopy();
        JianZhiOffer_35_RandomLinkedListCopy.Node node0 = outerClass.new Node(7);
        JianZhiOffer_35_RandomLinkedListCopy.Node node1 = outerClass.new Node(13);
        JianZhiOffer_35_RandomLinkedListCopy.Node node2 = outerClass.new Node(11);
        JianZhiOffer_35_RandomLinkedListCopy.Node node3 = outerClass.new Node(10);
        JianZhiOffer_35_RandomLinkedListCopy.Node node4 = outerClass.new Node(1);
        node0.next = node1; node0.random = null;
        node1.next = node2; node1.random = node0;
        node2.next = node3; node2.random = node4;
        node3.next = node4; node3.random = node2;
        node4.next = null; node4.random = node0;
        Node result = outerClass.new Solution().copyRandomList(node0);
        while(result!=null){
            System.out.print("val=" + result.val);
            if(result.next != null) System.out.print(", next=" + result.next.val);
            else System.out.print(", next=null");
            if(result.random != null) System.out.println(", random=" + result.random.val);
            else System.out.println(", random=null");
            result = result.next;
        }
    }
}
