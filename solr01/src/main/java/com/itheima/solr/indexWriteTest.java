package com.itheima.solr;

import com.itheima.pojo.Product;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.util.ArrayList;


public class indexWriteTest {
    @Test
    public void createIndex2Solr() throws Exception {
        //创建solr对象
        SolrServer solrServer = new HttpSolrServer("http://localhost/solr/collection1");

        //添加document对象
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", 1);
        doc.addField("title", "solr简介");
        doc.addField("content", "简介");

        //添加索引
        solrServer.add(doc);

        //提交索引
        solrServer.commit();
    }

    @Test
    public void createIndexList2Solr() throws Exception {
        //创建solrServer对象
        SolrServer solrServer = new HttpSolrServer("http://localhost/solr/collection1");

        //设置多条数据
        ArrayList<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        for (int i = 0; i < 6; i++) {
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id",i);
            doc.addField("title","简介啊简介");
            doc.addField("content","solr是一个独立的企业级搜索应用服务器，可通过http请求访问这个服务器，获取写入对应的内容，其底层是Lucene+i");

            docs.add(doc);
        }

        //写入索引
        solrServer.add(docs);

        //提交
        solrServer.commit();
    }

    @Test
    public void createIndexJavaBean2Solr() throws Exception {
        //创建solrServer对象
        SolrServer solrServer = new HttpSolrServer("http://localhost/solr/collection1");

        //创建product对象，写入索引
        Product product = new Product();
        product.setId("10");
        product.setName("iphone X 256G");
        product.setTitle("炫酷");
        product.setPrice(8888L);
        product.setContent("一天一个苹果");

        //写入索引
        solrServer.addBean(product);

        //提交索引
        solrServer.commit();
    }

    @Test
    public void delTest() throws Exception {
        //创建solServer对象
        SolrServer solrServer = new HttpSolrServer("http://localhost/solr/collection1");

        //执行删除
//        solrServer.deleteById("10");
        solrServer.deleteByQuery("*:*");

        //提交
        solrServer.commit();
    }

}
