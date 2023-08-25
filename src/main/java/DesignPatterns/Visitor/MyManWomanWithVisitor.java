package DesignPatterns.Visitor;

/**
 * 大话设计模式 - 访问者模式
 * 男人女人不同状态下的表现：当男人女人，成功、失败、结婚、恋爱时，会XXXXX。
 */
import java.util.ArrayList;
//状态抽象类
abstract class Action{
    public abstract void getManConclusion(Man concreteElementA); //得到男人结论或反应
    public abstract void getWomanConclusion(Woman concreteElementB); //得到女人结论或反应
}
//人类抽象类
abstract class Person {
    public abstract void accept(Action visitor); //接受
}
//成功
class Success extends Action{
    public void getManConclusion(Man concreteElementA){
        System.out.println(concreteElementA.getClass().getSimpleName() +" "+this.getClass().getSimpleName()+"时，背后多半有一个伟大的女人。");
    }
    public void getWomanConclusion(Woman concreteElementB){
        System.out.println(concreteElementB.getClass().getSimpleName() +" "+this.getClass().getSimpleName()+"时，背后大多有一个不成功的男人。");
    }
}
//失败
class Failing extends Action{
    public void getManConclusion(Man concreteElementA){
        System.out.println(concreteElementA.getClass().getSimpleName() +" "+this.getClass().getSimpleName()+"时，闷头喝酒，谁也不用劝。");
    }
    public void getWomanConclusion(Woman concreteElementB){
        System.out.println(concreteElementB.getClass().getSimpleName() +" "+this.getClass().getSimpleName()+"时，眼泪汪汪，谁也劝不了。");
    }
}
//恋爱
class Amativeness extends Action{
    public void getManConclusion(Man concreteElementA){
        System.out.println(concreteElementA.getClass().getSimpleName() +" "+this.getClass().getSimpleName()+"时，凡事不懂也要装懂。");
    }
    public void getWomanConclusion(Woman concreteElementB){
        System.out.println(concreteElementB.getClass().getSimpleName() +" "+this.getClass().getSimpleName()+"时，遇事懂也装作不懂。");
    }
}
//结婚
class Marriage extends Action{
    public void getManConclusion(Man concreteElementA){
        System.out.println(concreteElementA.getClass().getSimpleName() +" "+this.getClass().getSimpleName()+"时，感慨道：恋爱游戏终结时，‘有妻徒刑’遥无期。");
    }
    public void getWomanConclusion(Woman concreteElementB){
        System.out.println(concreteElementB.getClass().getSimpleName() +" "+this.getClass().getSimpleName()+"时，欣慰曰：爱情长跑路漫漫，婚姻保险保平安。");
    }
}
//男人类
class Man extends Person {
    public void accept(Action visitor) { visitor.getManConclusion(this); }  //获取状态后，根据状态类型，调用对应状态的根据男女的 getXxxConclusion() 方法，获取对应的表现
}
//女人类
class Woman extends Person {
    public void accept(Action visitor) { visitor.getWomanConclusion(this); }
}
//对象结构
class ObjectStructureA {
    private ArrayList<Person> elements = new ArrayList<Person>(); //本例中存了两个值，一个男人，一个女人。输出时男的和女的各遍历执行一次，展示各自的状态。
    //增加
    public void attach(Person element) { elements.add(element); }
    //移除
    public void detach(Person element) { elements.remove(element); }
    //查看显示
    public void display(Action visitor) {
        for(Person e : elements) e.accept(visitor);  //展示时，通过 accept(visitor) 方法进行输出
    }
}
//主程序入口
public class MyManWomanWithVisitor {
    public static void main(String[] args) {
        ObjectStructureA o = new ObjectStructureA();
        o.attach(new Man());
        o.attach(new Woman());

        //成功时的反应
        Success v1 = new Success();
        o.display(v1);
        //失败时的反应
        Failing v2 = new Failing();
        o.display(v2);
        //恋爱时的反应
        Amativeness v3 = new Amativeness();
        o.display(v3);
        //婚姻时的反应
        Marriage v4 = new Marriage();
        o.display(v4);
    }
}