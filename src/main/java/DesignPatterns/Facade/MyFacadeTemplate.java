package DesignPatterns.Facade;

/**
 * 大话 - 外观模式
 * 外观模式的模板代码
 */
//子系统1
class SubSystemOne{ public void methodOne(){ System.out.println("子系统方法一"); }}
//子系统2
class SubSystemTwo{ public void methodTwo(){ System.out.println("子系统方法二"); }}
//子系统3
class SubSystemThree{ public void methodThree(){ System.out.println("子系统方法三"); }}
//子系统4
class SubSystemFour{ public void methodFour(){ System.out.println("子系统方法四"); }}

//外观类，它需要了解所有的子系统的方法或属性，进行组合，以备外界调用
class Facade{
    SubSystemOne one;
    SubSystemTwo two;
    SubSystemThree three;
    SubSystemFour four;
    public Facade(){
        one = new SubSystemOne();
        two = new SubSystemTwo();
        three = new SubSystemThree();
        four = new SubSystemFour();
    }
    public void methodA(){  //按顺序执行 1 2 3 4 子系统
        one.methodOne();
        two.methodTwo();
        three.methodThree();
        four.methodFour();
    }
    public void methodB(){  //按顺序执行 2 3 子系统
        two.methodTwo();
        three.methodThree();
    }
}
//主程序入口
public class MyFacadeTemplate {
    public static void main(String[] args){
        Facade facade = new Facade();
        facade.methodA();
        facade.methodB();
    }
}
