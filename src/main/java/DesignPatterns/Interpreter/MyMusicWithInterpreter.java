package DesignPatterns.Interpreter;

/**
 * 大话设计模式 - 解释器模式
 * 音乐演奏示例
 */
//演奏内容
class PlayContext {
    private String playText;
    public String getPlayText(){ return this.playText; }
    public void setPlayText(String value){ this.playText = value; } //载入整个乐谱，以及别人把读取一个音符节后，乐谱中剩余的字符串传入进来。
}
//解释器抽象类
abstract class Expression {
    //解释器
    public void interpret(PlayContext context) {
        if (context.getPlayText().length() == 0) return;
        else {
            String playKey = context.getPlayText().substring(0, 1);  //重新读取乐器符号，进行具体解释功能的设置（main函数里只是确定要用哪个解释器）
            //System.out.println("playKey:"+playKey);
            context.setPlayText(context.getPlayText().substring(2));  //更新乐谱剩下字符串
            double playValue = Double.parseDouble(context.getPlayText().substring(0, context.getPlayText().indexOf(" ")));  //读取接下来的数字 <num> ，代表具体演奏值
            context.setPlayText(context.getPlayText().substring(context.getPlayText().indexOf(" ") + 1));  //裁剪字符串，用剩余的乐谱更新演奏内容
            this.excute(playKey, playValue);  //执行功能
        }
    }
    public abstract void excute(String key, double value); //执行功能，需要子类实现
}
//音符类，表示音高
class Note extends Expression {
    public void excute(String key, double value) {
        String note = "";
        switch (key) {
            case "C": note = "1";break;
            case "D": note = "2";break;
            case "E": note = "3";break;
            case "F": note = "4";break;
            case "G": note = "5";break;
            case "A": note = "6";break;
            case "B": note = "7";break;
        }
        System.out.print(note+" ");
    }
}
//音阶类
class Scale extends Expression {
    public void excute(String key, double value) {
        String scale = "";
        switch ((int)value) {
            case 1: scale = "低音";break;
            case 2: scale = "中音";break;
            case 3: scale = "高音";break;
        }
        System.out.print(scale+" ");
    }
}
//音速类
class Speed extends Expression {
    public void excute(String key, double value) {
        String speed;
        if (value < 500) speed = "快速";
        else if (value >= 1000) speed = "慢速";
        else speed = "中速";
        System.out.print(speed+" ");
    }
}
public class MyMusicWithInterpreter {
    public static void main(String[] args) {
        PlayContext context = new PlayContext(); //演奏内容对象
        //音乐-上海滩
        System.out.println("音乐-上海滩：");
        context.setPlayText("T 500 O 2 E 0.5 G 0.5 A 3 E 0.5 G 0.5 D 3 E 0.5 G 0.5 A 0.5 O 3 C 1 O 2 A 0.5 G 1 C 0.5 E 0.5 D 3 ");

        //用解释器解释每个符号：每个符号后面都要跟个数字
        Expression expression=null;
        while (context.getPlayText().length() > 0) {
            String str = context.getPlayText().substring(0, 1);  //字符串的第一个字符等价于 String.charAt(0)
            switch (str) {  //根据字符选择用那个解释器来解释接下来的数字
                //"O <num>" 代表音阶，012低中高音阶。
                case "O": expression = new Scale();break;
                //"CDEFGAB" 代表音高，P代表休止符（不响）
                case "C": case "D": case "E": case "F": case "G": case "A": case "B": case "P":
                    expression = new Note();
                    break;
                //"T <num>"代表演奏速度，数字单位是毫秒，每节拍是多少毫秒。
                case "T": expression = new Speed(); break;
            }
            expression.interpret(context);  //解释<num>数字
        }
    }
}
