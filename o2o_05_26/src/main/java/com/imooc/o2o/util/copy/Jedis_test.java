package com.imooc.o2o.util.copy;

import java.util.Iterator;
import java.util.Set;

//import ch.qos.logback.core.net.SyslogOutputStream;
import redis.clients.jedis.Jedis;

public class Jedis_test {
	public static void main(String[] args) {
		 //连接本地的 Redis 服务
        Jedis jedis = new Jedis("127.0.0.1");
        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());
        jedis.set("pengzhao", "liukai");
        //System.out.println("存入的数据为："+jedis.get("pengzhao"));
        jedis.set("chenlanfang", "lihuan");
        Set<String> keys=jedis.keys("*");
        Iterator<String> it=keys.iterator();
        while(it.hasNext()) {
        	String key=it.next();
        	System.out.println("Key:"+key+",value:"+jedis.get(key));
        }
	}
	
	
	
	
	
}
