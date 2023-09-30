package MySolutions.LeetCode;

//209.长度最小的子数组
//题目链接：https://leetcode.cn/problems/minimum-size-subarray-sum/
//文章讲解：https://programmercarl.com/0209.长度最小的子数组.html
public class LeetCode209_MinSubArrayLength {
    class Solution {
        public int minSubArrayLen(int target, int[] nums) {
            int left = 0, windowSum = 0;
            int historyLenth = Integer.MAX_VALUE;
            for(int right=0; right < nums.length; right++){
                windowSum += nums[right];
                while(windowSum >= target){
                    if(right-left+1 < historyLenth) historyLenth = right - left + 1;
                    windowSum -= nums[left++];
                }
            }
            if(historyLenth == Integer.MAX_VALUE) return 0;
            return historyLenth;
        }
    }
}
