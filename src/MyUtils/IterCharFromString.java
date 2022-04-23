package MyUtils;

public class IterCharFromString {
    public static void Solution(String str){
        for(int i=0; i<str.length(); i++){
            char c = str.charAt(i);
            System.out.println(c+",");
        }
    }
}
