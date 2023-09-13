package MyUtils;

import javax.crypto.*;
import java.io.*;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

/** 非对称加密，提供一个Key，又加密又解密 */
public class MyCipherRSA {
    private static String dataPlainFile = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\main\\java\\Datas\\temp.txt";
    private static String dataEncryptedFile = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\main\\java\\Datas\\temp2.txt";
    private static String NOT_USE_THIS_File = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\main\\java\\Datas\\temp3.txt";
    private static String keyPublicFile = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\main\\java\\Datas\\temp4.txt";
    private static String  keyPrivateFile= "D:\\code\\java\\hhyingzi_JavaUtils\\src\\main\\java\\Datas\\temp5.txt";

    public enum AlgorithmsEnum {RSA}
    public static String algorithm = AlgorithmsEnum.RSA.name();

    private static final int KEYSIZE = 512;
    public static Key keyPublic = null;
    public static Key keyPrivate = null;

    /** 根据传入的加密方式 Algorithm，生成公钥，私钥，AES对称密钥，输出到文件中，并返回
     * 一般来说，是应该只预先生成RSA公钥、私钥，而用于文件加密的 AES 在用的时候生成一个随机的，这里为了表达简便，就直接放一起了。 */
    public static void myGenerateKey(String algo){
        try(
                ObjectOutputStream outPublic = new ObjectOutputStream(new FileOutputStream(keyPublicFile));
                ObjectOutputStream outPrivate = new ObjectOutputStream(new FileOutputStream(keyPrivateFile));
        ){
            //生成 RSA 公钥和私钥 文件
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algo);
            SecureRandom random = new SecureRandom();
            keyPairGenerator.initialize(MyCipherRSA.KEYSIZE, random);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            outPublic.writeObject(keyPair.getPublic());
            outPrivate.writeObject(keyPair.getPrivate());
            //生成 AES 对称密钥
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(random);
            SecretKey key = keyGenerator.generateKey();
        }catch (Exception e){ e.printStackTrace(); }
    }
    //从文件读公钥、私钥，存入类变量中
    public static void myReadKeyFromFile(){
        try(
                ObjectInputStream inPublic = new ObjectInputStream(new FileInputStream(keyPublicFile));
                ObjectInputStream inPrivate = new ObjectInputStream(new FileInputStream(keyPrivateFile));
        ){
            keyPublic = (Key) inPublic.readObject();
            keyPrivate = (Key) inPrivate.readObject();
        }catch (Exception e){ e.printStackTrace(); }
    }
    /**
     * 文件加密：用公钥加密 AES key，用 AES key 加密原始文件。
     * @param in_file 原始数据文件
     * @param out_file 加密后的文件
     * */
    public static void myStreamEncrypt(String in_file, String out_file) {
        try (
                FileInputStream in = new FileInputStream(in_file);  //输入原始文件
                DataOutputStream out = new DataOutputStream(new FileOutputStream(out_file))  //输出加密文件，既要输出 int 又要输出 byte[]
        ){
            //生成一个 AES 密钥
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom random = new SecureRandom(); //比 Random 更安全的随机数生成器
            keyGenerator.init(random);
            SecretKey key = keyGenerator.generateKey();
            //用公钥将AES包装起来，输出到文件开头
            Cipher cipherAES = Cipher.getInstance(algorithm);
            cipherAES.init(Cipher.WRAP_MODE, keyPublic);
            byte[] wrappedKey = cipherAES.wrap(key);
            out.writeInt(wrappedKey.length);  //写 AES key 写入的字节的长度
            out.write(wrappedKey); //写入 AES key

            //用 AES 加密文件本身，并写入到文件
            //注意！！！！！我不知道这里提供的输出流，在工具类中会不会覆盖掉我已经输出的内容
//            MyUtils.MyCipherAES.myStreamEncrypt(in, out, key);  //调用自己写的AES加密工具对文件进行加密流输出
        } catch (Exception e) { e.printStackTrace(); }
    }

    /** 文件解密：用私钥解密 AES key，用 AES key 解密文件 */
    public static void myStreamDecrypt(String in_file, String out_file){
        try (
                DataInputStream in = new DataInputStream(new FileInputStream(in_file));  //读取加密文件，既要读取 int 又要读取 byte[]
                FileOutputStream out = new FileOutputStream(out_file)  //输出解密后的文件
        ){
            //读取并用私钥解密文件前部分的 AES key
            //读取 AES key 长度
            int aesKeyLength = in.readInt();
            byte[] wrappedKey = new byte[aesKeyLength];
            in.read(wrappedKey);
            //用 RSA 解密 AES key
            Cipher cipherAES = Cipher.getInstance(algorithm);
            cipherAES.init(Cipher.UNWRAP_MODE, keyPrivate);
            Key key = cipherAES.unwrap(wrappedKey, "AES", Cipher.SECRET_KEY);
            //读取文件后部分的加密数据，并用 AES key 解密，最后输出到文件中
//            MyUtils.MyCipherAES.myStreamDecrypt(in, out, key);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void main(String[] args){
        //生成 RSA 公钥和私钥，并写入到文件 temp4, temp5
//        myGenerateKey(AlgorithmsEnum.RSA.name());
        myReadKeyFromFile();

        //生成一个 AES key ，用 RSA 公钥进行加密，用 AES key 将文件 temp.txt 加密。然后将key和加密后的文件输出至 temp2.txt
//        myStreamEncrypt(dataPlainFile, dataEncryptedFile);  //加密
        myStreamDecrypt(dataEncryptedFile, dataPlainFile);  //解密
    }
}

