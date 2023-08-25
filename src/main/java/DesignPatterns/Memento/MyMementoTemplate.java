package DesignPatterns.Memento;

/**
 * 大话设计模式 - 备忘录模式
 * 模板示例
 */
//发起人类 Originator：负责创建一个备忘录 Memento，用于记录当前时刻发起人类 Originator 的内部状态，以及恢复到该状态。
class Originator {
    private String state; //状态
    public void setState(String value){ this.state = value; }  //设置 state
    public String getState(){ return this.state; }  //获取 state

    public void show(){ System.out.println("State:"+this.state); } //展示自身数据（state）
    public Memento createMemento(){ return new Memento(this.state); } //创建备忘录
    public void recoveryMemento(Memento memento){ this.setState(memento.getState()); } //恢复备忘录
}
//备忘录类 Memento：存储发起人类 Originator 的内部状态。
class Memento {
    private String state; //状态
    public Memento (String state){ this.state = state; }
    //查看与设置备忘录中的数据
    public String getState(){ return this.state; }  //获取备忘录中的数据
    public void setState(String value){ this.state = value; }
}
//管理者 Caretaker：仅负责保存好备忘录 Memento，不能查看或操作备忘录。
class Caretaker{
    private Memento memento; //拥有一个备忘录实例
    public Memento getMemento(){ return this.memento; } //返回备忘录实例（不能查看）
    public void setMemento(Memento value){ this.memento = value; } //传入一个备忘录并自己持有（不能查看）
}
//程序主入口
public class MyMementoTemplate {
    public static void main(String[] args){
        //Originator初始状态，状态属性为"On"
        Originator o = new Originator();  //发起人
        o.setState("On");  //发起人状态更改
        o.show();

        Caretaker c = new Caretaker(); //备忘录管理者
        c.setMemento(o.createMemento()); //发起人创建备忘录并交给管理者保存。（保存状态时，由于有了很好的封装，可以隐藏Originator的实现细节）

        o.setState("Off"); //发起人状态更改
        o.show();

        o.recoveryMemento(c.getMemento()); //发起人从备忘录管理者受众获取备忘录，用该备忘录恢复自己的初始状态
        o.show();
    }
}