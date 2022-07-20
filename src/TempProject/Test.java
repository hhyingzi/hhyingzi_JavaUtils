package TempProject;

import java.util.*;

public class Test {
    public static void main(String[] args){
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(4);
        pq.add(2);
        pq.add(1);
        pq.add(3);
        for(int i=0; i<4; i++){
            System.out.println(pq.remove());
        }
    }
}