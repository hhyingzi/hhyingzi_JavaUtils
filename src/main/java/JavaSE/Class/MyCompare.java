package JavaSE.Class;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

class MyNumber implements Comparable<MyNumber>{
    public Integer data;
    MyNumber(){ this.data =  new Random().nextInt(20); }
    @Override
    public int compareTo(MyNumber o) { return this.data - o.data; }
    @Override
    public String toString() { return this.data.toString(); }
}

public class MyCompare {
    public static void main(String[] args){
        ArrayList<MyNumber> list = new ArrayList<>();
        for(int i=0; i<5; i++) list.add(new MyNumber());
        System.out.println(list.toString());

        //根据 MyNumber 的 Comparable<MyNumber> 接口的 compareTo() 来自然排序
        Collections.sort(list);
        System.out.println(list.toString());

        //手动设置排序器进行排序
        Collections.sort(list, new Comparator<MyNumber>() {
            @Override
            public int compare(MyNumber o1, MyNumber o2) {
                return o1.data - o2.data;
            }
        });
        System.out.println(list.toString());

        //逆序排序
        Collections.sort(list, (MyNumber o1, MyNumber o2)-> {return o2.data - o1.data;});
        System.out.println(list.toString());
    }
}
