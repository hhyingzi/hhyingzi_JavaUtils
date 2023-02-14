package JavaSE.Class;

/**
 * 演示抽象类和接口的用法
 * */
public class MyAbstractClassAndInterface {
    public static void main(String[] args){
        AbstractChild child1 = new AbstractChild();
        child1.say();

        InterfaceChild child2 = new InterfaceChild();
        child2.say();
    }
}

abstract class AbstractParent{
    public abstract void say();
}

class AbstractChild extends AbstractParent{
    @Override
    public void say() {
        System.out.println("Say Extends.");
    }
}

interface InterfaceParent{
    public void say();
}

class InterfaceChild implements InterfaceParent{
    @Override
    public void say() {
        System.out.println("Say Interface.");
    }
}