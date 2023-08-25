package MySolutions.LeetCode;

/*
* 请定义一个函数实现字符串左旋转操作的功能。比如，输入字符串"abcdefg"和数字2，该函数将返回左旋转两位得到的结果"cdefgab"。
* */
public class JianZhiOffer_58_StringRotate {
    class Solution {
        public String reverseLeftWords(String s, int n) {
            return s.substring(n, s.length()) + s.substring(0, n);
        }

    /* 暴力解法：
    public String reverseLeftWords(String s, int n) {
        StringBuilder builder = new StringBuilder();
        for(int i=n; i<s.length(); i++){
            builder.append(s.charAt(i));
        }
        for(int i=0; i<n; i++){
            builder.append(s.charAt(i));
        }
        return builder.toString();
    }
    */
    /*
    * 聪明解法：整个字符串旋转，然后旋转前半部分，然后旋转后半部分。
    * */
    }
    public static void main(String[] args) {
        String str = "abcdefg";
        System.out.println(str);
        System.out.println(new JianZhiOffer_58_StringRotate().new Solution().reverseLeftWords(str, 2));
    }
}
