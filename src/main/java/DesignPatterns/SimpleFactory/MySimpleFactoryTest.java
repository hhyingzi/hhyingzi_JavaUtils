package DesignPatterns.SimpleFactory;

import java.util.Scanner;

/**
 * 简单工厂
 * 设计一个计算器类。
 */
/* 计算功能类 */
abstract class Operation {
    public double getResult(double numberA, double numberB){ return 0d; }
}
/* 计算功能实现：加减乘除 */
class Add extends Operation {
    public double getResult(double numberA, double numberB){ return numberA + numberB; }
}
class Sub extends Operation {
    public double getResult(double numberA, double numberB){ return numberA - numberB; }
}
class Mul extends Operation {
    public double getResult(double numberA, double numberB){ return numberA * numberB; }
}
class Div extends Operation {
    public double getResult(double numberA, double numberB){
        if (numberB == 0){
            System.out.println("除数不能为0");
            throw new ArithmeticException();
        }
        return numberA / numberB;
    }
}
/* 工厂类，根据使用时候的具体需要，生成对应的功能实现类。 */
class OperationFactory{
    public static Operation createOperate(String operate){
        Operation oper = null;
        switch (operate) {
            case "+":
                oper = new Add(); break;
            case "-":
                oper = new Sub(); break;
            case "*":
                oper = new Mul(); break;
            case "/":
                oper = new Div(); break;
        }
        return oper;
    }
}

public class MySimpleFactoryTest {
    public static void main(String[] args){
        try {
            Scanner sc = new Scanner(System.in);

            System.out.println("请输入数字A：");
            double numberA = Double.parseDouble(sc.nextLine());
            System.out.println("请选择运算符号(+、-、*、/)：");
            String strOperate = sc.nextLine();
            System.out.println("请输入数字B：");
            double numberB = Double.parseDouble(sc.nextLine());

            Operation oper = OperationFactory.createOperate(strOperate);
            double result = oper.getResult(numberA,numberB);
            System.out.println("结果是："+result);
        }
        catch(Exception e){
            System.out.println("您的输入有错："+e.toString());
        }
    }
}
