package com.itheima.lucene;

import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class IndexWriterUpdateTest {
    @Test
    public void update() throws IOException {
        //本质：先将匹配到的数据全部删除，然后添加新数据
        //如果是updateDocument，删除后添加一条
        //如果是updateDocuments，删除后将list集合中的数据添加到索引中
        //1 创建indexWriter对象
        FSDirectory directory = FSDirectory.open(new File("C:\\Users\\Administrator\\Desktop\\test"));
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, new IKAnalyzer());
        IndexWriter indexWriter = new IndexWriter(directory, config);

        //2 执行索引的修改
        Document doc = new Document();
        doc.add(new LongField("id",11L, Field.Store.YES));
        doc.add(new StringField("title","Lucene简介", Field.Store.NO));
        doc.add(new TextField("content","Lucene是一个全文检索的工具包，使用Lucene可以构建一个全文检索搜索引擎，修改数据", Field.Store.YES));

        //参数1：修改的内容 参数2：修改成什么内容
        indexWriter.updateDocument(new Term("content","扎心了老铁"),doc);

        //3 提交
        indexWriter.commit();

        //4 关闭写入器的对象
        indexWriter.close();

    }
}
