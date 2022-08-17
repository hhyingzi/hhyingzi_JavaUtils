package MyDataStructure.MyAlgorithms;

import java.util.Arrays;
import java.util.Random;


/**
 * 二分查找数组版：从一个有序数组中，找到目标元素所在的索引位置
 * 查找成功返回 -1，也可以返回待插入的位置（按照注释行修改return值即可）
 */
public class MyBinarySearch {
    //迭代法，没找到就返回 -1。
    public static int BinarySearchIter(int[] arr, int value){
        int left=0, right = arr.length-1;
        while(left <= right){
            int keyIndex = left + (right - left) / 2;
            if(arr[keyIndex] == value) return keyIndex;
            else if(arr[keyIndex] > value){
                right = keyIndex - 1;
            }
            else{
                left = keyIndex + 1;
            }
        }
        return -1;  //没找到，返回 -1
//        return left;  //没找到，返回应该插入的位置
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
        int resultIndex = MyBinarySearch.BinarySearchIter(arr, searchValue);
        System.out.println("result index is: arr[" + resultIndex + "]");
    }
    public static int testSearch(int[] arr, int value){
        return MyBinarySearch.BinarySearchIter(arr, value);
    }
}
