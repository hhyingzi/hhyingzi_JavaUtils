package MyUtils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.Key;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;

/** 非对称加密，提供一个Key，又加密又解密 */
public class MyCipherAES {
    private static String originFile = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\main\\java\\Datas\\temp.txt";  //原始文件 originFile
    private static String encryptedFile = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\main\\java\\Datas\\temp2.txt";  //加密后的文件 encryptedFile
    private static String decryptedFile = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\main\\java\\Datas\\temp3.txt";  //解密后的文件，生成的内容，应当与 originFile 内容一致
    private static String keyFile = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\main\\java\\Datas\\temp4.txt";  //密钥文件

    //生成 key 的参数
    //pbe通过 password 和盐，生成 Key
    public byte[] salt = {(byte)0x01, (byte)0x02, (byte)0x03, (byte)0x04, (byte)0x05, (byte)0x06, (byte)0x07, (byte)0x08};  //PBE的盐8字节（64位）足矣，32字节（256位）最佳，决定了密码冲突容量
    public int iterationCount = 1000;
    public String keySpecAlgorithm = "AES";  //key 的 algorithm 类型，必须设置为与 Cipher 使用的加密算法对应。 一般为"AES", "PBE"。
    public Key key;  //密钥对象。

    /* Cipher加密 */
    public byte[] iv = { (byte)0x00, (byte)0x01, (byte)0x02, (byte)0x03, (byte)0x04, (byte)0x05, (byte)0x06, (byte)0x07,
                        (byte)0x08, (byte)0x09, (byte)0x0A, (byte)0x0B, (byte)0x0C, (byte)0x0D, (byte)0x0E, (byte)0x0F }; //IV向量。在AES标准中必须是128位（16字节）长度，因为要与AES的block(128位）对应。

    /* ##### Begin:生成 key ##### */
    /** 从控制台输入一个字符串作为密码，解析为 Key 变量 key */
    public void getKeyFromConsole(){
        try(Scanner scanner = new Scanner(System.in)){
            System.out.println("Please input a key String: ");
            if(scanner.hasNext()){
                String password = scanner.next();
                getKeyFromPassword(password);  //用 password 生成 key
                System.out.println("Key(" + password + "): " + Base64.getEncoder().encodeToString(this.key.getEncoded()));
            }
        }catch (Exception e){e.printStackTrace();}
    }
    // 根据字符串 password 生成 key
    private void getKeyFromPassword(String password){
        try{
            PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), this.salt, this.iterationCount, 256);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");  //密钥工厂，使用 PBKDF2 算法，该算法根据 password 和其他 PBE 参数生成 key
            Key pbeKey = keyFactory.generateSecret(pbeKeySpec);  //向密钥工厂传入 PBE key 规范，生成密钥 key

