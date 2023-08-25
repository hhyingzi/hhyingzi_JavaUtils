package DesignPatterns.Facade;

/**
 * 大话设计模式 - 外观层
 * 买股票和债券示例，建立一个Facade外观，也就是基金，负责操控各种股票和债券
 */
//股票1
class Stock1{
    public void sell(){ System.out.println("股票1卖出"); }
    public void buy(){ System.out.println("股票1买入"); }
}
//股票2
class Stock2{
    public void sell(){ System.out.println("股票2卖出"); }
    public void buy(){ System.out.println("股票2买入"); }
}
//国债1
class NationalDebt1{
    public void sell(){ System.out.println("国债1卖出"); }
    public void buy(){ System.out.println("国债1买入"); }
}
//房地产1
class Realty1{
    public void sell(){ System.out.println("房地产1卖出"); }
    public void buy(){ System.out.println("房地产1买入"); }
}
//一个 Facade 外观，负责操控股票债券，即基金类
class Fund{
    Stock1 stock1;
    Stock2 stock2;
    NationalDebt1 nd1;
    Realty1 rt1;
    public Fund(){
        stock1 = new Stock1();
        stock2 = new Stock2();
        nd1 = new NationalDebt1();
        rt1 = new Realty1();
    }
    public void buyFund(){
        stock1.buy();
        stock2.buy();
        nd1.buy();
        rt1.buy();
    }
    public void sellFund(){
        stock1.sell();
        stock2.sell();
        nd1.sell();
        rt1.sell();
    }
}
public class MyBuyStockWithFacade {
    public static void main(String[] args){
        Fund fund1 = new Fund();
        fund1.buyFund();  //基金购买
        fund1.sellFund();  //基金赎回
    }
}