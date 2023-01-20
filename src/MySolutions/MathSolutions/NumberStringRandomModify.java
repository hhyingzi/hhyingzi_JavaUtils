package MySolutions.MathSolutions;

import java.util.Random;

/**
 * 随机生成数字串
 */
public class NumberStringRandomModify {
    private static String orderNum = "10001071012022120701494375530632";  //磊磊哥哥打车订单号
    private static int n = 10;
    //修改打车订单号，前四位是1000不变，第5位不为0，第5-32位（共28位）随机生成
    public void generateNumber(){
//        StringBuffer stringBuffer = new StringBuffer();
        StringBuilder stringBuffer = new StringBuilder();
        Random random = new Random();
        for(int i=0; i<orderNum.length(); i++){
            //前四位是1000不变
            if(i<=3) stringBuffer.append(orderNum.charAt(i));
            else if(i==4){
                stringBuffer.append(1 + random.nextInt(9));
            }
            else{
                stringBuffer.append(random.nextInt(10));
            }
        }
        //来个线程休眠试试
        try{
            Thread.sleep(random.nextInt(3000) + 1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(stringBuffer.toString());
    }
    public static void main(String[] args){
        NumberStringRandomModify myClass = new NumberStringRandomModify();
        System.out.println(NumberStringRandomModify.orderNum + "\n");
        String[] strArr = new String[n];
        for(int i=1; i<=n; i++){
            new Thread(()-> myClass.generateNumber()).start();  //Lambda多线程
        }
    }
}
