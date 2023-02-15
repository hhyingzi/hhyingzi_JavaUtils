package MyUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyDateAndTime {
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static String myGetDateTime(){
        LocalDateTime localDateTime = LocalDateTime.now();
        String strDateTime = localDateTime.format(MyDateAndTime.dateTimeFormatter);
        System.out.println(strDateTime);
        return strDateTime;
    }
    public static void main(String[] args){
        MyDateAndTime.myGetDateTime();
    }
}
