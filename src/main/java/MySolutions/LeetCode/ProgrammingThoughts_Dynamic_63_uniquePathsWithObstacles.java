package MySolutions.LeetCode;

/*
题目链接：https://leetcode.cn/problems/unique-paths-ii/
文章讲解：https://programmercarl.com/0063.%E4%B8%8D%E5%90%8C%E8%B7%AF%E5%BE%84II.html

一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish”）。
现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？
网格中的障碍物和空位置分别用 1 和 0 来表示。
输入一个二维数组，以单元矩阵的形式表示每个障碍物的坐标。

示例1：
输入：obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]
输出：2

示例2：
输入：obstacleGrid = [[0,1],[0,0]]
输出：1

提示：
m == obstacleGrid.length
n == obstacleGrid[i].length
1 <= m, n <= 100
obstacleGrid[i][j] 为 0 或 1
 */
public class ProgrammingThoughts_Dynamic_63_uniquePathsWithObstacles {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int rows = obstacleGrid.length;
        int cols = obstacleGrid[0].length;
        int[][] dp = new int[rows][cols];

        boolean flag = false; //检测到障碍物，ture为是，后面初始化全为0。
        for(int i=0; i<rows; i++){
            if(obstacleGrid[i][0] == 1) flag = true;
            if(!flag)dp[i][0] = 1;
            else dp[i][0] = 0;
        }
        flag = false;
        for(int j=0; j<cols; j++){
            if(obstacleGrid[0][j] == 1) flag = true;
            if(!flag)dp[0][j] = 1;
            else dp[0][j] = 0;
        }

        for(int i=1; i<rows; i++){
            for(int j=1; j<cols; j++){
                if(obstacleGrid[i][j] == 1) dp[i][j] = 0;
                else{
                    dp[i][j] = dp[i-1][j] + dp[i][j-1];
                }
            }
        }
        return dp[rows-1][cols-1];
    }

    public static void main(String[] args){
        ProgrammingThoughts_Dynamic_63_uniquePathsWithObstacles solution = new ProgrammingThoughts_Dynamic_63_uniquePathsWithObstacles();
        int[][] obstacleGrid1 = {{0,0,0}, {0,1,0}, {0,0,0}};
        System.out.println(solution.uniquePathsWithObstacles(obstacleGrid1));

        int[][] obstacleGrid2 = {{0,1}, {0,0}};
        System.out.println(solution.uniquePathsWithObstacles(obstacleGrid2));

    }
}
