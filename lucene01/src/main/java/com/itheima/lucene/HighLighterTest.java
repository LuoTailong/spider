package com.itheima.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;

public class HighLighterTest {
    public static void main(String[] args) throws Exception {
        //1 创建查询器索引对象
        DirectoryReader reader = DirectoryReader.open(FSDirectory.open(new File("C:\\Users\\Administrator\\Desktop\\test")));
        IndexSearcher indexSearcher = new IndexSearcher(reader);

        //2 执行查询
        MultiFieldQueryParser queryParser = new MultiFieldQueryParser(new String[]{"content", "title"}, new IKAnalyzer());
        Query query = queryParser.parse("全文检索");

        //高亮设置start
        SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
        QueryScorer scorer = new QueryScorer(query);
        Highlighter highlighter = new Highlighter(formatter, scorer);
        //高亮设置end

        //TopDocs：第一部分：查询总条数    第二部分：文档的数组
        TopDocs topDocs = indexSearcher.search(query, Integer.MAX_VALUE);
        int totalHits = topDocs.totalHits;
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;
            float score = scoreDoc.score;

            Document doc = indexSearcher.doc(docId);
            String content = doc.get("content");
            String title = doc.get("title");
            String id = doc.get("id");
            
            //获取高亮start
            content = highlighter.getBestFragment(new IKAnalyzer(), "content", content);
            //获取高亮end

            System.out.println("得分数："+score+"---"+id+"---"+title+"---"+content);
        }
    }
}
