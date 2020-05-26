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

public class Thread4 extends Thread{  
    //private Jedis jedis;
    private String URL_post_order;
    private String order_id;
    private String app_id;
    private String vehicle;
    private String order_status;
    private String information;
    private String time;

    
    public Thread4(String URL_post_order,String order_id,String app_id,String vehicle
			,String order_status,String information,String time) {  
    	//this.jedis=jedis; 
    	this.URL_post_order=URL_post_order;
    	this.order_id=order_id;
    	this.app_id=app_id;  
    	this.vehicle=vehicle;
    	this.order_status=order_status;
    	this.information=information;
    	this.time=time;
    } 

    public void run() {  
		try {
			RequestUtils.sendOrder_for_severtimes( URL_post_order, order_id, app_id, vehicle, order_status, information, time);
			//jedis.disconnect();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
    	
    }  
}  
