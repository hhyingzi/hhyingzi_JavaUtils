package MyUtils;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import org.apache.commons.codec.binary.Hex;

import java.io.File;
import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 阿里云OSS
 */
public class MyOss {
    private static final String file_delete_doc = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\main\\java\\Datas\\temp.txt";  //markdown 原文放入这里，自动识别文件名，放入回收站，并删除

    private static final String file_upload_dir = "D:\\code\\OSS传文件\\0上传";  //上传的文件放入该目录，执行时自动重命名并上传
    private static final String file_download_dir = "D:\\code\\OSS传文件\\1预览";  //下载指定文件到这个目录
    private static final String file_delete_dir = "D:\\code\\OSS传文件\\2已删除文件回收站";  //删除的文件自动放入该本地回收站

    //新获取的 OSS 密钥放在这里，转化为 Base64 后要进行删除。
    private static final String INIT_ACCESSKEYID = "";
    private static final String INIT_ACCESSKEYSECRET = "";

    //Base64 编码的 OSS 密钥。
    public static final String AccessKeyIdBase64 =      "TFRBSTV0OHBlRENaWkFHYVZORG5LYWZy";
    public static final String AccessKeySecretBase64 =  "WEpUaFVtd09VUFJDVVRpSW5zV2c2d3BBYTVYTGto";

    //系统运行时，将密钥解码后保存在这个类变量里。
    private static String AccessKeyId;
    private static String AccessKeySecret;
    private static String endpointHangzhou = "oss-cn-hangzhou.aliyuncs.com";

    private static OSS ossClient;
    private static String bucketYoudao = "hhyingzi-youdao";

    private static String urlPrefix = "https://hhyingzi-youdao.oss-cn-hangzhou.aliyuncs.com/";

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    // Markdown  识别待删除文件名
    // ![123](https://hhyingzi-youdao.oss-cn-hangzhou.aliyuncs.com/git%E5%8E%9F%E7%90%86.png)
    // ![.*](.+                                                   /(.+))
    //                                                           group(1)
    public static String patternStringMarkdown = "!\\[.*\\]\\(.*\\/(.+)\\)";
    // Log 识别待删除文件名
    // ![123](https://hhyingzi-youdao.oss-cn-hangzhou.aliyuncs.com/git%E5%8E%9F%E7%90%86.png)
    //                hhyingzi-youdao.oss-cn-hangzhou.aliyuncs.com/(.+)[)\s]
    //                                                           group(1)
//    public static String patternStringLog = "hhyingzi-youdao\\.oss-cn-hangzhou\\.aliyuncs\\.com\\/(.+?)[\\s<)]";
    public static String patternStringLog = "https://(hhyingzi-youdao\\.oss-cn-hangzhou\\.aliyuncs\\.com\\/.*\\.(png))";

    private static String data_file = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\main\\java\\Datas\\temp.txt"; //临时文件，草稿

    public static final System.Logger logger = System.getLogger(MyOss.class.getName());  //采用 System.Logger 使用日志
    public static final String logFile = "D:\\code\\OSS传文件\\OSSYoudao.log";

