package com.imooc.o2o.util;

import redis.clients.jedis.Jedis;

public class JedisUtil {
	public  static Jedis  jedisConnection(String ip) {
		Jedis jedis = new Jedis(ip);
		return jedis;
	}
	
}
