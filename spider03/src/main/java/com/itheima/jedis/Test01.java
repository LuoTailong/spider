package com.itheima.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

public class Test01 {
    @Test
    public void jedisOne(){
        Jedis jedis = new Jedis("192.168.6.200",6379);
        jedis.del("bigData:spider:jdspider:pid");
        String pong = jedis.ping();
        Long llen = jedis.llen("bigData:spider:jdspider:pid");
        System.out.println(llen);
        jedis.close();
    }
}
