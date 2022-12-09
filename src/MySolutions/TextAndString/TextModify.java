package MySolutions.TextAndString;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class TextModify {
    public static final String filePath = "Datas/temp.txt";

    /* 读取文件中的文本 */
    public String getFileAllText(){
        String filePath = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\Datas\\temp.txt";
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

    /* 将文本分隔为字符串数组，并重新组织分隔符 */
    public static void refactorString(String string){
        String[] resultArr = string.split("\\s");
        for(String item: resultArr){
            if(item.isEmpty()){  //空白符不输出（否则会单独占用一行）
                continue;
            }
            System.out.println(item);
        }
        String newResultArr = String.join("|", resultArr);
//        System.out.println(newResultArr);
    }

    public static void main(String[] args){
        TextModify solution = new TextModify();
        String result = solution.getFileAllText();
        System.out.println(result + "\n=================");
        solution.refactorString(result);
    }
}
