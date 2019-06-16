package com.itheima.Jsoup2;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JsoupTest3 {
    public static void main(String[] args) throws IOException {
        String url = "http://www.itcast.cn";
        Document document = Jsoup.connect(url).get();
        Elements aEls = document.select(".nav_txt>ul>li>a");
        for (Element aEl : aEls) {
            System.out.println(aEl.text());
        }
    }
}
