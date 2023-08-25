package MySolutions.LeetCode;

/*
* 一个长度为n-1的递增排序数组中的所有数字都是唯一的，并且每个数字都在范围0～n-1之内。在范围0～n-1内的n个数字中有且只有一个数字不在该数组中，请找出这个数字。
* 输入: [0,1,3]
* 输出: 2
* 限制：1 <= 数组长度 <= 10000
* */
public class JianZhiOffer_53_FindMissingNumber {
    class Solution {
    /* 时间复杂度 O(n)，并不完美
    public int missingNumber(int[] nums) {
        for(int i=0; i<nums.length; i++){
            if(nums[i] != i) return i;
        }
        return nums.length;
    }*/

        //时间复杂度 O(logN) 的左边界二分法，才是正解
        public int missingNumber(int[] nums){
            int head = 0; int tail = nums.length-1;
            while(head < tail){
                int mid = (head + tail) / 2;  //nums[mid] > mid 是正解，找其中的最小值
                //num[mid] 要么 等于 mid，要么大于 mid
                if(nums[mid] == mid) head = mid + 1;  //没找到
                else tail = mid;  //找到，则往左边推到头
            }
            //没找到，则返回 length
            if(nums[tail] == tail) return nums.length;
            return tail;
        }
    }
    public static void main(String[] args){
        int[] arr = new int[]{0,1,3};
        int result = new JianZhiOffer_53_FindMissingNumber().new Solution().missingNumber(arr);
        System.out.println(result);
    }
}
