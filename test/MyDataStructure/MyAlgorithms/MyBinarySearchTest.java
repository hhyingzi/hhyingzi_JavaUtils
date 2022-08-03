package MyDataStructure.MyAlgorithms;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 二分查找测试
 */
class MyBinarySearchTest extends MyBinarySearch {
    //对不正确数据的反应
    @org.junit.jupiter.api.Test
    void testSearchFalse() {
        System.out.println("##### Sort Test False. #####");
        //空数组查找应当返回-1
        int[] falseData1 = new int[]{};
        int falseValue1 = 999;
        testFalseAndShow(falseData1, falseValue1);

        //单元素数组查找失败案例
        int[] falseData2 = new int[]{0};
        int falseValue2 = 999;
        testFalseAndShow(falseData2, falseValue2);

        System.out.println("##### sort test False successed. #####");
    }

    //排序的正确性测试
    @org.junit.jupiter.api.Test
    void testSearchTrue() {
        System.out.println("***** Sort Test True. *****");
        //test1_1，查找首元素
        int[] trueData1_1 = new int[]{1, 2, 3, 4, 5, 6, 7};
        int valueTrue1_1 = 1;
        testTrueAndShow(trueData1_1, valueTrue1_1);

        //test1_2，查找末尾元素
        int[] trueData1_2 = new int[]{1, 2, 3, 4, 5, 6, 7};
        int valueTrue1_2 = 7;
        testTrueAndShow(trueData1_2, valueTrue1_2);

        //test1_3, 查找中间元素
        int[] trueData1_3 = new int[]{1, 2, 3, 4, 5, 6, 7};
        int valueTrue1_3 = 4;
        testTrueAndShow(trueData1_3, valueTrue1_3);

        //test2，原数组是相同元素
        int[] trueData2 = new int[]{1, 1, 1, 1, 1};
        int valueTrue2 = 1;
        testTrueAndShow(trueData2, valueTrue2);

        //test3，逆序数组测试
        int[] trueData3 = new int[]{7, 6, 5, 4, 3, 2, 1};
        int valueTrue3 = 5;
        testTrueAndShow(trueData3, valueTrue3);

        //test4，单元素数组正确性测试
        int[] trueData4 = new int[]{4};
        int valueTrue4 = 4;
        testTrueAndShow(trueData4, valueTrue4);

        //test5，两个元素数组正确性测试
        int[] trueData5_1 = new int[]{4, 5};
        int valueTrue5_1 = 4;
        testTrueAndShow(trueData5_1, valueTrue5_1);

        int[] trueData5_2 = new int[]{4, 5};
        int valueTrue5_2 = 5;
        testTrueAndShow(trueData5_2, valueTrue5_2);

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
            testTrueAndShow(randomData, randomData[random.nextInt(arrLength-1)]);
        }

        System.out.println("***** sort test True successed. *****");
    }

    //正确执行查找：输出数组数据，调用查找方法查找value，测试正确性
    void testTrueAndShow(int[] arr, int value){
        Arrays.sort(arr);
        System.out.print(Arrays.toString(arr)); //输出查找前的数组
        int result = getSolution(arr, value);  //对数组进行查找
        System.out.println("-->" + value + "  Index=" + result);  //输出查找后的结果
        assertEquals(value, arr[result]);  //检验查找结果
    }

    //错误执行查找：输出数组数据，调用查找方法查找一个不存在的元素value，测试是否应当返回 -1
    void testFalseAndShow(int[] arr, int value){
        Arrays.sort(arr);
        System.out.print(Arrays.toString(arr)); //输出查找前的数组
        int result = getSolution(arr, value); //查找后获得的结果，理应查不到返回-1
        System.out.println("-->" + value + "  Index=" + result);
        assertEquals(-1, result);
    }

    //调用源代码的主要函数
    int getSolution(int[] arr, int value) {
        return MyBinarySearch.testSearch(arr, value);
    }
}