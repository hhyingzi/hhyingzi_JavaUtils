package MyDataStructure.Sort;

import java.util.Random;
import java.util.Scanner;


/**
 * 快速排序
 * 选定一个值作为锚点，比采用双指针挖坑法，当锚点坑位在左侧时，右边寻找到小于锚点的数填入锚点，腾出右边坑位，再从左边找元素填入，依此类推整个数组
 * 递归地分别对左右两个数组执行快速排序，最终实现整体有序
 */
public class MyQuickSort {
    public static void quickSort(int[] arr){
        sortBetterInsertion(arr, 0, arr.length-1);
    }

    //经典的快速排序：双边指针挖坑法
    public static void sort(int[] arr, int low, int high){
        if(low >= high) return;

        //以最左边元素值为锚点，临时记录锚点值，空出该锚点位置，进行左右切分
        int left = low, right = high;
        int pivot = arr[left];

        while(left < right){
            //从右边和左边找出应该放到锚点位的元素，
            while(left < right && arr[right] >= pivot){
                right--;
            }
            arr[left] = arr[right];
            while(left < right && arr[left] <= pivot){
                left++;
            }
            arr[right] = arr[left];
        }
        //将锚点值写入最后一个空缺位置，此时 left==right
        arr[left] = pivot;
        //递归执行快速排序
        sort(arr, low, left);
        sort(arr, left+1, high);
    }

    //优化基准值的快速排序：随机选取一个元素与最左侧锚点交换后，再调用原先的快速排序
    public static void sortBetterPivot(int[] arr, int low, int high){
        if(low >= high) return;
        int randomIndex;
        //方法一：直接取一个数组中间的元素
        randomIndex = (low + high)/2;
        //方法二：取一个数组中的随机元素
        randomIndex = new Random().nextInt(arr.length);

        //交换位置
        int temp = arr[low];
        arr[low] = arr[randomIndex];
        arr[randomIndex] = temp;
        sort(arr, 0, arr.length-1);
    }

    //数组元素数量小于15时（5到15均可），切换到插入排序
    public static void sortBetterInsertion(int[] arr, int low, int high){
        //判断是否切换插入排序
        if(high < low + 15) {
            MyInsertionSort.insertionSort(arr);
            return;
        }
        //原快速排序代码
        int left = low, right = high;
        int pivot = arr[left];
        while(left < right){
            //从右边和左边找出应该放到锚点位的元素，
            while(left < right && arr[right] >= pivot){
                right--;
            }
            arr[left] = arr[right];
            while(left < right && arr[left] <= pivot){
                left++;
            }
            arr[right] = arr[left];
        }
        arr[left] = pivot;
        sort(arr, low, left);
        sort(arr, left+1, high);
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
        quickSort(arr);
    }
}
