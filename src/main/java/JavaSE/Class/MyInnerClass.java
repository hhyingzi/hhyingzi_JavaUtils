package JavaSE.Class;

/** 内部类 */
public class MyInnerClass {
    public static void main(String[] args){
        MyInnerClass myInnerClass = new MyInnerClass();
        C1 c1 = myInnerClass.new C1();
        C1.C2 c2 = c1.new C2();
        C1.C2.C3 c3 = c2.new C3();
        c3.getName();

        C1.C2.C3 c30 = myInnerClass.new C1().new C2().new C3();
        c30.getName();

        MyInnerClass.D1.test();
    }

    class C1{
        String name = "c1";
        String C1name = "C1name";
        class C2{
            String name = "c2";
            String C2name = "C2name";
            class C3{
                String name = "c3";
                public void getName(){
                    System.out.println(C1.this.name + C2.this.name + this.name + C1name + C2name);
                }
            }
        }
    }

    static class D1{
        static void test(){};
    }
}