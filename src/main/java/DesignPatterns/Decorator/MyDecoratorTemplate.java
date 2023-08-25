package DesignPatterns.Decorator;

//Component 抽象类
abstract class Component {
    public abstract void Operation();
}
//Component 的最基本实现类
class ConcreteComponent extends Component {
    public void Operation() { System.out.println("具体对象的实际操作"); }
}
//装饰器类，组合了 Conmonent，是为了达到“类型匹配”
abstract class Decorator extends Component {
    protected Component component;
    public void SetComponent(Component component) { this.component = component; }  //装饰一个Component对象
    //重写Operation()，实际调用component的Operation方法
    public void Operation() { if (component != null) component.Operation(); }
}
//Component 的装饰器实现 A
class ConcreteDecoratorA extends Decorator {
    private String addedState;//本类独有子段，以区别于ConcreteDecoratorB类
    public void Operation() {
        super.Operation();//首先运行了原有Component的Operation()
        this.addedState = "具体装饰对象A的独有操作";//再执行本类独有功能
        System.out.println(this.addedState);
    }
}
//Component 的装饰器实现 B
class ConcreteDecoratorB extends Decorator {
    public void Operation() {
        super.Operation();//首先运行了原有Component的Operation()
        this.AddedBehavior();//再执行本类独有功能
    }
    //本类独有方法，以区别于ConcreteDecoratorA类
    private void AddedBehavior() { System.out.println("具体装饰对象B的独有操作"); }
}

public class MyDecoratorTemplate {
    public static void main(String[] args){
        ConcreteComponent component = new ConcreteComponent();
        ConcreteDecoratorA a = new ConcreteDecoratorA();
        ConcreteDecoratorB b = new ConcreteDecoratorB();

        a.SetComponent(component);	//首先用 a 来包装 component
        b.SetComponent(a); //再用 b 来包装 a
        b.Operation();   	//最终执行 b 的Operation()
    }
}