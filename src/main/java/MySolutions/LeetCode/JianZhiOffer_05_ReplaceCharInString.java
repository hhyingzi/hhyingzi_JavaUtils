package MySolutions.LeetCode;

/**
* 请实现一个函数，把字符串 s 中的每个空格替换成"%20"。
* 输入：s = "We are happy."
* 输出："We%20are%20happy."
*/
public class JianZhiOffer_05_ReplaceCharInString {
/*笔试刷题快速解法
    class Solution {
        public String replaceSpace(String s){ return s.replaceAll(" ", "%20"); }
    }
*/
    //正确解法
    class Solution {
        public String replaceSpace(String s) {
            StringBuffer buffer = new StringBuffer();
            for(int i=0; i<s.length(); i++){
                if(s.charAt(i) == ' '){
                    buffer.append("%20");
                }
                else buffer.append(s.charAt(i));
            }
            return buffer.toString();
        }
    }

    public static void main(String[] args){
        String inStr = "We are happy.";
        System.out.println(inStr);
        System.out.println(new JianZhiOffer_05_ReplaceCharInString().new Solution().replaceSpace(inStr));
    }
}
