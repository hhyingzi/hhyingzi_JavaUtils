package JavaSE.Socket.BaseServerAndClient;

import java.net.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyServer {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(30000);
        while (true) {
            Socket s = ss.accept();
            PrintStream ps = new PrintStream(s.getOutputStream()); // 将Socket对应的输出流包装成PrintStream
            ps.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " 您好，您收到了服务器的新年祝福！");  //给连接过来的 Client 传一句话，然后关闭服务
            ps.close();
            s.close();
        }
    }
}