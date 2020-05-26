package com.imooc.o2o.util.copy;

import java.io.IOException;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import com.imooc.o2o.util.AcceptInfo;
import com.imooc.o2o.util.ChatUtil;
import com.imooc.o2o.util.Config;
import com.imooc.o2o.util.DispatherUtil;
import com.imooc.o2o.util.Log_Steps;
import com.imooc.o2o.util.MessageSend;
import com.imooc.o2o.util.OrderQueue;
import com.imooc.o2o.util.RedisDataUtil;
import com.imooc.o2o.util.RequestUtils;
import com.imooc.o2o.util.XmppConnet;

import redis.clients.jedis.Jedis;

public class Thread1 extends Thread{  
    private String order_id;
    private String from_station;
    private String to_station;
    private String order_source;
    private String area_code;
    public Thread1(String order_id,String from_station,String to_station,String order_source, String area_code) {  
    	this.order_id=order_id; 
    	this.from_station=from_station; 
    	this.to_station=to_station;
    	this.order_source=order_source;
    	this.area_code=area_code;
    } 

    public void run() {
    	
		//计算应该派哪辆车，无车可派则返回null
		DispatherUtil dispatherutil=new DispatherUtil();
		String car_id=dispatherutil.vehDispather(area_code);
		RedisDataUtil.delOrderQueue(area_code);
		//计算结束后先存储车信息
		String order_run_time="1";
		//如果无车可派则通知后台，                   如果有车可派则将订单信息存入redis，然后通知对应的车，然后通知后台
		if(car_id==null) {
			sendNoCarToDispathToFangYao(area_code, order_id, order_run_time);
		}else {

			String info="{\"order\":{\"car_id\":\""+car_id+"\",\"timestamp\":\""+System.currentTimeMillis()/1000+"\",\"order_id\":\""+order_id+"\",\"order_source\":\""+order_source+"\",\"from_station\":\""+from_station+"\",\"to_station\":\""+to_station+"\"}}";
			sendDispatchInfoToChatListener(info);
			
			//开始计时
			long start=System.currentTimeMillis();
			//默认情况是1下单成功
			int flag=1;
			flag=waitForOrderResponseFromCar(order_id,car_id, flag);
			System.out.println("flag:"+flag);
			sendOrderResultToFangYao(order_id, car_id, order_run_time, flag, area_code, from_station, to_station);
			
		}
		updataQueueState(area_code);
		
    }  
    public static void sendNoCarToDispathToFangYao(String area_code,String order_id,String order_run_time) {
		System.out.println("car_id为空,无车可派");
		//如果计算得到无车可派，给php后台发311
		System.out.println("order_id :"+order_id);
		Thread4 thread4=new Thread4(Config.orderChangeStatusURL, order_id, "centerControlSystem", null, "311", null, null);
		thread4.start();
		RedisDataUtil.setOrderStatus(order_id, "311");
		RedisDataUtil.setOrderOnOff(order_id, "311");
		order_run_time="0";
		
		
		String content="I find no car to dispatch , car_id is null,"+area_code+","+order_id+",so I send 311 to FangYao !";
        Log_Steps le=new Log_Steps();
        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
        le=null;
    }
    
