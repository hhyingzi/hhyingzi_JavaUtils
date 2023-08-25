package DesignPatterns.SimpleFactory;

import java.util.Scanner;
//抽象收银台
abstract class CashSuper {
    public abstract double acceptCash(double price,int num);
}
//收银台实现：原价
class CashNormal extends CashSuper {
    public double acceptCash(double price, int num) { return price * num; }
}
//收银台实现：折扣
class CashRebate extends CashSuper {
    private double moneyRebate = 1d;
    //打折收费。初始化时必需输入折扣率。八折就输入0.8
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
//收费工厂，主要功能为，在不同的优惠时期下，返回不同的收银台实例
class CashFactory {
    public static CashSuper createCashAccept(int cashType){
        CashSuper cs = null;
        switch (cashType) {
            case 1:
                cs = new CashNormal();      //正常收费
                break;
            case 2:
                cs = new CashRebate(0.8d);  //打八折
                break;
            case 3:
                cs = new CashRebate(0.7d);  //打七折
                break;
            case 4:
                cs = new CashReturn(300d,100d);//满300返100
                break;
        }
        return cs;
    }
}
public class MyShoppingWithSimpleFactory {
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
                //简单工厂模式根据discount的数字选择合适的收费类生成实例
                CashSuper csuper = CashFactory.createCashAccept(discount);
                //通过多态，可以根据不同收费策略计算得到收费的结果
                totalPrices = csuper.acceptCash(price,num);
                total = total + totalPrices;

                System.out.println();
                System.out.println("单价："+ price + "元 数量："+ num +" 合计："+ totalPrices +"元");
                System.out.println();
                System.out.println("总计："+ total+"元");
                System.out.println();
            }
        }
        while(price>0 && num>0);
    }
}
