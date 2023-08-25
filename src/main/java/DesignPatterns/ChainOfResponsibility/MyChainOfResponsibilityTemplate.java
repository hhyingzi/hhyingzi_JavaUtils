package DesignPatterns.ChainOfResponsibility;

/**
 * 大话设计模式 - 职责链模式
 * 模板示例
 */
//用于接收和处理请求的抽象类 Handler
abstract class Handler{
    protected Handler successor; //继任者（上级）
    public void setSuccessor(Handler successor){ this.successor = successor; }
    public abstract void handleRequest(int request);  //接收请求，并进行处理
}
//请求处理者实现类 ConcreteHandler1
class ConcreteHandler1 extends Handler{
    public void handleRequest(int request){  //只能处理int范围 0-10 的请求
        if (request >=0 && request < 10) System.out.println(this.getClass().getSimpleName()+" 处理请求 "+request);
        else if (successor != null) successor.handleRequest(request);
    }
}
//请求处理者实现类 ConcreteHandler2
class ConcreteHandler2 extends Handler{
    public void handleRequest(int request){  //只能处理int范围 10-20 的请求
        if (request >=10 && request < 20) System.out.println(this.getClass().getSimpleName()+" 处理请求 "+request);
        else if (successor != null) successor.handleRequest(request);
    }
}
//请求处理者实现类 ConcreteHandler3
class ConcreteHandler3 extends Handler{
    public void handleRequest(int request){  //只能处理int范围 20-30 的请求
        if (request >=20 && request < 30) System.out.println(this.getClass().getSimpleName()+" 处理请求 "+request);
        else if (successor != null) successor.handleRequest(request);
        else System.out.println("请求无人能够处理: " + request);
    }
}
//主程序入口
public class MyChainOfResponsibilityTemplate {
    public static void main(String[] args) {
        Handler h1 = new ConcreteHandler1();  //请求处理者 h1
        Handler h2 = new ConcreteHandler2();  //请求处理者 h2
        Handler h3 = new ConcreteHandler3();  //请求处理者 h3
        h1.setSuccessor(h2);  //h1 的上级是 h2
        h2.setSuccessor(h3);  //h2 的上级是 h3

        int[] requests = { 2, 5, 14, 22, 18, 3, 27, 20, 99 };  //发出请求
        for(int request : requests) h1.handleRequest(request);  //从 h1 开始，处理这些请求，处理不了的向上汇报
    }
}
