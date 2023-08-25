package DesignPatterns.Mediator;

/**
 * 大话设计模式 - 中介者模式
 * 安理会作为世界上各国的中介示例
 */
//抽象国家类 Country
abstract class Country {
    protected UnitedNations unitedNations; //中介国家对象
    public Country(UnitedNations unitedNations){ this.unitedNations = unitedNations; }
}
//国家实现类：美国
class USA extends Country {
    public USA(UnitedNations unitedNations) { super(unitedNations); }
    public void declare(String message) { this.unitedNations.declare(message, this); } //美国向中介者发出声明
    public void getMessage(String message) { System.out.println("美国获得对方信息:" + message); }  //中介者调用美国接收信息的方法，来传递信息给美国
}
//国家实现类：伊拉克
class Iraq extends Country {
    public Iraq(UnitedNations unitedNations) { super(unitedNations); }
    public void declare(String message) { this.unitedNations.declare(message, this); } //伊拉克向中介者发出声明
    public void getMessage(String message) { System.out.println("伊拉克获得对方信息:" + message); } //中介者调用伊拉克接收信息的方法，来传递信息给伊拉克
}
//中介国家抽象类 UnitedNations
abstract class UnitedNations{
    public abstract void declare(String message,Country country); //中介者负责接收一个国家的声明，然后根据各国接收信息的接口给他们传递信息。
}
//中介国家实现类：联合国安理会 UnitedNationsSecurityCouncil
class UnitedNationsSecurityCouncil extends UnitedNations{
    private USA countryUSA;  //中介者持有美国信息对象
    private Iraq countryIraq;  //中介者持有伊拉克对象
    public void setUSA(USA value) { this.countryUSA = value; }
    public void setIraq(Iraq value) { this.countryIraq = value; }
    public void declare(String message, Country country) {  //中介者接收某个国家 country 的声明 message，然后转发给其他国家
        if (country == this.countryUSA) this.countryIraq.getMessage(message);
        else if (country == this.countryIraq) this.countryUSA.getMessage(message);
    }
}
//主程序入口
public class MyContriesWithMediator {
    public static void main(String[] args) {
        UnitedNationsSecurityCouncil UNSC = new UnitedNationsSecurityCouncil(); //安理会
        USA c1 = new USA(UNSC);  //美国
        Iraq c2 = new Iraq(UNSC);  //伊拉克
        UNSC.setUSA(c1);  //安理会对接美国
        UNSC.setIraq(c2);  //安理会对接伊拉克

        c1.declare("不准研制核武器，否则要发动战争！"); //美国向安理会发出声明，安理会转发给其他国家（伊拉克）
        c2.declare("我们没有核武器，也不怕侵略。");  //伊拉克向安理会发出声明，安理会转发给其他国家（美国）
    }
}