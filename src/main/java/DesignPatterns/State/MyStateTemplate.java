package DesignPatterns.State;

/**
 * 大话设计模式 - 状态模式
 * 模板示例
 */
//抽象状态类 State
abstract class State {
    public abstract void handle(Context context);  //调用本方法，以改变 State 类的状态。
}
//具体状态类 ConcreteStateA
class ConcreteStateA extends State {
    public void handle(Context context) { context.setState(new ConcreteStateB()); }  //将 Context 中的状态实例从 A 改为 B
}
//具体状态类 ConcreteStateB
class ConcreteStateB extends State {
    public void handle(Context context) { context.setState(new ConcreteStateA()); }  //将 Context 中的状态实例从 B 改为 A
}
//上下文
class Context {
    private State state;
    public Context(State state) { this.state = state; }
    public State getState(){ return this.state; }
    public void setState(State value){
        this.state = value;
        System.out.println("当前状态:" + this.state.getClass().getName());
    }
    public void request() { this.state.handle(this); }
}
//主程序入口
public class MyStateTemplate {
    public static void main(String[] args){
        Context c = new Context(new ConcreteStateA());  //初始状态为 A
        c.request(); c.request(); c.request(); c.request();
    }
}
