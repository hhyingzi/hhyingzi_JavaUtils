package MyJavaUtils;

import java.util.Arrays;
import java.util.Scanner;
/*
* 请使用标准输入输出(System.in, System.out)；
勿使用图形、文件、网络、系统相关的操作，如java.lang.Process , javax.swing.JFrame , Runtime.getRuntime
不要自定义包名称，否则会报错，即不要添加package answer之类的语句；
您可以写很多个类，但是必须有一个类名为Main，并且为public属性，并且Main为唯一的public class
Main类的里面必须包含一个名字为'main'的静态方法（函数），这个方法是程序的入口*/


/*
* 流水潺潺
时间限制： 3000MS
内存限制： 1048576KB
题目描述：
最近，小七发现了一处干涸的河道。

河道不同的位置高度也不相同，从河道起点到终点有n个位置，这些位置编号为1~n。每一个位置i的高度可以表示为hi(1、n是河道的两端，因此1左边、n右边的高度可以视为无穷大)。

本着环保的精神，小七希望在恰好一个位置注入水源，使得这个位置是有水的。自然地，水会从高处向低处流动，但原来的位置仍然有水。具体地来说，如果当前一个位置i是有水的，并且有某一个相邻的格子j高度严格小于i(hj < hi)，那么j也会成为有水的，并且i仍然是有水的。对于j相邻的格子也是如此。

现在小七想知道，通过一次注入水源最多可以使得多少个位置变成有水的



输入描述
第一行一个正整数n(1<=n<=5000)，表示河道有n个位置

第二行n个正整数，第i个表示位置i的高度hi(0<=hi<=1000000000)

输出描述
输出仅一行，即答案


样例输入
5
5 1 2 1 5
样例输出
3
*/

public class Solution360_1 {
    public static void main(){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] h = new int[n];
        for(int i=0; i<n; i++){
            h[i] = sc.nextInt();
        }
        //System.out.println(Arrays.toString(h));

        boolean inc;
        int start=0, end=1;
        int resultNum=-1;
        if(n==1){
            System.out.println(1);
            return;
        }
        else if(h[0]<h[1]){
            inc = true;
        }
        else inc = false;
        while(end<n){
            if(inc){
                if(h[end-1] < h[end]){
                    end++;
                    continue;
                }
                else{
                    if(end != n-1) inc = false;
                    else break;
                    end++;
                    continue;
                }
            }
            else{
                if(h[end-1] > h[end]){
                    end++;
                    continue;
                }
                else{
                    int tempNum = end - start;
                    if(tempNum > resultNum){
                        resultNum = tempNum;
                        start = end-1;
                        inc = true;
                        continue;
                    }
                    else{
                        start = end-1;
                        inc = true;
                        continue;
                    }
                }
            }
        }
        if(inc){
            int tempNum = end - start;
            if(tempNum > resultNum){
                resultNum = tempNum;
                start = end-1;
                inc = true;
            }
        }
        System.out.println(resultNum);
    }
}
