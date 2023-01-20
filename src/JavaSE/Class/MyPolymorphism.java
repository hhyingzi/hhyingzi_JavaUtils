package JavaSE.Class;

/**
 * 多态
 */
public class MyPolymorphism {
    public static void main(String[] args){
        Parent child = new Child();
        child.say();  //Child
    }
}

class Parent{
    String name = "Ba Ba";
    public void say(){
        System.out.println("I am Parent: " + name);
    }
}

class Child extends Parent{
    String name = "Er zi";
    @Override
    public void say() {
//        super.say();
        System.out.println("I am Child: " + name);
    }
}