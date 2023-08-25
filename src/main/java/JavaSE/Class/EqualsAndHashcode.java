package JavaSE.Class;

/*
//原生的 equals()
public boolean equals(Object obj) { return (this == obj); }  //比较内存地址
//原生的 hashCode()
public native int hashCode();  //用对象的内部计算出来的
*/

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class EqualsAndHashcode{
    static class Person{
        String name;

        @Override
        public int hashCode() {
            return (name==null) ? 0:name.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj instanceof Person) {
                Person p = (Person)obj;
                return this.name.equals(p.name);  //只比较其中的 name 字符串值
            }
            return false;
        }
    }

    public static void main(String[] args) {
        EqualsAndHashcode.Person p1 = new EqualsAndHashcode.Person(); p1.name = "张三";
        EqualsAndHashcode.Person p2 = new EqualsAndHashcode.Person(); p2.name = "李四";
        EqualsAndHashcode.Person p3 = new EqualsAndHashcode.Person(); p3.name = "张三";
        //因为 hashcode 相等，此时set只有个两个元素。
        Set<EqualsAndHashcode.Person> set = new HashSet(); set.add(p1); set.add(p2); set.add(p3);

        for (Iterator<EqualsAndHashcode.Person> iter = set.iterator(); iter.hasNext(); ) {
            EqualsAndHashcode.Person p = iter.next();
            System.out.println("name=" + p.name);
        }
        System.out.println("p1.hashCode=" + p1.hashCode());
        System.out.println("p2.hashCode=" + p2.hashCode());
        System.out.println("p3.hashCode=" + p3.hashCode());
        System.out.println("=====");
        System.out.println("p1 equals p2," + p1.equals(p2));
        System.out.println("p1 equals p3," + p1.equals(p3));
    }
}
