package com.itheima.jedis;

import org.junit.Test;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;

import java.util.List;

public class Test03 {
    @Test
    public void jedisOfList() {
        Jedis jedis = new Jedis("192.168.6.200", 6379);
        jedis.del("list1");
        jedis.del("list2");

        //从左侧添加
        jedis.lpush("list1", "a", "b", "c", "d", "e");//已有数据往右顶
        //从右侧弹出数据
        String rElement = jedis.rpop("list1");
        System.out.println(rElement);

        //从右侧添加
        jedis.rpush("list2", "a", "b", "c", "d", "e");//已有数据往左顶
        //从左侧弹出数据
        String lElement = jedis.lpop("list2");
        System.out.println(lElement);

        //查看某个范围的数据
        List<String> list = jedis.lrange("list1", 0, -1);
        System.out.println(list);

        //获取元素个数
        Long llen = jedis.llen("list2");
        System.out.println(llen);

        //在b元素前插入0
        jedis.linsert("list1", BinaryClient.LIST_POSITION.BEFORE, "b", "0");
        list = jedis.lrange("list1", 0, -1);
        System.out.println(list);
        //在c元素前插入1
        jedis.linsert("list1", BinaryClient.LIST_POSITION.AFTER, "c", "1");
        list = jedis.lrange("list1", 0, -1);
        System.out.println(list);

        //将最后一个弹出并将其添加某个list的头部(自己也可以)
        jedis.rpoplpush("list1", "list2");
        list = jedis.lrange("list1", 0, -1);
        System.out.println(list);
        list = jedis.lrange("list2", 0, -1);
        System.out.println(list);
        jedis.close();

    }
}
