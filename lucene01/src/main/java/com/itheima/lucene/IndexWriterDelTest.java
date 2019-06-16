package com.itheima.lucene;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class IndexWriterDelTest {
    @Test
    public void delTest() throws IOException {
        //1 创建indexWriter对象
        FSDirectory directory = FSDirectory.open(new File("C:\\Users\\Administrator\\Desktop\\test"));
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, new IKAnalyzer());
        IndexWriter indexWriter = new IndexWriter(directory, config);

        //2 执行删除
//        indexWriter.deleteAll();
        indexWriter.deleteDocuments(new Term("content","修改"));

        //3 提交
        indexWriter.commit();

        //4 关闭
        indexWriter.close();
    }
}
