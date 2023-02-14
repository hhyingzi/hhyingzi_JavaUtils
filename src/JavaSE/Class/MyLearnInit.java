package JavaSE.Class;

/**
 * 类的成员初始化顺序：
 * 父类静态
 * 子类静态
 * 父类普通成员
 * 子类普通成员
 * 子类
 * */
public class ParentChildLearnInit {
    public static void main(String[] args){
        ConstructChild child1 = new ConstructChild();
        System.out.println("=====子类构造函数前调用 super()=====");
        ConstructChild child2 = new ConstructChild(true);
    }
}

class ConstructParent {
    public static String name = setStaticName();  // 1：父类 static 成员变量或 static 代码块
    static { System.out.println("父类静态初始化代码块"); }  // 1.1

    public String address = setAddress();  //3：父类普通成员变量或代码块
    public String parentHome = setParentHome();  //3.1
    { System.out.println("父类普通初始化代码块"); }  //3.2

    public ConstructParent(){ System.out.println("父类构造函数"); }  //4：父类构造函数

    public static String setStaticName(){ System.out.println("设置父类静态变量"); return "父类静态变量"; }
    public String setAddress(){ System.out.println("被重写，根本不会被执行：设置父类普通变量address"); return "父类普通变量address"; }
    public String setParentHome(){ System.out.println("设置父类普通变量parentHome"); return "父类普通变量parentHome"; }
}

class ConstructChild extends ConstructParent {
    public static String name = setStaticName();  // 2：子类 static 成员变量或 static 代码块
    static { System.out.println("子类静态初始化代码块"); }  // 2.1

    public String address = setAddress();  //5：子类普通成员变量或代码块
    public String childHome = setChildHome();  //5.1
    { System.out.println("子类普通初始化代码块"); }  //5.2

    public ConstructChild(){ System.out.println("子类构造函数"); }  //6：子类构造函数
    public ConstructChild(boolean doSuper){
        super(); //加了 super() 与不加没有任何不同，因为子类构造函数之前默认就会做这一步操作。
        System.out.println("super() + 子类构造函数");
    }

    public static String setStaticName(){ System.out.println("设置子类静态变量"); return "子类静态变量"; }
    public String setAddress(){ System.out.println("\n重写：设置子类普通变量address"); return "子类普通变量address"; }
    public String setChildHome(){ System.out.println("设置子类普通变量childHome"); return "子类普通变量childHome"; }
}