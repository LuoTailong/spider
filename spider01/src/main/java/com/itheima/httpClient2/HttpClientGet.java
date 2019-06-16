package com.itheima.httpClient2;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpClientGet {
    public static void main(String[] args) throws IOException {
        //1 指定url
        String url = "http://www.itcast.cn";
        //2 发送请求获取数据
        //2.1 获取httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //2.2 创建请求方式
        HttpGet httpGet = new HttpGet(url);
        //2.3 设置参数：请求参数 请求头
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
        //2.4 发送请求获取响应对象
        CloseableHttpResponse response = httpClient.execute(httpGet);
        //2.5 获取数据
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode);

        if (statusCode == 200) {
            Header[] headers = response.getHeaders("content-type");
            String value = headers[0].getValue();
            System.out.println(value);
        }

        String html = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println(html);
    }
}
