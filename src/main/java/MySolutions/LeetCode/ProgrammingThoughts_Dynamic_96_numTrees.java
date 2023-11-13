package MySolutions.LeetCode;

/*
题目链接：https://leetcode.cn/problems/unique-binary-search-trees/
文章讲解：https://programmercarl.com/0096.不同的二叉搜索树.html

给你一个整数 n ，求恰由 n 个节点组成且节点值从 1 到 n 互不相同的 二叉搜索树 有多少种？返回满足题意的二叉搜索树的种数。

输入：n = 3 输出：5
输入：n = 1 输出：1
 */
public class ProgrammingThoughts_Dynamic_96_numTrees {
    public int numTrees(int n) {
        int[] dp = new int[n+1];
        dp[0] = 1; dp[1] = 1;
        for(int i=2; i<=n; i++){  //计算出每个 i 的种类数量，dp[i] 有助于计算 dp[i+1]
            dp[i] = 0;
            for(int j=1; j<=i; j++){  //对 dp[i] ，树根 j 有 i 个选择，分别计算每种树根，有多少种不同形态
                dp[i] += dp[j-1] * dp[i-j];
            }
        }
        return dp[n];
    }

    public static void main(String[] args){
        ProgrammingThoughts_Dynamic_96_numTrees solution = new ProgrammingThoughts_Dynamic_96_numTrees();
        for(int i=1; i<5; i++){
            System.out.println("i=" + i + ", result=" + solution.numTrees(i));
        }
    }
}
