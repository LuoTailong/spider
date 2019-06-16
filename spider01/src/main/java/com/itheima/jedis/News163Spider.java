package com.itheima.jedis;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class News163Spider {
    public static void main(String[] args) throws IOException {
        //1 创建client对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //2 指定请求方式
        HttpGet httpGet = new HttpGet("https://news.163.com/18/0919/14/DS2TVFMS0001875O.html");
        //3 执行请求，获取响应
        CloseableHttpResponse response = httpClient.execute(httpGet);
        //4 获取响应数据
        String html = EntityUtils.toString(response.getEntity(), "utf-8");

        //1 获取document对象
        Document document = Jsoup.parse(html);
        //2 解析数据
        //2.1 解析新闻标题
        Elements elements = document.select("#epContentLeft>h1");
        System.out.println(elements.text());
        //2.2 解析新闻时间
        Elements timeAndSourceEl = document.select(".post_time_source");
        String timeAndSource = timeAndSourceEl.text();
        String[] split = timeAndSource.split("　");
        String time = split[0];
        System.out.println(time);
        //2.3 解析新闻来源
        String source = split[1];
        System.out.println(source);
        //2.4 解析新闻正文
        Elements pEl = document.select("#endText>P");
        for (Element p : pEl) {
            System.out.println(p.text());
        }
        //2.5 获取新闻编辑
        Elements editor = document.select(".ep-editor");
        System.out.println(editor.text());
    }
}
