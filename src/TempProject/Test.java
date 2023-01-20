package TempProject;

import java.net.ConnectException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;

public class Test {
    static final String data_file = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\Datas\\temp.txt";
    static final String data_file_out = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\Datas\\temp2.txt";

    public static void main(String[] args) {
        try(
                DatagramChannel datagramChannel = DatagramChannel.open() //自身随机分配了一个接收端口
        ){
            DatagramSocket datagramSocket = datagramChannel.socket();
            datagramSocket.bind(new InetSocketAddress("127.0.0.1", 9999));  //向该地址和端口发送数据
        }catch (ConnectException connectException){
            System.out.println("Connect fail!");
            connectException.printStackTrace();
        }
        catch(Exception e){e.printStackTrace();}
    }
}