            //以下key类型转换必不可少，必须把 key 对象中的 algorithm 设置为 “AES”，才能用于 AES 加密。
            byte[] keyBytes = pbeKey.getEncoded();
            this.key = new SecretKeySpec(keyBytes, this.keySpecAlgorithm);
        }catch (Exception e){e.printStackTrace();}
    }
    /** 生成一个 key */
    public void generateRandomKey(){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(keyFile))){
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom random = new SecureRandom(); //比 Random 更安全的随机数生成器
            keyGenerator.init(random);
            this.key = keyGenerator.generateKey();  //生成 key 对象
        }catch (Exception e){ e.printStackTrace(); }
    }
    //将 key 的二进制对象写入到 keyFile 文件中
    private void writeKeyToFileWithObject(){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(MyCipherAES.keyFile))){
            out.writeObject(key);
        }catch (Exception e){ e.printStackTrace(); }
    }
    //直接读取二进制文件表示的 key
    private void getKeyFromFileWithObject(){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(MyCipherAES.keyFile))){
            this.key = (Key) in.readObject();
        }catch (Exception e){ e.printStackTrace(); }
    }

    //将 key 的 Base64 编码（字符串形式）写入到 keyfile 文件中
    private void writeKeyToFileWithBase64(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(MyCipherAES.keyFile))){
            byte[] keybytes = this.key.getEncoded();
            String keystr = Base64.getEncoder().encodeToString(keybytes);
            writer.write(keystr);
        }catch (Exception e){e.printStackTrace();}
    }
    //从文件中读取 Base64 编码的 key
    private void getKeyFromFileWithBase64(){
        try(BufferedReader reader = new BufferedReader(new FileReader(MyCipherAES.keyFile))){
            StringBuilder stringBuilder = new StringBuilder();
            int temp;
            while((temp=reader.read())!= -1) stringBuilder.append((char) temp);  //Character.toChars(temp)[0];
            String keyStr = stringBuilder.toString();

            //将 Base64 字符串解码成 key 对象
            byte[] keybytes = Base64.getDecoder().decode(keyStr);  //从字符串解码成 byte[]
            this.key = new SecretKeySpec(keybytes, "AES");  //把这个 byte[] 重新构造为 key 对象
        }catch (Exception e){e.printStackTrace();}
    }
    /* ##### End:生成 key ##### */

    //生成随机 salt
    public byte[] getSecureRandomSalt(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] saltBytes = new byte[32];  //8~32 字节任选，只影响 key 的穷举集合大小
        secureRandom.nextBytes(saltBytes);
//        System.out.println("salt bytes: " + Arrays.toString(saltBytes));  //直接打印的话就是十进制形式

        //输出 salt 字符串
        StringBuilder stringBuilder = new StringBuilder();
        for(byte b: saltBytes) stringBuilder.append(String.format("0x%02x, ", b));  //转16进制打印
        String saltString = stringBuilder.toString();
        System.out.println("Ransom salt: " + saltString.substring(0, saltString.length()-2));

        return saltBytes;
    }

    //生成随机 iv
    public byte[] getSecureRandomIV(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] ivBytes = new byte[16];
        secureRandom.nextBytes(ivBytes);
