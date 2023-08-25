package DesignPatterns.AbstractFactory;
/**
 * 大话设计模式 - 抽象工厂
 * 读取不同的数据库，通过将字符串反射生成工厂类。
 */
//用户类 Entity
class UserA {
    private int _id; //用户ID
    private String _name; //用户姓名
    public int getId(){ return this._id; } public void setId(int value){ this._id=value; }
    public String getName(){ return this._name; } public void setName(String value){ this._name=value; }
}
//用户表接口
interface IUserA {
    public void insert(UserA userA); //新增一个用户
    public UserA getUser(int id); //获取一个用户信息
}
//Sqlserver 用户表
class SqlserverUserA implements IUserA {
    public void insert(UserA userA){ System.out.println("在SQL Server中给User表增加一条记录"); }
    public UserA getUser(int id){ System.out.println("在SQL Server中根据用户ID得到User表一条记录");return null; }
}
//Access 用户表
class AccessUserA implements IUserA {
    public void insert(UserA userA){ System.out.println("在Access中给User表增加一条记录"); }
    public UserA getUser(int id){ System.out.println("在Access中根据用户ID得到User表一条记录");return null; }
}
//部门类 Entity
class DepartmentA {
    private int _id; //部门ID
    private String _name; //部门名称
    public int getId(){ return this._id; } public void setId(int value){ this._id=value; }
    public String getName(){ return this._name; } public void setName(String value){ this._name=value; }
}
//部门表接口
interface IDepartmentA {
    public void insert(DepartmentA departmentA);
    public DepartmentA getDepartment(int id);
}
//Sqlserver 的部门表
class SqlserverDepartmentA implements IDepartmentA {
    public void insert(DepartmentA departmentA) { System.out.println("在SQL Server中给Department表增加一条记录"); } //新增一个部门
    public DepartmentA getDepartment(int id) { System.out.println("在SQL Server中根据部门ID得到Department表一条记录");return null; } //获取一个部门信息
}
//Access 的部门表
class AccessDepartmentA implements IDepartmentA {
    public void insert(DepartmentA departmentA) { System.out.println("在Access中给Department表增加一条记录"); }
    public DepartmentA getDepartment(int id) { System.out.println("在Access中根据部门ID得到Department表一条记录");return null; }
}
//工厂类，获取数据库操作各单元的实例
class DataAccessA {
    private static String assemblyName = "DesignPatterns.AbstractFactory.";
    private static String db ="Sqlserver";//数据库名称，可替换成Access
    //创建用户抽象对象工厂：根据传入字符串，可以生成 SqlServer, Access 的数据库 user 表操作器
    public static IUserA createUser() { return (IUserA)getInstance(assemblyName + db + "UserA"); }
    //创建部门对象工厂：根据传入字符串，可以生成 SqlServer, Access 的数据库 department 部门操作器
    public static IDepartmentA createDepartment(){ return (IDepartmentA)getInstance(assemblyName + db + "DepartmentA"); }
    //辅助方法，传入类名字符串，创建对应的类的实例并返回
    private static Object getInstance(String className){
        Object result = null;
        try{ result = Class.forName(className).getDeclaredConstructor().newInstance(); }
        catch (Exception e) { e.printStackTrace(); }
        return result;
    }
}
//主程序入口
public class MyMultiDatabaseAbstractFactory {
    public static void main(String[] args){
        UserA userA = new UserA();
        DepartmentA departmentA = new DepartmentA();

        //直接得到实际的数据库访问实例，而不存在任何依赖
        IUserA iu = DataAccessA.createUser();
        iu.insert(userA);    //新增一个用户
        iu.getUser(1);      //得到用户ID为1的用户信息

        //直接得到实际的数据库访问实例，而不存在任何依赖
        IDepartmentA idept = DataAccessA.createDepartment();
        idept.insert(departmentA);    //新增一个部门
        idept.getDepartment(2);      //得到部门ID为2的用户信息
    }
}
