package com.itheima.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtils {
    private static JedisPool jedisPool;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);//最多有100个
        config.setMaxIdle(5);//闲时最大连接数
        config.setMinIdle(3);//闲时最小连接数
        jedisPool = new JedisPool("192.168.6.201",6379);
    }

    public static Jedis getJedis(){
        return jedisPool.getResource();
    }
}
