package DesignPatterns.Adapter;

/**
 * 大话设计模式 - 适配器模式
 * 姚明在篮球队中的英语翻译适配器示例
 */
//球员
abstract class Player {
    protected String name;
    public Player(String name){ this.name = name; }
    public abstract void attack();  //进攻 attack()
    public abstract void defense(); //防守 defense()
}
//前锋
class Forwards extends Player {
    public Forwards(String name){ super(name); }
    public void attack(){ System.out.println("前锋 "+this.name+" 进攻"); }
    public void defense(){ System.out.println("前锋 "+this.name+" 防守"); }
}
//中锋
class Center extends Player {
    public Center(String name){ super(name); }
    public void attack(){ System.out.println("中锋 "+this.name+" 进攻"); }
    public void defense(){ System.out.println("中锋 "+this.name+" 防守"); }
}
//后卫
class Guards extends Player {
    public Guards(String name){ super(name); }
    public void attack(){ System.out.println("后卫 "+this.name+" 进攻"); }
    public void defense(){ System.out.println("后卫 "+this.name+" 防守"); }
}
//外籍中锋：姚明不会说英语
class ForeignCenter {
    private String name;
    public String getName(){ return this.name; }
    public void setName(String value){ this.name = value; }
    public void 进攻(){ System.out.println("外籍中锋 "+this.name+" 进攻"); }  // 姚明的进攻方法名字叫 进攻()
    public void 防守(){ System.out.println("外籍中锋 "+this.name+" 防守"); }  // 姚明的防守方法叫 防守()
}
//翻译者适配器：用姚明能理解的语言（方法）指挥姚明
class Translator extends Player {
    private ForeignCenter foreignCenter = new ForeignCenter();
    public Translator(String name){ super(name); foreignCenter.setName(name); }
    public void attack(){ foreignCenter.进攻(); }
    public void defense(){ foreignCenter.防守(); }
}
//主程序入口
public class MyYaoMingTranslaterWithAdapter {
    public static void main(String[] args){
        //组件球队
        Player forwards = new Forwards("巴蒂尔"); //前锋
        Player center = new Translator("姚明"); //中锋，用适配器来操控 ForeignCenter 类，使其能和 Center 具有一样的操作接口
        Player guards = new Guards("麦克格雷迪"); //后卫
        //开始比赛
        forwards.attack();  //前锋进攻
        guards.attack();   //后卫进攻
        center.attack();  //中锋进攻，调用适配器的 attack() 接口（与Player一致），适配器能操纵姚明调用 进攻() 做出进攻的动作
        center.defense(); //中锋防守，适配器操纵姚明做出防守动作
    }
}
