package DesignPatterns.ChainOfResponsibility;

/**
 * 大话设计模式 - 职责链模式
 * 员工向上级请求加薪、请假，上层向上层层汇报，一直到有职责的高层下达批示。
 */
//申请类
class Request {
    private String requestType; //申请类别
    private String requestContent; //申请内容
    private int number; //数量
    public String getRequestType(){ return this.requestType; }
    public void setRequestType(String value){ this.requestType = value; }
    public String getRequestContent(){ return this.requestContent; }
    public void setRequestContent(String value){ this.requestContent = value; }
    public int getNumber(){ return this.number; }
    public void setNumber(int value){ this.number = value; }
}
//管理者抽象类
abstract class Manager{
    protected String name;  //管理者姓名
    protected Manager superior;  //管理者上级
    public Manager(String name){ this.name = name; }
    public void setSuperior(Manager superior){ this.superior = superior; }
    public abstract void requestApplications(Request request); //接收一个申请，并处理该申请
}
//普通经理
class CommonManager extends Manager{
    public CommonManager(String name){ super(name); }
    public void requestApplications(Request request){  //接收到请求时，如果是“请假”类型，则自己处理，否则向上级汇报。
        if (request.getRequestType()=="请假" && request.getNumber()<=2) System.out.println(this.name+":"+request.getRequestContent()+" 数量："+request.getNumber()+"天，被批准");
        else {
            if (this.superior != null) this.superior.requestApplications(request);
        }
    }
}
//总监
class Director extends Manager{
    public Director(String name){ super(name); }
    public void requestApplications(Request request){
        if (request.getRequestType()=="请假" && request.getNumber()<=5) System.out.println(this.name+":"+request.getRequestContent()+" 数量："+request.getNumber()+"天，被批准");
        else {
            if (this.superior != null) this.superior.requestApplications(request);
        }
    }
}
//总经理
class GeneralManager extends Manager{
    public GeneralManager(String name){ super(name); }
    public void requestApplications(Request request){
        if (request.getRequestType()=="请假") System.out.println(this.name+":"+request.getRequestContent()+" 数量："+request.getNumber()+"天，被批准");
        else if (request.getRequestType()=="加薪" && request.getNumber()<=5000) System.out.println(this.name+":"+request.getRequestContent()+" 数量："+request.getNumber()+"元，被批准");
        else if (request.getRequestType()=="加薪" && request.getNumber()>5000) System.out.println(this.name+":"+request.getRequestContent()+" 数量："+request.getNumber()+"元，再说吧");
    }
}
//程序主入口
public class MyRequestWithChainOfResponsibility {
    public static void main(String[] args) {
        CommonManager manager = new CommonManager("经理小经");
        Director director = new Director("总监小总");
        GeneralManager generalManager = new GeneralManager("总经理大佬");
        manager.setSuperior(director);  //设置经理的上级为总监
        director.setSuperior(generalManager);  //设置总监的上级为总经理

        Request request = new Request();
        request.setRequestType("请假");
        request.setRequestContent("小菜请假");
        request.setNumber(1);
        manager.requestApplications(request);

        Request request2 = new Request();
        request2.setRequestType("请假");
        request2.setRequestContent("小菜请假");
        request2.setNumber(4);
        manager.requestApplications(request2);

        Request request3 = new Request();
        request3.setRequestType("加薪");
        request3.setRequestContent("小菜请求加薪");
        request3.setNumber(3000);
        manager.requestApplications(request3);

        Request request4 = new Request();
        request4.setRequestType("加薪");
        request4.setRequestContent("小菜请求加薪");
        request4.setNumber(10000);
        manager.requestApplications(request4);
    }
}
