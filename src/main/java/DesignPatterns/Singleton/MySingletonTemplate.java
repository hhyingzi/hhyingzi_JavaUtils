package DesignPatterns.Singleton;

/**
 * 大话设计模式 - 单例模式
 * 模板代码示例：饿汉模式（饿汉常用） + 懒汉模式
 */
//饿汉模式（静态初始化）
class HungerSingleton{
    private static HungerSingleton instance = new HungerSingleton();
    private HungerSingleton(){} //禁用构造函数
    public static HungerSingleton getInstance() {
        return instance;
    }
}
//懒汉模式（懒加载），双重锁定技术
class LazySingleton{
    private volatile static LazySingleton instance;
    private LazySingleton(){}   //禁用构造函数
    public static LazySingleton getInstance() {
        if (instance == null){
            synchronized(LazySingleton.class){
                if (instance == null){
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }
}
//主程序入口
public class MySingletonTemplate {
    public static void main(String[] args){
        //饿汉模式
        HungerSingleton hunger1 = HungerSingleton.getInstance();
        HungerSingleton hunger2 = HungerSingleton.getInstance();
        if (hunger1 == hunger2) System.out.println("两个 HungerSingleton 对象是相同的实例。");

        //懒汉模式
        LazySingleton lazy1 = LazySingleton.getInstance();
        LazySingleton lazy2 = LazySingleton.getInstance();
        if (lazy1 == lazy2) System.out.println("两个 LazySingleton 对象是相同的实例。");
    }
}
