package MySolutions.InterviewProgramming.JavaSE;

import java.util.Arrays;
import java.util.Comparator;

/*
运动会需要发布比赛奖牌榜，显示顺序按照优先级为 金牌数 > 银牌数 > 铜牌数 > 国家名称 的规则进行排序。

输入：一个列表，包含所所有国家的金银铜奖牌数量。 国家名称[]，奖牌：[金, 银, 铜, 金, 银, 铜, ...]
输出：
国家 金 银 铜
国家 金 银 铜
...

输入示例：
"AAA", "BBB", "ZZZ", "DDD", "EEE"

[10, 20, 30]
[5, 99, 99]
[5, 99, 99]
[10, 20, 5]
[10, 5, 99]

输出示例：
[AAA, 10, 20, 30]
[DDD, 10, 20, 5]
[EEE, 10, 5, 99]
[BBB, 5, 99, 99]
[ZZZ, 5, 99, 99]

 */
public class LeQi_02_MedalOrder {
    public void medelOrder(String[] countries, int[][] medals){
        String[][] datas = new String[countries.length][];
        for(int i=0; i<datas.length; i++){
            datas[i] = new String[4];
            datas[i][0] = countries[i];
            datas[i][1] = String.valueOf(medals[i][0]);
            datas[i][2] = String.valueOf(medals[i][1]);
            datas[i][3] = String.valueOf(medals[i][2]);
        }
        System.out.println("输入成绩： ");
        for(int i=0; i<5; i++) System.out.println(Arrays.toString(datas[i]));

        Arrays.sort(datas, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                //比较金牌，银牌，铜牌顺序，降序
                if(o1[1] != o2[1]) return Integer.parseInt(o2[1]) - Integer.parseInt(o1[1]);
                if(o1[2] != o2[2]) return Integer.parseInt(o2[2]) - Integer.parseInt(o1[2]);
                if(o1[3] != o2[3]) return Integer.parseInt(o2[3]) - Integer.parseInt(o1[3]);
                //比较国家名称，升序
                else return o1[0].compareTo(o2[0]);
            }
        });

        System.out.println("输出成绩： ");
        for(int i=0; i<5; i++) System.out.println(Arrays.toString(datas[i]));
    }

    public static void main(String[] args){
        LeQi_02_MedalOrder solution = new LeQi_02_MedalOrder();

        String[] countries = new String[]{"AAA", "BBB", "ZZZ", "DDD", "EEE"};
        int[][] medals = new int[][]{{10, 20, 30}, {5, 99, 99}, {5, 99, 99}, {10, 20, 5}, {10, 5, 99}};

        solution.medelOrder(countries, medals);
    }
}
