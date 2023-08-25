package JavaSE.Socket.BaseServerAndClient;

import java.net.*;
import java.io.*;

public class MyClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1" , 30000);
        // 将Socket对应的输入流包装成BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line = br.readLine();  //接收一句服务器的数据，就关闭连接
        System.out.println("来自服务器的数据：" + line);
        br.close();
        socket.close();
    }
}
