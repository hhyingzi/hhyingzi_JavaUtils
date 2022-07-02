package TempProject;
/*
* 输入一个一维数组，都是正整数，当作股票每天的价格，d[i]表示第i天价格。 【2，9，5，5, 3,| 7，5，5, 8, 3】
* 求：多次交易的话，不能同时参与多笔交易（买了卖了），求最大利润总和是多少。【】
* 例：9-3=6；  || 9-5=4 7-3=4，
* 买入：d[i] < d[i-1] 则第i天不买入。
*   d[i] > d[i-1] 第i天买入。
*   d[i] == d[i-1] 不买入。
* 卖出：d[i] > d[i-1] 则第i天不卖出。
* */
public class 未做完股票 {
    public static void solution(){
        int [] price = {2,9,5,5,3,7,5,5,8,3};
        int days = price.length;
        for(int i=0; i<price.length; i++) System.out.println(price[i]);
        System.out.println("days="+days);

        int sum=0;
        int buy=-1, sale=-1; //记录买入的天数，为-1则暂未买入。sale未使用。
        int index;
        if(days<=1) {
            System.out.println("总收益：" + 0);
            return ;
        }
        //从第1天起，与后一天比较（days-1是最后一天），到倒数第二天比较截至，最后一天单独处理
        for(index=0; index<days-2; index++){
            //还未买入，等待买入时机
            if(buy < 0){
                //不买
                if(price[index]<=price[index+1]){
                    continue;
                }
                else{

                }
            }
            //还未卖出，等待卖出时机
            else if(buy >=0 ){

            }

        }

    }
}
