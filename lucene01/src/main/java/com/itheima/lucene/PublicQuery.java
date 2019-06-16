package com.itheima.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import java.io.File;

public class PublicQuery {
    public static void query(Query query) throws Exception {
        //1 创建查询核心对象
        DirectoryReader reader = DirectoryReader.open(FSDirectory.open(new File("C:\\Users\\Administrator\\Desktop\\test")));
        IndexSearcher indexSearcher = new IndexSearcher(reader);
        //2 执行查询
        TopDocs topDocs = indexSearcher.search(query,Integer.MAX_VALUE);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            //获取文档id
            int docId = scoreDoc.doc;
            //获取文档得分
            float score = scoreDoc.score;
            //根据id获取文档
            Document doc = indexSearcher.doc(docId);
            String content = doc.get("content");
            String title = doc.get("title");
            System.out.println("文档得分为: "+score+"  "+content+"  "+title);
        }
    }
}
