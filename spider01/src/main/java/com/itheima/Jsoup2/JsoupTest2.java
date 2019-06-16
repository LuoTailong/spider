package com.itheima.Jsoup2;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JsoupTest2 {
    public static void main(String[] args) throws IOException {
        String url = "http://www.itcast.cn";
        Document document = Jsoup.connect(url).get();
        Elements divEls = document.getElementsByClass("nav_txt");
        Element divEl = divEls.get(0);
        Elements ulEls = divEl.getElementsByTag("ul");
        Element ulEl = ulEls.get(0);
        Elements liEls = ulEl.getElementsByTag("li");
        for (Element liEl : liEls) {
            Elements aEls = liEl.getElementsByTag("a");
            Element aEl = aEls.get(0);
            System.out.println(aEl.text());
        }
    }
}
