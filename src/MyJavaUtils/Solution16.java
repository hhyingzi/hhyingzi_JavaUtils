package MyJavaUtils;
//通过递归进行字符串反转
public class Solution16 {
    public static void main(String[] args){
        test("Hello, world!");
    }
    public static void test(String str){
        char[] charArray = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(int i=charArray.length-1;i>=0;i--){
            sb.append(charArray[i]);
        }
        System.out.println(sb.toString());
    }
}
