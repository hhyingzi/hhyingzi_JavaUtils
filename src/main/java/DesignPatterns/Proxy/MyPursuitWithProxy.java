package DesignPatterns.Proxy;

/**
 * 大话设计模式 - 代理模式
 * 代理人代替男孩追女孩
 */
//送礼物接口
interface IGiveGift{
    void giveDolls();
    void giveFlowers();
    void giveChocolate();
}
//追求者类
class Pursuit implements IGiveGift {
    private SchoolGirl mm;
    public Pursuit(SchoolGirl mm){ this.mm = mm; }
    public void giveDolls(){ System.out.println(this.mm.getName() + ",你好！送你洋娃娃。"); }
    public void giveFlowers(){ System.out.println(this.mm.getName() + ",你好！送你鲜花。"); }
    public void giveChocolate(){ System.out.println(this.mm.getName() + ",你好！送你巧克力。"); }
}
//代理类
class ProxyPursuit implements IGiveGift{
    private Pursuit gg; //认识追求者
    public ProxyPursuit(SchoolGirl mm){ this.gg = new Pursuit(mm); }  //也认识被追求者，代理初始化的过程，实际是追求者初始化的过程
    public void giveDolls(){ } //代理在送礼物，实质是追求者在送礼物
    public void giveFlowers(){ this.gg.giveFlowers(); }
    public void giveChocolate(){ this.gg.giveChocolate(); }
}
//被追求者类
class SchoolGirl {
    private String name;
    public String getName(){ return this.name; }
    public void setName(String value){ this.name = value; }
}
//主程序入口
public class MyPursuitWithProxy {
    public static void main(String[] args){
        SchoolGirl girlLjj = new SchoolGirl();
        girlLjj.setName("李娇娇");

        ProxyPursuit boyDl = new ProxyPursuit(girlLjj);
        boyDl.giveDolls(); boyDl.giveFlowers(); boyDl.giveChocolate();
    }
}
