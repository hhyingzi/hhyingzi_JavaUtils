package MyUtils;

import java.io.*;
import java.util.Scanner;

public class MyIO {
    /* 控制台按行读取，每行是一个 String，以空白行结束运行 */
    public static void StdIOLinesExample(){
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            String str = sc.nextLine();
            if(str==null || str.isEmpty()) break;
            System.out.println(str);
        }
    }
    /*
    * 控制台输入 int 值，先输入要读取几个值，然后依次输入每个 int 值，读取完成后结束运行。
    * 例如：以下示例输入5个数，分别是：0 1 2 3 4
    * 5
    * 0 1 2 3 4
    */
    public static void StdIOIntExample(){
        Scanner sc = new Scanner(System.in);
        int times = sc.nextInt();  //首先输入一共要读取几次 int
        for(int i=0; i<times; i++){
            int a = sc.nextInt();
            System.out.println(a);
        }
    }

    /*
    * 从文件中读取 int 值，当遇到文件末尾时停止读取。
    * Datas/temp.txt:
    * 1 2 3 4 5
    * */
    public static void MyFileInputIntExample(){
        String filePath = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\Datas\\temp.txt";
        try{
            File dataFile = new File(filePath);  //数据文件 dataFile
            Scanner sc = new Scanner(dataFile);
            while(sc.hasNext()){
                int tmp = sc.nextInt();
                System.out.println(tmp);
            }
        }catch (FileNotFoundException e){
            System.out.println("HH捕获异常，找不到文件："+filePath);
        }
    }
}
