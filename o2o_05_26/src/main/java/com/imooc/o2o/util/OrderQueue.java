package com.imooc.o2o.util;

import java.util.concurrent.ConcurrentLinkedQueue;

public class OrderQueue {

    private static volatile OrderQueue huanggang_instance = null;
    private static volatile OrderQueue nanning_instance = null;
    private static volatile OrderQueue nanning_cw_instance = null;
    private static volatile OrderQueue guangzhou_instance = null;
    
    public static ConcurrentLinkedQueue<String> huanggangqueue;
    public static ConcurrentLinkedQueue<String> nanningqueue;
    public static ConcurrentLinkedQueue<String> nanning_cwqueue;
    public static ConcurrentLinkedQueue<String> guangzhouqueue;
    
    public static boolean nanning_queue_flag=false;
    public static boolean nanning_cw_queue_flag=false;
    public static String area_code;
    private OrderQueue() {
    	
    }

    //运行时加载对象
    public static synchronized OrderQueue getHuangGangInstance() {
    	return null;
    }
   
    public static synchronized OrderQueue getGuangZhouInstance() {
        return null;
    }
    
    
    
    
    
    public static synchronized OrderQueue getNanNingInstance() {
        if (nanning_instance == null) {
            synchronized(OrderQueue.class){
                 if(nanning_instance == null){
                	 nanning_instance = new OrderQueue();
                	 nanning_instance.nanningqueue=new ConcurrentLinkedQueue<String>();
                 }
            }
        }
        return nanning_instance;
    }
    
    public static synchronized OrderQueue getNanNingCwInstance() {
        if (nanning_cw_instance == null) {
            synchronized(OrderQueue.class){
                 if(nanning_cw_instance == null){
                	 nanning_cw_instance = new OrderQueue();
                	 nanning_cw_instance.nanning_cwqueue=new ConcurrentLinkedQueue<String>();
                 }
            }
        }
        return nanning_cw_instance;
    }
    
    
    
    
}
