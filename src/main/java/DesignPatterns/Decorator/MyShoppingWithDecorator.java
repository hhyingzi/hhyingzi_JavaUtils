package DesignPatterns.Decorator;

import java.util.Scanner;

/**
 * 使用装饰器 + 策略模式，实现收银台。
 * 先计算原价金额 a ，再用打八折装饰金额 a 得到优惠金额 b，再用满300减100装饰金额 b 得到优惠金额 c。
 */
//接口
interface ISale { double acceptCash(double price,int num);}
//收银台实现类：原价
class CashNormal implements ISale {
    public double acceptCash(double price,int num){ return price * num; }
}
//装饰器类
class CashDecorator implements ISale {
    protected ISale component;
    public void decorate(ISale component) { this.component=component; }  //装饰对象
    public double acceptCash(double price,int num){
        var result = 0d;
        if (this.component != null) result = this.component.acceptCash(price,num);  //若装饰对象存在，则先执行装饰的算法运算
        return result;
    }
}
//收银台装饰器实现类：活动价：在计算原价前，先立减10元。
class CashMinus10 extends CashDecorator{
    @Override
    public double acceptCash(double price, int num) {
        return super.acceptCash(price, num) - 10;
    }
}
//收银台装饰器实现类：打折
class CashRebate extends CashDecorator {
    private double moneyRebate = 1d;
    //打折收费。初始化时必需输入折扣率。八折就输入0.8
    public CashRebate(double moneyRebate){ this.moneyRebate = moneyRebate; }
    //计算收费时需要在原价基础上乘以折扣率
    @Override
    public double acceptCash(double price,int num){
        double result = super.acceptCash(price, num);  //先获取原来的金额
        result = result * this.moneyRebate;  //再进行本算法的折扣
        return result;
    }
}
//收银台装饰器实现类：满减
class CashReturn extends CashDecorator {
    private double moneyCondition = 0d; //满减条件
    private double moneyReturn = 0d;    //返利值
    //初始化时需要输入满减条件和返利值。 比如“满300返100”，就是moneyCondition=300,moneyReturn=100
    public CashReturn(double moneyCondition,double moneyReturn){
        this.moneyCondition = moneyCondition;
        this.moneyReturn = moneyReturn;
    }
    //计算金额时，当达到返利条件，就原价减去返利值
    @Override
    public double acceptCash(double price,int num){
        double result = super.acceptCash(price, num);  //先获取原来的金额
        if (moneyCondition>0 && result >= moneyCondition) result = result - Math.floor(result / moneyCondition) * moneyReturn;  //再进行本算法的折扣
        return result;
    }
}
//策略模式：上下文
class CashContext {
    private ISale cs;   //声明一个ISale接口对象
    //通过构造方法，传入具体的收费策略
    public CashContext(int cashType){
        switch(cashType){
            case 1:
                this.cs = new CashNormal();
                CashDecorator cr = new CashMinus10();
                cr.decorate(this.cs);
                this.cs = cr;
                break;
            case 2:
                this.cs = new CashNormal();
                cr = new CashMinus10();
                cr.decorate(this.cs);
                this.cs = cr;

                cr = new CashRebate(0.8d);
                cr.decorate(this.cs);
                this.cs = cr;
                break;

            case 3:
                //先打8折,再满300返100
                CashNormal cn = new CashNormal();

                CashDecorator cr1 = new CashRebate(0.8d);
                CashDecorator cr2 = new CashReturn(300d,100d);
                cr1.decorate(cn);   //打8折算法装饰原价算法
                cr2.decorate(cr1);  //用满300返100算法包装8折后的算法
                this.cs = cr2;      //将包装好的算法组合引用传递给cs对象
                break;
        }
    }

    //根据收费策略的不同，获得计算结果
    public double getResult(double price,int num){ return this.cs.acceptCash(price,num); }
}
//主程序入口
public class MyShoppingWithDecorator {
    public static void main(String[] args){
        int discount = 0; 		//商品折扣模式
        double price = 0d; 		//商品单价
        int num = 0;			//商品购买数量
        double totalPrices = 0d;//当前商品合计费用
        double total = 0d;		//总计所有商品费用

        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("商品折扣模式如下，在立减10元基础上:");
            System.out.println("1.正常收费");
            System.out.println("2.打八折");
            System.out.println("3.先打8折，再满300送100");
            System.out.println("请输入商品折扣模式:");
            discount = Integer.parseInt(sc.nextLine());
            System.out.println("请输入商品单价：");
            price = Double.parseDouble(sc.nextLine());
            System.out.println("请输入商品数量：");
            num = Integer.parseInt(sc.nextLine());
            System.out.println();

            if (price>0 && num>0){
                //根据用户输入，将对应的策略对象作为参数传入CashContext对象中
                CashContext cc = new CashContext(discount);

                //通过Context的getResult方法的调用，可以得到收取费用的结果 让具体算法与客户进行了隔离
                totalPrices = cc.getResult(price,num);

                total = total + totalPrices;

                System.out.println();
                System.out.println("单价："+ price + "元 数量："+ num +" 合计："+ totalPrices +"元");
                System.out.println();
                System.out.println("总计："+ total+"元");
                System.out.println();
            }
        } while(price>0 && num>0);
    }
}
