package JavaSE.Socket.NIOServerAndClient;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

//《JAVA NIO 中文版》示例。
//如果多线程会同时访问 Selector，需要在此访问操作上加锁。这里略过。
public class NServer {
    public static int PORT_NUMBER = 30000;
    private ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

    public void go() throws Exception{
        int port = PORT_NUMBER;
        System.out.println("Listening on port: " + port);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        Selector selector = Selector.open();
        serverSocket.bind(new InetSocketAddress("127.0.0.1", port));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while(selector.select() > 0){
            Iterator it = selector.selectedKeys().iterator();
            while(it.hasNext()){
                SelectionKey key = (SelectionKey) it.next();
                it.remove();  //处理完这个键后，从 ready() 集合中删除（不删除它就会是一直ready的状态）

                //如果是客户端要来建立连接的 key，则创建一个 SocketChannel 与之通信。
                if(key.isAcceptable()){
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel channel = server.accept();
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ);  //监听 socket 通信
                    sayHello(channel);
                }
                //如果是客户端要来通信的，则直接在该 channel 中传输数据即可
                try {
                    if (key.isReadable()) readDataFromSocket(key);
                } catch (Exception e) {  //如果客户端关闭连接，那么要丢弃对应的 socket。
                    key.cancel();  //取消 selector 上面，对该 SocketChannel 的 key 的注册。
                    if (key.channel() != null) key.channel().close();  //关闭 SocketChannel 通道
                }
            }
        }
    }

    protected void readDataFromSocket(SelectionKey key) throws Exception {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        int count;
        this.buffer.clear();
        //读一段话，然后反手就写回去
        while ((count = socketChannel.read(buffer)) > 0) {
            buffer.flip();  //buffer 转为写姿态
            while (buffer.hasRemaining()) socketChannel.write(buffer);
            buffer.clear();  //buffer 转为读姿态
        }
        if (count < 0) socketChannel.close();
    }

    //新连接欢迎语
    private void sayHello(SocketChannel channel) throws Exception {
        buffer.clear();
        buffer.put("Hi there!\r\n".getBytes());
        buffer.flip();
        channel.write(buffer);
    }

    public static void main(String[] args) {
        try{
            new NServer().go();
        } catch(Exception e){e.printStackTrace();}
    }
}
