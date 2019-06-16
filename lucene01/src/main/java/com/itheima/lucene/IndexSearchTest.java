package com.itheima.lucene;


import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;

public class IndexSearchTest {
    @Test
    public void indexSearch() throws Exception {
        //1 创建查询的核心对象
        DirectoryReader reader = DirectoryReader.open(FSDirectory.open(new File("C:\\Users\\Administrator\\Desktop\\test")));
        IndexSearcher indexSearcher = new IndexSearcher(reader);

        //2 执行查询
//        QueryParser queryParser = new QueryParser("content", new IKAnalyzer());//一个条件
        QueryParser queryParser = new MultiFieldQueryParser(new String[]{"content", "title"}, new IKAnalyzer());
        Query query = queryParser.parse("全文检索");
        // TopDocs: 第一部分: 查询的总条数  第二部分: 文档的数组
        TopDocs topDocs = indexSearcher.search(query, Integer.MAX_VALUE);//Integer.MAX_VALUE代表查询全部
        int totalHits = topDocs.totalHits;//总条数

        //3 获取文档id
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;//获取得分文档集合
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;//获取文档ID
            float score = scoreDoc.score;//返回此文档得分
            Document doc = indexSearcher.doc(docId);
            String id = doc.get("id");
            String content = doc.get("content");
            String title = doc.get("title");
            System.out.println(id + "  " + content + " " + "得分为：" + score + "    " + title);
        }
    }
}
