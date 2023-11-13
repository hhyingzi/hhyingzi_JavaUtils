package MySolutions.InterviewProgramming.JavaSE;

import java.util.Arrays;
import java.util.Scanner;

/*
乐其电商笔试（缩略版） - 金银铜奖牌排序

题目描述
奥运会开始了,想请你为各个国家和地区做奖牌排序,按照金牌>银牌>铜牌的格式进行排序
------
输入
第一行 告诉你共有n个国家
接来下的n 行 每行三个数字 分别代表 金牌数量 银牌数量 铜牌数量 中间用空格隔开

输出
输出n行 每行三个数字 按照题意进行排序
------
样例输入
5
1 2 3
2 3 4
1 4 6
1 4 3
0 3 4

样例输出
2 3 4
1 4 6
1 4 3
1 2 3
0 3 4
*/
public class LeQi_01_MedalOrder {
    private static void Hand(int[][] arr){
        //使用lambda 表达式()->{}
        Arrays.sort(arr,(o1, o2)->{//Arrays.sort()排序时，使用的是排序数组里的元素，当前arr是一个二维数组，里面的元素是一维数组，所以o1,o2是一维数组
            for(int i =0;i<3;i++) {
                if(o1[i]==o2[i]) {
                    continue;//如果第一位相等，则进行第二位比较
                }else {
                    return o2[i]-o1[i];//倒序排序   o1[i]-o2[i]则为正序排序
                }
            }
            return 0;
        });
        //输出
        for(int[] arrs: arr) {
            for(int i=0;i<arrs.length;i++) {
                //对"空格"进行细化，注意使用的是print不进行换行
                if(i==0) {
                    System.out.print(arrs[i]);//如果是第一个数直接输出
                }else {
                    System.out.print(" "+arrs[i]);//否则，前面加空格
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //输入国家数量
        int n = scanner.nextInt();
        //构建二维数组
        int[][] arr = new int[n][3];
        for(int i=0;i<n;i++) {
            for(int j=0;j<3;j++) {
                arr[i][j]=scanner.nextInt();
            }
        }
        //调用排序方法
        Hand(arr);
    }
}
