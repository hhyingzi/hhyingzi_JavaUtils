package MyUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class MyDirectoriesAndFiles {
    private static final String dir_path = "D:\\王恒基文档\\音乐\\音乐\\好听的";
    public List<File> files = new ArrayList<File>();
    public List<File> dirs = new ArrayList<File>();

    //获得当前目录下所有文件夹和文件，并输出
    public void getSingleDirFiles(){
        this.files.clear();
        this.dirs.clear();
        File root = new File(dir_path);
        if(!root.isDirectory()) {
            System.out.println("Warn!!! Not a Directory: " + dir_path);
            return;
        }
        File[] files = root.listFiles(new MyDirFilter());
        for(File item:files){
            if(item.isFile()) this.files.add(item);
            else this.dirs.add(item);
        }
        //输出所有目录和文件
        for(File item:this.dirs){
            System.out.println("\t目录：./" + item.getName());
        }
        for(File item:this.files){
            System.out.println(item.getName());
        }
        System.out.println("共 " + this.files.size() + "个文件.");
    }

    //获得目录下所有文件，按目录输出每个文件
    public void getMultiDirFiles(){
        this.files.clear();
        this.dirs.clear();
        File root = new File(dir_path);
        File tempFile = null;
        LinkedList<File> dirStack = new LinkedList<>();
        dirStack.push(root);
        while(!dirStack.isEmpty()){
            tempFile = dirStack.pop();
            System.out.print("\n【" + tempFile.getName());  //输出目录名称
            File[] files = tempFile.listFiles(new MyDirFilter());
            System.out.println("】，" + files.length + "个内容");
            for(File item:files){
                if(item.isDirectory()){  //是目录则入栈
                    dirStack.push(item);
                    this.dirs.add(item);
                }
                else{
                    System.out.println(item.getName());  //是文件则访问
                    this.files.add(item);
                }
            }
        }
        System.out.println("共检测到 " + this.dirs.size() + "个目录，" + this.files.size() + "个文件.");
    }

    //实现一个文件名过滤器类，按照需求过滤文件
     class MyDirFilter implements FilenameFilter {
        private Pattern pattern;
        private final String default_regex = ".*";  //所有文件都允许
        private final String picture_regex = ".*\\.((jpg)|(jpeg)|(gif)|(png)|(bmp))";  //只保留图片格式后缀的文件
        private final String music_regex = ".*\\.((mp3)|(flac)|(ape)|(wav))";  //只保留音乐格式后缀的文件
        private final String custom_regex = ".*";  //用户自定义文件名过滤规则
        //构造函数，初始化pattern
        public MyDirFilter(){
            pattern = Pattern.compile(default_regex);
        }
        //实现 accept 方法，当操作文件目录时，判定文件是否符合要求
        public boolean accept(File dir, String name) {
            return pattern.matcher(name).matches();
        }
    }

    public static void main(String[] args){
        MyDirectoriesAndFiles mySolution = new MyDirectoriesAndFiles();
        mySolution.getMultiDirFiles();
    }
}
