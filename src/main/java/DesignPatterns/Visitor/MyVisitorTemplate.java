package DesignPatterns.Visitor;

/**
 * 大话设计模式 - 访问者模式
 * 模板示例
 */
import java.util.ArrayList;
abstract class Visitor {
    public abstract void visitConcreteElementA(ConcreteElementA concreteElementA);
    public abstract void visitConcreteElementB(ConcreteElementB concreteElementB);
}

class ConcreteVisitor1 extends Visitor {
    public void visitConcreteElementA(ConcreteElementA concreteElementA) {
        System.out.println(concreteElementA.getClass().getSimpleName()+"被"+this.getClass().getSimpleName()+"访问");
    }
    public void visitConcreteElementB(ConcreteElementB concreteElementB) {
        System.out.println(concreteElementB.getClass().getSimpleName()+"被"+this.getClass().getSimpleName()+"访问");
    }
}
class ConcreteVisitor2 extends Visitor {
    public void visitConcreteElementA(ConcreteElementA concreteElementA) {
        System.out.println(concreteElementA.getClass().getSimpleName()+"被"+this.getClass().getSimpleName()+"访问");
    }
    public void visitConcreteElementB(ConcreteElementB concreteElementB) {
        System.out.println(concreteElementB.getClass().getSimpleName()+"被"+this.getClass().getSimpleName()+"访问");
    }
}

abstract class Element {
    public abstract void accept(Visitor visitor);
}
class ConcreteElementA extends Element {
    public void accept(Visitor visitor) { visitor.visitConcreteElementA(this); }
    public void operationA(){ }
}

class ConcreteElementB extends Element {
    public void accept(Visitor visitor) { visitor.visitConcreteElementB(this); }
    public void operationB(){ }
}

class ObjectStructure {
    private ArrayList<Element> elements = new ArrayList<Element>();
    public void attach(Element element) { elements.add(element); }
    public void detach(Element element) { elements.remove(element); }
    public void accept(Visitor visitor) { for(Element e : elements) e.accept(visitor); }
}
public class MyVisitorTemplate {
    public static void main(String[] args) {
        ObjectStructure o = new ObjectStructure();
        o.attach(new ConcreteElementA());
        o.attach(new ConcreteElementB());

        ConcreteVisitor1 v1 = new ConcreteVisitor1();
        ConcreteVisitor2 v2 = new ConcreteVisitor2();

        o.accept(v1);
        o.accept(v2);
    }
}