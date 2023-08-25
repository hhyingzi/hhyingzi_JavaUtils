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
public class SuiXiangLu_Dynamic_2_70_ClimbStairs {
    class Solution {
        public int climbStairs(int n) {
            if(n==1) return 1;
            if(n==2) return 2;
            int prev_prev = 1, prev = 2, curr = 2+1;
            for(int i=3; i<=n; i++){
                curr = prev_prev + prev;  //当前值更新
                prev_prev = prev;  //上上个
                prev = curr; //上个
//                System.out.println("prev_prev=" + prev_prev + ", prev=" + prev + ", curr=" + curr);
            }
            return curr;
        }
    }
    public static void main(String[] args){
        for(int i=0; i<10; i++){
            int result = new SuiXiangLu_Dynamic_2_70_ClimbStairs().new Solution().climbStairs(i);
            System.out.println("N = " + i + "result = " + result);
        }
    }
}
