package jtest;

import redis.clients.jedis.Jedis;

public class RedisTest {
    public  static  void main(String [] args){
        Jedis jedis = new Jedis("127.0.0.1");
        jedis.auth("123456");
        jedis.set("key1","value2s");
        jedis.close();
        System.out.println("OKss");

    }
}
