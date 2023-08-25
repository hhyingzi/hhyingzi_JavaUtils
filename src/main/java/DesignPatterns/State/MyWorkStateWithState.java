package DesignPatterns.State;

/**
 * 大话设计模式 - 状态模式
 * 工作状态示例
 */

//抽象状态类
abstract class StateA {
    public abstract void writeProgram(Work w); //根据当前时间，判断现在应该处于哪个状态，并展示该状态
}
//上午工作状态
class ForenoonState extends StateA {
    public void writeProgram (Work w) {
        if (w.getHour() < 12) System.out.println("当前时间："+ w.getHour() +"点 上午工作，精神百倍");
        else {
            w.setState(new NoonState());
            w.writeProgram();
        }
    }
}
//中午工作状态
class NoonState extends StateA {
    public void writeProgram (Work w) {
        if (w.getHour() < 13) System.out.println("当前时间："+ w.getHour() +"点 饿了，午饭；犯困，午休。");
        else {
            w.setState(new AfternoonState());
            w.writeProgram();
        }
    }
}
//下午工作状态
class AfternoonState extends StateA {
    public void writeProgram (Work w) {
        if (w.getHour() < 17) System.out.println("当前时间："+ w.getHour() +"点 下午状态还不错，继续努力");
        else {
            w.setState(new EveningState());
            w.writeProgram();
        }
    }
}
//晚间工作状态
class EveningState extends StateA {
    public void writeProgram(Work w) {
        if (w.getWorkFinished()) {
            w.setState(new RestState());
            w.writeProgram();
        }
        else {
            if (w.getHour() < 21) System.out.println("当前时间："+ w.getHour() +"点 加班哦，疲累之极");
            else {
                w.setState(new SleepingState());
                w.writeProgram();
            }
        }
    }
}
//睡眠状态
class SleepingState extends StateA {
    public void writeProgram(Work w) { System.out.println("当前时间："+ w.getHour() +"点 不行了，睡着了。");
    }
}
//下班休息状态
class RestState extends StateA {
    public void writeProgram(Work w) { System.out.println("当前时间："+ w.getHour() +"点 下班回家了");
    }
}
//工作类
class Work {
    private StateA current;  //人物当前的精神状态
    private int hour;  //当前的钟点
    private boolean workFinished = false;  //当前工作是否完成
    public Work(){ current = new ForenoonState(); }  //构造函数,将初始状态初始化为：上午工作状态 ForenoonState
    public void setState(StateA value) { this.current = value; }
    public void writeProgram() { this.current.writeProgram(this); } //写代码的状态
    public int getHour(){ return this.hour; }
    public void setHour(int value){ this.hour = value; }
    public boolean getWorkFinished(){ return this.workFinished; }
    public void setWorkFinished(boolean value){ this.workFinished = value; }
}
//主程序入口
public class MyWorkStateWithState {
    public static void main(String[] args){
        //紧急项目
        Work emergencyProjects = new Work();
        emergencyProjects.setHour(9); //设置当前时刻 9 点
        emergencyProjects.writeProgram(); //更改并输出这个时刻的状态
        emergencyProjects.setHour(10);  //设置当前时刻 10 点
        emergencyProjects.writeProgram(); //更改并输出这个时刻的状态
        emergencyProjects.setHour(12); //后面略...
        emergencyProjects.writeProgram();
        emergencyProjects.setHour(13);
        emergencyProjects.writeProgram();
        emergencyProjects.setHour(14);
        emergencyProjects.writeProgram();
        emergencyProjects.setHour(17);

//        emergencyProjects.setWorkFinished(false);
        emergencyProjects.setWorkFinished(true);

        emergencyProjects.writeProgram();
        emergencyProjects.setHour(19);
        emergencyProjects.writeProgram();
        emergencyProjects.setHour(22);
        emergencyProjects.writeProgram();
    }
}
