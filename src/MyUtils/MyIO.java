package MyUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Scanner;

public class MyIO {
    /*********** 控制台读取 ***********/
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

    /*********** 文件读取 ***********/
    /*
     *  一次把整个文件读取成字符串。
     * */
    public void MyFileToString(){
        String filePath = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\Datas\\temp.txt";
        try{
            //方法一： FileInputStream 将每个字节存储进字节数组，限制2GB且根据内存实际情况小于该值。
            FileInputStream fileInputStream = new FileInputStream(filePath);  //FileInputStream 输入流
            int fileLength = fileInputStream.available();  //文件含有字节数
            byte[] bytes = new byte[fileLength];  //存放所有字节的 byte 数组，数组长度与文件字节数等同。
            fileInputStream.read(bytes);  //输入流往字节数组中存储字节
            fileInputStream.close();

            String result = new String(bytes, Charset.forName("utf-8"));  //默认为java虚拟机默认的编码utf-8，如果读 GBK 文件可修改此参数
            System.out.println(result);
        }catch(IOException e){
            e.printStackTrace();
        }
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
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        MyIO myIO = new MyIO();
        myIO.StdIOIntExample();
    }
}
