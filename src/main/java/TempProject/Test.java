package TempProject;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

//PBE，全称为“Password Base Encryption”，中文名“基于口令加密”，是一种基于密码的加密算法，其特点是使用口令代替了密钥，而口令由用户自己掌管，采用随机数杂凑多重加密等方法保证数据的安全性。
public class Test {
    public String password = "123456";
    public String plainText = "Hello world!";
    public String encryptedText;

    //pbe通过 password 和盐，生成 Key
    public static final String ALGORITHM = "PBKDF2WithHmacSHA256";//PBKDF2WithHmacSHA256 PBEWithHmacSHA512AndAES_256
    public byte[] salt = {(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08};  //PBE的盐8位足矣，32位最佳
    public int iterationCount = 1000;
    public String keyAlgorithm = "AES";  //从 byte[] 形式的key字节数组，生成 SecretKeySpec 时候，所用的密钥算法。有"AES", "PBE"
    public Key key;  //密钥。使用 getEncoded() 可转为 byte[]。使用 new SecretKeySpec(byte[] keyBytes, String algorithm) 可以读取 byte[] 生成 key。

    /* Cipher加密 */
    //Cipher.getInstance(this.TRANSFORMATION);
    public static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    //cipher.init(opmode, key, AlgorithmParameterSpec);
    // IV 参数。在AES标准中，必须是 128位（16字节）长度，因为要与 AES 的 block(128位）对应。
    public byte[] ivBytes = { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F };
    AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);  //IV参数，用于AES的CBC模式
    AlgorithmParameterSpec algorithmParameterSpec = new PBEParameterSpec(salt, iterationCount, ivSpec);  //PBE加密所用的携带 iv 的参数

