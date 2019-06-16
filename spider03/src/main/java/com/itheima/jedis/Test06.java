package com.itheima.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Set;

public class Test06 {
    @Test
    public void jedisOfsortedSet() {
        Jedis jedis = new Jedis("192.168.6.200", 6379);
        jedis.zadd("math", 98.2, "老赵");
        jedis.zadd("math", 93.4, "老钱");
        jedis.zadd("math", 95.2, "老孙");
        jedis.zadd("math", 96.2, "老李");

        Double zscore = jedis.zscore("math", "老李");
        System.out.println(zscore);

        //查看某一元素的排名
        Long zrank = jedis.zrank("math", "老李");
        System.out.println(zrank);

        //查看集合中的元素：从大到小
        Set<Tuple> zrevrange = jedis.zrevrangeWithScores("math", 0, -1);
        for (Tuple tuple : zrevrange) {
            System.out.println(tuple.getElement() + "---" + tuple.getScore());
        }

        //查看集合中的元素：从小到大
        Set<Tuple> zrange = jedis.zrangeWithScores("math", 0, -1);
        for (Tuple tuple : zrange) {
            System.out.println(tuple.getElement() + "---" + tuple.getScore());
        }

        //删除某个元素
        jedis.zrem("math","老赵");

        jedis.close();
    }
}
