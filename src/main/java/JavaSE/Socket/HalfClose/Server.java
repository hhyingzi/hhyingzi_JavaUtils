package JavaSE.Socket.HalfClose;

import java.io.*;
import java.net.*;
import java.util.*;
public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(30000);
        Socket socket = ss.accept();
        PrintStream ps = new PrintStream(socket.getOutputStream());
        Scanner scan = new Scanner(socket.getInputStream());

        //服务端连接成功后先给客户端发两条消息，然后关闭输出流
        ps.println("服务器的第一行数据");
        ps.println("服务器的第二行数据");
        // 关闭socket的输出流，表明服务器的输出数据已经结束
        socket.shutdownOutput();
        // 下面语句将输出false，表明socket还未关闭。
        System.out.println("Socket 是否关闭？" + socket.isClosed());
        try{
            ps.close();
            System.out.println("现在关闭掉输出流");
            System.out.println("socket 是否关闭？" + socket.isClosed());
        }catch (Exception e){ System.out.println("输出流已关闭"); }

        /* 服务端关闭输出流后，继续接收客户端消息，直到客户端关闭 socket */
        //Server 还可以接收客户端发来的数据
        while (scan.hasNextLine()) {
            System.out.println(scan.nextLine());
        }
        scan.close();
        socket.close();
        ss.close();
    }
}

