package DesignPatterns.Interpreter;

/**
 * 大话设计模式 - 解释器模式
 * 模板示例
 */
import java.util.ArrayList;
//抽象表达式类
abstract class AbstractExpression {
    public abstract void interpret(Context context);//解释操作
}
//终结符表达式
class TerminalExpression extends AbstractExpression {
    public void interpret(Context context) { System.out.println("终端解释器"); }
}
//非终结符表达式
class NonterminalExpression extends AbstractExpression {
    public void interpret(Context context) { System.out.println("非终端解释器"); }
}

class Context {
    private String input;
    public String getInput(){ return this.input; }
    public void setInput(String value){ this.input = value; }
    private String output;
    public String getOutput(){ return this.output; }
    public void setOutput(String value){ this.output = value; }
}
public class MyInterpreterTemplate {
    public static void main(String[] args) {
        Context context = new Context();
        ArrayList<AbstractExpression> list = new ArrayList<AbstractExpression>();
        list.add(new TerminalExpression());
        list.add(new NonterminalExpression());
        list.add(new TerminalExpression());
        list.add(new TerminalExpression());

        for (AbstractExpression exp : list) exp.interpret(context); }
}
