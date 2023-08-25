package DesignPatterns.TemplateMethod;

/**
 * 大话设计模式 - 模板方法
 * 考试卷模板
 */
//考题试卷抽象类
abstract class TestPaper {
    //试题1
    public void testQuestion1() {
        System.out.println(" 杨过得到，后来给了郭靖，炼成倚天剑、屠龙刀的玄铁可能是[ ]  a.球磨铸铁 b.马口铁 c.高速合金钢 d.碳素纤维 ");
        System.out.println("答案："+this.answer1());
    }
    protected abstract String answer1();  //作答1
    //试题2
    public void testQuestion2() {
        System.out.println(" 杨过、程英、陆无双铲除了情花，造成[ ] a.使这种植物不再害人 b.使一种珍稀物种灭绝 c.破坏了那个生物圈的生态平衡 d.造成该地区沙漠化  ");
        System.out.println("答案："+this.answer2());
    }
    protected abstract String answer2();  //作答2
    //试题3
    public void testQuestion3() {
        System.out.println(" 蓝凤凰致使华山师徒、桃谷六仙呕吐不止,如果你是大夫,会给他们开什么药[ ] a.阿司匹林 b.牛黄解毒片 c.氟哌酸 d.让他们喝大量的生牛奶 e.以上全不对   ");
        System.out.println("答案："+this.answer3());
    }
    protected abstract String answer3();  //作答3
}
//学生甲答的试卷
class TestPaperA extends TestPaper {
    protected String answer1() { return "b"; }  //试题1
    protected String answer2() { return "a"; }  //试题2
    protected String answer3() { return "c"; }  //试题3
}
//学生乙答的试卷
class TestPaperB extends TestPaper {
    protected String answer1() { return "d"; }  //试题1
    protected String answer2() { return "b"; }  //试题2
    protected String answer3() { return "a"; }  //试题3
}
//主程序入口
public class MyTestPaperWithTemplateMethod {
    public static void main(String[] args){
        System.out.println("学生甲的试卷：");
        TestPaper studentA = new TestPaperA();
        studentA.testQuestion1();
        studentA.testQuestion2();
        studentA.testQuestion3();

        System.out.println("学生乙的试卷：");
        TestPaper studentB = new TestPaperB();
        studentB.testQuestion1();
        studentB.testQuestion2();
        studentB.testQuestion3();
    }
}

