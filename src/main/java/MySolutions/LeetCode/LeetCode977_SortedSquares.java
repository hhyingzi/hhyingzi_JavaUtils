package MySolutions.LeetCode;

//977.有序数组的平方
//题目链接：https://leetcode.cn/problems/squares-of-a-sorted-array/
//文章讲解：https://programmercarl.com/0977.有序数组的平方.html
public class LeetCode977_SortedSquares {
    class Solution {
        public int[] sortedSquares(int[] nums) {
            int[] results = new int[nums.length];
            if(nums == null || nums.length == 0) return results;

            //左指针从左往右，右指针从右往左，相遇时最小。
            int left=0, right=nums.length - 1;
            int index = nums.length - 1; //从大到小录入
            while(left <= right){
                int leftResult = nums[left]*nums[left];
                int rightResult = nums[right] * nums[right];
                if(leftResult <= rightResult){
                    results[index--] = rightResult;
                    right--;
                }
                else{
                    results[index--] = leftResult;
                    left++;
                }
            }
            return results;
        }
    }
}
