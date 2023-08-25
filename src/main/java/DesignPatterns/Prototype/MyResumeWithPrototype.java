package DesignPatterns.Prototype;

/**
 * 大话设计模式 - 原型模式
 * 简历的深拷贝
 */
//简历类
class Resume implements Cloneable {
    private String name;
    private String sex;
    private String age;
    private WorkExperience work;
    public Resume(String name){
        this.name = name;
        this.work = new WorkExperience();
    }
    //设置个人信息
    public void setPersonalInfo(String sex,String age){
        this.sex=sex;
        this.age=age;
    }
    //设置工作经历
    public void setWorkExperience(String timeArea,String company){
        this.work.setTimeArea(timeArea);//给工作经历实例的时间范围赋值
        this.work.setCompany(company);	//给工作经历实例的公司赋值
    }
    //展示简历
    public void display(){
        System.out.println(this.name +" "+this.sex +" "+this.age);
        System.out.println("工作经历 "+this.work.getTimeArea() +" "+this.work.getCompany());
    }
    //深拷贝实现
    @Override
    public Resume clone(){
        Resume object = null;
        try {
            object = (Resume)super.clone();  //先浅拷贝一个对象
            object.work = this.work.clone();  //再对第一层中的对象进行浅拷贝，达到深拷贝的目的
        } catch(CloneNotSupportedException exception){ System.err.println("Clone异常。"); }
        return object;
    }
}

//简历中的引用类： 工作经历类
class WorkExperience implements Cloneable {
    private String timeArea;  //工作时间范围
    private String company;  //所在公司
    public String getTimeArea(){ return this.timeArea; }
    public void setTimeArea(String value){ this.timeArea=value; }
    public String getCompany(){ return this.company; }
    public void setCompany(String value){ this.company=value; }
    @Override
    public WorkExperience clone(){
        WorkExperience object = null;
        try { object = (WorkExperience)super.clone(); }
        catch(CloneNotSupportedException exception){ System.err.println("Clone异常。"); }
        return object;
    }
}
public class MyResumeWithPrototype {
    public static void main(String[] args){
        Resume resume1 = new Resume("大鸟");
        resume1.setPersonalInfo("男","29");
        resume1.setWorkExperience("1998-2000","XX公司");

        Resume resume2 = resume1.clone();
        resume2.setWorkExperience("2000-2003","YY集团");

        Resume resume3 = resume1.clone();
        resume3.setPersonalInfo("男","24");
        resume3.setWorkExperience("2003-2006","ZZ公司");

        resume1.display();
        resume2.display();
        resume3.display();
    }
}