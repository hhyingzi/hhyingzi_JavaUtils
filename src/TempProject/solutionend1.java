package TempProject;

public class solutionend1 {

    /*
     * 输入整型数组，2 3 4 1
     * 求它的最长递增子序列长度，如2， 3，4
     * 20分钟
     * */

    public static void main(String[] args) {


        int[] arr = {2, 3, 4, 1, 2, 3, 4, 5, 6, 2, 4, 7};

    }

    /*
     * head, tail
     * 如果 tail 未到末尾，检查tail是否符合递增
     * 否则 记录当前长度，将 tail 重置到当前位置
     * */
//    public void mysolution(int[] arr) {
//        int head = 1, tail = 1;
//        List<Integer> result = new ArrayList<>();
//        List<Integer> temp = new ArrayList<>();
//
//
//        while (head < arr.length && tail < arr.length) {
//            if (arr[tail - 1] < arr[tail]) { //如果符合递增
//                result.add(arr[tail - 1]);
//                tail++;
//            }
//
//            //不符合递增
//            else {
//                result.add(arr[tail - 1]);
//                if (temp.size() >= result.size()) { //未创造历史记录
//                    result.clear();
//                } else { //创造了历史记录
//                    temp.clear();
//                    for (Integer item : result) {
//                        temp.add(item);
//                    }
//                    result.clear();
//                    head = tail;
//                    tail++;
//                }
//            }
//        }
//
//        result.add(arr[tail - 1]);
//        if (temp.size() >= result.size()) { //未创造历史记录
//            result.clear();
//        } else {
//            temp.clear();
//            for (Integer item : result) {
//                temp.add(item);
//            }
//            result.clear();
//        }
//
//        for (Integer item : temp) {
//            System.out.print(item);
//        }
//        System.out.println();
//    }

}
