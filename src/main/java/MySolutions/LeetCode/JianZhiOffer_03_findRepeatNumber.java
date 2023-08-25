package MySolutions.LeetCode;

import java.util.HashSet;

/*
* 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。
* 数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。
* 请找出数组中任意一个重复的数字。
输入：[2, 3, 1, 0, 2, 5, 3]
输出：2 或 3

限制：2 <= n <= 100000
* */
public class JianZhiOffer_03_findRepeatNumber {
    class Solution {
        /* //直接排序，然后双指针遍历 时间O(nlogn)
        public int findRepeatNumber(int[] nums) {
            if(nums.length < 2) return 0;
            Arrays.sort(nums);
            for(int i=1; i<nums.length; i++){
                if(nums[i-1] == nums[i]) return nums[i];
            }
            return -1;
        }*/
        //时间 O(n)
        public int findRepeatNumber(int[] nums){
            HashSet<Integer> set = new HashSet();
            for(int item: nums){
                if(set.contains(item)) return item;
                set.add(item);
            }
            return -1;
        }
    }

    public static void main(String[] args){
        int[] arrs = new int[]{2, 3, 1, 0, 2, 5, 3};
        int result = new JianZhiOffer_03_findRepeatNumber().new Solution().findRepeatNumber(arrs);
        System.out.println(result);
    }
}
