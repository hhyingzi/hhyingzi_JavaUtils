package MyDataStructure.Sort;

import java.util.Arrays;
import java.util.Random;

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
        //test1，原数组是正确的
        int[] trueData1 = new int[]{1, 2, 3, 4, 5, 6, 7};
        int[] expected1 = new int[]{1, 2, 3, 4, 5, 6, 7};
        testTrueAndShow(trueData1, expected1);

        //test2，原数组是相同元素
        int[] trueData2 = new int[]{1, 1, 1, 1, 1};
        int[] expected2 = new int[]{1, 1, 1, 1, 1};
        testTrueAndShow(trueData2, expected2);

        //test3，逆序数组测试
        int[] trueData3 = new int[]{7, 6, 5, 4, 3, 2, 1};
        int[] expected3 = new int[]{1, 2, 3, 4, 5, 6, 7};
        testTrueAndShow(trueData3, expected3);

        //test random
        System.out.println("---Random test---");
        int groupNum = 10, arrLength = 10;  //测试10次随机序列，每个序列生成20个随机元素
        for(int i=0; i<groupNum; i++){
            int[] randomData = new int[arrLength];
            int[] expectedData = new int[arrLength];
            Random random = new Random();
            for(int j=0; j < arrLength; j++){
                randomData[j] = random.nextInt(20) - 10;
                expectedData[j] = randomData[j];
            }
            Arrays.sort(expectedData);
            testTrueAndShow(randomData, expectedData);
        }

        System.out.println("***** sort test True successed. *****");
    }

    //正确执行排序：输出待排序数据，调用排序方法，测试正确性，并输出排序结果
    void testTrueAndShow(int[] arr, int[] expected){
        System.out.print(Arrays.toString(arr)); //输出排序前的数组
        getSolution(arr);  //对数组进行排序
        System.out.println(" -> " + Arrays.toString(arr));  //输出排序后的结果
        assertArrayEquals(expected, arr);  //检验排序结果
    }

    //调用源代码的排序函数（默认直接对原数组进行排序）
    void getSolution(int[] arr) {
        MyMaxPriorityQueues.testSort(arr);
    }
}