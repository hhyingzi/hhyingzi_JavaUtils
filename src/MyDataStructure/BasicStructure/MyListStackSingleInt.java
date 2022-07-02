package MyDataStructure.BasicStructure;

public class MyListStackSingleInt {
    private Node first;
    private int size;
    private class Node{
        int data;
        Node next;
    }

    public boolean isEmpty(){ return first==null; }

    public int size(){
        return size;
    }

    public void addFirst(int data){
        Node newNode = new Node();
        newNode.data = data;
        newNode.next = first;

        first = newNode;
        size++;
    }

    public int removeFirst(){
        int result = first.data;
        first = first.next;
        size--;
        if(size < 0) size=0;
        return result;
    }

    public int search(int index){
        Node tmp = first;
        for(int i=0; i<index; i++){
            tmp = first.next;
        }
        return tmp.data;
    }
}
