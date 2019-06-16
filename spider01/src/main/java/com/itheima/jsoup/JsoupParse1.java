package com.itheima.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JsoupParse1 {
    public static void main(String[] args) throws IOException {
        //1 获取document对象
        Document document = Jsoup.connect("http://www.itcast.cn/").get();
        //2 解析数据
        Elements elements = document.getElementsByClass("nav_txt");
        Element element = elements.get(0);
        Elements lis = element.getElementsByTag("li");
        for (Element li : lis) {
            System.out.println(li.text());
        }
    }
}
