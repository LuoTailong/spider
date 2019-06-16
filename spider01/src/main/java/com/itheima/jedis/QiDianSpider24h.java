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

public class QiDianSpider24h {
    public static void main(String[] args) throws IOException {
        //1 指定url
        String url = "https://www.qidian.com";
        //2 获取httpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //3 获取请求方式
        HttpGet httpGet = new HttpGet(url);
        //4 执行请求
        CloseableHttpResponse response = httpClient.execute(httpGet);
        //5 获取网页
        String html = EntityUtils.toString(response.getEntity(), "utf-8");
        //6 解析网页
        Document document = Jsoup.parse(html);
        //7 获取24h热门榜单
        Elements time24H = document.select("[class=rank-list sort-list]");
        //8 获取榜单标题
        String title = time24H.select("h3").text();
        //9 获取书籍列表
        Elements bookList = time24H.select(".book-list li a[href*=book.qidian.com]");
        //10 获取书籍url
        for (Element element : bookList) {
            String href = element.attr("href");
            //11 url拼接
            String bookUrl = "https:" + href;
            //12 根据书籍的url获取书籍的详情页
            httpClient = HttpClients.createDefault();
            httpGet = new HttpGet(bookUrl);
            response = httpClient.execute(httpGet);
            html = EntityUtils.toString(response.getEntity(), "utf-8");
            document = Jsoup.parse(html);
            //13 获取详情页中点击免费试读的链接和地址
            String readHref = document.select("#readBtn").attr("href");
            readHref = "https:" + readHref;
            while (true) {
                //14 获取小说章节的内容(第一章)
                httpClient = HttpClients.createDefault();
                httpGet = new HttpGet(readHref);
                response = httpClient.execute(httpGet);
                html = EntityUtils.toString(response.getEntity(), "utf-8");
                document = Jsoup.parse(html);
                //15 获取章节名称
                String hread = document.select(".text-head h3").text();
                System.out.println(hread);
                //16 获取小说的内容
                Elements contents = document.select("[class=read-content j_readContent]>p");
                for (Element content : contents) {
                    System.out.println(content.text());
                }
                //17 获取下一章节的url
                String nextUrl = document.select("#j_chapterNext[href*=read.qidian.com]").attr("href");
                if (nextUrl == null || nextUrl.length() == 0) {
                    System.out.println("跳出循环，开始下一本");
                    break;
                }
                readHref = "https:" + nextUrl;
            }
        }
    }
}
