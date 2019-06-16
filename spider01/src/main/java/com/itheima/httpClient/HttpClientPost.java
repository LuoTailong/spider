package com.itheima.httpClient;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

public class HttpClientPost {
    public static void main(String[] args) throws Exception {
        //1 指定url
        String url = "http://www.itcast.cn";
        //2 指定Post方式
        HttpPost httpPost = new HttpPost(url);
        //3 获取httpclient实列用来执行请求
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //3.1 封装参数
        ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("username","张三"));
        parameters.add(new BasicNameValuePair("password", "123"));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters);
        httpPost.setEntity(entity);
        //4 执行请求
        CloseableHttpResponse response = httpClient.execute(httpPost);
        //5 获取数据
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            String html = EntityUtils.toString(response.getEntity());
            System.out.println(html);
        }
    }
}
