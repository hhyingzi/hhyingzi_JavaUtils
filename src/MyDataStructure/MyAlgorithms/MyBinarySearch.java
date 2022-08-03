package MyDataStructure.MyAlgorithms;

import MyDataStructure.Sort.MyBubbleSort;

import java.util.Scanner;

/**
 * 二分查找数组版：从一个有序数组中，找到目标元素所在的索引位置
 */
public class MyBinarySearch {
    public static int BinarySearch(int[] arr, int value){
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
        return -1;
    }

    //录入一串数字然后回车，观察排序结果
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String[] strs = sc.nextLine().split(" ");
        int[] arr = new int[strs.length];
        for(int i=0; i<strs.length; i++){
            arr[i] = Integer.parseInt(strs[i]);
        }
        MyBubbleSort.bubbleSortBest(arr);
        for(int i=0; i<arr.length; i++){
            System.out.println(arr[i]);
        }
    }
    public static int testSearch(int[] arr, int value){
        return MyBinarySearch.BinarySearch(arr, value);
    }
}
