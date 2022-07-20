package MyDataStructure.Sort;

import java.util.Scanner;

/**
 * 归并排序链表版
 * */
public class MyMergeSortList {
    private Node head;
    private int size;
    private class Node{
        int data;
        Node next;
    }

    //头插法，将int数组转化为链表
    public Node getList(int[] arr){
        Node head = null;
        for(int i=arr.length-1; i>=0; i--){  //倒序读取数组，头插后即为正序链表
            Node newNode = new Node();
            newNode.data = arr[i];
            newNode.next = head;
            head = newNode;
        }
        return head;
    }

    //链表归并
    public Node mergeSortList(Node head){
        head = sort(head);
        return head;
    }

    public Node sort(Node left){
        if(left == null || left.next == null) return left;
        //寻找链表中点，slow 1步, fast 2步，当 fast 走到头时，slow 即为 mid。
        Node slow = left, fast = left;
        while(fast!= null && fast.next != null && fast.next.next!=null){
            fast = fast.next.next;
            slow = slow.next;
        }
        //拆分链表，让左边部分以 null 结尾
        Node right = slow.next;
        slow.next = null;

        //归并排序
        left = sort(left);
        right = sort(right);
        left = merge(left, right);
        return left;
    }

    public Node merge(Node left, Node right){
        Node temp = new Node();
        Node tempHead = temp;
        while(left!=null && right!=null){
            if(left.data <= right.data){
                temp.next = left;
                temp = temp.next;
                left = left.next;
            }
            else{
                temp.next = right;
                temp = temp.next;
                right = right.next;
            }
        }
        if(left != null){
            temp.next = left;
            temp = temp.next;
        }
        else{
            temp.next = right;
            temp = temp.next;
        }
        return tempHead.next;
    }

    //录入一串数字然后回车，观察排序结果
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String[] strs = sc.nextLine().split(" ");
        int[] arr = new int[strs.length];
        for(int i=0; i<strs.length; i++){
            arr[i] = Integer.parseInt(strs[i]);
        }
        testSort(arr);
        for(int i=0; i<arr.length; i++){
            System.out.println(arr[i]);
        }
    }

    public static void testSort(int[] arr){
        MyMergeSortList myMergeSortList = new MyMergeSortList();
        Node head = myMergeSortList.getList(arr);
        head = myMergeSortList.mergeSortList(head);
        for(int i=0; i<arr.length; i++){
            arr[i] = head.data;
            head = head.next;
        }
    }
}
