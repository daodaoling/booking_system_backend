package com.imooc.o2o.util.copy;
import java.io.IOException;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import com.imooc.o2o.util.ChatUtil;
import com.imooc.o2o.util.DispatherUtil;
import com.imooc.o2o.util.MessageSend;
import com.imooc.o2o.util.RedisDataUtil;
import com.imooc.o2o.util.RequestUtils;
import com.imooc.o2o.web.superadmin.OrderController;

import redis.clients.jedis.Jedis;

public class Thread5_ extends Thread{  
    //private Jedis jedis;


    
    public Thread5_(Jedis jedis) {  
    	//this.jedis=jedis; 

    } 

    public void run() {
    	String result="";
    	while(true) {
        	long seconds=System.currentTimeMillis()/1000;
        	
        	result=RequestUtils.timeStamp2Date(seconds+"", null);
        	//System.out.println("seconds:"+seconds);
        	//System.out.println("result:"+result);
        	if(result.contains("03:30:00")) {
        		RedisDataUtil.batchDel_order_run_time();
        		//break;
        	}
    		
    	}
    	
    }  
}  
