package com.itheima.jdk2;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JdkGet2 {
    public static void main(String[] args) throws Exception {
        //1 确定url
        String indexurl = "http://www.itcast.cn";

        //2 发送请求 获取数据
        //2.1 将url转换成一个url对象
        URL url = new URL(indexurl);
        //2.2 根据url获取此url的远程连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //2.3 设置请求方式
        conn.setRequestMethod("GET");
        //2.4 设置参数：请求参数，请求头

        //2.5 发送请求
        InputStream inputStream = conn.getInputStream();
        //2.6 获取数据
        int len;
        byte[] bytes = new byte[1024];
        while ((len = inputStream.read(bytes)) != -1) {
            System.out.println(new String(bytes, 0, len));
        }
    }
}
