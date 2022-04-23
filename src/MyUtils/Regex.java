package MyUtils;
import java.util.regex.*;

public class Regex {
    public static void regexExample(){
        String str = "我有十六进制代码：0xF01C 和 0xA0B2，你能提取出来吗？";
        String patternString = "0x\\p{XDigit}{4}";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(str);

        //System.out.println(matcher.matches()); //调试时这一句屏蔽掉，否则下一局matcher.find()就会从结尾开始，什么都匹配不到。

        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}
