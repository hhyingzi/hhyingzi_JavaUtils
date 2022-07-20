package MyDataStructure.Sort;

import java.util.Scanner;

/**
 *  选择排序
 *  首先在整个数组中找到最小的元素，将它和数组第一个元素交换位置。
 *  然后，在剩下的元素中找到最小元素，将它和数组第二个元素交换位置。
 *  如此往复，直到整个数组已排序。
 */
public class MySelectionSort {
    public static void selectionSort(int[] arr){
        for(int i = 0; i < arr.length - 1; i++){
            int minIndex = i;
            //由于minIndex初始锚定于arr[i]，因此内循环索引 j 可以从 i+1 开始,与 arr[minIndex] 比较。
            for(int j = i + 1; j < arr.length; j++){
                if(arr[j] < arr[minIndex]){
                    minIndex = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
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
        selectionSort(arr);
    }
}
