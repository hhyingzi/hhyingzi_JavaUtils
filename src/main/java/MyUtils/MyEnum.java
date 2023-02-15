package MyUtils;

import java.util.Arrays;

/**
 * 枚举类学习与使用示例
 */
public class MyEnum {
    public static void main(String[] args){
        //输入或创建一个枚举类型的方法
        //方法一：直接创建这个类
        Weekdays weekday1 = Weekdays.MONDAY;
        //方法二：通过字符串创建枚举类型
        String str2 = "MONDAY";
        Weekdays weekday2 = Enum.valueOf(Weekdays.class, str2);

        //输出枚举类值的方法
        //方法一：直接输出这个类（会调用自带的 toString() 方法进行转换）
        System.out.println(weekday1 + " -- " + weekday1.toString() +
                " -- " + weekday1.dayName + " -- " + weekday1.dayNumber);
        System.out.println(Weekdays.TUESDAY + " -- " + Weekdays.TUESDAY.dayName);
        System.out.println("FRIDAY 的枚举位置是：" + Weekdays.FRIDAY.ordinal());  //输出枚举类所在的位置
        System.out.println("星期五比星期三多几天？" + Weekdays.FRIDAY.compareTo(Weekdays.WEDNESDAY));  //比较枚举类位置顺序

        //方法二：直接获取所有枚举值数组
        Weekdays[] values = Weekdays.values();
        System.out.println(Arrays.toString(values));
    }
}

//简单枚举类
enum Color{
    RED, YELLOW, BLUE;
}

//带成员变量的枚举类
enum Weekdays{
    MONDAY("星期一", 1), TUESDAY("星期二", 2),
    WEDNESDAY("星期三", 3), THURSDAY("星期四", 4),
    FRIDAY("星期五", 5), SATURDAY("星期六", 6),
    SUNDAY("星期日", 7);

    public String dayName;
    public int dayNumber;
    private Weekdays(String dayName, int dayNumber){ this.dayName = dayName; this.dayNumber = dayNumber; };
}