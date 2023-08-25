package JavaSE.Socket.HTTPClient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpClientExample {
    public static void main(String[] args)throws Exception{
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.baidu.com/"))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "text/html")
                .GET()
                .build();
        //Post Request
        /*
        HttpRequest requestPost = HttpRequest.newBuilder()
                .uri(URI.create("https://www.baidu.com"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("name=hhyingzi&pass=123456"))  //Post 数据
//                .POST(HttpRequest.BodyPublishers.ofByteArray(new byte[]{...}))  //Post 数据
                .build();
        */

        //同步示例
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("状态码" + response.statusCode());
        System.out.println("响应头" + response.headers());
        System.out.println("响应体" + response.body());

        //异步示例：发送 request，返回 CompletableFuture 对象。
        System.out.println("=====异步=====");
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response2 -> new Object[]{response.statusCode(), response.body()})  //当 CompletableFuture 代表的任务完成时，传入的 Lambda 表达式对该返回值进行转换，这里提取响应体
                .thenAccept(obj -> {
                    System.out.println("响应码：" + obj[0]);
                    System.out.println("响应体：" + obj[1]);
                });
        Thread.sleep(1000);  //异步的线程不会阻塞，如果不停止则程序结束了，来不及看到异步响应了
    }
}
