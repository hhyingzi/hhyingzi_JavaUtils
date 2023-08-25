package JavaSE.Socket.MapUserSocketServerAndClient;

import java.net.*;
import java.io.*;

class ServerThread extends Thread {
    private Socket socket;
    BufferedReader br = null;
    PrintStream ps = null;
    //构造器：接收服务器与客户端的 Socket 连接实例，构造此聊天线程。
    public ServerThread(Socket socket) { this.socket = socket; }
    public void run() {
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ps = new PrintStream(socket.getOutputStream());
            String line = null;
            while((line = br.readLine())!= null) {
                // 设置用户名：如果读到的行以CrazyitProtocol.USER_ROUND开始，并以其结束，可以确定读到的是用户登录的用户名
                if (line.startsWith(CrazyitProtocol.USER_ROUND) && line.endsWith(CrazyitProtocol.USER_ROUND)) {
                    String userName = getRealMsg(line); //识别出消息中的用户名字符串
                    // 如果用户名重复，则向控制台提示用户名重复，向客户输出 -1。
                    if (Server.clients.dataMap.containsKey(userName)) {
                        System.out.println("用户名重复");
                        ps.println(CrazyitProtocol.NAME_REP);
                    }
                    else {
                        System.out.println("成功");
                        ps.println(CrazyitProtocol.LOGIN_SUCCESS);
                        Server.clients.put(userName , ps);
                    }
                }
                // 获取消息类型：如果读到的行以CrazyitProtocol.PRIVATE_ROUND开始，并以其结束，
                // 可以确定是私聊信息，私聊信息只向特定的输出流发送
                else if (line.startsWith(CrazyitProtocol.PRIVATE_ROUND) && line.endsWith(CrazyitProtocol.PRIVATE_ROUND)) {
                    String userAndMsg = getRealMsg(line);
                    // 以SPLIT_SIGN分割字符串，前半是私聊用户，后半是聊天信息
                    String user = userAndMsg.split(CrazyitProtocol.SPLIT_SIGN)[0];
                    String msg = userAndMsg.split(CrazyitProtocol.SPLIT_SIGN)[1];
                    // 获取私聊用户对应的输出流，并发送私聊信息
                    Server.clients.dataMap.get(user).println(Server.clients.getKeyByValue(ps) + "悄悄地对你说：" + msg);
                }
                // 公聊要向每个Socket发送
                else {
                    // 得到真实消息
                    String msg = getRealMsg(line);
                    // 遍历clients中的每个输出流
                    for (PrintStream clientPs : Server.clients.valueSet()) {
                        clientPs.println(Server.clients.getKeyByValue(ps) + "说：" + msg);
                    }
                }
            }
        } catch (IOException e) {
            Server.clients.removeByValue(ps);
            System.out.println(Server.clients.dataMap.size());
            try {
                if (br != null) br.close();
                if (ps != null) ps.close();
                if (socket != null) socket.close();
            } catch (IOException ex) { ex.printStackTrace(); }
        }
    }
    // 将读到的内容去掉前后的协议字符，恢复成真实数据
    private String getRealMsg(String line) {
        return line.substring(CrazyitProtocol.PROTOCOL_LEN, line.length() - CrazyitProtocol.PROTOCOL_LEN);
    }
}

public class Server {
    private static final int SERVER_PORT = 30000;
    // 使用CrazyitMap对象来保存 <客户名字, 对应输出流> 之间的对应关系。
    public static CrazyitMap<String , PrintStream> clients = new CrazyitMap<>();
    public void init() {
        try(ServerSocket ss = new ServerSocket(SERVER_PORT)){
            while(true) {
                Socket socket = ss.accept();
                new ServerThread(socket).start();
            }
        } catch (IOException ex) { System.out.println("服务器启动失败，是否端口" + SERVER_PORT + "已被占用？"); }
    }
    public static void main(String[] args) {
        Server server = new Server();
        server.init();
    }
}

