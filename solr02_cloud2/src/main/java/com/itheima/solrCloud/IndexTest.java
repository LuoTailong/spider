package com.itheima.solrCloud;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.util.ArrayList;

public class IndexTest {
    @Test
    public void createIndexTest() throws Exception {
        //1 创建CloudSolrServer对象
        CloudSolrServer cloudSolrServer = new CloudSolrServer("192.168.6.200:2181,192.168.6.201:2181,192.168.6.202:2181");
        //设置默认连接的库
        cloudSolrServer.setDefaultCollection("collection2");
        //连接zookeeper的时间
        cloudSolrServer.setZkConnectTimeout(5000);
        //从zookeeper中获取solr的连接时间
        cloudSolrServer.setZkClientTimeout(5000);

        //2 创建索引
        ArrayList<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        for (int i = 0; i < 5; i++) {
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id", i);
            doc.addField("title", "这是一个solr集群");
            doc.addField("content", "今天要部署solrCloud");
            docs.add(doc);
        }

        cloudSolrServer.add(docs);

        //3 提交
        cloudSolrServer.commit();
    }

    @Test
    public void delIndexTest() throws Exception {
        //1 创建cloudSolrServer对象
        CloudSolrServer cloudSolrServer = new CloudSolrServer("192.168.6.200:2181,192.168.6.201:2181,192.168.6.202:2181");
        //设置默认连接的库
        cloudSolrServer.setDefaultCollection("collection2");
        //设置zookeeper连接时间
        cloudSolrServer.setZkConnectTimeout(5000);
        //设置从zookeeper获取solr连接的时间
        cloudSolrServer.setZkClientTimeout(5000);

        //2 删除索引
        SolrQuery solrQuery = new SolrQuery("*:*");
        QueryResponse response = cloudSolrServer.query(solrQuery);
        SolrDocumentList docs = response.getResults();
        ArrayList<String> ids = new ArrayList<String>();
        for (SolrDocument doc : docs) {
            Object id = doc.get("id");
            ids.add((String) id);
        }
        cloudSolrServer.deleteById(ids);

        //3 提交
        cloudSolrServer.commit();
    }

    @Test
    public void queryTest() throws Exception {
        //1 创建cloudSolrServer对象
        CloudSolrServer cloudSolrServer = new CloudSolrServer("192.168.6.200:2181,192.168.6.201:2181,192.168.6.202:2181");
        //设置默认连接的库
        cloudSolrServer.setDefaultCollection("collection2");
        //设置zookeeper连接时间
        cloudSolrServer.setZkConnectTimeout(5000);
        //设置从zookeeper中获取solr连接的时间
        cloudSolrServer.setZkConnectTimeout(5000);

        //2 查询索引
        SolrQuery solrQuery = new SolrQuery("*:*");
        QueryResponse response = cloudSolrServer.query(solrQuery);
        SolrDocumentList docs = response.getResults();
        for (SolrDocument doc : docs) {
            Object id = doc.get("id");
            Object title = doc.get("title");
            Object content = doc.get("content");
            System.out.println(id + "--" + title + "--" + content);
        }
    }
}
