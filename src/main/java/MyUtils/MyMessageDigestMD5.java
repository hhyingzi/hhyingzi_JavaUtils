package MyUtils;

import org.apache.commons.codec.binary.Hex;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;

/** 生成数字摘要的工具类：MD5, SHA-1, SHA-256, SHA-384, SHA-512。 */
public class MyMessageDigestMD5 {
    private static final String algorithm = "MD5";
    private static final String str = "Hello.";
    private static final String dataFile = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\main\\java\\Datas\\temp.txt";

    /** bytes[] 转十六进制输出 */
    private static String myBytestoHexString(byte[] bytes){
        //方法一，Hex.encodeHex(bytes)
        String resultStr = new String(Hex.encodeHex(bytes)).toUpperCase();

        //方法二，自己硬算
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF; //去除正负号，等价于 if(hash[i]<0) hash[i]+=256;
            if (v < 16) builder.append("0"); //前一位补0
            builder.append(Integer.toHexString(v).toUpperCase() + " ");
        }
        String resultStr2 =builder.toString();

        System.out.println(resultStr);
        System.out.println(resultStr2);
        return resultStr;
    }

    /**
     * 对 bytes 生成数字摘要，对 String 可使用 myDigestWithBytes(str.getBytes()) 调用本方法。
     */
    public static String myBytesToDigest(byte[] bytes){
        try{
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            byte[] hashBytes = messageDigest.digest(bytes);  //摘要结果
            String hashStr = new String(Hex.encodeHex(hashBytes)).toUpperCase();
//            myBytestoHexString(hashBytes);
            return hashStr;
        }catch (Exception e){ e.printStackTrace(); }
        return null;
    }

    /**
     * 对文件生成数字摘要，一次性全部计算
     * @param filePath 用于转换的文件路径
     * @return 转换完毕后的 MD5 字符串
     * */
    public static String myFileToDigest(String filePath){
        try{
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            byte[] hashBytes = messageDigest.digest(Files.readAllBytes(Paths.get(filePath)));
            String hashStr = new String(Hex.encodeHex(hashBytes)).toUpperCase();
            return hashStr;
        }catch (Exception e){ e.printStackTrace(); }
        return null;
    }

    /**
     * 对大文件进行流式读取，生成数字摘要
     */
    public static String myLargeFileToDigest(String filePath){
        try{
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            InputStream inputStream = new FileInputStream(filePath);
            int readLength;
            byte[] bytes = new byte[1024];
            while((readLength=inputStream.read(bytes)) > 0){
                messageDigest.update(bytes, 0, readLength);
            }
            byte[] hashBytes = messageDigest.digest();
            String hashStr = new String(Hex.encodeHex(hashBytes)).toUpperCase();
            return hashStr;
        }catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public static void main(String[] args){
        String hashStr;
        hashStr = myBytesToDigest("hhyingzi".getBytes());
        System.out.println(hashStr);

//        hashStr = myFileToDigest(dataFile);
//        System.out.println(hashStr);
//
//        hashStr = myLargeFileToDigest(dataFile);
//        System.out.println(hashStr);
    }
}
