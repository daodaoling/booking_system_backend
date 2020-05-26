package com.imooc.o2o.util.copy;

import com.imooc.o2o.util.Config;
import com.imooc.o2o.util.Log_Steps;
import com.imooc.o2o.util.OrderQueue;
import com.imooc.o2o.util.RedisDataUtil;

import redis.clients.jedis.Jedis;

public class Thread7  extends Thread{
    private String order_id;
    private String from_station;
    private String to_station;
    private String order_source;
    private String area_code;
    public Thread7(String order_id,String from_station,String to_station,String order_source, String area_code) {  
    	this.order_id=order_id; 
    	this.from_station=from_station; 
    	this.to_station=to_station;
    	this.order_source=order_source;
    	this.area_code=area_code;
    } 

    public void run() {
    	
		OrderQueue guangzhou_instance=null;
		OrderQueue nanning_instance = null;
		OrderQueue nanning_cw_instance = null;
		long start_time=System.currentTimeMillis();
		int times=0;

			if("guangzhou".equals(area_code)) {

			}else if("nanning_cw".equals(area_code)) {

				nanning_cw_instance=OrderQueue.getNanNingCwInstance();
				
				if(nanning_cw_instance.nanning_cw_queue_flag) {
					RedisDataUtil.setOrderOnOff(order_id, "311");
					RedisDataUtil.setOrderStatus(order_id, "311");
					Thread4 thread4=new Thread4( Config.orderChangeStatusURL, order_id, "centerControlSystem", null, "311", null, null);
					thread4.start();
					System.out.println("nanning_cw_instance.nanning_cw_queue_flag:"+nanning_cw_instance.nanning_cw_queue_flag);
					String content="10 second passed ,but I find no car to dispatch ,so I send 311 to FangYao";
					Log_Steps le=new Log_Steps();
			        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
			        le=null;

			        
				}else {
					System.out.println("nanning_cw_queue_flag"+nanning_cw_instance.nanning_cw_queue_flag);
					nanning_cw_instance.nanning_cw_queue_flag=true;
					RedisDataUtil.setOrderQueue( area_code, nanning_cw_instance.nanning_cwqueue.toString());
			        Thread1 mTh1=new Thread1(order_id,from_station,to_station,order_source,area_code);  
			        mTh1.start();
			        
			        //System.out.println("nanning_queue_flag:"+nanning_instance.nanning_queue_flag);
			        
				}
		
			}else if("nanning".equals(area_code)) {
				nanning_instance=OrderQueue.getNanNingInstance();
				
				if(nanning_instance.nanning_queue_flag) {
					RedisDataUtil.setOrderOnOff(order_id, "311");
					RedisDataUtil.setOrderStatus(order_id, "311");
					Thread4 thread4=new Thread4( Config.orderChangeStatusURL, order_id, "centerControlSystem", null, "311", null, null);
					thread4.start();
					System.out.println("nanning_instance.nanning_queue_flag:"+nanning_instance.nanning_queue_flag);
					String content="10 second passed ,but I find no car to dispatch ,so I send 311 to FangYao";
					Log_Steps le=new Log_Steps();
			        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
			        le=null;
			        
				}else {
					System.out.println("nanning_queue_flag"+nanning_instance.nanning_queue_flag);
					nanning_instance.nanning_queue_flag=true;
					RedisDataUtil.setOrderQueue( area_code, nanning_instance.nanningqueue.toString());
			        Thread1 mTh1=new Thread1(order_id,from_station,to_station,order_source,area_code);  
			        mTh1.start();
			        
			        //System.out.println("nanning_queue_flag:"+nanning_instance.nanning_queue_flag);
			        
				}


			
		}
    }
}
