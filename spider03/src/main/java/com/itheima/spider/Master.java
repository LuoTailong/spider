package com.itheima.spider;

import com.itheima.dao.ProductDao;
import com.itheima.utils.HttpClientUtils;
import com.itheima.utils.JedisUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;

import java.util.concurrent.ArrayBlockingQueue;

public class Master {
    //根据分页url获取pid，把pid存到redis的list集合中
    public static void main(String[] args) throws Exception {
        page();
    }
    //分页
    public static void page() throws Exception {
        for (int i = 0; i < 100; i++) {
            String pageUrl = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&page=" + (2 * i - 1);

            String html = HttpClientUtils.doGet(pageUrl);
            parsePid(html);
        }
    }

    //解析pid
    private static void parsePid(String html) throws Exception {
        //3.1 将html转换成document
        Document document = Jsoup.parse(html);
        //3.2 解析pid
        Elements liEls = document.select("[class=gl-warp clearfix]>li");
        for (Element liEl : liEls) {
            String pid = liEl.attr("data-pid");
            System.out.println(pid);
            //将pid存储到redis的list集合中
            Jedis jedis = JedisUtils.getJedis();
            jedis.lpush("bigData:spider:jdspider:pid",pid);
            jedis.close();
        }
    }
}
