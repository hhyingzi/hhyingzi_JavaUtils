package JavaSE.Socket.AIOServerAndClient;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

public class ASimpleServer {
    static final int PORT = 30000;
    public static void main(String[] args) throws Exception {
        try(AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open()) {
            serverChannel.bind(new InetSocketAddress(PORT));
            while (true) {
                Future<AsynchronousSocketChannel> future = serverChannel.accept();
                // 获取连接完成后返回的AsynchronousSocketChannel
                AsynchronousSocketChannel socketChannel = future.get();
                socketChannel.write(ByteBuffer.wrap("欢迎你来自AIO的世界！".getBytes("UTF-8"))).get();
            }
        }
    }
}
