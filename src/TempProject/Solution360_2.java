package TempProject;

/*
* Yeah数列
时间限制： 3000MS
内存限制： 1048576KB
题目描述：
若数列a1…an，对于任意1<i<n的位置满足：a[i+1]>a[i]或a[i-1]>a[i] ，那么称这个数列为yeah数列。（通俗的理解，yeah数列除两端外，对于任意一个位置，左右两边至少有一个比他大）。

比如4 1 8 或 1 2 3或 8 6 4 2就是yeah数列，而1 1 1和0 1 0就不是yeah数列

现在有一个数列b1…bn，每次操作你可以选择一个i，使得bi减1(操作过程中允许bi减为负数)，问最少经过多少次操作，可以使得bi变为一个yeah数列



输入描述
输入第一行包含一个正整数n（3<=n<=100000），表示数列b的长度

输入第二行包含n个空格隔开的整数，第i个整数表示bi(0<=bi<=1000000000)

输出描述
输出一行一个整数，表示最小操作次数。


样例输入
3
4 1 8
样例输出
0

提示
输入样例2

8

6 6 6 6 2 3 3 3

输出样例2

9

样例1中无需操作即可满足条件；样例2中可以调整为6 5 4 3 2 1 2 3需要9次操作
*/
public class Solution360_2 {
    public static void main(){
        /*
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for(int i=0; i<n; i++){
            a[i] = sc.nextInt();
        }

        int diff; //x1 > x2 -1, 0, 1
        int start=0, end=1;
        int resultNum=0;
        if(n==1){
            System.out.println(1);
            int pass;
            return;
        }
        else diff = a[1] - a[0];

        while(end<n){
            if(inc){
                if(a[end-1] < a[end]){
                    int pass;
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
                if(a[end-1] > a[end]){
                    end++;
                    continue;
                }
                else{
                    int tempNum = end - start;
                    if(tempNum > resultNum){
                        int pass;
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
*/
    }
}
