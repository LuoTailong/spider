package com.itheima.Jdk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class JdkGet {
    public static void main(String[] args) throws IOException {
        //1 指定一个url
        String domain = "http://www.itcast.cn";
        //2 打开一个连接
        URL url = new URL(domain);
        //3 如果是一个get请求，需要设置请求头信息
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //4 发起请求
        InputStream inputStream = conn.getInputStream();
        //5 打印数据
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("utf-8")));
        String line = null;
        while ((line =br.readLine())!= null) {
            System.out.println(line);
        }
    }
}
