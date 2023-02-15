package MyDataStructure.Sort;

import java.util.Scanner;


/**
 * 插入排序
 * 索引从左往右依次执行大循环，索引左边都是有序的，把索引所在的元素插入到左边有序序列中的合适位置。
 * 插入的方法是索引所在的元素依次与前一个元素交换，直到位于正确的位置上。
 * 该算法是稳定的，因为每一个元素都是一个一个位置挪动的，相同大小的元素不会交换次序。
 */
public class MyInsertionSort {
    public static void insertionSort(int[] arr){
        for(int i=1; i<arr.length; i++){
            for(int j=i; j > 0 && arr[j]<arr[j-1]; j--){  //这里把元素大小的判断放到for条件里，因为只要出现一次false，后面就不需要循环了
                int temp = arr[j];
                arr[j] = arr[j-1];
                arr[j-1] = temp;
            }
        }
    }

    //改进，原先是内循环每次判断后都进行元素交换，改进只后移有序序列，找到正确位置再放入新元素。
    public static void insertSortBest(int[] arr){
        for(int i=1; i<arr.length; i++){
            int newItem = arr[i];
            int j;
            for(j=i; j > 0 && newItem<arr[j-1]; j--){
                arr[j] = arr[j-1];
            }
            arr[j] = newItem;
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
        insertSortBest(arr);
    }
}
