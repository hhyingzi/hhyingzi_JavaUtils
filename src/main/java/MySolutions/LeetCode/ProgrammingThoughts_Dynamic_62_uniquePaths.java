package MySolutions.LeetCode;

/*
题目链接：https://leetcode.cn/problems/unique-paths/
文章讲解：https://programmercarl.com/0062.%E4%B8%8D%E5%90%8C%E8%B7%AF%E5%BE%84.html

一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
机器人**每次只能向下或者向右移动一步**。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
问总共有多少条不同的路径？

示例1：
输入：m = 3, n = 7
输出：28

示例2：
输入：m = 3, n = 2
输出：3
解释：
从左上角开始，总共有 3 条路径可以到达右下角。
1. 向右 -> 向下 -> 向下
2. 向下 -> 向下 -> 向右
3. 向下 -> 向右 -> 向下

示例3：
输入：m = 7, n = 3
输出：28

示例4：
输入：m = 3, n = 3
输出：6

提示：
1 <= m, n <= 100
题目数据保证答案小于等于 2 * 109
*/

public class ProgrammingThoughts_Dynamic_62_uniquePaths {
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];

        for(int i=0; i<m; i++) dp[i][0] = 1;
        for(int j=0; j<n; j++) dp[0][j] = 1;

        for(int i=1; i<m; i++){
            for(int j=1; j<n; j++){
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }

    public static void main(String[] args){
        ProgrammingThoughts_Dynamic_62_uniquePaths solution = new ProgrammingThoughts_Dynamic_62_uniquePaths();
        System.out.println(solution.uniquePaths(3, 7));  //28
        System.out.println(solution.uniquePaths(3, 2));  //3
        System.out.println(solution.uniquePaths(7, 3));  //28
        System.out.println(solution.uniquePaths(3, 3));  //6
    }
}
