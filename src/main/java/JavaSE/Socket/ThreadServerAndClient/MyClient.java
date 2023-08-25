package JavaSE.Socket.ThreadServerAndClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

class MyClientSocketThread implements Runnable {
    private Socket s;
    BufferedReader br = null;
    MyClientSocketThread(Socket s) throws IOException {
        this.s = s;
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));  //获取服务器传回的数据
    }
    public void run() {
        try {
            String content = null;
            while ((content = br.readLine()) != null) System.out.println(content);
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}

public class MyClient{
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("127.0.0.1" , 30000);
        new Thread(new MyClientSocketThread(socket)).start();
        PrintStream ps = new PrintStream(socket.getOutputStream());  //建立连接后，先向 server 中写入
        String line = null;
        // 不断读取键盘输入，读取后写入到 server
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while ((line = br.readLine()) != null) {
            ps.println(line);
        }
    }
}