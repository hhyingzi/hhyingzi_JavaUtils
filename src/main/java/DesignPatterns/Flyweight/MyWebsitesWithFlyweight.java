package DesignPatterns.Flyweight;

/**
 * flyweight：轻量级选手，享元
 * 大话设计模式 - 享元模式
 * 多家公司共享一套web项目，享元模式示例
 */
import java.util.Hashtable;
//用户
class User{
    private String name;  //用户名
    public User(String value){ this.name=value; }
    public String getName(){ return this.name; }
}
//抽象的享元（网站）类 WebSite
abstract class WebSite{
    public abstract void use(User user);  //使用该网站
}
//具体网站类 ConcreteWebSite，也是享元。
// 享元内部状态（所有使用这个实例的用户共享）为 String name.
// 享元外部状态（每次使用享元都要传入的参数）为 User user
class ConcreteWebSite extends WebSite {
    private String name = ""; //表示这个享元实例的名称，也是同一个享元实例共享的一个状态。
    public ConcreteWebSite(String name) { this.name = name; }
    public void use(User user) { System.out.println("网站分类：" + name+" 用户："+user.getName()); }  //使用网站，即打印网站名和拥有者
}
//网站工厂
class WebSiteFactory {
    private Hashtable<String,WebSite> flyweights = new Hashtable<String,WebSite>();  //持有一个享元Map数组，key为产品id，每次获取这个产品，如果已有则直接给，没有则创建
    //获得产品实例
    public WebSite getWebSiteCategory(String key) {  //根据产品id获取该产品（网站页面），如果没有创建过，则创建一个，然后放入享元数组。
        if (!flyweights.contains(key)) flyweights.put(key, new ConcreteWebSite(key));  //创建产品实例，放入享元数组
        return (WebSite)flyweights.get(key);
    }
    //获得网站分类总数
    public int getWebSiteCount() { return flyweights.size(); }
}
public class MyWebsitesWithFlyweight {
    public static void main(String[] args) {
        WebSiteFactory f = new WebSiteFactory();  //网站工厂

        WebSite fx = f.getWebSiteCategory("产品展示");  //获取一个产品（享元）实例【产品展示】，加入工厂的享元数组，这里生成了一个新的
        fx.use(new User("小菜"));  //小菜 拥有这个产品页面。
        WebSite fy = f.getWebSiteCategory("产品展示");  //获取一个产品实例【产品展示】，已经有了，不再创建新实例
        fy.use(new User("大鸟")); //大鸟拥有这个产品页面
        WebSite fz = f.getWebSiteCategory("产品展示");
        fz.use(new User("娇娇"));
        WebSite fl = f.getWebSiteCategory("博客");  //获取一个【博客】实例，因为享元数组里没有，所以生成一个新的博客实例
        fl.use(new User("老顽童"));
        WebSite fm = f.getWebSiteCategory("博客");
        fm.use(new User("桃谷六仙"));
        WebSite fn = f.getWebSiteCategory("博客");
        fn.use(new User("南海鳄神"));
        System.out.println("网站分类总数为:"+f.getWebSiteCount());
        System.out.println();

        // String titleA = new String("大话设计模式");
        // String titleB = new String("大话设计模式");

        // System.out.println(" titleA==titleB:          "+(titleA == titleB));        //比较内存引用地址
        // System.out.println(" titleA.equals(titleB):   "+(titleA.equals(titleB)));   //比较字符串的值

        // String titleC = "大话设计模式";
        // String titleD = "大话设计模式";

        // System.out.println(" titleC==titleD:          "+(titleC == titleD));        //比较内存引用地址
        // System.out.println(" titleC.equals(titleD):   "+(titleC.equals(titleD)));   //比较字符串的值
    }
}
