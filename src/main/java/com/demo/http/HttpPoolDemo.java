package com.demo.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * HttpClient：内部有连接池 Connection Pool，支持TCP多路复用、HTTP/2、自动管理Keep-alive、不会反复创建socket
 * HttpRequest：封装了请求信息
 * HttpResponse：封装了响应信息.读取返回内容( response.body() ) 和 响应头( response.headers() )
 */
public class HttpPoolDemo {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .header("User-Agent", "Mozilla/5.0 (compatible; MSIE 11; Windows NT 5.1)")
                .header("Accept", "*/*")
                .uri(URI.create("https://www.baidu.com/"))
                .timeout(Duration.ofSeconds(5)) // 设置超时时间
                .version(HttpClient.Version.HTTP_2) // 设置http版本
                .build();
//        这里除了ofString，还可以使用 ofInputStream + response.body() 来读取流
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 读取返回内容( response.body() ) 和 响应头( response.headers() )
        // HTTP允许重复的Header，因此一个Header可对应多个Value:
        Map<String, List<String>> headers = response.headers().map();
        for (String header : headers.keySet()) {
            System.out.println(header + ": " + headers.get(header).get(0));
        }
        System.out.println(response.body().substring(0, 1024) + "...");


//        异步版本不占用戏堪称，但不保证顺序一致；使用了 CompletableFuture， 与Reactor 一致
//        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
//                .thenApply(HttpResponse::body)
//                .thenAccept(System.out::println)
//                .join();

    }
}
