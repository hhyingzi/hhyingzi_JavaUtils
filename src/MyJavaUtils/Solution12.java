package MyJavaUtils;

public class Solution12 { //大小写转换
    public static void main(String[] args){
        test("weilcom to myhexin");
    }

    public static void test(String str){
        char[] charArray = str.toCharArray();
        for(int i=0; i<charArray.length; i++){
            if(charArray[i] >= 'a' && charArray[i] <= 'z'){
                charArray[i] = (char) (charArray[i] - 32);
            }
        }
        String result = String.valueOf(charArray);
        System.out.println(result);
    }
}
