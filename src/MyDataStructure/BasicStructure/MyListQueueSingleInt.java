package MyDataStructure.BasicStructure;

public class MyListQueueSingleInt {
    private int size;
    private Node first, last;
    class Node{
        int data;
        Node next;
    }

    public boolean isEmpty(){ return first==null; }

    public int size(){
        return size;
    }

    public void addLast(int data){
        Node newNode = new Node();
        newNode.data = data;
        newNode.next = null;
        if(isEmpty()) first = last = newNode;
        else{
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    public int removeFirst(){
        int result = first.data;
        first = first.next;
        if(isEmpty()) last = null;
        size--;
        return result;
    }
}
