package com.itheima.solrCloud;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class IndexTest {
    @Test
    public void createIndex2SolrCloud() throws Exception {
        //1 创建连接solrCloud的服务对象
        //String zkHost:需要传递zookeeper集群的地址
        String zkHost = "192.168.6.200:2181,192.168.6.201:2181,192.168.6.202:2181";
        CloudSolrServer cloudSolrServer = new CloudSolrServer(zkHost);

        //2 设置连接那个solr的索引库
        cloudSolrServer.setDefaultCollection("collection2");

        //3 可选的参数
        // 设置连接zookeeper的时间
        cloudSolrServer.setZkClientTimeout(5000);
        // 设置获取和solr的连接时间
        cloudSolrServer.setZkConnectTimeout(5000);
        // 获取连接
        cloudSolrServer.connect();

        //4 添加索引操作
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", 1);
        doc.addField("title", "学生");
        doc.addField("content", "学生听老师讲solrCloud");

        cloudSolrServer.add(doc);
        //5 提交
        cloudSolrServer.commit();

    }

    @Test
    public void delIndex2SolrCloud() throws Exception {
        //1 创建连接solrCloud的服务对象
        CloudSolrServer cloudSolrServer = new CloudSolrServer("192.168.6.200:2181,192.168.6.201:2181,192.168.6.202:2181");

        //2 设置连接那个solr的索引库
        cloudSolrServer.setDefaultCollection("collection2");

        //3 可选的参数
        //设置连接zookeeper的时间
        cloudSolrServer.setZkClientTimeout(5000);
        //设置获取和solr的连接时间
        cloudSolrServer.setZkConnectTimeout(5000);
        //获取连接
        cloudSolrServer.connect();

        //4 删除索引
        cloudSolrServer.deleteById("1");

        //5 提交
        cloudSolrServer.commit();
    }

    @Test
    public void queryIndex2SolrCloud() throws Exception {
        //1 创建连接solrCloud的服务对象
        //String zkHost:需要传递zookeeper的地址
        String zkHost = "192.168.6.200:2181,192.168.6.201:2181,192.168.6.202:2181";
        CloudSolrServer cloudSolrServer = new CloudSolrServer(zkHost);

        //2 设置连接那个solr的索引库
        cloudSolrServer.setDefaultCollection("collection2");

        //3 可选参数
        //设置连接zookeeper的时间
        cloudSolrServer.setZkClientTimeout(5000);
        //设置获取和solr的连接时间
        cloudSolrServer.setZkConnectTimeout(5000);
        //获取连接
        cloudSolrServer.connect();

        //4 查询索引
        SolrQuery solrQuery = new SolrQuery("*:*");
        QueryResponse response = cloudSolrServer.query(solrQuery);
        SolrDocumentList documentList = response.getResults();
        for (SolrDocument doc : documentList) {
            Object id = doc.get("id");
            Object title = doc.get("title");
            Object content = doc.get("content");
            System.out.println(id + "---" + title + "---" + content);
        }

    }
}
