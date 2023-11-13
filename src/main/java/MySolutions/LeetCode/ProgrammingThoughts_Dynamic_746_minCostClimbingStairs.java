package MySolutions.LeetCode;

//使用最小花费爬楼梯
/*
给你一个整数数组 cost ，其中 cost[i] 是从楼梯第 i 个台阶向上爬需要支付的费用。一旦你支付此费用，即可选择向上爬一个或者两个台阶。
你可以选择从下标为 0 或下标为 1 的台阶开始爬楼梯。
请你计算并返回达到楼梯顶部的最低花费。

示例1：
输入：cost = [10,15,20]
输出：15
解释：你将从下标为 1 的台阶开始。
- 支付 15 ，向上爬两个台阶，到达楼梯顶部。
总花费为 15 。

提示：
2 <= cost.length <= 1000
0 <= cost[i] <= 999
* */
public class ProgrammingThoughts_Dynamic_746_minCostClimbingStairs {
    public int minCostClimbingStairs(int[] cost) {
        int pre_pre = cost[0], pre = cost[1], curr = -1;
        for(int i=2; i<cost.length; i++){
            if(pre_pre <= pre)curr = pre_pre + cost[i];
            else curr = pre + cost[i];
            pre_pre = pre;
            pre = curr;
        }
        return Math.min(pre_pre, pre);
    }

    public static void main(String[] args){
        ProgrammingThoughts_Dynamic_746_minCostClimbingStairs solution = new ProgrammingThoughts_Dynamic_746_minCostClimbingStairs();
        int[] cost1 = {10,15,20};
        System.out.println(solution.minCostClimbingStairs(cost1));
    }
}
