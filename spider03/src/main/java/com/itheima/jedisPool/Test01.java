package com.itheima.jedisPool;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Test01 {
    @Test
    public void jedisPoolOfTest(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        JedisPool jedisPool = new JedisPool(poolConfig, "192.168.6.200", 6379);
        Jedis jedis = jedisPool.getResource();
        String pong = jedis.ping();
        System.out.println(pong);
        jedis.close();
    }
}
