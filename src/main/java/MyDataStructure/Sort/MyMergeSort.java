package MyDataStructure.Sort;


/**
 * 归并排序
 *
 */

import java.util.Scanner;
public class MyMergeSort {
    //自顶向下归并，包括 mergeSort, sort, merge 三个函数。
    public static void mergeSort(int[] arr){
        int[] tempArr = new int[arr.length];
        sort(arr, 0, arr.length-1, tempArr);  //因为右边是闭区间，所以初始右边界-1以防越界
    }

    public static void sort(int[] arr, int low, int high, int[] tempArr){
        if(high <= low) return;

        int mid = (low + high)/2;
        sort(arr, low, mid, tempArr);
        sort(arr, mid+1, high, tempArr);
        merge(arr, low, mid, high, tempArr);
    }

    public static void merge(int[] arr, int low, int mid, int high, int[] tempArr){
        //复制到临时数组 tempArr 中
        for(int i = low; i <= high; i++){
            tempArr[i] = arr[i];
        }
        int left = low, right = mid + 1, k = left;

        //执行归并
        while(left <= mid && right <= high){
            if(tempArr[left] <= tempArr[right]){
                arr[k++] = tempArr[left++];
            }
            else{
                arr[k++] = tempArr[right++];
            }
        }

        //另一个数组剩余的部分
        if(left <= mid){  //剩下左边
            while(left <= mid){
                arr[k++] = tempArr[left++];
            }
        }
        else{  //剩下右边
            while(right <= high){
                arr[k++] = tempArr[right++];
            }
        }
    }

    //自底向上归并（采用循环，非递归）包括 mergeSortExtend 和 merge 两个函数
    public static void mergeSortExtend(int[] arr){
        int[] tempArr = new int[arr.length];
        //大循环，从子数组大小为1开始，然后子数组大小为2，4，8，...，直到接近 arr.length，最后一轮一般是左边数组长于右边数组
        for(int subSize = 1; subSize < arr.length; subSize = subSize*2){
            //合并两个有序子数组 [low, low + subSize), [low + subSize, low + subSize * 2)
            for(int low=0; arr.length-low > subSize; low = low + subSize*2) { // 【arr.length-low > subSize】是为了确保第二个待排序数组至少有1个元素
                int high = low+subSize * 2 - 1;
                if(high > arr.length-1) high = arr.length - 1;//如果右边提前到头了，则调整终点
                merge(arr, low, low+subSize-1, high, tempArr);
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
        mergeSort(arr);
    }
}
