package MySolutions.LeetCode;

import java.util.Arrays;

//59.螺旋矩阵II
//题目链接：https://leetcode.cn/problems/spiral-matrix-ii/
//文章讲解：https://programmercarl.com/0059.螺旋矩阵II.html
public class LeetCode59_GenerateMatrix {
    public static void main(String[] args){
        Solution solution = new LeetCode59_GenerateMatrix().new Solution();
        int n = 3;
        int[][] result = solution.generateMatrix(n);
//        System.out.println(Arrays.deepToString(result));  //题目要求的遍历
        for(int i=0; i<n; i++) System.out.println(Arrays.toString(result[i]));
    }

    class Solution{
        public int[][] generateMatrix(int n){
            int[][] result = new int[n][n];
            int currNum = 1; //当前填充数字
            int loop = 0; //每次走四周为一个 loop，每个 loop 重新确定小圈的原点。
            int i=0,j=0;  //横坐标i，纵坐标j

            //按圈数进行大循环，按归纳法为 loopMax < n/2，最后实验 n%2==1 则输出中心点。
            //归纳法：n为1，2，3，4时，（1：0圈+1），（2：1圈），（3：1圈+1），（4：2圈）。
            while(loop < n/2){
                //从左到右，i 固定，变动 j
                for(i = loop , j = loop; j < n - 1 - loop; j++){
                    result[i][j] = currNum;
                    currNum++;
                }
                //从上到下，j 固定，动 i
                for(; i < n - 1 - loop; i++){
                    result[i][j] = currNum;
                    currNum++;
                }
                //从右到左，i 固定，变动 j
                for(; j > loop; j--){
                    result[i][j] = currNum;
                    currNum++;
                }
                //从下到上，j 固定，变动i
                for(; i > loop; i--){
                    result[i][j] = currNum;
                    currNum++;
                }
                loop++;
            }
            if(n%2 == 1) result[loop][loop] = currNum;
            return result;
        }
    }
}
