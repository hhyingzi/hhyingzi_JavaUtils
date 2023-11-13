package MySolutions.LeetCode;

public class ProgrammingThoughts_Dynamic_343_integerBreak {
    public int integerBreak(int n) {
        int[] dp = new int[n+1];
        dp[0] = 0; dp[1] = 0; dp[2] = 1;
        for(int i=3; i<=n; i++){
            for(int j=1; j<=i; j++){
                //j从小到大一直在试探怎样使 dp[i]最大。所以每次循环都要提取出历史最大的 dp[i]。
                System.out.println("i=" + i + ", j=" + j + ", dp[i]=" + dp[i] + ", dp[i-j]=" + dp[i-j] + ", j*(i-j)=" + j*(i-j) + ", j*dp[i-j]=" + j*dp[i-j]);
                dp[i] = Math.max(dp[i], Math.max(j*(i-j), j*dp[i-j]));   //直接拆分所得的 j*(i-j)，以及历史最大的 dp[i-j]与j的乘积
            }
            System.out.println();
        }
        return dp[n];
    }

    public static void main(String[] args){
        ProgrammingThoughts_Dynamic_343_integerBreak solution = new ProgrammingThoughts_Dynamic_343_integerBreak();
        int n = 10;
        System.out.println(n + ": " + solution.integerBreak(10));
    }
}
