package DesignPatterns.Proxy;

//ISubject接口
interface ISubject{
    void request();
}
//RealSubject类
class RealSubject implements ISubject {
    public void request(){ System.out.println("真实的动作。"); }
}
//Proxy类
class Proxy implements ISubject{
    private RealSubject rs;
    public Proxy(){ this.rs = new RealSubject(); }
    public void request(){
        System.out.print("代理者动作 --> ");
        this.rs.request();
    }
}
public class MyProxyTemplate {
    public static void main(String[] args){
        Proxy proxy = new Proxy();
        proxy.request();
    }
}