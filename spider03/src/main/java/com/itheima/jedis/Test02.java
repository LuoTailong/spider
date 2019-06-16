package com.itheima.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

public class Test02 {
    @Test
    public void jedisOfString() throws InterruptedException {
        Jedis jedis = new Jedis("192.168.6.200", 6379);
        jedis.set("age","10");
        jedis.set("age1","11");

        String age = jedis.get("age");
        System.out.println(age);

        jedis.del("age");

        Long incr = jedis.incr("age1");
        System.out.println(incr);

        Long decr = jedis.decr("age1");
        System.out.println(decr);

        jedis.set("hobby","6");

        //为已有的key设置时间
        Long expire = jedis.expire("hobby", 5);
        System.out.println(expire);//返回1表示设置成功，返回0表示key不存在
        while (jedis.exists("hobby")){
            Thread.sleep(1000);
            System.out.println(jedis.ttl("hobby"));//返回-1表示永久有效，返回-2表示key不存在
        }

        //为新建的key设置时间
        jedis.setex("date",5,"2018.9.15");
        while (jedis.exists("date")){
            System.out.println(jedis.ttl("date"));
            Thread.sleep(1000);
        }

        jedis.close();
    }
}
