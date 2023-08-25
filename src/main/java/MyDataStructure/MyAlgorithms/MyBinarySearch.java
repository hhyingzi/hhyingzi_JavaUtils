package MyDataStructure.MyAlgorithms;

import java.util.Arrays;
import java.util.Random;


/**
 * 二分查找数组版：从一个有序数组中，找到目标元素所在的索引位置
 * 查找成功返回 -1，也可以返回待插入的位置（按照注释行修改return值即可）
 * 代码随想录解析：https://programmercarl.com/0704.%E4%BA%8C%E5%88%86%E6%9F%A5%E6%89%BE.html#%E6%80%9D%E8%B7%AF
 * 力扣解析：https://leetcode.cn/problems/binary-search/
 */
public class MyBinarySearch {
    /*经典解法1：左闭右闭，迭代法，没找到就返回 -1。*/
    public static int BinarySearchIter(int[] arr, int value){
        int head=0, tail=arr.length-1;
        while(head <= tail){
            int mid = head + (tail - head)/2;  //防止溢出
            if(arr[mid] < value) head = mid+1;
            else if(arr[mid] > value) tail = mid-1;
            else return mid;
        }
        return -1;
    }
    /*经典解法二：左闭右开*/
    public static int BinarySearchIter2(int[] arr, int value){
        int head=0, tail=arr.length;  //右开注意下标
        while(head < tail){  //右开的while条件也是开的
            int mid = head + (tail - head)/2;
            if(value < arr[mid]) tail = mid; //右开的if
            else if(arr[mid] < value) head = mid + 1;
            else return mid;
        }
        return -1;
    }

    /*其他解法*/
    //寻找最左边出现的value索引，验证结果理想，且符合鲁棒性
    public static int myBinarySearchLeft(int[] arr, int value){
        if(arr.length == 0) return -1; //没有元素则直接返回
        int head=0; int tail=arr.length-1;
        while(head < tail){
            int mid = (head+tail)/2;
            if(arr[mid] < value) head = mid+1;
            else tail = mid;  //tail主动向左移动
        }
        if(arr[tail]!=value) return -1;
        return tail;
    }
    //寻找右边界，验证结果理想，且符合鲁棒性
    public static int myBinarySearchRight(int[] arr, int value){
        if(arr.length == 0) return -1; //没有元素则直接返回
        int head=0; int tail=arr.length-1;
        while(head < tail){
            int mid = (head+tail+1)/2; //不让向下取整了。两数不同时，偏向 tail 索引。
            if(arr[mid] <= value) head = mid;  //head主动向右移动，不用担心数组越界
            else tail = mid-1;
        }
        if(arr[head]!=value) return -1;
        return head;
    }

    //递归法，注意比迭代法多了起点与终点的参数。返回应该插入的位置。
    public static int BinarySearchRecur(int[] arr, int value, int left, int right){
        if(left > right){
            return -1;  //如果没找到，返回 -1
//            return left;  //如果没找到，就返回应该插入的位置（第一个大于 value 的元素的位置）
        }
        int mid = left + (right - left) / 2;
        if(arr[mid] < value) return BinarySearchRecur(arr, value, mid+1, right);
        else if(arr[mid] > value) return BinarySearchRecur(arr, value, left, mid-1);
        else return mid;
    }

    public static void main(String[] args){
        //随机生成待查找数组
        int length = 10;
        int[] arr = new int[length];
        for(int i=0; i<length; i++){
            arr[i] = new Random().nextInt(10);
        }
        Arrays.sort(arr);
        //随机生成查找值
        int searchValue = new Random().nextInt(10);
        //计算查找结果并输出
        System.out.println(Arrays.toString(arr) + " search: " + searchValue);
//        int resultIndex = MyBinarySearch.BinarySearchIter(arr, searchValue);
        int resultIndex = MyBinarySearch.myBinarySearchRight(arr, searchValue);
        System.out.println("result index is: arr[" + resultIndex + "]");
    }
    public static int testSearch(int[] arr, int value){
        return MyBinarySearch.BinarySearchIter(arr, value);
    }
}
