package JavaSE.Class;

/**
 * 演示匿名内部类的用法
 */

import java.util.regex.*;
import java.io.*;
import java.util.*;

public class MyAnonymousInnerClass {
    private static final String dir_path = "D:\\王恒基文档\\Pictures\\表情包\\";
    private static String default_regex = ".*\\.jpg";

    //实现 FilenameFilter 接口的匿名内部类（没有类名，原功能见 src -> JavaSE -> IO -> MyDirFile.java）
    public static FilenameFilter getNameFilterByAnonymous(String fileNameRegex) {
        return new FilenameFilter() {
            private Pattern pattern = Pattern.compile(fileNameRegex);
            public boolean accept(File dir, String name) {
                return pattern.matcher(name).matches();
            }
        };
    }
    //输出表情包文件夹下所有 .jpg 结尾的文件
    public static void main(String[] args) {
        File path = new File(dir_path);
        String[] list;
        if(args.length == 0)
            list = path.list();
        else
            //初级使用匿名内部类，需结合上面的 getNameFilterByAnonymous 函数。
            list = path.list(getNameFilterByAnonymous(default_regex));
            //更终极地使用匿名内部类
            list = path.list(new FilenameFilter() {
                private Pattern pattern = Pattern.compile(default_regex);
                @Override
                public boolean accept(File dir, String name) {
                    return pattern.matcher(name).matches();
                }
            });
        Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);  //内建比较器，无视大小写，按字典顺序对数组排序
        for(String dirItem : list)
            System.out.println(dirItem);
    }
}