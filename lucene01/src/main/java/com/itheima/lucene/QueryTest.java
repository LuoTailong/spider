package com.itheima.lucene;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.junit.Test;

public class QueryTest {
    @Test
    public void queryTest() throws Exception {
        //1 词条查询    用来查询不需要分词的数据
        //词条特点：词条是一个不可再分割的单位，词条看作是文档分词后的关键字
        //词条不允许有错误，否则查不到
        TermQuery termQuery = new TermQuery(new Term("content", "扎心了老铁"));
//        PublicQuery.query(termQuery);

        //2 通配符查询
        //?: 占用一个字符 类似于mysql中like的查询_
        //*: 占用0到多个字符 类似于mysql中的like的查询%
        //? * 可以在任意位置使用 但是_ %只能用于mysql的末尾
        WildcardQuery wildcardQuery = new WildcardQuery(new Term("content", "扎?了?*"));
//        PublicQuery.query(wildcardQuery);

        //3 模糊查询
        //模糊查询只能在2次范围内能够查询到对应的词条：补位 替换 移动
        //可以自定义最大编辑次数：0~2次  默认为2
        FuzzyQuery fuzzyQuery = new FuzzyQuery(new Term("content", "信札了老铁"));
//        PublicQuery.query(fuzzyQuery);

        //4 数值范围查询
        //默认的字段：final String field
        //最小值：Long min
        //最大值：Long max,
        //是否包含最小值：final boolean minInclusive
        //是否包含最大值：final boolean maxInclusive4
        NumericRangeQuery<Long> numericRangeQuery = NumericRangeQuery.newLongRange("id", 1L, 15L, true, true);
        PublicQuery.query(numericRangeQuery);

        //5 组合查询    没有任何的查询条件
        BooleanQuery booleanQuery = new BooleanQuery();
        booleanQuery.add(numericRangeQuery, BooleanClause.Occur.MUST);//此条件必须存在
        booleanQuery.add(fuzzyQuery, BooleanClause.Occur.SHOULD);//可选的
        booleanQuery.add(termQuery,BooleanClause.Occur.MUST_NOT);//不可能出现这个条件的内容
        PublicQuery.query(booleanQuery);
    }
}
