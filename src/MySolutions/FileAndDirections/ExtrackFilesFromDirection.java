package MySolutions.FileAndDirections;

import java.io.File;
import java.util.ArrayList;

/**
 * 读取目录下所有文件，存储进 ArrayList 对象 resultFileArray 中。然后输出其每个文件名。
 */
public class ExtrackFilesFromDirection {
    public static String rootPath;
    private ArrayList<File> resultFileArray = new ArrayList<>();

    //构造函数，传入路径字符串，然后调用 initFromPath() 函数进行初始化。然后自动输出所有文件名。
    public ExtrackFilesFromDirection(){}
    public ExtrackFilesFromDirection(String dirPath){
        this.rootPath = dirPath;
        initFromPath(dirPath);
        printAllFileName();
    }
    //初始化函数，接收文件夹的路径字符串，构造成 File 对象，解析其下所有文件。若该路径并不是目录，则进行错误提示。
    public void initFromPath(String dirPath){
        File path = new File(dirPath);
        if(path.isDirectory()){
            getFileFromDirection(path);
        }
        else System.out.println(dirPath + " - 该路径不是正确的文件夹。");
    }

    //自我递归，接收代表目录的File对象，将其下所有文件存到类成员 resultFileArray 动态数组中。
    public void getFileFromDirection(File dir){
        File[] fileList; //所有文件
        if(!dir.isDirectory()){
            return;
        }
        fileList = dir.listFiles();
        if(fileList==null) {
            System.out.println("没有文件");
            System.exit(0);
        }
        for(File item : fileList){
            resultFileArray.add(item);  //如果是目录，就将目录名先存储。
            if(item.isDirectory()){
                getFileFromDirection(item);
            }
        }
    }

    public void visitFile(File file){
    }

    public void printAllFileName(){
        int fileNum = 1;
        for(File file: resultFileArray){
            //输出目录名，不计数
            if(file.isDirectory()){
                System.out.println("\\" + file.getName());
            }
            //输出文件名，计数
            else if(file.getName().endsWith(".jpg") || file.getName().endsWith(".ini")){
                continue;
            }
            else {
                System.out.println("[" + fileNum++ + "]\t" + file.getName());
            }
        }
    }

    public static void main(String[] args){
        String dirPath = "D:\\王恒基文档\\音乐\\音乐\\好听的";
        ExtrackFilesFromDirection myclass = new ExtrackFilesFromDirection(dirPath);
    }
}
