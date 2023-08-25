package MySolutions.LeetCode;

/*
* 统计一个数字在排序数组中出现的次数。
* 输入: nums = [5,7,7,8,8,10], target = 8
* 输出: 2
* */
public class JianZhiOffer_53_Search {
    class Solution {
        public int search(int[] nums, int target) {
            if(nums.length == 0) return 0;
            //int index = Arrays.binarySearch(nums, target); //可以用库函数，这里我自己写了一个二分查找
            int index = myBinarySearch(nums, target);
            if(index < 0) return 0;
            int head = index, tail = index;
            for(int i=index; i>=0; i--){
                if(nums[i] == nums[index]) head = i;
            }
            for(int i=index; i<nums.length; i++){
                if(nums[i] == nums[index]) tail = i;
            }
            return tail-head+1;
        }
        private int myBinarySearch(int[] nums, int target){
            int head=0; int tail=nums.length-1;
            while(head <= tail){
                int mid = (head+tail)/2;
                if(nums[mid] < target) head = mid+1;
                else if(nums[mid] > target) tail = mid-1;
                else return mid;
                //System.out.println("midIndex:" + mid + ", value=" + nums[mid]);
            }
            return -1;
        }
    }
    public static void main(String[] args){
        int[] arr = new int[]{5,7,7,8,8,10};
        int result = new JianZhiOffer_53_Search().new Solution().search(arr, 8);
        System.out.println(result);
    }
}
