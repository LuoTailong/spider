package com.itheima.spider.jd.spider;

import com.google.gson.Gson;
import com.itheima.spider.jd.dao.ProductDao;
import com.itheima.spider.jd.pojo.Product;
import com.itheima.spider.jd.utils.HttpClientUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JdSpider2 {
    private static ProductDao productDao = new ProductDao();
    private static ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<String>(1000);

    public static void main(String[] args) throws Exception {
        /*//1 指定url
        String url = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8";
        //2 发送请求 获取数据
        String html = HttpClientUtils.doGet(url);
        //3 解析数据
        parsePid(html);*/
        threadPoolExecute();
        page();
    }

    //线程相关内容
    public static void threadPoolExecute() {
        //开启30个线程同时消费pid
        ExecutorService threadPool = Executors.newFixedThreadPool(31);
        for (int i = 0; i < 30; i++) {
            threadPool.execute(new Runnable() {
                //1 获取pid
                public void run() {
                    while (true) {
                        //从队列中取值
                        String pid = null;
                        try {
                            pid = arrayBlockingQueue.take();
                            //2 拼接url解析数据
                            Product product = parseProduct(pid);
                            //3 保存数据
                            productDao.addProduct(product);
                        } catch (Exception e) {
                            e.printStackTrace();
                            //将解析失败的pid放回队列，重新解析
                            try {
                                arrayBlockingQueue.put(pid);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            });
        }

        //开启一个线程来监控队列中pid的数量
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    //1s查询一次
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //获取队列的数量
                    int size = arrayBlockingQueue.size();
                    System.out.println("队列中共有"+size+"个pid");
                }
            }
        });
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
            //将pid存储到阻塞队列中
            arrayBlockingQueue.put(pid);
        }
    }

    //根据给定的pid解析商品数据
    private static Product parseProduct(String pid) throws Exception {
        //1 拼接url
        String productUrl = "https://item.jd.com/" + pid + ".html";
        //2 发送数据 获取数据
        String html = HttpClientUtils.doGet(productUrl);
        Product product = new Product();
        //3 解析商品数据
        //3.1 将html转化成document对象
        Document document = Jsoup.parse(html);
        //3.2 获取商品标题
        Elements title = document.select(".sku-name");//类
        product.setTitle(title.text());

        //3.3 获取商品的价格
        //https://p.3.cn/prices/mgets?skuIds=J_7479820
        /*String priceUrl = "https://p.3.cn/prices/mgets?pduid=1539865594681309570743&skuIds=J_" + pid;
        String priceJson = HttpClientUtils.doGet(priceUrl);*/
//        System.out.println(priceJson);

        //Json的转换
        //[]:可以转换成数组，也可以转换成集合
        //{}:可以转换成map，也可以转换成对象
        //如何区分一个json字符串是什么类型的呢?  只需要查看json的最外侧的符号, 如果是[] 就是数组 如果是{} 对象
        /*Gson gson = new Gson();
        List<Map<String, String>> list = gson.fromJson(priceJson, List.class);
        String price = list.get(0).get("p");
        product.setPrice(price);*/

        //3.4 获取商品品牌
        Elements brandEl = document.select("#parameter-brand>li");//id
        product.setBrand(brandEl.attr("title"));

        //3.5 获取商品的名称
        Elements nameEl = document.select("[class=parameter2 p-parameter-list]>li:first-child");//元素 因为有空格所以不能作为类选择器
        product.setName(nameEl.attr("title"));

        //3.6 设置url,pid
        product.setUrl(productUrl);
        product.setPid(pid);
        System.out.println(product);
        return product;
    }
}
