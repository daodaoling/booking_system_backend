package com.imooc.o2o.util;

import java.io.IOException;

import redis.clients.jedis.Jedis;

public class DispatherUtil {
	public static void main(String[] args) throws IOException {

	}

	public static String vehDispather(String area_code)  {
		
		long s=System.currentTimeMillis()/1000;
		String start=null;
		while(true) {
			long e=System.currentTimeMillis()/1000;
			start=RedisDataUtil.getCount_start( area_code);
			if(start!=null) {
				
				System.out.println("发现有车，跳出循环！");
				String content="I find a car,so I dispatch the car !"+RedisDataUtil.get_dispatch_car( area_code, start);
		        Log_Steps le=new Log_Steps();
		        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
		        le=null;
				break;
			}
			
			if(e-s>5) {
				
				System.out.println("超时，无车可派！");
				String content="Time has passed for 5 seconds ,so I regard it as no car to dispatch!";
		        Log_Steps le=new Log_Steps();
		        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
		        le=null;
				return null;
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		//如果不为空，则根据初始指针编号取出车编号作为要派的车编号
		String car_id=RedisDataUtil.get_dispatch_car(area_code, start);

		return car_id;
	}
}
