package DesignPatterns.AbstractFactory;

/**
 * 大话设计模式 - 抽象工厂
 * 读取不同的数据库，借助 properties 配置文件。
 */
import java.io.FileInputStream;
import java.util.Properties;

/* dataFile 文件内容如下：
db=Sqlserver
*/

//用户类 Entity
class User {
    private int _id; //用户ID
    private String _name; //用户姓名
    public int getId(){ return this._id; } public void setId(int value){ this._id=value; }
    public String getName(){ return this._name; } public void setName(String value){ this._name=value; }
}
//用户类接口
interface IUser {
    public void insert(User user); //新增一个用户
    public User getUser(int id); //获取一个用户信息
}
//Sqlserver 用户表
class SqlserverUser implements IUser {
    public void insert(User user){ System.out.println("在SQL Server中给User表增加一条记录"); }
    public User getUser(int id){ System.out.println("在SQL Server中根据用户ID得到User表一条记录"); return null; }
}
//Access 用户表
class AccessUser implements IUser {
    public void insert(User user){ System.out.println("在Access中给User表增加一条记录"); }
    public User getUser(int id){ System.out.println("在Access中根据用户ID得到User表一条记录"); return null; }
}
//部门类
class Department {
    private int _id; //部门ID
    private String _name; //部门名称
    public int getId(){ return this._id; } public void setId(int value){ this._id=value; }
    public String getName(){ return this._name; } public void setName(String value){ this._name=value; }
}
//部门类接口
interface IDepartment {
    public void insert(Department department); //新增一个部门
    public Department getDepartment(int id); //获取一个部门信息
}
//Sqlserver 的部门表
class SqlserverDepartment implements IDepartment {
    public void insert(Department department){ System.out.println("在SQL Server中给Department表增加一条记录"); }
    public Department getDepartment(int id){ System.out.println("在SQL Server中根据部门ID得到Department表一条记录"); return null; }
}
//Access 的部门表
class AccessDepartment implements IDepartment {
    public void insert(Department department){ System.out.println("在Access中给Department表增加一条记录"); }
    public Department getDepartment(int id){ System.out.println("在Access中根据部门ID得到Department表一条记录");return null; }
}
//工厂类，获取数据库操作各单元的实例
class DataAccess {
    /* dataFile 文件内容如下：
    db=Sqlserver
    */
    private static final String dataFile = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\main\\java\\Datas\\temp.txt";
    //private static String path=System.getProperty("user.dir")+"/code/chapter15/abstractfactory6/db.properties";  //原书中读取特定目录下 properties 文件

    private static String assemblyName = "DesignPatterns.AbstractFactory.";  //装配类名的前缀
    public static String getDb() {
        System.out.println("path:"+dataFile);
        Properties properties = new Properties();
        String result="";
        try(FileInputStream in = new FileInputStream(dataFile)){
            properties.load(in);
        } catch(Exception e){ e.printStackTrace(); }
        result = properties.getProperty("db");  //读取数据库名称
        return result;
    }
    //创建用户对象工厂
    public static IUser createUser() {
        String db=getDb();
        return (IUser)getInstance(assemblyName + db + "User");
    }
    //创建部门对象工厂
    public static IDepartment createDepartment(){
        String db=getDb();
        return (IDepartment)getInstance(assemblyName + db + "Department");
    }
    private static Object getInstance(String className){
        Object result = null;
        try{ result = Class.forName(className).getDeclaredConstructor().newInstance(); }
        catch (Exception e) { e.printStackTrace(); }
        return result;
    }
}
//主程序入口
public class MyMultiDatabasePropertiesAbstractFactory {
    public static void main(String[] args){
        User user = new User();
        Department department = new Department();

        //直接得到实际的数据库访问实例，而不存在任何依赖
        IUser iu = DataAccess.createUser();
        iu.insert(user);    //新增一个用户
        iu.getUser(1);      //得到用户ID为1的用户信息

        //直接得到实际的数据库访问实例，而不存在任何依赖
        IDepartment idept = DataAccess.createDepartment();
        idept.insert(department);    //新增一个部门
        idept.getDepartment(2);      //得到部门ID为2的用户信息
    }
}
