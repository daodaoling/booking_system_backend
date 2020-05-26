package com.imooc.o2o.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

//import org.eclipse.jdt.internal.compiler.impl.Constant;
//import org.apache.tomcat.util.bcel.classfile.Constant;
import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import com.imooc.o2o.util.copy.Thread4;

import redis.clients.jedis.Jedis;

public class TTTTTT {
	public static void main(String[] args) {
		//Jedis jedis = new Jedis("127.0.0.1");
    	String IP_post="180.117.162.174:6081";
    	//String IP_post="192.168.1.210:81";
    	String order_id="123456789";
    	String URL_post_order="http://"+IP_post+"/api/order/changeStatus";
    	
    	String car_id="LZWADAGA6JB645011";
    	System.out.println(car_id.contains("LZWADAGA"));
    	

    	
    	
    	
    	
		
		
	}

	public static void batchDel_order_run_time(Jedis jedis){
		Set<String> set = jedis.keys("order_run_time" +"*");
		Iterator<String> it = set.iterator();
		while(it.hasNext()){
			String keyStr = it.next();
			System.out.println(keyStr);
			jedis.del(keyStr);
		}
	}

		
		
}
