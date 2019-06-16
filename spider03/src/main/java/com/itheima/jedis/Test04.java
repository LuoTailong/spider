package com.itheima.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Test04 {
    @Test
    public void jedisOfHash(){
        Jedis jedis = new Jedis("192.168.6.200",6379);

        //添加数据
        jedis.hset("person","name","隔壁老王");
        jedis.hset("person","age","30");
        jedis.hset("person","birthday","1933年5月3日");

        //获得数据
        String name = jedis.hget("person", "name");
        String age = jedis.hget("person", "age");
        String birthday = jedis.hget("person", "birthday");

        //一次获取
        List<String> values = jedis.hmget("person", "name", "age", "birthday");
        System.out.println(values);

        //获取hash所有数据包
        Map<String, String> map = jedis.hgetAll("person");
        for (String key : map.keySet()) {
            System.out.println(key+"---"+map.get(key));
        }

        //获取map中所有key和所有value
        Set<String> hkeys = jedis.hkeys("person");
        List<String> hvals = jedis.hvals("person");
        System.out.println(hkeys);
        System.out.println(hvals);

        //删除name和age
        jedis.hdel("person","name","age");
        //删除map
        jedis.del("person");

        //释放资源
        jedis.close();
    }
}
