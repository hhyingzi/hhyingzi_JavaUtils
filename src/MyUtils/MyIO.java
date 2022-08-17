package MyUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class MyIO {
    /* 控制台按行读取，每行是一个 String，以空白行结束运行 */
    public void StdIOLinesExample(){
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            String str = sc.nextLine();
            if(str==null || str.isEmpty()) break;
            System.out.println(str);
        }
    }

    /* 控制台输入许多数字，将这些数字自动存入数组。 */
    public void StdInLineIntArray(){
        Scanner sc = new Scanner(System.in);
        String[] strs = sc.nextLine().split(" ");
        int[] arr = new int[strs.length];
        for(int i=0; i<strs.length; i++){
            arr[i] = Integer.parseInt(strs[i]);
        }
        System.out.println(Arrays.toString(arr));
    }

    /*
    * 控制台输入 int 值，先输入要读取几个值，然后依次输入每个 int 值，存入数组 arr。
    */
    public void StdIOIntExample(){
        Scanner sc = new Scanner(System.in);
        int length = sc.nextInt();  //首先输入一共要读取几次 int
        int[] arr = new int[length];
        for(int i=0; i<length; i++){
            arr[i] = sc.nextInt();
        }
        System.out.println(Arrays.toString(arr));
    }

    /*
    * 从文件中读取 int 值，当遇到文件末尾时停止读取。
    * Datas/temp.txt:
    * 1 2 3 4 5
    * */
    public void MyFileInputIntExample(){
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

    public static void main(String[] args){
        MyIO myIO = new MyIO();
        myIO.StdIOIntExample();
    }
}
