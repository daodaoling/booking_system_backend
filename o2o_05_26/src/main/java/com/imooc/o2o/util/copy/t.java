package com.imooc.o2o.util.copy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class t {
	public static void main(String[] args) {
		
		//Map<String,String> map = Maps.newHashMap();
		Map<String,String> map = new HashMap();
		Jedis jedis = new Jedis("127.0.0.1");
		map.put("order_id","12345");
		String str="{\"speed\":\"5\",\"location\":\"(25.0,12.4)\",\"heartbeat\":\"1223223\"}";
		map.put("vechicle_information",str);
		//map.put("Memphis Grizzlies","Mike Conley");
		//map.put("Miami Heat","Goran Dragic");
		//map.put("Golden State Warriors","Stephen Curry");
		//设置值
		//jedis.hmset("NBA",map);
		jedis.hmset("vechicle_id_001",map);
		//根据map当中的key，去查询key对应的value
		List<String> order_id = jedis.hmget("vechicle_id_001", "order_id");
		List<String> vechicle_information = jedis.hmget("vechicle_id_001", "vechicle_information");
		System.out.println("order_id:"+order_id);
		System.out.println("vechicle_information:"+vechicle_information);
		jedis.hdel("vechicle_id_001", "vechicle_information");
		jedis.hdel("vechicle_id_001", "order_id");
		System.out.println("order_id:"+jedis.hmget("vechicle_id_001", "order_id"));
		System.out.println("vechicle_information:"+jedis.hmget("vechicle_id_001", "vechicle_information"));
		
		//删除某个key对应的value
		//jedis.hdel("NBA","Miami Heat");
		//返回NBA当中map的键值对的数量
		//jedis.hlen("NBA");
		//是否存在NBA这个key
		//jedis.exists("NBA");
		//返回NBA当中map的所有的key
		//Set<String> teams = jedis.hkeys("NBA");
		//返回NBA当中所有的key对应value的值
		//List<String> players = jedis.hvals("NBA");
		//返回NBA当中对应的map的值
		//Map<String, String> map1 = jedis.hgetAll("NBA");
		
	}
}

