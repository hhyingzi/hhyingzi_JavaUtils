package DesignPatterns.Strategy;

import java.util.Scanner;
//抽象收银台
abstract class CashSuper {
    public abstract double acceptCash(double price,int num);
}
//收银台实现：原价
class CashNormal extends CashSuper {
    public double acceptCash(double price,int num){ return price * num; }
}
//收银台实现：折扣
class CashRebate extends CashSuper {
    private double moneyRebate = 1d;
    //初始化时必需输入折扣率。八折就输入0.8
    public CashRebate(double moneyRebate){ this.moneyRebate = moneyRebate; }
    //计算收费时需要在原价基础上乘以折扣率
    public double acceptCash(double price,int num){ return price * num * this.moneyRebate; }
}
//收银台实现：返利
class CashReturn extends CashSuper {
    private double moneyCondition = 0d; //返利条件
    private double moneyReturn = 0d;    //返利值

    //初始化时需要输入返利条件和返利值。 比如“满300返100”，就是moneyCondition=300,moneyReturn=100
    public CashReturn(double moneyCondition,double moneyReturn){
        this.moneyCondition = moneyCondition;
        this.moneyReturn = moneyReturn;
    }
    //计算收费时，当达到返利条件，就原价减去返利值
    public double acceptCash(double price,int num){
        double result = price * num;
        if (moneyCondition>0 && result >= moneyCondition)
            result = result - Math.floor(result / moneyCondition) * moneyReturn;
        return result;
    }
}
//收银台策略上下文，主要功能为，在不同的优惠时期下，进行不同的优惠计算方法，并提供不同的计算结果。
class CashContext {
    private CashSuper cs;   //声明一个CashSuper对象
    //通过构造方法，传入具体的收费策略
    public CashContext(int cashType){
        switch(cashType){
            case 1:
                this.cs = new CashNormal();
                break;
            case 2:
                this.cs = new CashRebate(0.8d);
                break;
            case 3:
                this.cs = new CashRebate(0.7d);
                break;
            case 4:
                this.cs = new CashReturn(300d,100d);
                break;
        }
    }
    //根据收费策略的不同，获得计算结果
    public double getResult(double price,int num){
        return this.cs.acceptCash(price,num);
    }
}
//主程序执行入口
public class MyShoppingWithStratagyAndSimpleFactory {
    public static void main(String[] args){
        int discount = 0; 		//商品折扣模式(1.正常收费 2.打八折 3.打七折)

        double price = 0d; 		//商品单价
        int num = 0;			//商品购买数量
        double totalPrices = 0d;//当前商品合计费用
        double total = 0d;		//总计所有商品费用

        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("请输入商品折扣模式（1.正常收费 2.打八折 3.打七折 4.满300送100）：");
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
