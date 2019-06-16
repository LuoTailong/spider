package com.itheima.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JsoupParse2 {
    public static void main(String[] args) throws IOException {
        //1 使用选择器解析数据
        String url = "http://www.itcast.cn";
        Document document = Jsoup.connect(url).get();
        //2 需求解析出此网页中的所有学科
        Elements lis = document.select("nav_txt li");
        for (Element li : lis) {
            //3 获取分类课程信息
            System.out.println(li.text());
        }
    }
}
