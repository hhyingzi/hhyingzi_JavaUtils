package TempProject;
/* 收作业批量更改文件名，从 oldFileName 中读取到 姓名，学号，创业班，作业详情，然后按格式归类
 * 1.输出更改预览
 * 2.提示输入 y 确认更改，输入 n 或直接退出取消更改。
*/


import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public class ChangeFileName{
    private static final String dirPath = "D:\\code\\JAVA\\WHJ_JavaUtils\\testFiles\\名单";
    private File[] fileList; //所有文件

    private String[] stuNoList; //学号
    private String[] stuNameList; //姓名
    private String[] classNameList; //班级
    private String[] jobNameList; //作业名
    private String[] fileSuffixList; //文件后缀名
    private String[] newFileNameList; //新文件名
    
    //构造函数
    public ChangeFileName(){}

    //执行入口
    public void run(){
        getFiles();
        
        //提示是否操作
        Scanner scanner = new Scanner(System.in);
        System.out.println("是否执行重命名？ 输入“1”执行，否则输入“2”或退出窗口。");
        try{
            if(scanner.nextInt()==1)
            renameFiles();
        }
        finally{
            scanner.close();
        }
    }

    //读取目录下所有文件，并执行解析
    private void getFiles(){
        File dir = new File(dirPath);
        if(!dir.isDirectory()){
            System.out.println("文件夹路径有误");
            System.exit(0);
        }

        //初始化各成员变量的数组
        fileList = dir.listFiles();
        if(fileList==null) {
            System.out.println("没有文件");
            System.exit(0);
        }
        stuNoList = new String[fileList.length];
        stuNameList = new String[fileList.length];
        classNameList = new String[fileList.length];
        jobNameList = new String[fileList.length];
        fileSuffixList = new String[fileList.length];
        newFileNameList = new String[fileList.length];

        for(int i=0; i<fileList.length; i++){
            parseFilesDetail(i);
            if(fileList[i].getName().equals(newFileNameList[i])){
                System.out.println("无需修改。");
            }
            else{
                System.out.println(fileList[i].getName() + " -> " + newFileNameList[i]);
            }
        }
    }

    //解析单个文件，并组装成新文件名
    private void parseFilesDetail(int index){
        //解析。。。

        //构造新文件名
        List<String> params = new ArrayList<>();
        if(stuNoList[index] != null || stuNoList[index] != "") params.add(stuNoList[index]); //学号
        if(stuNameList[index] != null || stuNameList[index] != "") params.add(stuNameList[index]); //姓名
        if(classNameList[index] != null || classNameList[index] != "") params.add(classNameList[index]); //班级
        if(jobNameList[index] != null || jobNameList[index] != "") params.add(jobNameList[index]); //作业名
        if(fileSuffixList[index] != null || fileSuffixList[index] != "") params.add(fileSuffixList[index]); //后缀
        newFileNameList[index] = String.join("-", params);
    }

    //重命名所有文件：学号-姓名-班级-作业名-后缀
    private void renameFiles(){
        for(int i=0; i<fileList.length; i++){
            //重命名并处理异常
            try{
                boolean isOk = fileList[i].renameTo(new File(dirPath + "\\" + newFileNameList[i]));
                if(isOk){
                    System.out.println(newFileNameList[i]); //修改成功，显示新文件名。
                }
            }
            catch(SecurityException e){
                System.out.println(fileList[i].getName() + " 重命名失败。" + e.getMessage()); //待确定
            }
            catch(NullPointerException e){
                System.out.println(fileList[i].getName() + " 未解析出文件名，代码bug。"); //待确定
            }
        }
        System.out.println("重命名完成");
    }
}