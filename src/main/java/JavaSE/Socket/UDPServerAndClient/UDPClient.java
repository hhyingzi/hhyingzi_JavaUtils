package JavaSE.Socket.UDPServerAndClient;

import java.net.*;
import java.io.*;
import java.util.*;

public class UDPClient {
    // 定义发送数据报的目的地
    public static final int DEST_PORT = 30000;
    public static final String DEST_IP = "127.0.0.1";
    // 定义每个数据报的最大大小为4K
    private static final int DATA_LEN = 4096;
    // 定义接收网络数据的字节数组
    byte[] inBuff = new byte[DATA_LEN];
    // 以指定字节数组创建准备接受数据的DatagramPacket对象
    private DatagramPacket inPacket = new DatagramPacket(inBuff , inBuff.length);
    // 定义一个用于发送的DatagramPacket对象
    private DatagramPacket outPacket = null;

    public void init()throws IOException {
        try(DatagramSocket socket = new DatagramSocket()) {  // 创建一个客户端DatagramSocket，使用随机端口
            // 初始化发送用的DatagramSocket，它包含一个长度为0的字节数组
            outPacket = new DatagramPacket(new byte[0] , 0, InetAddress.getByName(DEST_IP) , DEST_PORT);
            // 不断读取键盘输入
            Scanner scanner = new Scanner(System.in);
            while(scanner.hasNextLine()) {
                byte[] buff = scanner.nextLine().getBytes();
                outPacket.setData(buff);  // 设置发送用的DatagramPacket里的字节数据
                socket.send(outPacket);  // 发送数据报

                //读取服务端发回的数据，并输出
                socket.receive(inPacket);
                System.out.println(new String(inBuff , 0, inPacket.getLength()));
            }
        }
    }
    public static void main(String[] args) throws IOException {
        new UDPClient().init();
    }
}