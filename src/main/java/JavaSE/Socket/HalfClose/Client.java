package JavaSE.Socket.HalfClose;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost" , 30000);
        Scanner scan = new Scanner(s.getInputStream());
        //接收服务端一次性发来的数据
        while (scan.hasNextLine()) {
            System.out.println(scan.nextLine());
        }
        PrintStream ps = new PrintStream(s.getOutputStream());
        ps.println("客户端的第一行数据");
        ps.println("客户端的第二行数据");
        ps.close();
        scan.close();
        s.close();
    }
}

