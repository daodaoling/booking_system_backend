package com.imooc.o2o.util;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.jivesoftware.smack.XMPPConnection;

import redis.clients.jedis.Jedis;

public class JedisConn_ {

    private static volatile Jedis instance = null;
    public static volatile int a=0;
    public static ConcurrentLinkedQueue<String> queue;
    private JedisConn_() {
    	
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

