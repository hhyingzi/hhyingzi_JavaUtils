package MyUtils;

import javax.crypto.*;
import java.io.*;
import java.security.Key;
import java.security.SecureRandom;

/** 非对称加密，提供一个Key，又加密又解密 */
public class MyCipherAES {
    private static String tempFile = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\main\\java\\Datas\\temp.txt";
    private static String temp2File = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\main\\java\\Datas\\temp2.txt";
    private static String keyFile = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\main\\java\\Datas\\temp3.txt";

    public enum AlgorithmsEnum {AES, DES, CBC, PKCS5Padding}
    public static String algorithm = AlgorithmsEnum.AES.name();

    public static Key key = null;

    /** 根据传入的加密方式 Algorithm，生成一个 Key，输出到文件中，并返回 */
    public static void myGenerateKey(String algo){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(keyFile))){
            KeyGenerator keyGenerator = KeyGenerator.getInstance(algo);
            SecureRandom random = new SecureRandom(); //比 Random 更安全的随机数生成器
            keyGenerator.init(random);
            SecretKey key = keyGenerator.generateKey();

            //将密钥写入到 keyFile 文件中
            out.writeObject(key);
        }catch (Exception e){ e.printStackTrace(); }
    }
    /** 从文件中读取 Key，存入类变量 */
    public static void myGetKeyFromFile(String keyFile){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(keyFile))){
            MyCipherAES.key = (Key) in.readObject();
        }catch (Exception e){ e.printStackTrace(); }
    }

    /**
     * 文件加密/解密
     * @param in_file 原始数据文件
     * @param out_file 加密后的文件
     * @param key 用于加密的 key
     * @param doEncrypt true 为加密，false 为解密
     * */
    public static void myFileEncryptOrDecrypt(String in_file, String out_file, Key key, boolean doEncrypt) {
        try (
                FileInputStream fileInputStream = new FileInputStream(in_file);
                FileOutputStream fileOutputStream = new FileOutputStream(out_file)
        ) {
            //设置模式和加密密钥，对其初始化
            int mode;
            //加密模式
            if (doEncrypt) mode = Cipher.ENCRYPT_MODE;
                //解密模式
            else{
                mode = Cipher.DECRYPT_MODE;
            }

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

    //从文件中读取key，设置到 Test.key ，并返回。
    public static Key mySetKeyFromFile(String key_file){
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(key_file))){
            MyCipherAES.key = (Key) objectInputStream.readObject();
            return MyCipherAES.key;
        }catch (Exception e){ e.printStackTrace(); }
        return null;
    }

    /** 加密/解密 IO 流 */
    //加密
    public static void myStreamEncrypt(InputStream inputStream, OutputStream outputStream, Key key) throws Exception{
        Cipher cipher = Cipher.getInstance(MyCipherAES.algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key);
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

    //解密
    public static void myStreamDecrypt(InputStream inputStream, OutputStream outputStream, Key key)throws Exception{
            Cipher cipher = Cipher.getInstance(MyCipherAES.algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key);
            CipherInputStream in = new CipherInputStream(inputStream, cipher); //用 CipherInputStream 包装输入流，输入时直接读取解密后的数据

            int outBlockSize = 1024;
            byte[] outBlockBytes = new byte[outBlockSize];
            //从输入流 fileInputStream 中源源不断读取，当场加密后输出
            int readLenth;
            while ((readLenth = in.read(outBlockBytes)) > 0) {
                outputStream.write(outBlockBytes, 0, readLenth);
            }
    }

    public static void main(String[] args){
        //设定加密算法
        MyCipherAES.algorithm = AlgorithmsEnum.AES.name();

        //获取 key
//        Test.myGenerateKey(Test.algorithm);  //生成新 key ，并写入文件
        myGetKeyFromFile(keyFile);  //从文件中读取 key，存入类变量。

        //将 tempFile 文件加密，输出至 temp2File 文件中。
        MyCipherAES.myFileEncryptOrDecrypt(MyCipherAES.tempFile, MyCipherAES.temp2File, MyCipherAES.key, true);
//        //从文件读取 Key，并解密
        MyCipherAES.myFileEncryptOrDecrypt(MyCipherAES.temp2File, MyCipherAES.tempFile, MyCipherAES.key, false);

        //通过输出流进行编码
        try(
                FileInputStream in = new FileInputStream(tempFile);
                FileOutputStream out = new FileOutputStream(temp2File);
        ) {
            MyCipherAES.myStreamEncrypt(in, out, MyCipherAES.key);  //temp.txt 加密到 temp2.txt
        }catch (Exception e){ e.printStackTrace(); }
        //通过输入流进行解码
        try(
                FileInputStream in = new FileInputStream(temp2File);
                FileOutputStream out = new FileOutputStream(tempFile);
        ) {
            MyCipherAES.myStreamDecrypt(in, out, MyCipherAES.key);  //temp2.txt 解密到 temp.txt
        }catch (Exception e){ e.printStackTrace(); }
    }
}