    /* ========== Begin 密钥获取，与登录处理 ========== */
    /** 将拷贝过来的原密钥 INIT_KEY 转化为 Base64 数据。*/
    public static void myConvertInitKeyToBase64(){
        try{
            System.out.println("Base64 of AccessKeyId: " + MyOss.AccessKeyIdBase64);
            System.out.println("Base64 of AccessKeySecret: " + MyOss.AccessKeySecretBase64);
        }catch (Exception e){ e.printStackTrace(); }
    }
    /** 查看 OSS 账户和密钥（依托于 Base64 字符串）*/
    public static void printKeyString(){
        try{
            myInitAccountAccess();
            System.out.println("AccessKeyId: " + MyOss.AccessKeyId);
            System.out.println("AccessKeySecret: " + MyOss.AccessKeySecret);
        }catch (Exception e){e.printStackTrace();}
    }
    /** 从 Base64 获取密钥，并连接到 OSS 实例 */
    public static void myInitAccountAccess(){
        try{
            MyOss.AccessKeyId = new String(Base64.getDecoder().decode(MyOss.AccessKeyIdBase64), "utf-8");
            MyOss.AccessKeySecret = new String(Base64.getDecoder().decode(MyOss.AccessKeySecretBase64), "utf-8");

            /* 创建 OSS 实例 */
            /* 对 ossClient 进行 Configuration 配置 */
            ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
            conf.setConnectionTimeout(1000 * 3); // 设置建立连接的超时时间，默认为50000毫秒。

            MyOss.ossClient = new OSSClientBuilder().build(MyOss.endpointHangzhou, MyOss.AccessKeyId, MyOss.AccessKeySecret, conf);
            System.out.println("Aliyun OSS Loading Successful!");
        }catch (Exception e){ e.printStackTrace(); }
    }
    /* ========== END 密钥获取，与登录处理 ========== */
    /* ========== Begin OSS 的底层处理（DAO） ========== */
    // 列举指定 bucketName 中的文件，默认 1000 个。
    public static void myPrintObjects(String bucketName){
        ListObjectsV2Result result = ossClient.listObjectsV2(bucketName);
        System.out.println("文件列表：");
        List<OSSObjectSummary> ossObjectSummaryList = result.getObjectSummaries();
        for (int i=0; i<ossObjectSummaryList.size(); i++) {
            System.out.println("\t[" + (i+1) + "]: " + ossObjectSummaryList.get(i).getKey());  //打印文件名
        }
    }
    //根据文件名或 URL，下载一个文件
    public static void ossDownloadFile(String file_url){
        Pattern pattern = Pattern.compile(patternStringLog);  //识别 URL，提取文件名为 group(1)
        Matcher matcher = pattern.matcher(file_url);

        String pureFileName;  //OSS中对应的纯文件名
        //不是URL，则字符串本身就是纯文件名，是URL，则提取其中的文件名。
        if(!matcher.matches()) pureFileName = file_url;
        else pureFileName = matcher.group(1);

        //检测OSS是否存在此文件
        boolean isExist = ossClient.doesObjectExist(bucketYoudao, pureFileName);
        if(isExist){
            try{
                File downloadFile = new File(file_download_dir + File.separator + "手动下载:" + pureFileName);
                ossClient.getObject(new GetObjectRequest(bucketYoudao, pureFileName), downloadFile);
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("OSS含有此文件，但下载失败：【" + pureFileName + "】， Of URL: " + file_url);
            }
        }
        else{
            System.out.println("OSS不含此文件：【" + pureFileName + "】， Of URL: " + file_url);
        }
    }
    //上传一个文件，并返回 URL
    public static boolean ossUploadAFile(File file) throws Exception{
        //失败重传3次
        for(int i=0; i<3; i++){
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketYoudao, file.getName(), file);
            // 设置该属性可以返回response。如果不设置，则返回的response为空。
            putObjectRequest.setProcess("true");
            // 上传文件。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            // 如果上传成功，则会接收到成功代码 200，那么制造 URL 并返回。
            if(result.getResponse().getStatusCode() == 200) {
                String urlString = urlPrefix + file.getName();

                //将该文件移动到 file_download_dir 文件夹
                myMoveToDownloadDir(file);
                //记录上传日志
                String logSuccessStr = myGetDateTime() + "上传成功：" + file.getName() + System.lineSeparator() + urlString;
//                System.out.println(logSuccessStr);
                logger.log(System.Logger.Level.INFO, logSuccessStr);
                return true;
            }
            else{
                logger.log(System.Logger.Level.ERROR, "上传失败" + file.getName());
            }
        }
        return false;
    }
    //查询一个文件的 url。
    public static String ossIsExist(File file) throws Exception{
        //判断是否存在
        boolean found = ossClient.doesObjectExist(bucketYoudao, file.getName());
        if(found){
            String urlString = urlPrefix + URLEncoder.encode(file.getName(), "utf-8");
            return urlString;
        }
        else return null;
    }
    //下载到回收站，并删除这个文件
    public static boolean ossDelAFile(File file) throws Exception{
        //判断是否存在
        boolean found = ossClient.doesObjectExist(bucketYoudao, file.getName());
        if(found){
            //下载
            File downloadFile = new File(file_delete_dir + File.separator + file.getName());
            ossClient.getObject(new GetObjectRequest(bucketYoudao, file.getName()), downloadFile);
            //删除
            ossClient.deleteObject(bucketYoudao, file.getName());
            String logDelStr = myGetDateTime() + "检测到 url 并删除：" + file.getName();  //日后用于记录 Log
//            System.out.println(logDelStr);
            logger.log(System.Logger.Level.INFO, logDelStr);
            return true;
        }
        else return false;
    }
    /* ========== End Bucket 中对文件的处理 ========== */
    /* ========== Begin 其他辅助工具类  ========== */
    //打印所拥有的的 bucket
    public static void myPrintBuckets(){
        try {
            List<Bucket> buckets = ossClient.listBuckets();
            System.out.println("拥有的 bucket 名称为：" + buckets.size());
            for (int i=0; i<buckets.size(); i++) {
                System.out.println("[" + (i+1) + "]: " + buckets.get(i).getName());
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
    //将文件移动到 file_download_dir 文件夹
    public static void myMoveToDownloadDir(File file) throws Exception{
        File newFile = new File(file_download_dir + File.separator + file.getName());
        Files.move(file.toPath(), newFile.toPath()); //如果重名冲突会异常
    }
    //获取当前时间
    public static String myGetDateTime(){
        try{
            return LocalDateTime.now().format(MyOss.formatter);
        }catch (Exception e){e.printStackTrace(); }
        return null;
    }
    //写入日志
    public static void initLogger() throws Exception{
        Logger.getLogger(MyOss.class.getName()).setLevel(Level.INFO);  //忽略DEBUG等，输出 INFO, WARNING, ERROR。
        Logger.getLogger(MyOss.class.getName()).addHandler(new FileHandler(logFile, true));
    }
    /* ========== End 其他辅助工具类  ========== */
    //给文件重新命名
    private static File myRenameFile(File file) throws Exception{
        String fileName = file.getName();

        //前缀
        StringBuffer nameBuffer = new StringBuffer();
        String prefix = "OSS_";
        nameBuffer.append(prefix);

        //上传时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");
        String date_time = LocalDateTime.now().format(formatter);
        nameBuffer.append(date_time + "_");

        //哈希值：文件 MD5 摘要
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] hashBytes = messageDigest.digest(Files.readAllBytes(file.toPath()));
        String hash = new String(Hex.encodeHex(hashBytes)).toUpperCase();
        nameBuffer.append(hash);

        //提取文件后缀名 ".xxx"
        String suffix = null;
        Pattern suffixPattern = Pattern.compile(".*(\\..+)");
        Matcher suffixMatcher = suffixPattern.matcher(fileName);
        if(suffixMatcher.matches()) suffix = suffixMatcher.group(1);
        else throw new RuntimeException("!!!!!!文件缺少后缀名!!!!!!");
        nameBuffer.append(suffix);

        String newFilePath = file.getParent() + File.separator + nameBuffer.toString();
        File newFile = new File(newFilePath);
        file.renameTo(newFile); //重命名文件
        return newFile;
    }
    /* ========== Begin 主要功能 ========== */
    //从 上传文件夹 MyOSS.file_upload_dir 中重命名文件，并上传，然后删除上传的文件。命名规则： OSS_date_time_Hash.xxx
    public static void myUploadFiles(){
        ArrayList<String> fileNames = new ArrayList<>();
        try{
            File dir = new File(MyOss.file_upload_dir);
            if(!dir.isDirectory()){
                System.out.println("!!!!! 上传文件夹不存在 !!!!!");
                return;
            }
            File[] fileList= dir.listFiles();
            int upLoadCount = 0;
            for(File file: fileList){
                String oldName = file.getName();
                if(file.isFile()){
                    file = myRenameFile(file);
                    logger.log(System.Logger.Level.INFO, "即将上传文件： 【" + oldName + "】");
                    //上传该文件
                    boolean isSuccess = ossUploadAFile(file);
                    if(isSuccess) upLoadCount++;
                }
            }
            System.out.println("共上传：" + upLoadCount + " 个文件。");
        }catch (Exception e){ e.printStackTrace(); }
    }
    //从 markdown 中识别文件名，下载进 file_delete_dir 文件夹，然后从 OSS 中删除。
    public static void myDelete(String patternString) throws Exception{
        String dataString = Files.readString(Paths.get(file_delete_doc));

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(dataString);
        int delCount = 0;
        while(matcher.find()){
            String temp = matcher.group(1);
            String fileName = URLDecoder.decode(temp, "utf-8");
            boolean isdel = ossDelAFile(new File(fileName));  //调用 删除方法
            if(isdel) delCount++;
        }
        System.out.println("共删除 " + delCount + "个文件。");
    }
    /* ========== End 主要功能 ========== */

    public static void main(String[] args){
        try{
            MyOss.initLogger();
            myInitAccountAccess(); //初始化一个 OSS 实例 MyOss.ossClient

            //检测上传文件夹，上传所有文件，记录上传日志，移动至下载文件夹，
            myUploadFiles();

            //从 markdown 中检测需要删除的文件，移动至下载文件夹，删除所有文件
//            myDelete(patternStringLog);

            //针对文件的删除
//            ArrayList<String> delList = new ArrayList<>();
//            delList.add();
//            for(String item: delList) ossDelAFile(new File(item));

        }catch (Exception e){e.printStackTrace(); }
        finally { if(ossClient != null) ossClient.shutdown(); }
    }
}
