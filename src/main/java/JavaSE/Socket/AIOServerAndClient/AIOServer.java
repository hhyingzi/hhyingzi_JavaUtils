package JavaSE.Socket.AIOServerAndClient;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AIOServer {
    static final int PORT = 30000;
    static List<AsynchronousSocketChannel> channelList = new ArrayList<>();  //每次建立一个连接，将 Socket 放入此 ArrayList

    public void startListen() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(20);  //FixedThreadPool 线程池，20个线程
        AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup.withThreadPool(executor);  //组管理器
        AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open(channelGroup);  //组管理器分配一个 Server Socket
        serverSocketChannel.bind(new InetSocketAddress(PORT));
        serverSocketChannel.accept(null, new MyAcceptHandler(serverSocketChannel));  //异步获取连接，获取后回调 MyAcceptHandler 的 completed() 方法
        Thread.sleep(100000);
    }
    public static void main(String[] args) throws Exception {
        AIOServer server = new AIOServer();
        server.startListen();
    }
}
// 实现自己的CompletionHandler类
class MyAcceptHandler implements CompletionHandler<AsynchronousSocketChannel, Object> {
    private AsynchronousServerSocketChannel serverSocketChannel;
    public MyAcceptHandler(AsynchronousServerSocketChannel serverSocketChannel) { this.serverSocketChannel = serverSocketChannel; }
    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    //当调用者方法完成后，由 JVM 回调该方法
    @Override
    public void completed(final AsynchronousSocketChannel socketChannel, Object attachment) {  //？？？？哪来的 socketChannel
        // 记录新连接的进来的Channel
        AIOServer.channelList.add(socketChannel);  //将 socket channel 添加进主类成员 ArrayList，防止被回收
        serverSocketChannel.accept(null , this);  // 准备接受客户端的下一次连接
        socketChannel.read(byteBuffer, null, new CompletionHandler<Integer,Object>() {  //异步读取数据到 byteBuffer 中
            @Override
            public void completed(Integer result, Object attachment) {
                byteBuffer.flip();
                // 将buff中内容转换为字符串
                String content = Charset.forName("utf-8").decode(byteBuffer).toString();
                // 遍历每个Channel，将收到的信息写入各Channel中
                for(AsynchronousSocketChannel c : AIOServer.channelList) {
                    try {
                        c.write(ByteBuffer.wrap(content.getBytes("utf-8"))).get();
                    } catch (Exception ex) { ex.printStackTrace(); }
                }
                byteBuffer.clear();
                // 读取下一次数据
                socketChannel.read(byteBuffer, null , this);
            }
            // 从该Channel读取数据失败，说明 socket 已失效。就将该Channel删除
            @Override
            public void failed(Throwable ex, Object attachment) {
                System.out.println("读取数据失败: " + ex);
                AIOServer.channelList.remove(socketChannel);
            }
            });
    }

    //当调用者方法失败时，由 JVM 回调该方法
    @Override
    public void failed(Throwable ex, Object attachment) { System.out.println("连接失败: " + ex); }
}