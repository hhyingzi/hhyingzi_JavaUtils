package TempProject;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/** 非对称加密，提供一个Key，又加密又解密 */
public class Test {
    private static String tempFile = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\main\\java\\Datas\\temp.txt";
    private static String temp2File = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\main\\java\\Datas\\temp2.txt";
    private static String keyFile = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\main\\java\\Datas\\temp3.txt";

    public static void main(String[] args){
        try{
            String str1 = "https://hhyingzi-youdao.oss-cn-hangzhou.aliyuncs.com/git原理.png";
            String str2 = "https://hhyingzi-youdao.oss-cn-hangzhou.aliyuncs.com/git%E5%8E%9F%E7%90%86.png";

            String urlDomain = "https://hhyingzi-youdao.oss-cn-hangzhou.aliyuncs.com";
            String urlObject = "git原理.png";
            String urlString = urlDomain + URLEncoder.encode(urlObject, "utf-8");
            System.out.println(urlString);

            String plainString = URLDecoder.decode(str2, "utf-8");
            System.out.println(plainString);


        }catch (Exception e){e.printStackTrace();}
    }
}

