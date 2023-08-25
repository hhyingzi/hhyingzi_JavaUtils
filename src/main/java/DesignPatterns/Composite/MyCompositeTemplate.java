package DesignPatterns.Composite;
/**
 * 大话设计模式 - 组合模式
 * 模板示例：实质上就是实现了一个多叉树结构
 */
import java.util.ArrayList;
//对象声明接口 Component，实现所有类的共有接口的默认行为。声明一个接口用于访问和管理 Component 的子部件。
abstract class Component{
    protected String name;
    public Component(String name){ this.name = name; }
    public abstract void add(Component component);  //增加孩子节点
    public abstract void remove(Component component);  //移除孩子节点
    public abstract void display(int depth);  //展示树，depth为根前面的‘-’ 符号的长度
}
//叶子节点对象 Leaf，叶子节点没有也不可操作子节点。
class Leaf extends Component{
    public Leaf(String name){ super(name); }
    public void add(Component component){ System.out.println("Cannot add to a leaf."); }  //add() 不可用
    public void remove(Component component){ System.out.println("Cannot remove from a leaf."); }  //remove() 不可用
    public void display(int depth){
        //叶节点的具体显示方法，此处是显示其名称和级别
        for(var i=0;i<depth;i++) System.out.print("-");  //打印节点前面的 ‘-’ 标志
        System.out.println(name);  //打印节点名称
    }
}
//有枝节点 Composite，用来存储子部件，在 Component 接口中实现与子部件有关的操作，比如增加 add 和删除 remove。
class Composite extends Component{
    private ArrayList<Component> children = new ArrayList<Component>();//一个子对象集合用来存储其下属的枝节点和叶节点
    public Composite(String name){ super(name); }
    public void add(Component component){ children.add(component); }  //增加子节点
    public void remove(Component component){ children.remove(component); }  //移除子节点
    //树的深度优先遍历
    public void display(int depth){
        for(var i=0;i<depth;i++) System.out.print("-");  //打印节点前面的 '-' 标志
        System.out.println(name);
        for(Component item : children) item.display(depth+2);  //对子节点进行遍历
    }
}
//主程序入口
public class MyCompositeTemplate {
    public static void main(String[] args){
        //定义根枝条节点 root
        Composite root = new Composite("root");
        root.add(new Leaf("Leaf A"));  //root 挂载叶子 LeafA
        root.add(new Leaf("Leaf B"));  //root 挂载叶子 LeafB
        //定义枝条节点 Composite X
        Composite comp = new Composite("Composite X");
        comp.add(new Leaf("Leaf XA"));  //CompositeX 挂载叶子 Leaf XA
        comp.add(new Leaf("Leaf XB"));  //CompositeX 挂载叶子 Leaf XB
        root.add(comp);  //节点 root 挂载 CompositeX
        //定义枝条节点 Composite XY
        Composite comp2 = new Composite("Composite XY");
        comp2.add(new Leaf("Leaf XYA"));  //Composite XY 挂载叶子 Leaf XYA
        comp2.add(new Leaf("Leaf XYB"));  //Composite XY 挂载叶子 Leaf XBY
        comp.add(comp2);  //节点 CompositeX 挂载 Composite XY
        //叶子节点 Leaf C 直接挂载到 root 节点
        Leaf leaf = new Leaf("Leaf C");
        root.add(leaf);
        //叶子节点 Leaf D 挂载到 root，再移除
        Leaf leaf2 = new Leaf("Leaf D");
        root.add(leaf2);
        root.remove(leaf2);

        root.display(1);
    }
}