    public static void sendDispatchInfoToChatListener(String info) {
    	
		MessageSend packet=new MessageSend();
		XMPPConnection conn=XmppConnet.getInstance();
		
		String content="before  sending the info to the chatlistener:"+info;
        Log_Steps le=new Log_Steps();
        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
        le=null;
        
		try {
			packet.sendInfo(conn, info, Config.listenerUsr, Config.xmppIP);
			System.out.println("packet.sendInfo to chatlistener 无异常！！！");

		} catch (XMPPException e) {
			String content2="packet.sendInfo  to chatlistener exception: "+e;
			Log_Steps  le2=new Log_Steps();
	        le2.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
	        le2=null;
		}finally {
			packet=null;
		}
		
		
		String content3="after sending the info to the chatlistener:"+info;
        Log_Steps le3=new Log_Steps();
        le3.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
        le3=null;
        
		System.out.println("发送车消息结束！！！");

    }
    
    
    public static int waitForOrderResponseFromCar(String order_id,String car_id,int flag) {
    	long start=System.currentTimeMillis();
		//开始等待车回复消息
		while(true) {
			//chatlistener端收到车回复的信息后会将收到回复的标志写入数据库，这里监控是否看到此标志，据此判断车端是否下单成功
			
			//如果收到的标志为0表示车端拒绝下单请求，下单失败
			String orderreceive=RedisDataUtil.getOrderReceived( order_id, car_id);
			System.out.println("orderreceive:"+orderreceive);
			if(orderreceive!=null&&orderreceive.equals("0")) {
				
				String content=car_id+" refused the order "+order_id;
		        Log_Steps le=new Log_Steps();
		        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
		        le=null;
		        
				System.out.println(car_id+"车端拒绝下单，下单失败！！！");		        
				flag=0;
				break;
			}
			//如果收到的标志为1表示车端同意下单请求，下单成功
			if(orderreceive!=null&&orderreceive.equals("1")) {
				
				String content=car_id+" accepted  the order "+order_id;
		        Log_Steps le=new Log_Steps();
		        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
		        le=null;
				
				System.out.println("车端同意下单！！！");
				flag=1;
				break;
			}
			//取出当前时间
			long now=System.currentTimeMillis();
			//当超过某一时间，监控并没有发现对应的收到消息的标志，则认为车没有收到消息或者chatlistener没有收到消息，或者写入数据库失败，通信发生了故障
			if(now-start>80000) {
				System.out.println("超时，下单失败！！！");
		        
				String content="80 seconds passed ,but  did not get response from "+car_id+",so "+ order_id+" failed!";
		        Log_Steps le=new Log_Steps();
		        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
		        le=null;
				
				flag=0;
				break;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return flag;
    }
    public static void sendOrderResultToFangYao(String order_id,String car_id,String order_run_time,int flag,String area_code,String from_station,String to_station) {
		if(flag==0) {
			
			System.out.println("flag==0下单失败");
			//下单失败则删除收到的车端发来的标志
			//RedisDataUtil.delOrderReceived( order_id, car_id);
			
			String content="I send failed 311 to FangYao , "+car_id+","+order_id;
	        Log_Steps le=new Log_Steps();
	        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
	        le=null;
	        
			Thread4 thread4=new Thread4(Config.orderChangeStatusURL, order_id, "centerControlSystem", null, "311", null, null);
			thread4.start();
			RedisDataUtil.setOrderStatus(order_id, "311");
			RedisDataUtil.setOrderOnOff(order_id, "311");
			order_run_time="0";
		}else if(flag==1) {
			
				System.out.println("flag==1下单成功");

				//存储相关信息进入redis
				RedisDataUtil.setVehicleOrder(car_id,order_id);
				RedisDataUtil.setOrderVehicle(order_id,car_id);
				RedisDataUtil.setOrderStation(order_id,from_station,to_station);
				String content="I send success 400 to FangYao , "+car_id+","+order_id;
		        Log_Steps le=new Log_Steps();
		        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
		        le=null;

				Thread4 thread4=new Thread4(
						Config.orderChangeStatusURL, 
						order_id, 
						"centerControlSystem", 
						"{\"vehicle_code\":\""+car_id+"\"}", 
						"400",
						null, 
						null);
				thread4.start();
				RedisDataUtil.setOrderStatus(order_id, "400");
				RedisDataUtil.setOrderOnOff(order_id, "400");
				try {
					AcceptInfo.delVehicleFromQueue(car_id, area_code);
				} catch (NumberFormatException e) {
					e.printStackTrace();
					String content2=car_id+" del vehicle exception:"+e;
			        Log_Steps le2=new Log_Steps();
			        le2.writeEror_to_txt(content2,Config.logLocation+le.get_nowDay());
			        le2=null;
				}
			order_run_time="1";
		}
    }
    public static void updataQueueState(String area_code) {
		OrderQueue guangzhou_instance=null;
		OrderQueue nanning_instance = null;
		OrderQueue nanning_cw_instance = null;
		if("guangzhou".equals(area_code)) {

		}else if("nanning_cw".equals(area_code)) {
			nanning_cw_instance=OrderQueue.getNanNingCwInstance();
			nanning_cw_instance.nanning_cw_queue_flag=false;
		}else if("nanning".equals(area_code)) {
			nanning_instance=OrderQueue.getNanNingInstance();
			nanning_instance.nanning_queue_flag=false;
		}
    }
}  
