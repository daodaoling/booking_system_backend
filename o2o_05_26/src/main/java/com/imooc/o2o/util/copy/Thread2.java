package com.imooc.o2o.util.copy;

import com.imooc.o2o.util.AcceptInfo;
import com.imooc.o2o.util.Config;
import com.imooc.o2o.util.Log_Steps;
import com.imooc.o2o.util.RedisDataUtil;
import com.imooc.o2o.util.RequestUtils;
import redis.clients.jedis.Jedis;

public class Thread2 extends Thread{
	String car_id;
	String area_code;
	String order_id;
	public Thread2(String car_id,String area_code,String order_id) {
		this.car_id=car_id;
		this.area_code=area_code;
		this.order_id=order_id;
	}
	public void run() {
		boolean flag=true;
		while(flag) {
			//获取当前时间
			long currenttime=System.currentTimeMillis()/1000;
			//取出上次心跳时间
			String vehicleHeartbeat_0=RedisDataUtil.getVehicleHeartbeat_0(car_id);
			long Heartbeat_0=Integer.parseInt(vehicleHeartbeat_0);
			//System.out.println("jinru time");
			//计算两次心跳的时间间隔
			if(currenttime-Heartbeat_0>2*60) {
				
				RedisDataUtil.delVehicleHeartbeat_flag(car_id);
				String carstate=RedisDataUtil.getCarState(car_id, area_code);
				//如果此时车辆在等待队列，则将其从等待队列删除
				if(carstate.equals("1")) {
					AcceptInfo.delVehicleFromQueue(car_id, area_code);
				}
				System.out.println("order_id:"+order_id);
				RequestUtils.sendRequest_online(Config.vehicleChangeStatusURL, "centerControlSystem", car_id, null, currenttime+"", "7", null);
				checkOrderException(car_id, order_id);
				String content="120 seconds passed ,but we did not received new heartbeat ! so I remove the car from  avaliable sequece if carstate is 1,"+carstate;
		        Log_Steps le=new Log_Steps();
		        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
		        le=null;
				break;
			}
			
			
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		
		
	}
	
	public static void checkOrderException(String car_id,String order_id) {
		String order_id_bef=RedisDataUtil.getVehicleOrder(car_id);
		String order_status=RedisDataUtil.getOrderOnOff(order_id_bef);
		if(order_status==null||order_status.equals("false")) {
			return;
		}
		if("400".equals(order_status)) {
			//通知方瑶这一单结束了
			String time_stamp=(System.currentTimeMillis()/1000)+"";
			Thread4 thread4=new Thread4( Config.orderChangeStatusURL, order_id_bef, "centerControlSystem", null, "650", null, time_stamp);
			thread4.start();
			RedisDataUtil.setOrderOnOff(order_id, "false");
			RedisDataUtil.delVehicleOrder(car_id);
			String content="checkOrderException ,so I send  650 to FangYao ! "+order_status+","+order_id_bef+","+car_id;
	        Log_Steps le=new Log_Steps();
	        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
	        le=null;
		}else if("500".equals(order_status)) {
			//行程异常结束
			String time_stamp=(System.currentTimeMillis()/1000)+"";
			Thread4 thread4=new Thread4( Config.orderChangeStatusURL, order_id_bef, "centerControlSystem", null, "610", null, time_stamp);
			thread4.start();
			RedisDataUtil.setOrderOnOff(order_id, "false");
			RedisDataUtil.delVehicleOrder(car_id);
			String content="checkOrderException ,so I send  610 to FangYao ! "+order_status+","+order_id_bef+","+car_id;
	        Log_Steps le=new Log_Steps();
	        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
	        le=null;
		}
		
		
		String content="order exception ,so I send end the order to FangYao ! "+order_status;
        Log_Steps le=new Log_Steps();
        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
        le=null;

	}
	
	
	
	
	
}
