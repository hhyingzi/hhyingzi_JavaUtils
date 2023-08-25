package JavaSE.Socket.MapUserSocketServerAndClient;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

class ClientThread extends Thread {
    BufferedReader br = null;
    //构造器：给线程传入一个输入流 BufferedReader ，来创建客户端线程
    public ClientThread(BufferedReader br)
    {
        this.br = br;
    }
    //依托类成员中的socket输入流，不断从输入流中读取数据，并将这些数据打印输出
    public void run() {
        try {
            String line = null;
            while((line = br.readLine())!= null) {
                System.out.println(line);
				/*
				本例仅打印了从服务器端读到的内容。实际上，此处的情况可以更复杂：
				如果希望客户端能看到聊天室的用户列表，则可以让服务器在
				每次有用户登录、用户退出时，将所有用户列表信息都向客户端发送一遍。
				为了区分服务器发送的是聊天信息，还是用户列表，服务器也应该
				在要发送的信息前、后都添加一定的协议字符串，客户端此处则根据协议
				字符串的不同而进行不同的处理！
				更复杂的情况：
				如果两端进行游戏，则还有可能发送游戏信息，例如两端进行五子棋游戏，
				则还需要发送下棋坐标信息等，服务器同样在这些下棋坐标信息前、后
				添加协议字符串后再发送，客户端就可以根据该信息知道对手的下棋坐标。
				*/
            }
        } catch (IOException ex) { ex.printStackTrace(); }
        finally {
            try { if (br != null) br.close(); }
            catch (IOException ex) { ex.printStackTrace(); }
        }
    }
}

//
public class Client {
    private static final int SERVER_PORT = 30000;
    private Socket socket;
    private PrintStream ps;
    private BufferedReader brServer;
    private BufferedReader keyIn;
    //从键盘获取用户名，发送给服务器。根据服务器返回结果决定操作是否成功。
    //初始化成功后，则对指定用户名启动一个 socket 线程，与服务器通信。
    public void init() {
        try {
            keyIn = new BufferedReader(new InputStreamReader(System.in));  // 初始化代表键盘的输入流
            socket = new Socket("127.0.0.1", SERVER_PORT);  // 连接到服务器
            // 获取该Socket对应的输入流和输出流
            ps = new PrintStream(socket.getOutputStream());
            brServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String tip = "";
            // 采用循环不断地弹出对话框要求输入用户名
            while(true) {
                System.out.println(tip + "请输入用户名：");
                String userName = new Scanner(System.in).nextLine();
//                String userName = JOptionPane.showInputDialog(tip + "输入用户名");
                // 将用户输入的用户名的前后增加协议字符串后发送
                ps.println(CrazyitProtocol.USER_ROUND + userName + CrazyitProtocol.USER_ROUND);
                // 读取服务器的响应
                String result = brServer.readLine();
                // 如果用户重复，开始下次循环
                if (result.equals(CrazyitProtocol.NAME_REP)) {
                    tip = "用户名重复！请重新";
                    continue;
                }
                // 如果服务器返回登录成功，结束循环
                if (result.equals(CrazyitProtocol.LOGIN_SUCCESS)) break;
            }
        } catch (UnknownHostException ex) {
            System.out.println("找不到远程服务器，请确定服务器已经启动！");
            closeRs();
            System.exit(1);
        } catch (IOException ex) {
            System.out.println("网络异常！请重新登录！");
            closeRs();
            System.exit(1);
        }
        // 以该Socket对应的输入流启动ClientThread线程
        new ClientThread(brServer).start();
    }
    //用于发送普通信息的方法
    private void readAndSend() {
        try {
            // 不断读取键盘输入
            String line = null;
            while((line = this.keyIn.readLine()) != null) {
                // 如果发送的信息中，以 // 开头，且有冒号，则认为想发送私聊信息
                //示例： //user1:nihao
                if (line.startsWith("//") && line.indexOf(":") > 0) {
                    line = line.substring(2);
                    ps.println(CrazyitProtocol.PRIVATE_ROUND +
                            line.split(":")[0] + CrazyitProtocol.SPLIT_SIGN
                            + line.split(":")[1] + CrazyitProtocol.PRIVATE_ROUND);
                } else {
                    ps.println(CrazyitProtocol.MSG_ROUND + line + CrazyitProtocol.MSG_ROUND);
                }
            }
        } catch (IOException ex) {
            System.out.println("网络通信异常！请重新登录！");
            closeRs();
            System.exit(1);
        }
    }
    // 关闭Socket、输入流、输出流的方法
    private void closeRs() {
        try {
            if (this.keyIn != null) ps.close();
            if (brServer != null) ps.close();
            if (ps != null) ps.close();
            if (socket != null) keyIn.close();
        } catch (IOException ex) { ex.printStackTrace(); }
    }
    public static void main(String[] args) {
        Client client = new Client();
        client.init();
        client.readAndSend();
    }
}
