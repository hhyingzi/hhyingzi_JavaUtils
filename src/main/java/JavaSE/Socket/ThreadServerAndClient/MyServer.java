package JavaSE.Socket.ThreadServerAndClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class MyServerSocketThread implements Runnable {
    Socket socket = null;
    BufferedReader bufferedReader = null;
    public MyServerSocketThread(Socket socket) throws IOException {
        this.socket = socket;
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));  //服务器从 socket 通信中接收到的字符输入流 br
    }
    public void run() {
        try {
            String content = null;
            while ((content = readFromClient()) != null) {
                for (Socket s : MyServer.socketList) { //对每个socket都写入
                    PrintStream ps = new PrintStream(s.getOutputStream());  //服务器向 socket 通信，写入
                    ps.println(content);
                }
            }
        }
        catch (IOException e) { e.printStackTrace(); }
    }
    // 定义读取客户端数据的方法
    private String readFromClient() {
        try { return bufferedReader.readLine(); }
        // 如果捕捉到异常，表明该Socket对应的客户端已经关闭
        catch (IOException e) { MyServer.socketList.remove(socket); }
        return null;
    }
}

public class MyServer {
    //用 socketList 存储所有 socket，是为了建立强引用，防止 while 下一个循环时，之前的会话 socket 被系统回收。
    static List<Socket> socketList = Collections.synchronizedList(new ArrayList<>());
    public static void main(String[] args) throws Exception{
        ServerSocket ss = new ServerSocket(30000);
        while (true) {
            Socket socket = ss.accept();
            socketList.add(socket);
            PrintStream ps = new PrintStream(socket.getOutputStream()); ps.println("服务器已连接！"); //给客户端发出一条欢迎语句
            new Thread(new MyServerSocketThread(socket)).start();  //创建一个线程，为客户端服务
        }
    }
}