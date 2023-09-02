package MyUtils;

import TempProject.Test;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Scanner;

/** 非对称加密，提供一个Key，又加密又解密 */
public class MyCipherAES {
    private static String originFile = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\main\\java\\Datas\\temp.txt";  //原始文件 originFile
    private static String encryptedFile = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\main\\java\\Datas\\temp2.txt";  //加密后的文件 encryptedFile
    private static String keyFile = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\main\\java\\Datas\\temp3.txt";  //密钥文件

    //生成 key 的参数
    //pbe通过 password 和盐，生成 Key
    public static final String ALGORITHM = "PBKDF2WithHmacSHA256";//PBKDF2WithHmacSHA256 PBEWithHmacSHA512AndAES_256
    public byte[] salt = {(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08};  //PBE的盐8位足矣，32位最佳
    public int iterationCount = 1000;
    public String keyAlgorithm = "AES";  //生成的 key 的算法。有"AES", "PBE"
    public Key key;  //密钥。使用 getEncoded() 可转为 byte[]。

    //设定加密算法，用字符串形式的 algorithm 变量来使用。
    public static String algorithm = "AES";

    //加密所使用的密钥对象 Key
    public static Key key = null;  //从密钥文件中解析出来的 Key

    /* ##### Begin:生成 key ##### */
    /** 生成随机 key */
    public void myGenerateRandomKey(String algo){
        //根据传入的加密方式 Algorithm，生成一个二进制文件 Key，保存为 keyFile
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(keyFile))){
            KeyGenerator keyGenerator = KeyGenerator.getInstance(algo);
            SecureRandom random = new SecureRandom(); //比 Random 更安全的随机数生成器
            keyGenerator.init(random);
            SecretKey key = keyGenerator.generateKey();

            //将密钥写入到 keyFile 文件中
            out.writeObject(key);
        }catch (Exception e){ e.printStackTrace(); }
    }
    /** 从文件 keyFile 中读取二进制 Key，解析为 Key 变量 key */
    public void myGetKeyFromFile(String keyFile){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(keyFile))){
            MyCipherAES.key = (Key) in.readObject();
        }catch (Exception e){ e.printStackTrace(); }
    }

    /** 根据字符串 password 生成 key */
    //根据字符串生成 key
    public Key getKeyFromPassword(String password){
        String ALGORITHM = "PBKDF2WithHmacSHA256";//PBKDF2WithHmacSHA256 PBEWithHmacSHA512AndAES_256
        byte[] salt = {(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08};  //PBE的盐8位足矣，32位最佳
        int iterationCount = 1000;
        String keyAlgorithm = "AES";  //从 byte[] 形式的key字节数组，生成 SecretKeySpec 时候，所用的密钥算法。有"AES", "PBE"
        Key key;  //密钥。使用 getEncoded() 可转为 byte[]。使用 new SecretKeySpec(byte[] keyBytes, String algorithm) 可以读取 byte[] 生成 key。

        try{
            PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt, this.iterationCount, 256);  //PBE key 规范
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Test.ALGORITHM);  //密钥工厂，使用 PBKDF2 算法
            Key pbeKey = keyFactory.generateSecret(pbeKeySpec);  //向密钥工厂传入 PBE key 规范，生成密钥 key

            byte[] keyBytes = pbeKey.getEncoded();  //正式拿来使用的byte[]形式的 key
            this.key = new SecretKeySpec(keyBytes, this.keyAlgorithm);  //默认 "AES"
//            this.key = pbeKey;
        }catch (Exception e){e.printStackTrace();}
    }
    /* ##### End:生成 key ##### */

    /** 从控制台输入一个字符串作为密码，解析为 Key 变量 key */
    public void myGetKeyFromConsole(){
        try(Scanner scanner = new Scanner(System.in)){
            System.out.println("Please input a key String: ");
            if(scanner.hasNext()){
                String keyStr = scanner.next();
//                byte[] salt = new byte[]{1, 2, 3, 4, 5, 6, 'a', 'b', 'c'};  //随便设置盐混淆
//                KeySpec keySpec = new PBEKeySpec(keyStr.toCharArray(), salt, 100);
//                SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("AES");
//                byte[] keyBytes = secretKeyFactory.generateSecret(keySpec).getEncoded();

                MyCipherAES.key = new SecretKeySpec(keyStr.getBytes(), this.keyAlgorithm);
//                MyCipherAES.key = new SecretKeySpec(keyBytes, "AES");
                System.out.println(MyCipherAES.key);
            }
        }catch (Exception e){e.printStackTrace();}
    }

    /***** Begin:加密解密 *****/
    /**
     * 字符串加密
     */
    public static void myEncryptString(String originString, String encryptedString){

    }

    /**
     * 字符串解密
     */
    public static void myDecryptString(String encryptedString, String originString){

    }

    /**
     * 对文件加密
     * @param originFile 原始文件
     * @param encryptedFile 加密后的文件
     */
    public static void myEncryptFile(String originFile, String encryptedFile){ myFileEncryptOrDecrypt(originFile, encryptedFile, MyCipherAES.key, true); }

    /**
     * 对文件解密
     * @param encryptedFile 加密后的文件
     * @param originFile 解密后的文件
     */
    public static void myDecryptFile(String encryptedFile, String originFile){ myFileEncryptOrDecrypt(encryptedFile, originFile, MyCipherAES.key, false); }

    /**
     * 辅助方法：文件加密/解密
     * @param in_file 输入的文件
     * @param out_file 输出的文件
     * @param key 用于加密的 key
     * @param doEncrypt true 为加密，false 为解密
     * */
    private static void myFileEncryptOrDecrypt(String in_file, String out_file, Key key, boolean doEncrypt) {
        try (
                FileInputStream fileInputStream = new FileInputStream(in_file);
                FileOutputStream fileOutputStream = new FileOutputStream(out_file)
        ) {
            //设置模式和加密密钥，对其初始化
            int mode;
            //加密模式
            if (doEncrypt) mode = Cipher.ENCRYPT_MODE;
            //解密模式
            else mode = Cipher.DECRYPT_MODE;

            // 获得一个 Cipher 密码对象
            Cipher cipher = Cipher.getInstance(MyCipherAES.algorithm);
            cipher.init(mode, MyCipherAES.key);

            //反复调用 update 方法，对数据块进行加密
            int inBlockSize = cipher.getBlockSize();  //算法要求的，每次读入的数据块大小
            int outBlockSize = cipher.getOutputSize(inBlockSize);  //算法每次生成的，密文数据块的大小
            byte[] inBlockBytes = new byte[inBlockSize];  //源数据块
            byte[] outBlockBytes = new byte[outBlockSize];  //密文数据块

            int readLenth;
            //当数据快一直能读满，则循环调用 update(...) 处理数据。
            while ((readLenth = fileInputStream.read(inBlockBytes)) == inBlockSize) {
                int outLength = cipher.update(inBlockBytes, 0, inBlockSize, outBlockBytes);  //将 inBlockBytes[0, read-1] 加密，写入到 outBlockBytes 中，返回写入的字节数量 outLength
                fileOutputStream.write(outBlockBytes, 0, outLength);
            }
            //最后进行填充，如果最后一个数据块不满，则调用 doFinal 对末尾区域进行填充。并且无论怎样，都会在密文尾部额外之后追加一个字节。
            //如果还有点数据，则 doFinal(...) 填充剩余空间。否则 doFinal() 结束。
            if (readLenth > 0) outBlockBytes = cipher.doFinal(inBlockBytes, 0, readLenth);
            else outBlockBytes = cipher.doFinal();
            fileOutputStream.write(outBlockBytes);
        } catch (Exception e) { e.printStackTrace(); }
    }

    /**
     * IO 流加密
     */
    public static void myStreamEncrypt(InputStream inputStream, OutputStream outputStream, Key key) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher. init(Cipher.ENCRYPT_MODE, key);
        try(CipherOutputStream out = new CipherOutputStream(outputStream, cipher)) {
            int inBlockSize = cipher.getBlockSize();
            byte[] inBlockBytes = new byte[inBlockSize];
            //从输入流 fileInputStream 中源源不断读取，当场加密后输出
            int readLenth;
            while ((readLenth = inputStream.read(inBlockBytes)) > 0) {
                out.write(inBlockBytes, 0, readLenth);
            }
            out.flush();
        }
    }

    /**
     * IO 流解密
     */
    public void myStreamDecrypt(InputStream inputStream, OutputStream outputStream, Key key)throws Exception{
            Cipher cipher = Cipher.getInstance(this.keyAlgorithm);
            cipher.init(Cipher.DECRYPT_MODE, key);  //用密钥 key 初始化此 Cipher
            CipherInputStream in = new CipherInputStream(inputStream, cipher); //用 CipherInputStream 包装输入流，输入时直接读取解密后的数据

            int outBlockSize = 1024;
            byte[] outBlockBytes = new byte[outBlockSize];
            //从输入流 fileInputStream 中源源不断读取，当场加密后输出
            int readLenth;
            while ((readLenth = in.read(outBlockBytes)) > 0) {
                outputStream.write(outBlockBytes, 0, readLenth);
            }
    }
    /***** End:加密解密 *****/

    /***** 测试类 *****/
    public void testWithString(){
        try{
            myGetKeyFromConsole();  //从控制台输入 AES 密钥并解析到 MyCipherAES.key 变量

            String plainText = "hello, 123, 滑滑的影子";
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, MyCipherAES.key);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            System.out.println("加密后文本：" + Base64.getEncoder().encodeToString(encryptedBytes));
        }catch (Exception e){e.printStackTrace();}
    }

    public void testWithFile(){
        //设定加密算法
        MyCipherAES.algorithm = "AES";

        /***** begin: 生成key文件，以及从文件中读取key *****/
        //MyCipherAES.myGenerateKey(MyCipherAES.algorithm);  //生成新 key ，并写入文件
//        MyCipherAES.myGetKeyFromFile(keyFile);  //从文件中读取 key，存入类变量。
        /***** end：生成key文件，以及从文件中读取key *****/

        /***** begin: 从控制台输入key *****/
        //MyCipherAES.myGetKeyFromConsole();
        /***** end：从控制台输入key *****/

        //将 tempFile 文件加密，输出至 temp2File 文件中。
        MyCipherAES.myFileEncryptOrDecrypt(MyCipherAES.originFile, MyCipherAES.encryptedFile, MyCipherAES.key, true);
//        //从文件读取 Key，并解密
        MyCipherAES.myFileEncryptOrDecrypt(MyCipherAES.encryptedFile, MyCipherAES.originFile, MyCipherAES.key, false);

        //通过输出流进行编码
        try(
                FileInputStream in = new FileInputStream(originFile);
                FileOutputStream out = new FileOutputStream(encryptedFile);
        ) {
            MyCipherAES.myStreamEncrypt(in, out, MyCipherAES.key);  //temp.txt 加密到 temp2.txt
        }catch (Exception e){ e.printStackTrace(); }
        //通过输入流进行解码
        try(
                FileInputStream in = new FileInputStream(encryptedFile);
                FileOutputStream out = new FileOutputStream(originFile);
        ) {
            MyCipherAES.myStreamDecrypt(in, out, MyCipherAES.key);  //temp2.txt 解密到 temp.txt
        }catch (Exception e){ e.printStackTrace(); }
    }

    //测试IO流的加密解密
    public void testWithIO(){
        this.keyAlgorithm = "AES"; //设定加密算法
        MyCipherAES.myGetKeyFromConsole();  //从控制台输入 AES 密钥并解析到 MyCipherAES.key 变量

        //通过输出流进行加密
        try(
                FileInputStream in = new FileInputStream(originFile);
                FileOutputStream out = new FileOutputStream(encryptedFile);
        ) {
            MyCipherAES.myStreamEncrypt(in, out, MyCipherAES.key);  //temp.txt 加密到 temp2.txt
        }catch (Exception e){ e.printStackTrace(); }

        //通过输入流进行解密
        try(
                FileInputStream in = new FileInputStream(encryptedFile);
                FileOutputStream out = new FileOutputStream(originFile);
        ) {
            MyCipherAES.myStreamDecrypt(in, out, MyCipherAES.key);  //temp2.txt 解密到 temp.txt
        }catch (Exception e){ e.printStackTrace(); }

    }

    public static void main(String[] args){
//        MyCipherAES.test();

        MyCipherAES.testWithString();
    }
}