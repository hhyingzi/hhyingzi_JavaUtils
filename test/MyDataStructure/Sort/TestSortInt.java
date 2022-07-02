package MyDataStructure.Sort;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class TestSortInt extends MyBubbleSort {
    //对不正确数据的反应
    @org.junit.jupiter.api.Test
    void testSortFalse() {
        System.out.println("##### Sort Test False. #####");
        //空数组排序应当返回空int数组（数组长度为0）
        int[] falseData1 = new int[]{};
        int[] expected1 = {};
        testTrueAndShow(falseData1, expected1);

        //单元素数组排序应当返回一个元素
        int[] falseData2 = new int[]{0};
        int[] expected2 = {0};
        testTrueAndShow(falseData2, expected2);

        System.out.println("##### sort test False successed. #####");
    }

    //排序的正确性测试
    @org.junit.jupiter.api.Test
    void testSortTrue() {
        System.out.println("***** Sort Test True. *****");
        //test1
        int[] trueData1 = new int[]{1, 3, 2, -4, 4, 6, 5};
        int[] expected1 = new int[]{-4, 1, 2, 3, 4, 5, 6};
        testTrueAndShow(trueData1, expected1);

        //test2
        int[] trueData2 = new int[]{9, 8, 7, 7, 4, 5, 6};
        int[] expected2 = new int[]{4, 5, 6, 7, 7, 8, 9};
        testTrueAndShow(trueData2, expected2);


        System.out.println("***** sort test True successed. *****");
    }

    //正确执行排序：输出待排序数据，调用排序方法，测试正确性，并输出排序结果
    void testTrueAndShow(int[] arr, int[] expected){
        System.out.print(Arrays.toString(arr)); //输出排序前的数组
        getSolution(arr);  //对数组进行排序
        assertArrayEquals(expected, arr);  //检验排序结果
        System.out.println(" -> " + Arrays.toString(arr));  //输出排序后的结果
    }

    //调用源代码的排序函数（默认直接对原数组进行排序）
    void getSolution(int[] arr) {
        MySelectionSort.testSort(arr);
    }
}