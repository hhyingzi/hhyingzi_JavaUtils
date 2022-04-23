package MyUtils;

import java.util.Scanner;

public class StdIO {
    //控制台输入输出
    public static void ConsoleIOExample(){
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            String str = sc.nextLine();
            if(str==null || str.isEmpty()) break;
            System.out.println(str);
        }
    }

}
