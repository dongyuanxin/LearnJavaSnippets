package com.demo.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 处理逻辑：
 *  新建URL对象 ->
 *  打开连接openConnection ->
 *  设置请求头setRequestProperty ->
 *  发送请求connect（可忽略） ->
 *  获取响应 getInputStream ->
 *  获取响应头 getHeaderFields ->
 *  获取响应文本 InputStream => InputStreamReader => BufferedReader
 */
public class HttpConnectionDemo {
    public static void main(String[] args) throws IOException {
        URL url = new URL("https://www.baidu.com/");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setUseCaches(false);
        conn.setConnectTimeout(1000);

        // setRequestProperty：设置请求头
        conn.setRequestProperty("Accept", "*/*");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 11; Windows NT 5.1)");

        // 连接+发送http请求：
        //  只是主动建立tcp连接（发SYN、建TCP通道，不发送HTTP请求头和请求体），多数情况下可以省略。
        //  当调用 getInputStream 时，能省略掉 connect
        conn.connect();

        // 获取响应文本
        Integer statusCode = conn.getResponseCode();
        InputStream inputStream = statusCode >= 200 && statusCode < 300 ? conn.getInputStream() : conn.getErrorStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        System.out.println(sb.toString());

        // 打印响应头
        Map<String, List<String>> map = conn.getHeaderFields();
        for(String key : map.keySet()) {
            System.out.println(key + ":" + map.get(key));
        }

        reader.close();
        conn.disconnect();
    }
}
