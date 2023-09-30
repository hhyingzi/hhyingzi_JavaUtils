package MySolutions.LeetCode;

//力扣：https://leetcode.cn/problems/remove-element/
//代码随想录：https://programmercarl.com/0027.移除元素.html
public class LeetCode27_RemoveElement {
    class Solution{
        public int removeElement(int[] nums, int val) {
            int left=0, right=0;
            int count=0;
            while(right < nums.length){
                if(nums[right] == val) right++;
                else{
                    nums[left++] = nums[right++];
                    count++;
                }
            }
            return count;
        }
    }
}
