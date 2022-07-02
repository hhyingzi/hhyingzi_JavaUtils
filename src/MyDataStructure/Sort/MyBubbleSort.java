package MyDataStructure.Sort;

import java.util.Scanner;

/*
* 冒泡排序
* 从头开始，通过两两交换的方式把最大元素移动到末尾。
* 然后再次从头开始，把最大元素移动到倒数第二，依此类推。
* 最终结果会从后到前排好序。
* - 最坏时间复杂度 O(n^2)
* - 是稳定排序，大小相同的元素不会被交换位置。
* */
public class MyBubbleSort {
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

    public static void bubbleSort(int[] arr){
        for(int i=0; i<arr.length-1; i++){  //i代表已经排序好的尾巴长度，越来越长
            for(int j=0; j<arr.length-1-i; j++){  //j代表待排序部分[0, arr.length-1-i)，因为与后一个元素比较，所以提前了一个位置
                if(arr[j] > arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }

    //优化版：增加 flag 判定，如果没有比较过则不比较
    public static void bubbleSortBest(int[] arr){
        if (arr == null || arr.length < 2) { return; }

        for(int i=0; i<arr.length-1; i++){
            boolean isSorted = true;
            for(int j=0; j<arr.length-1-i; j++){
                if(arr[j] > arr[j+1]){
                    isSorted = false;
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
            if(isSorted) break;
        }
    }

    //扩展版：判定最后一个比较的位置，如果提前结束说明后面那几个是有序的，直接加入进尾巴
    public static void bubbleSortExtend(int[] arr){
        if (arr == null || arr.length < 2) { return; }

        int last = arr.length - 1;  //每趟排序后，记录最后一次交换的位置 last，后面一定是已排序的
        int lastTemp = last;  //每次发生交换，就用一个临时变量 lastTemp 来更新索引。完成一趟排序后用该变量来更新 last
        for(int i=0; i<arr.length-1; i++){
            boolean isSorted = true;
            for(int j=0; j<last; j++){
                if(arr[j] > arr[j+1]){
                    isSorted = false;
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    lastTemp = j;
                }
            }
            last = lastTemp;
            if(isSorted) break;
        }
    }

    public static int[] getBubbleSort(int[] arr){
        bubbleSortBest(arr);
        return arr;
    }
}
