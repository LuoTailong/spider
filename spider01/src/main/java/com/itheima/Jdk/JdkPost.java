package com.itheima.Jdk;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JdkPost {
    public static void main(String[] args) throws IOException {
        //1 指定url
        String url = "http://www.itcast.cn";
        //2 创建URL对象
        URL urlObj = new URL(url);
        //3 打开一个连接
        HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
        //3.1 指定请求方式
        conn.setRequestMethod("POST");
        //3.2 打开输出流
        conn.setDoOutput(true);
        //3.3 封装参数
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write("username=张三&password=123".getBytes());
        //4 获取输入流
        InputStream inputStream = conn.getInputStream();
        byte[] bytes = new byte[1024];
        //5 打印数据
        int len;
        while ((len=inputStream.read(bytes))!=-1){
            System.out.println(new String(bytes,0,len));
        }
        inputStream.close();
        outputStream.close();
    }
}