    //根据 password 生成 Key
    public void getKeyFromPassword(){
        try{
            PBEKeySpec pbeKeySpec = new PBEKeySpec(this.password.toCharArray(), this.salt, this.iterationCount, 256);  //PBE key 规范
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Test.ALGORITHM);  //密钥工厂，使用 PBKDF2 算法
            Key pbeKey = keyFactory.generateSecret(pbeKeySpec);  //向密钥工厂传入 PBE key 规范，生成密钥 key

            byte[] keyBytes = pbeKey.getEncoded();  //正式拿来使用的byte[]形式的 key
            this.key = new SecretKeySpec(keyBytes, this.keyAlgorithm);  //默认 "AES"
//            this.key = pbeKey;
        }catch (Exception e){e.printStackTrace();}
    }

    public void encryptTest(){
        try{
            Cipher cipher = Cipher.getInstance(this.TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, this.key);
            byte[] encryptedBytes = cipher.doFinal(this.plainText.getBytes());
            System.out.println("Encrypted Text: " + Base64.getEncoder().encodeToString(encryptedBytes));

            cipher.init(Cipher.DECRYPT_MODE, this.key);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            System.out.println("Decrypted Text: " + new String(decryptedBytes, "utf-8"));
        }catch (Exception e){e.printStackTrace();}
    }

    /* Cipher 使用 PBE 模式加密示例 */
    public void PBEExample(){
        System.out.println("===== Begin: [PBE] Example =====");
        this.keyAlgorithm = "PBE";
        this.getKeyFromPassword();  //初始化 AES key
        try{
            Cipher cipher = Cipher.getInstance("PBEWithHmacSHA256AndAES_128");
            //加密并输出
            cipher.init(Cipher.ENCRYPT_MODE, this.key, algorithmParameterSpec);  //PBE，不需要设置 iv Parameter
            byte[] encryptedBytes = cipher.doFinal(this.plainText.getBytes());
            System.out.println("Encrypted Text: " + Base64.getEncoder().encodeToString(encryptedBytes));
            //解密并输出
            cipher.init(Cipher.DECRYPT_MODE, this.key, algorithmParameterSpec);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            System.out.println("Decrypted Text: " + new String(decryptedBytes, "utf-8"));
        }catch (Exception e){e.printStackTrace();}
        System.out.println("===== End: [PBE] Example =====" + "\n");
    }

    /* Cipher 使用 AES/ECB 模式加密示例 */
    public void AESECBExample(){
        System.out.println("===== Begin: [AES/ECB/PKCS5Padding] Example =====");
        this.keyAlgorithm = "AES";
        this.getKeyFromPassword();  //初始化 AES key
        try{
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            //加密并输出
            cipher.init(Cipher.ENCRYPT_MODE, this.key);  //ECB是一种不使用初始向量（IV）的AES加密模式，因此不需要为它创建AlgorithmParameterSpec
            byte[] encryptedBytes = cipher.doFinal(this.plainText.getBytes());
            System.out.println("Encrypted Text: " + Base64.getEncoder().encodeToString(encryptedBytes));
            //解密并输出


            cipher.init(Cipher.DECRYPT_MODE, this.key);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            System.out.println("Decrypted Text: " + new String(decryptedBytes, "utf-8"));
        }catch (Exception e){e.printStackTrace();}
        System.out.println("===== End: [AES/ECB/PKCS5Padding] Example =====" + "\n");
    }

    /* Cipher 使用 AES/CBC 模式加密示例 */
    public void AESCBCExample(){
        System.out.println("===== Begin: [AES/CBC/PKCS5Padding] =====");
        this.keyAlgorithm = "AES";
        this.getKeyFromPassword();  //初始化 AES key
        try{
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            //加密并输出
            cipher.init(Cipher.ENCRYPT_MODE, this.key, ivSpec);  //CBC特点，必须设置 iv Parameter
            byte[] encryptedBytes = cipher.doFinal(this.plainText.getBytes());
            System.out.println("Encrypted Text: " + Base64.getEncoder().encodeToString(encryptedBytes));
            //解密并输出
            cipher.init(Cipher.DECRYPT_MODE, this.key, ivSpec);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            System.out.println("Decrypted Text: " + new String(decryptedBytes, "utf-8"));
        }catch (Exception e){e.printStackTrace();}
        System.out.println("===== End: [AES/CBC/PKCS5Padding] =====" + "\n");
    }

    public void encryptWithText(){
        try{
            Cipher cipher = Cipher.getInstance("PBEWithHmacSHA256AndAES_128");
            cipher.init(Cipher.ENCRYPT_MODE, this.key, this.algorithmParameterSpec);

            byte[] encryptedBytes = cipher.doFinal(this.plainText.getBytes());
            this.encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
        }catch (Exception e){e.printStackTrace();}
    }

    public void decryptWithText(){
        try{
            Cipher cipher = Cipher.getInstance("PBEWithHmacSHA256AndAES_128");
            this.algorithmParameterSpec = new PBEParameterSpec(this.salt, 1000);
            cipher.init(Cipher.DECRYPT_MODE, this.key, this.algorithmParameterSpec);

            byte[] encryptedBytes = Base64.getDecoder().decode(this.encryptedText);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            String textResult = new String(decryptedBytes);
            System.out.println("The decrypted result is: " + textResult);
        }catch (Exception e){e.printStackTrace();}
    }

    //教程
    private static Key getPasswordBasedKey(String cipher, int keySize, char[] password) throws Exception {
        byte[] salt = new byte[100];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password, salt, 1000, keySize);
        SecretKey pbeKey = SecretKeyFactory.getInstance("PBEWithHmacSHA256AndAES").generateSecret(pbeKeySpec);
        return new SecretKeySpec(pbeKey.getEncoded(), cipher);
    }

    /* GPT 可运行的示例 */
    public void gptExample(){
        String password = "whj"; // 用户提供的密码
        String plaintext = "Hello, World!"; // 要加密的明文

        // 生成PBE密钥
        byte[] salt = {
                (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04,
                (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08
        };
        int iterationCount = 1000;

        try {
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterationCount);
            SecretKey secretKey = SecretKeyFactory.getInstance("PBEWithHmacSHA256AndAES_128").generateSecret(keySpec);

            // 创建PBE参数规范
            AlgorithmParameterSpec parameterSpec = new PBEParameterSpec(salt, iterationCount);

            // 创建加密器并初始化为加密模式
            Cipher cipher = Cipher.getInstance("PBEWithHmacSHA256AndAES_128");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

            // 加密明文
            byte[] ciphertext = cipher.doFinal(plaintext.getBytes());

            // 将密文进行Base64编码
            String encodedCiphertext = Base64.getEncoder().encodeToString(ciphertext);

            System.out.println("Plaintext: " + plaintext);
            System.out.println("Encrypted ciphertext: " + encodedCiphertext);
        }catch (Exception e){e.printStackTrace();}
    }

    /* 列举 java 的 Security 包支持的 SecretKeyFactory algorithms 算法 */
    private void showAlgorithmsInSecretKeyFactory(){
        Set<String> algorithms = new HashSet<>();

        Provider[] providers = Security.getProviders();
        for (Provider provider : providers) {
            Set<Object> keys = provider.keySet();
            for (Object key : keys) {
                String keyString = key.toString();
                if (keyString.startsWith("SecretKeyFactory.")) algorithms.add(keyString.substring("SecretKeyFactory.".length()));
            }
        }
        for (String algorithm : algorithms) System.out.println(algorithm);
    }

    /* 列举 java 的 Security 包支持的 cipher 的 transformations 算法 */
    private void showTransformationsInSecretKeyFactory(){
        Set<String> transformations = new HashSet<>();

        Provider[] providers = Security.getProviders();
        for (Provider provider : providers) {
            Set<Provider.Service> services = provider.getServices();
            for (Provider.Service service : services) {
                if (service.getType().equals("Cipher")) transformations.add(service.getAlgorithm());
            }
        }
        for (String transformation : transformations) System.out.println(transformation);
    }


    public static void main(String[] args) throws Exception {
        Test test = new Test();

        //My Example with step.
        try{
            System.out.println("Plain Text is: " + test.plainText);
            test.getKeyFromPassword();
            test.PBEExample();  //完美运行
            test.AESECBExample();  //完美运行
            test.AESCBCExample();
//            test.encryptTest();
//            test.encryptWithText();
//            test.decryptWithText();
        }catch (Exception e){e.printStackTrace();}

        //GPT Example
//        try{
//            System.out.println("=====Begin: GPT Test=====");
//            test.gptExample();
//            System.out.println("=====End: GPT Test=====");
//        }catch (Exception e){e.printStackTrace();}
    }
}
