package JavaSE.Socket.Proxy;

import java.io.*;
import java.net.*;
import java.util.*;

/* 本机开 V2Ray，能成功读取百度页面信息 */
public class ProxyTest {
    String dataFile = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\main\\java\\Datas\\temp.txt";
    final String PROXY_ADDR = "127.0.0.1";
    final int PROXY_PORT = 10808;
    String urlStr = "http://www.baidu.com";
    public void init() throws IOException{
        URL url = new URL(urlStr);
        // 创建一个代理服务器对象
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(PROXY_ADDR , PROXY_PORT));
        // 使用指定的代理服务器打开连接
        URLConnection conn = url.openConnection(proxy);
        // 设置超时时长。
        conn.setConnectTimeout(5000);
        try(
                // 通过代理服务器读取数据的Scanner
                Scanner scan = new Scanner(conn.getInputStream(), "utf-8");
                PrintStream ps = new PrintStream(dataFile))
        {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                // 在控制台输出网页资源内容
                System.out.println(line);
                // 将网页资源内容输出到指定输出流
                ps.println(line);
            }
        }
    }
    public static void main(String[] args) throws IOException , MalformedURLException {
        new ProxyTest().init();
    }
}

