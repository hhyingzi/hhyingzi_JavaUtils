package JavaSE.IO;

import java.net.URI;
import java.util.regex.*;
import java.io.*;
import java.util.*;

public class MyDirFile {
    private static final String dir_path = "D:\\王恒基文档\\Pictures\\表情包\\";

    //查看目录信息
    public void printDirInfo(){
        File file = new File(dir_path);

        //查看file是否为目录
        if(file.isDirectory()) {
            System.out.println("File is Directory.");
            //获取目录信息
            System.out.println("file.toString(): " + file.toString());  //打印该File路径
            System.out.println("file.getName(): " + file.getName());  //目录名
            System.out.println("file.getAbsolutePath(): " + file.getAbsolutePath());  //打印绝对路径名
            URI uri = file.toURI();
            System.out.println("file.toURI: " + uri);  //打印 URI 形式的路径
            try{
                System.out.println("file.toURL: " + uri.toURL());  //打印 URL 形式的路径，必须先通过 URI 转义特殊符号。
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("Total Space(byte):" + file.getTotalSpace() + "  Usable Space:" + ((double) file.getUsableSpace())/1024/1024 + "Mb");  //打印可用空间，注意 getFreeSpace() 可能大于实际可用值，所以我们没有使用它。
            System.out.println("File is Readable? " + (file.canRead()?"Yes":"No"));  //检查可读权限
            System.out.println("Last Modified time is: " + new Date(file.lastModified()));  //最后修改时间
            System.out.println();
        }
        else{
            System.out.println("Not a Directory.");
            //创建目录
            if(file.getParentFile().exists()) file.mkdir();  //存在父目录则直接创建目录
            else file.mkdirs();  //不存在父目录则连父目录一起创建（推荐）
        }
    }

    //输出目录下文件内容，经过文件名匹配和结果排序。
    public void printFileInDir(){
        File dir = new File(dir_path);
        if(!dir.isDirectory()){
            System.out.println("ERROR: File Not A Directory!");
            return;
        }
        //输出子目录的名称
        System.out.println("========输出子目录==========");
        File[] dirArr = dir.listFiles();
        for(File item:dirArr){
            if(item.isDirectory()) System.out.println(item);
        }

        //过滤出符合要求的文件名
        System.out.println("========输出jpg文件并排序==========");
        String[] nameArr = dir.list(new DirFilter());  //使用文件名过滤器类，只留下 .jpg 后缀文件

        //对输出结果进行排序
        Arrays.sort(nameArr, new DirSorter());  //自建的比较器，比较字符串大小时忽略大小写。
        //Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);  //与上面等价的。String内建比较器，也是忽略大小写比较字符串大小，可免去自建比较器的麻烦。
        for(String dirItem : nameArr)
            System.out.println(dirItem = dirItem);
    }

    public static void main(String[] args) {
        MyDirFile myObj = new MyDirFile();
        myObj.printDirInfo();
        myObj.printFileInDir();
    }
}

//实现一个文件名过滤器类，只保留 .jpg 后缀的文件
class DirFilter implements FilenameFilter {
    private Pattern pattern;
    private String default_regex = ".*\\.jpg";
    //构造函数，初始化pattern
    public DirFilter(){
        pattern = Pattern.compile(default_regex);
    }
    //实现 accept 方法，当操作文件目录时，判定文件是否符合要求
    public boolean accept(File dir, String name) {
        return pattern.matcher(name).matches();
    }
}

//实现一个文件名排序器 Comparator 类（忽略大小写），字符串1比字符串2 字母小，长度短，则返回负值
class DirSorter implements Comparator<String>{
    @Override
    public int compare(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();
        int minLen = len1;
        if(minLen > len2) minLen = len2;  //字符串最小长度
        for(int i=0; i<minLen; i++){
            char c1 = s1.charAt(i); c1 = Character.toLowerCase(c1);
            char c2 = s2.charAt(i); c2 = Character.toLowerCase(c2);
            if(c1 != c2) return c1-c2;  //字符串中每个字符，转化为小写，然后比较
        }
        return len1 - len2;  //如果字符串一样，则比较长度
    }
}
