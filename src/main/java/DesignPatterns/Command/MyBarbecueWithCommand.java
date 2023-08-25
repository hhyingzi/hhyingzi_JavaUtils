package DesignPatterns.Command;

/**
 * 大话设计模式 - 命令模式
 * 烧烤店服务员记录和执行每桌客人的要求
 */
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

//抽象命令类：表示菜单上为顾客提供的命令
abstract class CommandOfCustomer {
    protected Barbecuer receiver;
    public CommandOfCustomer(Barbecuer receiver){ this.receiver = receiver; }
    public abstract void excuteCommand(); //执行命令
}
//烤羊肉命令类
class BakeMuttonCommand extends CommandOfCustomer{
    public BakeMuttonCommand(Barbecuer receiver){ super(receiver); }
    public void excuteCommand(){ receiver.bakeMutton(); }
}
//烤鸡翅命令类
class BakeChickenWingCommand extends CommandOfCustomer{
    public BakeChickenWingCommand(Barbecuer receiver){ super(receiver); }
    public void excuteCommand(){ receiver.bakeChickenWing(); }
}

//服务员类
class Waiter{
    private ArrayList<CommandOfCustomer> orders = new ArrayList<CommandOfCustomer>();  //服务员的笔记本，记录收到的所有命令
    //设置订单
    public void setOrder(CommandOfCustomer commandOfCustomer){
        String className=commandOfCustomer.getClass().getSimpleName();
        if (className.equals("BakeChickenWingCommand")) System.out.println("服务员：鸡翅没有了，请点别的烧烤。");
        else{
            this.orders.add(commandOfCustomer);  //往笔记本中记录一条命令
            System.out.println("增加订单："+className+" 时间："+getNowTime());
        }
    }
    //取消订单
    public void cancelOrder(CommandOfCustomer commandOfCustomer){
        orders.remove(commandOfCustomer);  //取消笔记本中的命令
        String className=commandOfCustomer.getClass().getSimpleName();
        System.out.println("取消订单："+className+" 时间："+getNowTime());
    }
    //通知厨师执行笔记本中记录的每条命令
    public void notifyCommand(){
        for(CommandOfCustomer commandOfCustomer : orders) commandOfCustomer.excuteCommand();
    }
    private String getNowTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(new Date()).toString();
    }
}
//烧烤店的烧烤厨师 Barbecuer
class Barbecuer{
    public void bakeMutton(){ System.out.println("烤羊肉串！"); } //烤羊肉
    public void bakeChickenWing(){ System.out.println("烤鸡翅！"); } //烤鸡翅
}
public class MyBarbecueWithCommand {
    public static void main(String[] args) {
        //开店前的准备
        Barbecuer boy = new Barbecuer();//烤肉厨师
        CommandOfCustomer bakeMuttonCommand1 = new BakeMuttonCommand(boy);            //顾客可以根据菜单发出的命令：烤羊肉串
        CommandOfCustomer bakeChickenWingCommand1 = new BakeChickenWingCommand(boy);  //顾客可以根据菜单发出的命令：烤鸡翅
        Waiter girl = new Waiter();     //服务员

        System.out.println("开门营业，顾客点菜");
        girl.setOrder(bakeMuttonCommand1);      //服务员记录顾客要求：下单烤羊肉串
        girl.setOrder(bakeMuttonCommand1);      //服务员记录顾客要求：下单烤羊肉串
        girl.setOrder(bakeMuttonCommand1);      //服务员记录顾客要求：下单烤羊肉串
        girl.setOrder(bakeMuttonCommand1);      //服务员记录顾客要求：下单烤羊肉串
        girl.setOrder(bakeMuttonCommand1);      //服务员记录顾客要求：下单烤羊肉串
        girl.cancelOrder(bakeMuttonCommand1);   //服务员记录顾客要求：取消一串羊肉串订单
        girl.setOrder(bakeChickenWingCommand1); //服务员记录顾客要求：下单烤鸡翅

        System.out.println("点菜完毕，通知厨房烧菜");
        girl.notifyCommand();                   //通知厨师
    }
}