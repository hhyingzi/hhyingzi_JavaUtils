package MyDataStructure.Sort;

import java.util.Scanner;


/**
 * 希尔排序
 * 相当于改进后的插入排序，在大循环中遇到的每个元素进行跳步插入。
 */
public class MyShellSort {
    public static void shellSort(int[] arr){
        //间隔h个元素，间隔数每次减半，超过两个元素时必然有1，最后h=0结束
        for(int gap = arr.length / 2; gap > 0; gap = gap / 2){
            //执行插入排序
            for(int i = gap; i < arr.length; i++){
                int newItem = arr[i];
                int j;
                for(j=i; j >= gap && newItem < arr[j - gap]; j = j - gap){
                    arr[j] = arr[j-gap];
                }
                arr[j] = newItem;
            }
        }
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

    public static void testSort(int[] arr){
        shellSort(arr);
    }
}
