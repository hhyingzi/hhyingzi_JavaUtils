package TempProject;

import java.util.*;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main {
    public static void main(String[] args){
        //输入 n
        int n = 0;  //n
        Scanner in = new Scanner(System.in);
        if(in.hasNextInt())  n = in.nextInt();
        //输入 m
        int[] mArr = new int[n];
        for(int i=0; i<n; i++){
            if(in.hasNextInt()) mArr[i] = in.nextInt();
        }
        //平分后每堆石头应有的的重量和
        int halfWeight = 0;
        for(int i=0; i<mArr.length; i++) halfWeight += mArr[i];
        halfWeight /= 2;

        //开始平分
        int count = -1;  //当前堆石头的数量
        int currWeight = 0;  //当前堆石头的重量
        //包含有石头的栈
        ArrayDeque<Integer> stack = new ArrayDeque<>();  //被分出去的石头

        Main main = new Main();

    }
}
