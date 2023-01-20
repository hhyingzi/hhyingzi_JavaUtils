package MyUtils;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyIO {
    /*********** 控制台读取 ***********/
    /* 控制台按行读取方法1：Scanner。每行是一个 String，以空白行结束运行 */
    public void myStdInWithLines1(){
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            String str = sc.nextLine();
            if(str==null || str.isEmpty()) break;
            System.out.println(str);
        }
    }
    /* 控制台按行读取方法2：BufferedReader */
    public void myStdInWithLines2(){
        BufferedReader bufferedReader = null;
        try{
            try{
                bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                String str = null;
                while((str = bufferedReader.readLine()) != null && !str.isEmpty()){
                    System.out.println(str);
                }
            }finally {
                bufferedReader.close();
            }
        }catch (Exception e){
            e.printStackTrace();
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
    /*********** 文件写入 ***********/
    //按字符文件写入
    public void writeFileWithString(){
        String filePath = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\Datas\\temp.txt";
        PrintWriter out = null;
        String text = "Hello!你好！";
        try{
            out = new PrintWriter(filePath);
            out.print(text);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            out.close();
        }
    }
    /*********** 文件读取 ***********/
    /*
     *  一次把整个文件读取成字符串。
     * */
    //方法一：按字节读取, FileInputStream
    public void readFileWithBytes(){
        String filePath = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\Datas\\temp.txt";
        try{
            //FileInputStream 将每个字节存储进字节数组，限制2GB且根据内存实际情况小于该值。
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
    //方法二：按行读取, InputStreamReader -> BufferedReader
    public void readFileWithLine(){
        final String file_path = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\Datas\\temp.txt";
        try{
            //当字符输入流，编码默认为 utf-8 时可直接用 FileReader 构建 BufferedReader
            BufferedReader in = new BufferedReader(new FileReader(file_path));
            //当字符输入流，需要指定编码时，需要用 InputStreamReader 构建 BufferedReader
            //InputStreamReader reader = new InputStreamReader(new FileInputStream(file_path), "utf-8");
            //BufferedReader in = new BufferedReader(reader);
            String temp;
            StringBuilder sb = new StringBuilder();
            while((temp = in.readLine())!= null){
                sb.append(temp + "\n");  //readline会自动消除换行符，需要手动加上
            }
            in.close();
            String result = sb.toString();
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
    }
    //一个字符一个字符从文件中读取（结果完全与源文件相同，换行符也会正常读取）
    public String readFileWithChar(){
        final String file_path = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\Datas\\temp.txt";
        try{
            BufferedReader in = new BufferedReader(new FileReader(file_path));
            StringBuilder sb = new StringBuilder();
            int temp;
            try{
                while((temp = in.read())!= -1){
                    sb.append((char)temp);  //readline会自动消除换行符，需要手动加上
                }
                String result = sb.toString();
                return result;
            }finally {
                in.close();
            }
//            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /*
    * 从文件中读取 int 值，当遇到文件末尾时停止读取。
    * Datas/temp.txt:
    * 1 2 3 4 5
    * */
    public void readFileWithInt(){
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

    /**********************实用功能*************************/
    //将 API 中复制的不带返回值函数（构造函数），转化为有道云表格
    public void myStringConvertYoudaoTable1(){
        String filePath = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\Datas\\temp.txt";
        try{
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String temp;
            StringBuilder sb = new StringBuilder();
            Pattern pattern = Pattern.compile("(\\w+.*)");  //构造函数，无返回值
            while((temp=in.readLine()) !=  null){
                temp = temp.trim();
                Matcher matcher = pattern.matcher(temp);
                if(matcher.matches()){
                    sb.append(matcher.group(1) + " | ");  //第一行，构造函数
                }
                if((temp=in.readLine()) != null){
                    sb.append(temp.trim() + "\n");  //第二行，说明行
                }
            }
            System.out.println(sb.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //将 API 中复制的带返回值函数，转化为有道云表格
    public void myStringConvertYoudaoTable2(){
        String filePath = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\Datas\\temp.txt";
        try{
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String temp;
            StringBuilder sb = new StringBuilder();
            Pattern pattern = Pattern.compile("((\\w+\\s+)+)(.*)");  //有返回值的普通方法。返回值：((\w+\s+)+) ，函数：(.*)
            while((temp=in.readLine()) !=  null){
                temp = temp.trim();
                Matcher matcher = pattern.matcher(temp);
                if(matcher.matches()){
                    sb.append(matcher.group(1) + "| " + matcher.group(3) + " | ");  //第一行，带返回值的方法。其中group(2)是大括号里的小括号，忽略这个分组
                }
                if((temp=in.readLine()) != null){
                    sb.append(temp.trim() + "\n");  //第二行，说明行
                }
            }
            System.out.println(sb.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //一键更新文件/文件夹最新修改时间
    public void myFileOrDirModifyTimes(){
        final String DEFAULT_PATH = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\Datas\\temp.txt";

        String path = DEFAULT_PATH;  //真正的文件目录，用之前要修改路径名，用完要恢复到 DEFAULT_PATH
        File file = new File(path);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date nowDate = new Date(); //当前日期
        String lastModifiedString = simpleDateFormat.format(new Date(file.lastModified()));  //最后修改日期

        System.out.println("文件：" + path);
        System.out.println("当前时间    ：" + simpleDateFormat.format(nowDate));  //打印当前时间
        if(file.isFile()){
            System.out.println("文件修改日期：" + lastModifiedString);
            file.setLastModified(nowDate.getTime()); lastModifiedString = simpleDateFormat.format(new Date(file.lastModified()));
            System.out.println("文件最新日期：" + lastModifiedString);
        }
        else if(file.isDirectory()){
            System.out.println("目录修改日期：" + lastModifiedString);
            file.setLastModified(nowDate.getTime()); lastModifiedString = simpleDateFormat.format(new Date(file.lastModified()));
            System.out.println("目录最新日期：" + lastModifiedString);
        }
    }

    public void myYoudaoMarkdownRepair(){
        final String out_file = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\Datas\\temp.txt";
        final String data_file = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\Datas\\temp2.txt";
        try(
                FileChannel fisChannel = new FileInputStream(data_file).getChannel();
                BufferedReader bufferedReader = new BufferedReader(Channels.newReader(fisChannel, "UTF-8"));
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(out_file));
            ){
            String line;
            while((line = bufferedReader.readLine())!=null){
                if(line.startsWith("|")){
                    line = line.substring(2, line.length()-2);  //去除表格内容每行首尾多余的 "|"
                    line = line.replaceAll("----+", "---");  //过长的 "---" 全都替换为 三横线
                    //表格每个单元格去除多余的空白符号
                    String[] results = line.split("\\|");
                    for(int i=0; i<results.length; i++){
                        results[i] = results[i].trim();
                    }
                    System.out.println(String.join(" | ", results));
                    bufferedWriter.write(String.join(" | ", results) + "\n");
                }
                else {
                    System.out.println(line);
                    bufferedWriter.write(line + "\n");
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public static void main(String[] args){
        MyIO myIO = new MyIO();

        /* 将 API 转化为有道云表格 */
        //构造函数
//        myIO.myStringConvertYoudaoTable1();
        //普通函数（带返回值）
        myIO.myStringConvertYoudaoTable2();

        //修改文件最新修改日期
//        myIO.myFileOrDirModifyTimes();

        //修改有道云 markdown 乱码
//        myIO.myYoudaoMarkdownRepair();
    }
}
