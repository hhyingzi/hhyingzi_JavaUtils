package MyUtils;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ServerAndClientWithChannel {
    class TimeServer implements Runnable{
        final int DEFAULT_TIME_PORT = 9999;
        final long DIFF_1900 = 2208988800L;
        DatagramChannel channel;

        public void listen() throws Exception{
            ByteBuffer longBuffer = ByteBuffer.allocate(8);  //接收long类型的buffer
            longBuffer.order(ByteOrder.BIG_ENDIAN); //默认是大端字节序（参考 bytebuffer）
            longBuffer.putLong(0, 0);
            longBuffer.position(4);
            ByteBuffer buffer = longBuffer.slice();
            while(true){
                buffer.clear();
                SocketAddress socketAddress = this.channel.receive(buffer);
                if(socketAddress == null) {
                    System.out.println("[Server]No client.");
                    Thread.sleep(2000);
                    continue;
                }
                System.out.println("[Server]Time request from: " + socketAddress);
                buffer.clear();
                longBuffer.putLong(0, (System.currentTimeMillis()/1000) + DIFF_1900);
                this.channel.send(buffer, socketAddress);
            }
        }
        @Override
        public void run(){
            try{
                this.channel = DatagramChannel.open();
                this.channel.socket().bind(new InetSocketAddress("127.0.0.1", DEFAULT_TIME_PORT));
                System.out.println("[Server]Listening on port: " + DEFAULT_TIME_PORT + " for time requests.");
                listen();
            }catch (SocketException e){
                System.out.println("[Server]Can't bind to port: " + DEFAULT_TIME_PORT + ", try a different one.");
            }catch (Exception e){e.printStackTrace();}
        }
    }

    class TimeClient implements Runnable{
        final int DEFAULT_TIME_PORT = 9998;
        final long DIFF_1900 = 220898800L;
        List remoteHosts;
        DatagramChannel datagramChannel;

        InetSocketAddress receivePacket(DatagramChannel channel, ByteBuffer buffer)throws Exception{
            buffer.clear();
            return ((InetSocketAddress) channel.receive(buffer));
        }

        void sendRequests() throws Exception{
            ByteBuffer buffer = ByteBuffer.allocate(1);
            Iterator iterator = remoteHosts.iterator();
            while(iterator.hasNext()){
                InetSocketAddress inetSocketAddress = (InetSocketAddress) iterator.next();
                System.out.println("[Client]Requesting time from: " + inetSocketAddress.getHostName() + " : " + inetSocketAddress.getPort());
                buffer.clear().flip();
                datagramChannel.send(buffer, inetSocketAddress);
            }
        }

        void printTime(long remote1900, InetSocketAddress socketAddress){
            long local = System.currentTimeMillis()/1000;
            long remote = remote1900 - DIFF_1900;
            Date remoteDate = new Date(remote * 1000);
            Date localDate = new Date(local * 1000);
            long skew = remote - local;
            System.out.println("[Client]Reply from " + socketAddress.getHostName() + " : " + socketAddress.getPort());
            System.out.println("There: " + remoteDate);
            System.out.println("Here: " + localDate);
            if(skew == 0) System.out.println("Time equals");
            else if(skew > 0) System.out.println(skew + " seconds ahead");
            else System.out.println(skew + " seconds behind");
        }

        void getReplies() throws Exception{
            ByteBuffer longBuffer = ByteBuffer.allocate(8);
            longBuffer.order(ByteOrder.BIG_ENDIAN);
            longBuffer.putLong(0, 0);
            longBuffer.position(4);
            ByteBuffer buffer = longBuffer.slice();
            int expect = remoteHosts.size();
            int replies = 0;
            System.out.println("[Client]Waiting for replies...");
            while(true){
                InetSocketAddress inetSocketAddress;
                inetSocketAddress = receivePacket(datagramChannel, buffer);
                buffer.flip();
                replies++;
                printTime(longBuffer.getLong(0), inetSocketAddress);
                if(replies == expect){
                    System.out.println("All packets answered");
                    break;
                }
                System.out.println("[Client]Received " + replies + " of " + expect + " replies");
            }
        }

        @Override
        public void run(){
            try{
                this.datagramChannel = DatagramChannel.open();
                sendRequests();
                getReplies();
            }catch (Exception e){e.printStackTrace();}
        }
    }

    public static void main(String[] args){
        ServerAndClientWithChannel solution = new ServerAndClientWithChannel();
        Thread timeServer = new Thread(solution.new TimeServer());
        timeServer.start();

        Thread timeClient = new Thread(solution.new TimeClient());
        timeClient.start();
    }
}
