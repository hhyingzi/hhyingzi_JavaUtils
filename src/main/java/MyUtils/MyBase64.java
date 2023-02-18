package MyUtils;

import java.util.Base64;

public class MyBase64 {
    //常用，推荐的方法
    /** 将 byte[] 转化为 Base64 字符串，并返回该字符串 */
    public static String myEncodeToString(byte[] bytes){ return Base64.getEncoder().encodeToString(bytes); }
    /** 将 Base64 字符串，解码为 byte[]  */
    public static byte[] myStringDecode(String encodedStr){ return Base64.getDecoder().decode(encodedStr); }

    //以下为不常用，不推荐的方法，仅做了解
    /** 将 byte[] 转化为 Base64 编码的 byte[]，并返回新 byte[] */
    public static byte[] myEncodeToBytes(byte[] bytes){ return Base64.getEncoder().encode(bytes); }
    /** 将 Base64 byte[]，解码为 byte[] */
    public static byte[] myBytesDecode(byte[] encodedBytes){ return Base64.getDecoder().decode(encodedBytes); }

    public static void main(String[] args){
        try {
            MyBase64 test = new MyBase64();
            String str = "你好啊";

            //编码到 String，推荐
            String encodingStr = MyBase64.myEncodeToString(str.getBytes("utf-8"));  //JVM 默认的 charset，可不写
            System.out.println("编码为 Base64 String: " + encodingStr);

            //从 Base64 String 解码， 推荐
            byte[] bytes = MyBase64.myStringDecode(encodingStr);
            System.out.println("解码为 byte[]，转化成 String 输出: " + new String(bytes, "utf-8"));  //JVM 默认的 charset，可不写

//            System.out.println("=====");
//            //编码到 byte[]，不推荐
//            byte[] encodingBytes = MyBase64.myEncodeToBytes(str.getBytes());
//            System.out.println("编码为 Base64 bytes[]，再手动转化为 String: " + new String(encodingBytes, "iso-8859-1"));
//            //从 Base64 byte[] 解码
//            byte[] bytes1 = MyBase64.myBytesDecode(encodingBytes);
//            System.out.println("解码为 byte[]，转化成 String 输出: " + new String(bytes1));

        } catch (Exception e) { e.printStackTrace(); }
    }
}