//        System.out.println("IV bytes: " + Arrays.toString(ivBytes));  //直接打印的话就是十进制形式

        //输出 IV 可读字符串
        StringBuilder stringBuilder = new StringBuilder();
        for(byte b: ivBytes) stringBuilder.append(String.format("0x%02x, ", b));  //转16进制打印
        String ivString = stringBuilder.toString();
        System.out.println("Ransom IV: " + ivString.substring(0, ivString.length()-2));

        return ivBytes;
    }

    /***** Begin:加密解密 *****/
    /**
     * 字符串加密，AES
     * 传入明文字符串 originString，加密后，进行 Base64 编码，返回密文字符串
     */
    public String encryptString(String originString){
        try{
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");  //ECB不需要iv向量，CBC需要iv向量
            //加密
            cipher.init(Cipher.ENCRYPT_MODE, this.key);
            byte[] encryptedBytes = cipher.doFinal(originString.getBytes());  //加密后的密文是 byte[]
            String encStr = Base64.getEncoder().encodeToString(encryptedBytes);  //密文转 Base64
            return encStr;
        }catch (Exception e){e.printStackTrace();}
        return null;
    }

    /**
     * 字符串解密
     * 传入 Base64 编码的密文，解码后再解密，返回解密后的明文字符串
     */
    public String decryptString(String encryptedString){
        try{
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");  //ECB不需要iv向量，CBC需要iv向量
            //解密
            cipher.init(Cipher.DECRYPT_MODE, this.key);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedString));
            String plarnStr = new String(decryptedBytes, "utf-8");
            return plarnStr;
        }catch (Exception e){e.printStackTrace();}
        return null;
    }

    /** 对文件加密，由 originFile 生成到 encryptedFile */
    public void encryptFile(String originFile, String encryptedFile){
        myFileEncryptOrDecrypt(originFile, encryptedFile, this.key, true);
    }

    /** 对文件解密，由 encryptedFile 生成到decryptedFile */
    public void decryptFile(String encryptedFile, String originFile){
        myFileEncryptOrDecrypt(encryptedFile, originFile, this.key, false);
    }

    /**
     * 辅助方法：文件加密/解密
     * @param in_file 输入的文件
     * @param out_file 输出的文件
     * @param key 用于加密的 key
     * @param doEncrypt true 为加密，false 为解密
     * */
    private void myFileEncryptOrDecrypt(String in_file, String out_file, Key key, boolean doEncrypt) {
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
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");  //"AES/ECB/PKCS5Padding"
            cipher.init(mode, this.key);  //AES 加密无需输入 parameter

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

    /** IO 流加密 */
    public void myStreamEncrypt(InputStream inputStream, OutputStream outputStream, Key key) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");  //"AES/ECB/PKCS5Padding"
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
    /** IO 流解密 */
    public void myStreamDecrypt(InputStream inputStream, OutputStream outputStream, Key key)throws Exception{
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, this.key);  //用密钥 key 初始化此 Cipher
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

    /***** 其他加密算法示例 *****/
    //AES/CBC 加密，CBC的特点是需要 IV 向量
    public void exampleAES_CBC(){
        System.out.println("===== Begin: [AES/CBC/PKCS5Padding] =====");
        this.salt = getSecureRandomSalt();
        this.iv = getSecureRandomIV();

        this.keySpecAlgorithm = "AES";
        getKeyFromConsole();
        try{
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  //设置 AES/CBC 模式
            IvParameterSpec ivParameterSpec = new IvParameterSpec(this.iv);

            String plainText =  "Hello, 滑滑的影子, " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
            System.out.println("原始文本：" + plainText);
            //加密并输出
            cipher.init(Cipher.ENCRYPT_MODE, this.key, ivParameterSpec);  //这里与 AES_ECB 不一样：CBC特点，必须设置 iv Parameter
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            String encryptString = Base64.getEncoder().encodeToString(encryptedBytes);
            System.out.println("加密后文本：" + encryptString);

            //解密并输出
            cipher.init(Cipher.DECRYPT_MODE, this.key, ivParameterSpec);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptString));
            String decryptString = new String(decryptedBytes, "utf-8");
            System.out.println("解密后文本：" + decryptString);
        }catch (Exception e){e.printStackTrace();}
        System.out.println("===== End: [AES/CBC/PKCS5Padding] =====" + "\n");
    }

    //PBE 加密
    public void examplePBE(){
        System.out.println("===== Begin: [PBE] Example =====");
        this.keySpecAlgorithm = "PBE";
        getKeyFromConsole();
        try{
            Cipher cipher = Cipher.getInstance("PBEWithHmacSHA256AndAES_128");  //设置 PBE 算法
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, iterationCount, ivParameterSpec);  //是否需要 ivParameterSpec 由 Cipher 算法最后的 AES_128 决定

            String plainText =  "Hello, 滑滑的影子, " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
            System.out.println("原始文本：" + plainText);
            //加密并输出
            cipher.init(Cipher.ENCRYPT_MODE, this.key, pbeParameterSpec);  //这里与 AES_ECB 不一样：PBE特点，必须设置 PBEParameterSpec
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            String encryptString = Base64.getEncoder().encodeToString(encryptedBytes);
            System.out.println("加密后文本：" + encryptString);

            //解密并输出
            cipher.init(Cipher.DECRYPT_MODE, this.key, pbeParameterSpec);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptString));
            String decryptString = new String(decryptedBytes, "utf-8");
            System.out.println("解密后文本：" + decryptString);
        }catch (Exception e){e.printStackTrace();}
        System.out.println("===== End: [PBE] Example =====\" + \"\\n");
    }

    /***** 测试类 *****/
    //示例所有生成 AES/ECB key 的方法
    public void exampleGenerateAESKey(){
        try{
            /* 生成 key */
            this.keySpecAlgorithm = "AES";  //设置key的算法为"AES"，用于后续 AES/ECB、AES/CBC 加密。
            //生成一个随机 AES key
            generateRandomKey();
            //从控制台输入password，构造成一个 AES key
            getKeyFromConsole();

            /* 保存 key 到文件 */
            //二进制 key 读写到文件
            writeKeyToFileWithObject(); //将 AES key 以二进制形式保存到文件
            getKeyFromFileWithObject(); //从二进制文件中读取 AES key
            //Base64 key 读写到文件
            writeKeyToFileWithBase64(); //将 AES key 以Base64保存到文件
            getKeyFromFileWithBase64(); //从Base64的文件中读取并解析为 AES key

            /* 检查 key 正确性，使用 AES 加密一个明文字符串 */
            String plainText =  "Hello, 滑滑的影子, " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            String encryptString = encryptString(plainText);  //加密后文本
            String decryptString = decryptString(encryptString);  //解密后文本
            System.out.println("原始文本：" + plainText + "\n加密后文本：" + encryptString + "\n解密后文本：" + decryptString);
        }catch (Exception e){e.printStackTrace();}
    }

    //生成一个 AES 密钥，然后对一个明文字符串进行 AES/ECB 加密。再对密文解密，检查解密前后的字符串是否一致
    public void testWithString(){
        try{
            this.keySpecAlgorithm = "AES";  //设置key的算法为"AES"，用于后续 AES/ECB加密。
//            generateRandomKey();  //生成一个随机key
            getKeyFromConsole();  //从控制台输入password，构造成一个 AES key

            String plainText =  "Hello, 滑滑的影子, " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            System.out.println("原始文本：" + plainText);
            //加密
            String encryptString = encryptString(plainText);
            System.out.println("加密后文本：" + encryptString);

            //解密
            String decryptString = decryptString(encryptString);
            System.out.println("解密后文本：" + decryptString);
        }catch (Exception e){e.printStackTrace();}
    }

    //测试文件 AES/ECB 加密解密，要求使用的 this.key 是 AES 算法。
    public void testWithFile(){
        this.keySpecAlgorithm = "AES";  //设置key的算法为"AES"，用于后续 AES/ECB、AES/CBC 加密。
        generateRandomKey();  //生成新 key

        //加密：将 originFile 文件加密，输出至 encryptedFile 文件中。
        encryptFile(MyCipherAES.originFile, MyCipherAES.encryptedFile);
        //解密：从 encryptedFile 文件读取密文，并解密到 decryptedFile 文件中。
        decryptFile(MyCipherAES.encryptedFile, MyCipherAES.decryptedFile);
    }

    //测试IO流的 AES/ECB 加密解密，要求使用的 this.key 是 AES 算法。
    public void testWithIO(){
        this.keySpecAlgorithm = "AES";  //设置key的算法为"AES"，用于后续 AES/ECB、AES/CBC 加密。
        generateRandomKey();  //生成新 key ，并写入文件

        //加密：将 originFile 文件加密，输出至 encryptedFile 文件中。
        try(
                FileInputStream in = new FileInputStream(originFile);
                FileOutputStream out = new FileOutputStream(encryptedFile);
        ) {
            myStreamEncrypt(in, out, this.key);  //temp.txt 加密到 temp2.txt
        }catch (Exception e){ e.printStackTrace(); }

        //解密：从 encryptedFile 文件读取密文，并解密到 decryptedFile 文件中。
        try(
                FileInputStream in = new FileInputStream(encryptedFile);
                FileOutputStream out = new FileOutputStream(decryptedFile);
        ) {
            myStreamDecrypt(in, out, this.key);
        }catch (Exception e){ e.printStackTrace(); }
    }

    public static void main(String[] args){
        MyCipherAES myCipherAES = new MyCipherAES();
//        myCipherAES.testWithString();
        myCipherAES.exampleAES_CBC();
    }
}