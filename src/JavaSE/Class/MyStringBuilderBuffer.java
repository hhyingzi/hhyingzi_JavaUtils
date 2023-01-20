package JavaSE.Class;

/* StringBuiler 为什么线程不安全, StringBuffer是安全的
* 原因是他们内置的 char[] 数组是动态扩容的，依赖 int count 检测数组容量。
*       多线程时 count+=string.length() 不是原子操作，可能造成溢出数组
*       例如线程B在append时进行了扩容，但是
* 而 StringBuffer 对 append 方法整个加了 synchronized 关键字
* */
public class MyStringBuilderBuffer {
    public static void main(String[] args){
        StringBuilder stringBuilder = new StringBuilder();  //输出可能有溢出异常，就算无异常也不满10000
//        StringBuffer stringBuilder = new StringBuffer();  //输出10000，无异常
        for (int i = 0; i < 10; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++){
                        stringBuilder.append("a");
                    }
                }
            }).start();
        }
        try{
            Thread.sleep(100);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(stringBuilder.length());
    }
}
