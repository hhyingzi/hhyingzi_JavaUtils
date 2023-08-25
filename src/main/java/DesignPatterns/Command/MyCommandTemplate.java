package DesignPatterns.Command;

/**
 * 大话设计模式 - 命令模式
 * 模板代码示例
 */
//抽象命令类：用来声明命令的执行动作
abstract class Command {
    protected Receiver receiver;
    public Command(Receiver receiver){ this.receiver = receiver; }
    public abstract void excuteCommand(); //执行命令
}
//具体命令类
class ConcreteCommand extends Command{
    public ConcreteCommand(Receiver receiver){ super(receiver); }
    public void excuteCommand(){ receiver.action(); }  //执行该命令
}
//要求该命令执行这个要求
class Invoker{
    private Command command;
    public void setCommand(Command command){ this.command = command; }
    public void executeCommand(){ command.excuteCommand(); }
}
class Receiver{
    public void action(){ System.out.println("执行请求！"); }
}
public class MyCommandTemplate {
    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        Command command = new ConcreteCommand(receiver);
        Invoker invoker = new Invoker();

        invoker.setCommand(command);
        invoker.executeCommand();
    }
}