package com.itheima.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class IndexWriterTest {
    public static void main(String[] args) throws IOException {
        //1 需要创建indexWriter对象
        //1.1 创建索引库
        FSDirectory directory = FSDirectory.open(new File("C:\\Users\\Administrator\\Desktop\\test"));
        //1.2 创建写入器配置对象：参数1 版本号，参数2 分词器
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, new IKAnalyzer());
//        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);//默认就是这种
        IndexWriter indexWriter = new IndexWriter(directory, config);
        //2 写入文档
        //2.1 创建文档对象
        ArrayList<Document> docs = new ArrayList<Document>();
        for (int i = 2; i < 11; i++) {
            Document doc = new Document();
            //2.2 添加文档的属性
            doc.add(new LongField("id", i, Field.Store.YES));
            doc.add(new StringField("title", "Lucene介绍", Field.Store.YES));
            doc.add(new TextField("content", "Lucene是一个全文检索的工具包, 官宣, 扎心了老铁", Field.Store.YES));
            docs.add(doc);
        }
//        indexWriter.addDocument(doc);
        //添加多条索引
        indexWriter.addDocuments(docs);


        //3 提交文档
        indexWriter.commit();
        //4 释放资源
        indexWriter.close();
        directory.close();
    }
}
