package DesignPatterns.Bridge;

/**
 * 大话设计模式 - 桥接模式
 * 设计手机功能通讯录，游戏，让这些功能分别能在品牌 M 和 N 的手机上运行示例
 */
//手机功能（软件）抽象类 HandsetSoft
abstract class HandsetSoft{
    public abstract void run(); //运行
}
//功能实现类：手机游戏
class HandsetGame extends HandsetSoft{
    public void run(){ System.out.println("手机游戏"); }
}
//功能实现类：手机通讯录
class HandsetAddressList extends HandsetSoft{
    public void run(){ System.out.println("通讯录"); }
}
//功能实现类：手机音乐播放
class HandsetMusicPlay extends HandsetSoft{
    public void run(){ System.out.print("音乐播放"); }
}

//手机品牌抽象类 HandsetBrand
abstract class HandsetBrand{
    protected HandsetSoft soft;  //手机中装的软件抽象类
    public void setHandsetSoft(HandsetSoft soft){ this.soft=soft; } //设置具体手机软件
    public abstract void run(); //运行
}
//手机品牌M
class HandsetBrandM extends HandsetBrand{
    public void run(){
        System.out.print("品牌M");
        soft.run();
    }
}
//手机品牌N
class HandsetBrandN extends HandsetBrand{
    public void run(){
        System.out.print("品牌N");
        soft.run();
    }
}
//手机品牌S
class HandsetBrandS extends HandsetBrand{
    public void run(){
        System.out.print("品牌S");
        soft.run();
    }
}
public class MySoftwareDiffBrandsWithBridge {
    public static void main(String[] args) {
        HandsetBrand ab;
        ab = new HandsetBrandM();

        ab.setHandsetSoft(new HandsetGame());  //手机游戏
        ab.run();

        ab.setHandsetSoft(new HandsetAddressList());  //手机通讯录
        ab.run();

        HandsetBrand ab2;
        ab2 = new HandsetBrandN();

        ab2.setHandsetSoft(new HandsetGame());  //手机游戏
        ab2.run();

        ab2.setHandsetSoft(new HandsetAddressList());  //手机通讯录
        ab2.run();

        //向扩展开放，可以任意新增品牌，新增功能
        HandsetBrand ab3;
        ab3 = new HandsetBrandS();
        ab3.setHandsetSoft(new HandsetMusicPlay());  //音乐播放器
        ab3.run();
    }
}

