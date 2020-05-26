package com.imooc.o2o.util;
/*
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import redis.clients.jedis.Jedis;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.jivesoftware.smack.XMPPConnection;

import redis.clients.jedis.Jedis;

class JedissConn {

    private static volatile Jedis instance = null;
    public static volatile int a=0;
    public static ConcurrentLinkedQueue<String> queue;
    private JedissConn() {
    	
    }

    //运行时加载对象
    public static synchronized Jedis getInstance() {
        if (instance == null) {
            synchronized(Jedis.class){
                 if(instance == null){
    				String content=" jedis isntance  null ,new jedis";
    				Log_Steps le=new Log_Steps();
    		        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
            		instance=new Jedis(Config.redisIP);
                 }
            }
        }
        return instance;
    }
    public static synchronized int getA() {
       
            synchronized(XmppConnet.class){
                 a=a+1;
                 System.out.println(a);
                 queue.add(a+"----");
                 //System.out.println(queue);
                 return a;
            }
    }
    
    public static synchronized ConcurrentLinkedQueue<String> addElem(String car_id) {
        
        synchronized(XmppConnet.class){
             queue.add(car_id);
             System.out.println(queue);
             return queue;
        }
    }
    public static synchronized ConcurrentLinkedQueue<String> delElem(String order_id) {
        
        synchronized(XmppConnet.class){
             
             if(queue.peek().equals(order_id)) {
            	 queue.poll();
             }
             System.out.println(queue);
             return queue;
        }
    }
    
}


public class Main {

    public static void main(String[] args) throws IOException {

    	Jedis jedis = new Jedis("127.0.0.1");
    	//jedis.set("123", "321");
    	//String car_id="LZWADAGA0KB998183";
    	
    	//System.out.println("SL".equals(car_id.substring(0, 2)));
    	//System.out.println("LZWADAGA".equals(car_id.substring(0, 8)));
    	String order_id="6551633635ead18aeec15c5075846733";
    	String car_id=getOrderVehicle( order_id);
    	System.out.println(car_id);

    }
	public static String getOrderVehicle(String order_id) {
		Jedis jedis =JedissConn.getInstance();
		List<String> order_vehicle=null;
		try {
			order_vehicle = jedis.hmget("order_vehicle", order_id);
			return order_vehicle.get(0);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
		
	}
}
*/