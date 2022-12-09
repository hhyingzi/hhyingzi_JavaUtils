package MySolutions.MathSolutions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MyCalculate {
    /*
     * 从文件中读取 int 值，当遇到文件末尾时停止读取。
     * Datas/temp.txt:
     * 1 2 3 4 5
     * */
    public ArrayList<Double> MyFileInputIntExample(){
        String filePath = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\Datas\\temp.txt";
        ArrayList<Double> intList = new ArrayList<>();
        try{
            File dataFile = new File(filePath);  //数据文件 dataFile
            Scanner sc = new Scanner(dataFile);
            while(sc.hasNext()){
                Double tmp = sc.nextDouble();
                intList.add(tmp);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return intList;
    }

    public static void main(String[] args){
        MyCalculate myCalculate = new MyCalculate();
        ArrayList<Double> intList = myCalculate.MyFileInputIntExample();
        for(Double item: intList){
            System.out.println(item + "  /20 = " + item/20);
        }
    }
}
