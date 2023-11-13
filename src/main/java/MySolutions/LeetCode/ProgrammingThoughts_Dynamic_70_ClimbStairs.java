package MySolutions.LeetCode;

/*
* 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
* 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
* dp(1) = 1
* dp(2) = 2
* dp(3) = 3
* dp(4) = 5
* dp(5) = 8
* ...
*/
public class ProgrammingThoughts_Dynamic_70_ClimbStairs {
    //最佳方法，用两个变量代替前前一个和前一个
    public int climbStairs1(int n) {
        if(n==1) return 1;
        if(n==2) return 2;
        int prev_prev = 1, prev = 2, curr = -1;
        for(int i=3; i<=n; i++){
            curr = prev_prev + prev;  //当前值更新
            prev_prev = prev;  //上上个
            prev = curr; //上个
        }
        return curr;
    }

    //方法二：显式使用 dp 数组
    public int climbStairs2(int n) {
        if(n==1) return 1;
        if(n==2) return 2;

        int[] dp = new int[n + 1];
        dp[0] = -1;
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    public static void main(String[] args){
        ProgrammingThoughts_Dynamic_70_ClimbStairs solution = new ProgrammingThoughts_Dynamic_70_ClimbStairs();
        for(int i=1; i<10; i++){
            int result = solution.climbStairs1(i);
            System.out.println("N = " + i + ", result = " + result);
        }
    }
}
