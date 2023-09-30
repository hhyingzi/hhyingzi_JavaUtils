package MySolutions.LeetCode;

//leetcode704: https://leetcode.cn/problems/binary-search/
//代码随想录： https://programmercarl.com/0704.二分查找.html#思路
public class LeetCode704_BinarySearch {
    class Solution{
        public int search(int[] nums, int target) {
            if(nums.length==0) return -1;
            //左闭右闭
            int left=0, right=nums.length-1;
            while(left <= right){
                int mid = left + (right - left) / 2; //防止溢出
                if(target < nums[mid]) right = mid - 1;
                else if(nums[mid] < target) left = mid + 1;
                else return mid;
            }
            return -1;
        }
    }
}

