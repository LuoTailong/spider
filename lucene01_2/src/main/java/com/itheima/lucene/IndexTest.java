package com.itheima.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.util.ArrayList;

public class IndexTest {
    @Test
    public void indexWriteTest() throws Exception {
        //1 创建indexWriter
        FSDirectory directory = FSDirectory.open(new File("C:\\Users\\Administrator\\Desktop\\test"));
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, new IKAnalyzer());
        IndexWriter indexWriter = new IndexWriter(directory, config);

        //2 添加索引
        ArrayList<Document> docs = new ArrayList<Document>();
        for (int i = 0; i < 15; i++) {
            Document doc = new Document();
            doc.add(new LongField("id", i, Field.Store.YES));
            doc.add(new StringField("title", "Lucene简介", Field.Store.YES));
            doc.add(new TextField("content", "lucene是一个全文检索的工具包，使用lucene可以创建一个全文检索的搜索引擎，官宣，扎心了老铁", Field.Store.YES));

            docs.add(doc);
        }
        indexWriter.addDocuments(docs);

        //3 提交
        indexWriter.commit();

        //4 关闭
        indexWriter.close();
    }

    @Test
    public void indexSearchTest() throws Exception {
        //1 创建indexSearcher
        DirectoryReader reader = DirectoryReader.open(FSDirectory.open(new File("C:\\Users\\Administrator\\Desktop\\test")));
        IndexSearcher indexSearcher = new IndexSearcher(reader);

        //2 执行查询
        QueryParser queryParser = new QueryParser("content", new IKAnalyzer());
        Query query = queryParser.parse("官宣");

        //3 提交
        //topDocs: 第一部分：总结果数    第二部分：文档数组
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
            System.out.println("得分数：" + score + "---" + id + "---" + title + "---" + content);
        }
    }

    public void publicQuery(Query query) throws Exception {
        //1 创建indexSearcher
        DirectoryReader reader = DirectoryReader.open(FSDirectory.open(new File("C:\\Users\\Administrator\\Desktop\\test")));
        IndexSearcher indexSearcher = new IndexSearcher(reader);

        //3 提交
        //topDocs: 第一部分：总结果数    第二部分：文档数组
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
            System.out.println("得分数：" + score + "---" + id + "---" + title + "---" + content);
        }
    }

    @Test
    public void QueryTest() throws Exception {
        //词条查询：用来查询不需要分词的数据
        //特点：词条是一个不可再分割的数据，不能写错，否则查不到
        TermQuery termQuery = new TermQuery(new Term("content", "扎心了老铁"));
        //publicQuery(termQuery);

        //通配符查询
        //?：表示代替一个字符，类似于mysql中like的查询_
        //*：表示代替0个/多个字符，类似于mysql中like的查询%
        //和mysql不同的是通配符?/*可以放在任意位置，而mysql只能放在末尾
        WildcardQuery wildcardQuery = new WildcardQuery(new Term("content", "?*老铁"));
        //publicQuery(wildcardQuery);

        //模糊查询
        //模糊查询只能在2次范围内编辑：补位，替换，移动
        //可以自定义编辑次数：0~2次，默认为2次
        FuzzyQuery fuzzyQuery = new FuzzyQuery(new Term("content", "扎心大铁"));
        publicQuery(fuzzyQuery);

        //数之范围查询
        //String field: 默认查询字段
        //Long min: 最小值
        //Long max: 最大值
        //boolean minInclusive:是否包含最小值
        //boolean maxInclusive:是否包含最大值
        NumericRangeQuery<Long> numericRangeQuery = NumericRangeQuery.newLongRange("id", 1L, 5L, false, true);
        //publicQuery(numericRangeQuery);

        //组合查询
        //本身没有任何查询条件
        BooleanQuery booleanQuery = new BooleanQuery();
        booleanQuery.add(termQuery, BooleanClause.Occur.MUST_NOT);
        booleanQuery.add(wildcardQuery, BooleanClause.Occur.SHOULD);
        booleanQuery.add(numericRangeQuery, BooleanClause.Occur.MUST);
//        publicQuery(booleanQuery);
    }

    @Test
    public void updateTest() throws Exception {
        //本质：先把匹配到的删除，然后添加新的数据
        //updateDocument:进行删除后添加一条数据
        //updateDocument:进行删除后添加list中的数据
        //1 创建indexWriter
        FSDirectory directory = FSDirectory.open(new File("C:\\Users\\Administrator\\Desktop\\test"));
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, new IKAnalyzer());
        IndexWriter indexWriter = new IndexWriter(directory, config);

        //2 执行修改
        ArrayList<Document> docs = new ArrayList<Document>();
        for (int i = 0; i <= 7; i++) {
            Document doc = new Document();
            doc.add(new LongField("id", i, Field.Store.YES));
            doc.add(new StringField("title", "Lucene", Field.Store.YES));
            doc.add(new TextField("content", "我爱大数据", Field.Store.YES));

            docs.add(doc);
        }
        indexWriter.updateDocuments(new Term("content", "扎心了老铁"), docs);

        //3 提交
        indexWriter.commit();

        //4 关闭
        indexWriter.close();
    }

    @Test
    public void delTest() throws Exception {
        //1 创建indexWriter
        FSDirectory directory = FSDirectory.open(new File("C:\\Users\\Administrator\\Desktop\\test"));
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, new IKAnalyzer());
        IndexWriter indexWriter = new IndexWriter(directory, config);

        //2 执行
        indexWriter.deleteAll();

        //3 提交
        indexWriter.commit();

        //4 关闭
        indexWriter.close();
    }

    @Test
    public void highlighterTest() throws Exception {
        //1 创建indexSearcher
        DirectoryReader reader = DirectoryReader.open(FSDirectory.open(new File("C:\\Users\\Administrator\\Desktop\\test")));
        IndexSearcher indexSearcher = new IndexSearcher(reader);

        //2 执行查询
        QueryParser queryParser = new QueryParser("content", new IKAnalyzer());
        Query query = queryParser.parse("全文检索");

        //高亮start
        SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<font color=red>", "</font>");
        QueryScorer scorer = new QueryScorer(query);
        Highlighter highlighter = new Highlighter(formatter, scorer);
        //高亮end

        TopDocs topDocs = indexSearcher.search(query, Integer.MAX_VALUE);
        int totalHits = topDocs.totalHits;
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docID = scoreDoc.doc;
            float score = scoreDoc.score;
            Document doc = indexSearcher.doc(docID);
            String content = doc.get("content");
            String title = doc.get("title");
            String id = doc.get("id");

            //获取高亮start
            content = highlighter.getBestFragment(new IKAnalyzer(), "content", content);
            //获取高亮end
            System.out.println("得分数为：" + "---" + score + "---" + id + "---" + title + "---" + content);
        }
    }
}
