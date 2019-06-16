package com.itheima.solr;

import com.itheima.pojo.Product;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class indexSearcher {
    @Test
    public void indexSearcherTest() throws Exception {
        //创建solrServer对象
        SolrServer solrServer = new HttpSolrServer("http://localhost/solr/collection1");

        //执行查询
        SolrQuery solrQuery = new SolrQuery("*:*");
        solrQuery.setRows(100);//默认是查询10行，可以自定义
        QueryResponse response = solrServer.query(solrQuery);

        //获取数据
        SolrDocumentList documentList = response.getResults();
        for (SolrDocument document : documentList) {
            String id = (String) document.get("id");
            String title = (String) document.get("title");
            String content = (String) document.get("content");
            Long price = (Long) document.get("price");
            System.out.println(id+"---"+title+"---"+content+"---"+price);
        }
    }

    @Test
    public void indexSearcherTest2() throws Exception {
        //创建solrServer对象
        SolrServer solrServer = new HttpSolrServer("http://localhost/solr/collection1");

        //执行查询
        SolrQuery solrQuery = new SolrQuery("*:*");
        QueryResponse response = solrServer.query(solrQuery);
        List<Product> productList = response.getBeans(Product.class);
        for (Product product : productList) {
            System.out.println(product);
        }
    }

    public void publicSearcher(SolrQuery solrQuery) throws Exception {
        SolrServer solrServer = new HttpSolrServer("http://localhost/solr/collection1");
        QueryResponse response = solrServer.query(solrQuery);
        List<Product> productList = response.getBeans(Product.class);
        for (Product product : productList) {
            System.out.println(product);
        }
    }

    //多样化查询
    @Test
    public void indexSearcherTest3() throws Exception {
        //通配符查询: ? *
        //q的格式： 字段的名:字段的值
        SolrQuery solrQuery = new SolrQuery("name:ipho*");
        //publicSearcher(solrQuery);

        //相似度查询：相当于模糊查询
        //最大编辑次数：2次 次数大于2按2取，次数小于0按0取
        //如果用户输入~，会导致solr报错，此时需要将用户的输入内容两侧加上单引号或者双引号
        solrQuery = new SolrQuery("name:iphop~2");
        publicSearcher(solrQuery);

        //范围查询：TO一定要大写
        //id是String类型的，如果是String类型使用范围查询的时候是按照字典顺序进行排序
        //demo：1，2，3，4      1，2，3，10，20 :[1~2]  1,10,2,20
        solrQuery = new SolrQuery("id:[1 TO 3]");
        //publicSearcher(solrQuery);

        //布尔查询(组合查询的一种)：AND(MUST) OR(SHOULD) NOT(MUST_NOT)
        solrQuery = new SolrQuery("name:iphone NOT id:3");
        //publicSearcher(solrQuery);

        //子表达式查询
        solrQuery = new SolrQuery("(name:iphone OR id:10) AND title:炫");
        //publicSearcher(solrQuery);

    }

    @Test
    public void indexSearcherTest4() throws Exception {
        //1 创建SolrServer对象
        SolrServer solrServer = new HttpSolrServer("http://localhost/solr/collection1");

        //2 执行查询
        SolrQuery solrQuery = new SolrQuery("content:搜索");

        //设置高亮start--------------------------------
        solrQuery.setHighlight(true);//开启高亮
        solrQuery.addHighlightField("content");//设置高亮字段
        solrQuery.setHighlightSimplePre("<font color='red'");
        solrQuery.setHighlightSimplePost("</font>");

        //设置高亮的分片数：默认值为1
        solrQuery.setHighlightSnippets(1000);
        //高亮设置end--------------------------------

        QueryResponse response = solrServer.query(solrQuery);

        //获取高亮start--------------------------------
        //最外层的map：
        //key:高亮文档的id值  value:对应这个文档的高亮内容的map集合
        //内层map:
        //key:高亮的字段名称   value:高亮的内容集合
        //list集合：里面一般只有一个数据,除非当前的字段是一个多值的字段，同时高亮的分片必须大于1
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        for (String docId : highlighting.keySet()) {
            System.out.println(docId);
            Map<String, List<String>> listMap = highlighting.get(docId);
            List<String> list = listMap.get("content");
            System.out.println(list.get(0));
        }
        //获取高亮结束--------------------------------

        //3 获取结果
        List<Product> list = response.getBeans(Product.class);
        for (Product product : list) {
            System.out.println(product);
        }
    }

    @Test
    public void indexSearcherTest5() throws Exception {
        int page = 3;
        int pageSize = 2;
        //1 创建SolrServer对象
        SolrServer solrServer = new HttpSolrServer("http://localhost/solr/collection1");

        //2 执行查询
        SolrQuery solrQuery = new SolrQuery("*:*");
        solrQuery.setSort("id", SolrQuery.ORDER.asc);
        solrQuery.setStart((page-1)*pageSize);
        solrQuery.setRows(pageSize);

        QueryResponse response = solrServer.query(solrQuery);

        //3 获取数据
        List<Product> productList = response.getBeans(Product.class);
        for (Product product : productList) {
            System.out.println(product);
        }
    }
}
