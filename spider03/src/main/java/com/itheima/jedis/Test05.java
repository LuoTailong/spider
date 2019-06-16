package com.itheima.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class Test05 {
    @Test
    public void jedisOfSet(){
        Jedis jedis = new Jedis("192.168.6.200",6379);
        jedis.sadd("set1","a","b","c","d");
        jedis.sadd("set2","a","e","f","d");

        //获取数据
        Set<String> set1 = jedis.smembers("set1");
        System.out.println(set1);

        //删除指定元素b，d
        jedis.srem("set1","b","d");

        //判断某个元素在set集合中是否存在
        Boolean is = jedis.sismember("set1", "b");
        System.out.println(is);

        //求两个set的集合[交：sinter，并：sunion，差：sdiff]
        Set<String> sinter = jedis.sinter("set1", "set2");//求交集
        System.out.println(sinter);

        //查看set集合有多少元素
        Long size = jedis.scard("set1");
        System.out.println(size);

        jedis.close();
    }
}
