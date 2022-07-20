package TempProject;

class B {
    static {
        System.out.println("父类静态代码块");
    }

    {
        System.out.println("父类非静态代码块");
    }

    public B() {
        System.out.println("父类构造器");
    }
}


public class A extends B{
    static {
        System.out.println("子类静态代码块");
    }

    {
        System.out.println("子类非静态代码块");
    }

    public A() {
        System.out.println("子类构造器");
    }

    public static void main(String[] args) {
        new A();
    }
}
