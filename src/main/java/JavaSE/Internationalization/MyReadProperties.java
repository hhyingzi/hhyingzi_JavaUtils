package JavaSE.Internationalization;

import java.io.FileInputStream;
import java.util.Properties;

/* temp.txt 内容为：
welcome=hello
key1=value1
key2=value2
* */
public class MyReadProperties {
    private static final String dataFile = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\main\\java\\Datas\\temp.txt";
    static class ReadPropertiesWithProperties{
        public static void work(){
            Properties properties = new Properties();
            try(FileInputStream fis = new FileInputStream(dataFile)){
                properties.load(fis);
            }catch (Exception e){e.printStackTrace();}
            //单个查询
            System.out.println("Find value of 【welcome】:" + properties.getProperty("welcome"));
            //遍历
            for(String name: properties.stringPropertyNames()) System.out.println("Prop name: " + name + ", value: " + properties.getProperty(name));
        }
    }

    public static void main(String[] args){
        MyReadProperties.ReadPropertiesWithProperties.work();
    }
}
