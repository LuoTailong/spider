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

public class QiDianSpider {
    public static void main(String[] args) throws IOException {
        String url  = "https://read.qidian.com/chapter/2R9G_ziBVg41/eSlFKP1Chzg1";
        while (true) {
            //2 发送请求获取数据
            //2.1 创建httpClient对象
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //2.2 创建请求方式
            HttpGet httpGet = new HttpGet(url);
            //2.3 设置参数
            //2.4 发送请求 获取相应对象
            CloseableHttpResponse response = httpClient.execute(httpGet);
            //2.5 获取数据
            String html = EntityUtils.toString(response.getEntity(), "utf-8");
            //3 解析数据
            //3.1 获取document对象
            Document document = Jsoup.parse(html);
            //3.2 获取小说章节标题
            Elements chapterNameEl = document.select(".j_chapterName");
            System.out.println(chapterNameEl.text());
            //3.3 获取小说内容
            Elements pEls = document.select("[class=read-content j_readContent]>p");
            for (Element pEl : pEls) {
                System.out.println(pEl.text());
            }
            //3.4 获取下一章数据
            //https://vipreader.qidian.com/chapter/1209977/23724364
            //https://read.qidian.com/chapter/2R9G_ziBVg41/dBHJYlixU5oex0RJOkJclQ2
            Elements chapterNextUrlEl = document.select("#j_chapterNext[href*=read.qidian.com]");
            if (chapterNameEl == null || chapterNameEl.size() == 0) {
                System.out.println("跳出循环");
                break;
            }
            String chapterNextUrl = chapterNextUrlEl.attr("href");
            System.out.println(chapterNextUrl);
            url ="https:"+chapterNextUrl;
        }
    }
}
