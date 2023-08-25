package JavaSE.Socket.HTTPClient;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class URLConnectionExample {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://www.baidu.com");
        URLConnection connection = url.openConnection();
        try(Scanner scanner = new Scanner(connection.getInputStream())){
            while(scanner.hasNextLine()){
                System.out.println(scanner.nextLine());
            }
        }catch (Exception e){ e.printStackTrace(); }

    }
}
