package JavaSE.Class;

public class MyAbstractClass {
    public static void main(String[] args){
        AbstractChild child = new AbstractChild();
        child.say();
    }
}

abstract class AbstractParent{
    public abstract void say();
}

class AbstractChild extends AbstractParent{
    @Override
    public void say() {
        System.out.println("say hello.");
    }
}
