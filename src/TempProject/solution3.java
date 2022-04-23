package TempProject;

public class solution3 {
    static void demo(int i){
        Integer num1 = new Integer(i);
        Integer num2 = new Integer(i);
        Integer num3 = i;
        Integer num4 = Integer.valueOf(i);
        Integer num5 = Integer.valueOf(i);

        System.out.print(num1==num2);
        System.out.print(",");
        System.out.print(num2==num3);
        System.out.print(",");
        System.out.print(num3==num4);
        System.out.print(",");
        System.out.print(num4==num5);
    }
    public static void main(String[] args){
        demo(49);
        demo(200);
    }
}
