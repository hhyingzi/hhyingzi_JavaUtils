package MyJavaUtils;
/* ����ҵ���������ļ������� oldFileName �ж�ȡ�� ������ѧ�ţ���ҵ�࣬��ҵ���飬Ȼ�󰴸�ʽ����
 * 1.�������Ԥ��
 * 2.��ʾ���� y ȷ�ϸ��ģ����� n ��ֱ���˳�ȡ�����ġ�
*/


import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public class ChangeFileName{
    private static final String dirPath = "D:\\code\\JAVA\\WHJ_JavaUtils\\testFiles\\����";
    private File[] fileList; //�����ļ�

    private String[] stuNoList; //ѧ��
    private String[] stuNameList; //����
    private String[] classNameList; //�༶
    private String[] jobNameList; //��ҵ��
    private String[] fileSuffixList; //�ļ���׺��
    private String[] newFileNameList; //���ļ���
    
    //���캯��
    public ChangeFileName(){}

    //ִ�����
    public void run(){
        getFiles();
        
        //��ʾ�Ƿ����
        Scanner scanner = new Scanner(System.in);
        System.out.println("�Ƿ�ִ���������� ���롰1��ִ�У��������롰2�����˳����ڡ�");
        try{
            if(scanner.nextInt()==1)
            renameFiles();
        }
        finally{
            scanner.close();
        }
    }

    //��ȡĿ¼�������ļ�����ִ�н���
    private void getFiles(){
        File dir = new File(dirPath);
        if(!dir.isDirectory()){
            System.out.println("�ļ���·������");
            System.exit(0);
        }

        //��ʼ������Ա����������
        fileList = dir.listFiles();
        if(fileList==null) {
            System.out.println("û���ļ�");
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
                System.out.println("�����޸ġ�");
            }
            else{
                System.out.println(fileList[i].getName() + " -> " + newFileNameList[i]);
            }
        }
    }

    //���������ļ�������װ�����ļ���
    private void parseFilesDetail(int index){
        //����������

        //�������ļ���
        ArrayList<String> params = new ArrayList<String>();
        if(stuNoList[index] != null || stuNoList[index] != "") params.add(stuNoList[index]); //ѧ��
        if(stuNameList[index] != null || stuNameList[index] != "") params.add(stuNameList[index]); //����
        if(classNameList[index] != null || classNameList[index] != "") params.add(classNameList[index]); //�༶
        if(jobNameList[index] != null || jobNameList[index] != "") params.add(jobNameList[index]); //��ҵ��
        if(fileSuffixList[index] != null || fileSuffixList[index] != "") params.add(fileSuffixList[index]); //��׺
        newFileNameList[index] = String.join("-", params);
    }

    //�����������ļ���ѧ��-����-�༶-��ҵ��-��׺
    private void renameFiles(){
        for(int i=0; i<fileList.length; i++){
            //�������������쳣
            try{
                boolean isOk = fileList[i].renameTo(new File(dirPath + "\\" + newFileNameList[i]));
                if(isOk){
                    System.out.println(newFileNameList[i]); //�޸ĳɹ�����ʾ���ļ�����
                }
            }
            catch(SecurityException e){
                System.out.println(fileList[i].getName() + " ������ʧ�ܡ�" + e.getMessage()); //��ȷ��
            }
            catch(NullPointerException e){
                System.out.println(fileList[i].getName() + " δ�������ļ���������bug��"); //��ȷ��
            }
        }
        System.out.println("���������");
    }
}