package DesignPatterns.FactoryMethod;

import java.util.Scanner;

//收银台接口 ISale
interface ISale {
    public double acceptCash(double price,int num);
}
//正常收费实现
class CashNormal implements ISale {
    //正常收费，原价返回
    public double acceptCash(double price,int num){
        return price * num;
    }
}
//装饰器接口 CashSuper
class CashSuper implements ISale {
    protected ISale component;
    public void decorate(ISale component) { this.component=component; }  //装饰对象
    public double acceptCash(double price,int num){
        if (this.component != null) return this.component.acceptCash(price,num);  //若装饰对象存在，则执行装饰的算法运算
        else return 0d;
    }
}
//打折收费装饰器实现
class CashRebate extends CashSuper {
    private double moneyRebate = 1d;
    //打折收费。初始化时必需输入折扣率。八折就输入0.8
    public CashRebate(double moneyRebate){ this.moneyRebate = moneyRebate; }
    //计算收费时需要在原价基础上乘以折扣率
    public double acceptCash(double price,int num){
        double result = price * num * this.moneyRebate;
        return super.acceptCash(result,1);
    }
}
//满减收费装饰器实现
class CashReturn extends CashSuper {
    private double moneyCondition = 0d; //返利条件
    private double moneyReturn = 0d;    //返利值
    //返利收费。初始化时需要输入返利条件和返利值。 比如“满300返100”，就是moneyCondition=300,moneyReturn=100
    public CashReturn(double moneyCondition,double moneyReturn){
        this.moneyCondition = moneyCondition;
        this.moneyReturn = moneyReturn;
    }
    //计算收费时，当达到返利条件，就原价减去返利值
    public double acceptCash(double price,int num){
        double result = price * num;
        if (moneyCondition>0 && result >= moneyCondition)
            result = result - Math.floor(result / moneyCondition) * moneyReturn;
        return super.acceptCash(result,1);
    }
}
//工厂接口 IFactory
interface IFactory {
    public ISale createSalesModel(); //创建销售模式
}
//生产“先打折再满减”计算类的工厂
class CashRebateReturnFactory implements IFactory {
    private double moneyRebate = 1d;
    private double moneyCondition = 0d;
    private double moneyReturn = 0d;
    public CashRebateReturnFactory(double moneyRebate,double moneyCondition,double moneyReturn){
        this.moneyRebate=moneyRebate;
        this.moneyCondition=moneyCondition;
        this.moneyReturn=moneyReturn;
    }
    //先打x折,再满m返n
    public ISale createSalesModel(){
        CashNormal cn = new CashNormal();
        CashReturn cr1 = new CashReturn(this.moneyCondition,this.moneyReturn);
        CashRebate cr2 = new CashRebate(this.moneyRebate);
        cr1.decorate(cn);   //用满m返n算法包装基本的原价算法
        cr2.decorate(cr1);  //打x折算法装饰满m返n算法
        return cr2;         //将包装好的算法组合返回
    }
}
//生产“先满减再打折”计算类的工厂
class CashReturnRebateFactory implements IFactory {
    private double moneyRebate = 1d;
    private double moneyCondition = 0d;
    private double moneyReturn = 0d;
    public CashReturnRebateFactory(double moneyRebate,double moneyCondition,double moneyReturn){
        this.moneyRebate=moneyRebate;
        this.moneyCondition=moneyCondition;
        this.moneyReturn=moneyReturn;
    }
    //先满m返n,再打x折
    public ISale createSalesModel(){
        CashNormal cn2 = new CashNormal();
        CashRebate cr3 = new CashRebate(this.moneyRebate);
        CashReturn cr4 = new CashReturn(this.moneyCondition,this.moneyReturn);
        cr3.decorate(cn2);  //用打x折算法包装基本的原价算法
        cr4.decorate(cr3);  //满m返n算法装饰打x折算法
        return cr4;         //将包装好的算法组合返回
    }
}
//上下文，传入用户选择的收费策略，决定具体使用哪个工厂。
class CashContext {
    private ISale cs;   //声明一个ISale接口对象
    //通过构造方法，传入具体的收费策略
    public CashContext(int cashType){
        IFactory fs=null;
        switch(cashType) {
            case 1://原价
                fs = new CashRebateReturnFactory(1d,0d,0d);
                break;
            case 2://打8折
                fs = new CashRebateReturnFactory(0.8d,0d,0d);
                break;
            case 3://打7折
                fs = new CashRebateReturnFactory(0.7d,0d,0d);
                break;
            case 4://满300返100
                fs = new CashRebateReturnFactory(1,300d,100d);
                break;
            case 5://先打8折,再满300返100
                fs = new CashRebateReturnFactory(0.8d,300d,100d);
                break;
            case 6://先满200返50，再打7折
                fs = new CashReturnRebateFactory(0.7d,200d,50d);
                break;
        }
        this.cs = fs.createSalesModel();
    }

    public double getResult(double price,int num){
        //根据收费策略的不同，获得计算结果
        return this.cs.acceptCash(price,num);
    }
}
public class MyShoppingWithFactoryMethodAndOther {
    public static void main(String[] args){
        int discount = 0; 		//商品折扣模式
        double price = 0d; 		//商品单价
        int num = 0;			//商品购买数量
        double totalPrices = 0d;//当前商品合计费用
        double total = 0d;		//总计所有商品费用

        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("商品折扣模式如下:");
            System.out.println("1.正常收费");
            System.out.println("2.打八折");
            System.out.println("3.打七折");
            System.out.println("4.满300送100");
            System.out.println("5.先打8折，再满300送100");
            System.out.println("6.先满200送50，再打7折");
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

                //通过Context的getResult方法的调用，可以得到收取费用的结果
                //让具体算法与客户进行了隔离
                totalPrices = cc.getResult(price,num);

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
