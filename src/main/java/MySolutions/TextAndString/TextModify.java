package MySolutions.TextAndString;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class TextModify {
    public static final String filePath = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\Datas\\temp.txt";

    /* 读取方法1 FileInputStream：读取文件中的所有文本，放进一个字符串中 */
    public String getFileAllText(){
        try{
            //方法一： FileInputStream 将每个字节存储进字节数组，限制2GB且根据内存实际情况小于该值。
            FileInputStream fileInputStream = new FileInputStream(filePath);  //FileInputStream 输入流
            int fileLength = fileInputStream.available();  //文件含有字节数
            byte[] bytes = new byte[fileLength];  //存放所有字节的 byte 数组，数组长度与文件字节数等同。
            fileInputStream.read(bytes);  //输入流往字节数组中存储字节
            fileInputStream.close();

            String result = new String(bytes, Charset.forName("utf-8"));  //默认为java虚拟机默认的编码utf-8，如果读 GBK 文件可修改此参数
            return result;
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    /* 读取方法2 BufferedReader：按行读取，每行作为一个字符串，全部放进 String[] 中 */
    public List<String> getFileWithLine(){
        File file = new File(filePath);
        List<String> lineList = new ArrayList<>();
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            //lineList = bufferedReader.lines().collect(Collectors.toList());  //与下面等价，java8新特性
            String line = null;
            while((line = bufferedReader.readLine()) != null){
                lineList.add(line);
            }

            //重新处理每一行，去掉首位空白，删除空白行
            String temp;
            for(int i=lineList.size()-1; i>=0; i--){
                temp = lineList.get(i).trim();  //去除首尾空白
                if(temp.isEmpty()) lineList.remove(i);  //删除空白行
                else lineList.set(i, temp);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return lineList;
    }

    /* 将 String 根据空白符，分割为字符串数组，并用自定义的分隔符重新组合 */
    public static void refactorString(String string){
        String[] resultArr = string.split("\\s");
        for(String item: resultArr){
            if(item.isEmpty()){  //空白符不输出（否则会单独占用一行）
                continue;
            }
            System.out.println(item);
        }
        String newResultArr = String.join("|", resultArr);  //用新的分隔符
    }

    public static void main(String[] args){
        TextModify solution = new TextModify();
        /** 读取方法1 FileInputStream：读取文件中的所有文本，放进一个字符串中 */
//        String result = solution.getFileAllText();
//        System.out.println(result + "\n=================");
//        solution.refactorString(result);

        /** 读取方法2 BufferedReader：按行读取，每行作为一个字符串，全部放进 String[] 中 */
        List<String> result = solution.getFileWithLine();
        List<String> newResult = new ArrayList<>();
        String temp =  null;
        for(int i=0; i<result.size(); i++){
            if(i%2 == 0){
                temp = result.get(i);
            }
            else{
                newResult.add(temp + "\t" + result.get(i));
                temp = null;
            }
        }
        newResult.forEach(item -> System.out.println(item));
    }
}
