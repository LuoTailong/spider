package com.itheima.httpClient;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

public class HttpClientGet {
    public static void main(String[] args) throws IOException {
        //1 指定url
        String url = "http://www.itcast.cn";
        //2 指定发送方式
        HttpGet httpGet = new HttpGet(url);
        //3 获取httpclient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //4 执行请求
        CloseableHttpResponse response = httpClient.execute(httpGet);
        //5 获取网页数据
        int status = response.getStatusLine().getStatusCode();
        if (status == 200) {
            String html = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
            System.out.println(html);
        }
        response.close();
        httpClient.close();
    }
}
