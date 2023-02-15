package JavaSE.Class;

class BasePoly {
    public static String staticVar = "父类 static 变量";
    public String var = "父类普通成员变量";
    public static void staticMethod() { System.out.println("父类的 static 方法"); }
    public void method() { System.out.println("父类普通方法"); }
}

class SubPoly extends BasePoly {
    public static String staticVar = "子类 static 变量";
    public String var = "子类普通成员变量";
    public static void staticMethod() { System.out.println("子类的 static 方法"); }
    public void method() { System.out.println("子类普通方法"); }
    public void subMethod() { System.out.print("子类调用父类的work()方法"); super.method(); }  //子类真想重写后访问父类的方法，就像这样
}

public class MyPolymorphism {
    public void work2(BasePoly basePoly){ basePoly.staticMethod(); basePoly.method(); }
    public static void main(String[] args) {
        BasePoly basePoly = new SubPoly();

        System.out.println("===多态中成员变量===");
        System.out.println(basePoly.staticVar + " , " + basePoly.var);  //都是父类的成员变量
//        System.out.println(((SubPoly)basePoly).staticVar + " , " + ((SubPoly)basePoly).var);  //在多态中访问子类成员变量

        System.out.println("===多态中方法===");
        System.out.print("basePoly.staticMethod(): "); basePoly.staticMethod(); //父类的 static 方法
        System.out.print("basePoly.method(): "); basePoly.method();  //多态：子类的普通方法

//        ((SubPoly)basePoly).subMethod(); //在多态中访问子类的方法，且子类调用父类被重写的方法

        System.out.println("===传参时也可使用多态，父类形参，可传入子类实参===");
        MyPolymorphism myPolymorphism = new MyPolymorphism();
        myPolymorphism.work2(new SubPoly());
    }
}
