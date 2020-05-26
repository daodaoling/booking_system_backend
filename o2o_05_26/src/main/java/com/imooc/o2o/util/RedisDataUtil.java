package com.imooc.o2o.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import ch.qos.logback.core.net.SyslogOutputStream;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisDataUtil{
	public static void main(String[] args) {
		String order_id="123456";
		String order_status="400";
		setOrderOnOff(order_id, order_status);
		System.out.println(getOrderOnOff(order_id));
	}
	public static void setCancelOrder(String vehicle_code,String order_id,String cancel_status ) {
		Jedis jedis=null;
		//Jedis jedis = JedisConn.getInstance();
		Map<String,String> map = new HashMap();
		map.put(order_id+"_"+vehicle_code,cancel_status);
		try {
			jedis=RedisPool.getJedis();
			jedis.hmset("cancel_order",map);
		}catch (JedisConnectionException e){
		    e.printStackTrace();
		    System.out.println("连接redis服务器失败");
		    RedisPool.returnBrokenResource(jedis);
		}finally {
		    map=null;
		    RedisPool.returnResource(jedis);
		}

	}
	public static String  getVehicle_error(String car_id) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		//System.out.println("getVehicle_error:"+car_id);
		String errorcode_before=null;
		try {
			jedis=RedisPool.getJedis();
			errorcode_before = jedis.hget("vehicle_error",car_id);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
		return errorcode_before;
	}
	public static String  getCancelOrder(String vehicle_code,String order_id ) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		String list=null;
		try {
			jedis=RedisPool.getJedis();
			list=jedis.hget("cancel_order", order_id+"_"+vehicle_code);
		}catch (JedisConnectionException e){
		    e.printStackTrace();
		    System.out.println("连接redis服务器失败");
		    RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
		return list;
		
	}
	public static void  delCancelOrder(String vehicle_code,String order_id,String cancel_status ) {
		//Jedis jedis =JedisConn.getInstance();
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			jedis.del("cancel_order", order_id+"_"+vehicle_code);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
		
	}
	
	public static void setVehicleError(String vehicle_code,String error_code ) {
		//Jedis jedis =JedisConn.getInstance();
		Jedis jedis=null;
		Map<String,String> map = new HashMap();
		
		try {
			jedis=RedisPool.getJedis();
			map.put(vehicle_code,error_code);
			jedis.hmset("vehicle_error",map);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
			map=null;
		}

	}
	
	public static void setVehicleOrder(String vehicle_code,String order_id) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		Map<String,String> map = new HashMap();
		try {
			jedis=RedisPool.getJedis();
			map.put(vehicle_code,order_id);
			jedis.hmset("vehicle_order",map);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
			map=null;
		}

	}
	public static String  getVehicleOrder(String vehicle_code) {
		//Jedis jedis = JedisConn.getInstance();
		String result=null;
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			result=jedis.hget("vehicle_order", vehicle_code);
			
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
		return result;
	}
	
	
	
	
	
	
	
	public static void delVehicleOrder(String vehicle_code) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			jedis.hdel("vehicle_order", vehicle_code);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
		

	}
	public static void setOrderVehicle(String order_id,String vehicle_code) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		Map<String,String> map = new HashMap();
		
		try {
			jedis=RedisPool.getJedis();
			map.put(order_id,vehicle_code);
			jedis.hmset("order_vehicle",map);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			map=null;
			RedisPool.returnResource(jedis);
		}

	}
	public static String getOrderVehicle(String order_id) {
		//Jedis jedis =JedisConn.getInstance();
		List<String> order_vehicle=null;
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			order_vehicle = jedis.hmget("order_vehicle", order_id);
			System.out.println("order_vehicle:"+order_vehicle);
			return order_vehicle.get(0);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
			System.out.println("getOrderVehicle yichang");
		}finally {
			RedisPool.returnResource(jedis);
		}
		return null;
		
	}
	public static void delOrderVehicle(String order_id) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			jedis.hdel("order_vehicle", order_id);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}

	}
	public static void setOrderStation(String order_id,String from_station,String to_station) {
		//Jedis jedis =  JedisConn.getInstance();
		Jedis jedis=null;
		Map<String,String> map = new HashMap();
		String station="{\"from_station\":\""+from_station+"\",\"to_station\":\""+to_station+"\"}";
		try {
			jedis=RedisPool.getJedis();
			map.put("station", station);
			jedis.hmset("order_station:"+order_id,map);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			map=null;
			RedisPool.returnResource(jedis);
			
		}
	
	}
	public static String getOrderStation(String order_id) {
		//Jedis jedis = JedisConn.getInstance();
		String result=null;
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			result = jedis.hget("order_station:"+order_id, "station");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
		return result;

	}
	public static void delOrderStation(String order_id) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			jedis.hdel("order_station:"+order_id, "station");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}

	}
	public static void setVehicleHeartbeat(String vehicle_code,String information) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		Map<String,String> map = new HashMap();
		try {
			jedis=RedisPool.getJedis();
			map.put("information", information);
			jedis.hmset("vehicle_heartbeat:"+vehicle_code,map);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			map=null;
			RedisPool.returnResource(jedis);
		}

	}
	public static void setOrderOnOff(String order_id,String order_status) {
		Jedis jedis=null;
		Map<String,String> map = new HashMap();
		try {
			jedis=RedisPool.getJedis();
			map.put("order_status", order_status);
			jedis.hmset("OrderOnOff:"+order_id,map);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			map=null;
			RedisPool.returnResource(jedis);
		}

	}
	public static String getOrderOnOff(String order_id) {
		List<String> orderOnOff=null;
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			orderOnOff = jedis.hmget("OrderOnOff:"+order_id, "order_status");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
		//System.out.println("vehicle_heartbeat:"+vehicle_heartbeat.get(0));
		return orderOnOff.get(0);
	}
	
	
	
	public static void delOrderQueue(String area_code) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			jedis.hdel("order_queue:"+area_code,"queue");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
	}
	public static void setOrderQueue(String area_code,String queue) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		Map<String,String> map = new HashMap();
		try {
			jedis=RedisPool.getJedis();
			map.put("queue", queue);
			jedis.hmset("order_queue:"+area_code,map);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			map=null;
			RedisPool.returnResource(jedis);
			
		}
	}
	public static String getOrderQueue(String area_code) {
		//Jedis jedis =JedisConn.getInstance();
		String result=null;
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			result = jedis.hget("order_queue:"+area_code, "queue");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
		return result;
	}
	
	
	
	
	public static void setPathToPickup(String order_id,String path_to_pickup) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		Map<String,String> map = new HashMap();
		try {
			jedis=RedisPool.getJedis();
			map.put("path_to_pickup", path_to_pickup);
			jedis.hmset("order_path:"+order_id,map);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			map=null;
			RedisPool.returnResource(jedis);
		}

	}
	
	
	
	public static String getPathToPickup(String order_id) {
		//Jedis jedis = JedisConn.getInstance();
		String result=null;
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			result = jedis.hget("order_path:"+order_id, "path_to_pickup");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
		return result;
	}
	
	public static void setPathToDropoff(String order_id,String path_to_dropoff) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		Map<String,String> map = new HashMap();
		try {
			jedis=RedisPool.getJedis();
			map.put("path_to_dropoff", path_to_dropoff);
			jedis.hmset("order_path:"+order_id,map);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			map=null;
			RedisPool.returnResource(jedis);
		}

	}
	public static String getPathToDropoff(String order_id) {
		//Jedis jedis =JedisConn.getInstance();
		Jedis jedis=null;
		String result=null;
		try {
			jedis=RedisPool.getJedis();
			result = jedis.hget("order_path:"+order_id, "path_to_dropoff");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
		return result;
	}
	
	
	
	
	public static void setVehicleHeartbeat_flag(String vehicle_code,String flag) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		Map<String,String> map = new HashMap();
		try {
			jedis=RedisPool.getJedis();
			map.put("flag", flag);
			jedis.hmset("vehicle_heartbeat:"+vehicle_code,map);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			map=null;
			RedisPool.returnResource(jedis);
		}
	}
	public static String getVehicleHeartbeat_flag(String vehicle_code) {
		//Jedis jedis = JedisConn.getInstance();
		List<String> vehicle_heartbeat_flag=null;
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			vehicle_heartbeat_flag = jedis.hmget("vehicle_heartbeat:"+vehicle_code, "flag");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
		//System.out.println("vehicle_heartbeat:"+vehicle_heartbeat.get(0));
		return vehicle_heartbeat_flag.get(0);
	}
	
	public static void setVehicleHeartbeatArea(String vehicle_code,String area) {
		//Jedis jedis =JedisConn.getInstance();
		Jedis jedis=null;
		Map<String,String> map = new HashMap();
		try {
			jedis=RedisPool.getJedis();
			map.put("area", area);
			jedis.hmset("vehicle_heartbeat:"+vehicle_code,map);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			map=null;
			RedisPool.returnResource(jedis);
		}

	}
	public static String getVehicleHeartbeatArea(String vehicle_code) {
		//Jedis jedis =JedisConn.getInstance();
		List<String> vehicle_heartbeat_flag=null;
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			vehicle_heartbeat_flag = jedis.hmget("vehicle_heartbeat:"+vehicle_code, "area");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
		//System.out.println("vehicle_heartbeat:"+vehicle_heartbeat.get(0));
		return vehicle_heartbeat_flag.get(0);
	}
	
	public static void delVehicleHeartbeat_flag(String vehicle_code) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			jedis.hdel("vehicle_heartbeat:"+vehicle_code, "flag");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
	}
	public static void setVehicleHeartbeat_0(String vehicle_code,String timestamp) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		Map<String,String> map = new HashMap();
		try {
			jedis=RedisPool.getJedis();
			map.put("heartbeat_0", timestamp);
			jedis.hmset("vehicle_heartbeat:"+vehicle_code,map);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			map=null;
			RedisPool.returnResource(jedis);
		}

	}
	public static String getVehicleHeartbeat_0(String vehicle_code) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		List<String> vehicle_heartbeat_0=null;
		try {
			jedis=RedisPool.getJedis();
			vehicle_heartbeat_0 = jedis.hmget("vehicle_heartbeat:"+vehicle_code, "heartbeat_0");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
		//System.out.println("vehicle_heartbeat:"+vehicle_heartbeat.get(0));
		if(vehicle_heartbeat_0!=null) {
			return vehicle_heartbeat_0.get(0);
		}
		return null;
	}
	public static void delVehicleHeartbeat_0(String vehicle_code) {
		//Jedis jedis =JedisConn.getInstance();
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			jedis.hdel("vehicle_heartbeat:"+vehicle_code, "heartbeat_0");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
	}
	public static void setOrderReceived(String order_id,String car_id,String response_status) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		Map<String,String> map = new HashMap();

		try {
			jedis=RedisPool.getJedis();
			map.put(order_id+"_"+car_id,response_status);

			jedis.hmset("order_received",map);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
			map=null;
		}

	}
	public static String getOrderReceived(String order_id,String car_id) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		List<String> OrderReceived=null;
		try {
			jedis=RedisPool.getJedis();
			OrderReceived = jedis.hmget("order_received", order_id+"_"+car_id);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}

		return OrderReceived.get(0);

	}
	
	public static void delOrderReceived(String order_id,String car_id ) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			jedis.del("order_received",order_id+"_"+car_id);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}

	}
	public static void setOrder_Cancel_ID(String order_id,String car_id) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		Map<String,String> map = new HashMap();
		try {
			jedis=RedisPool.getJedis();
			map.put("car_id", car_id);

			jedis.hmset("order_cancel_ID_"+order_id,map);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			map=null;
			RedisPool.returnResource(jedis);
		}

	}
	public static String getOrder_Cancel_ID(String order_id) {
		//Jedis jedis = JedisConn.getInstance();
		String result=null;
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			result = jedis.hget("order_cancel_ID_"+order_id, "car_id");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}

		return result;
	}
	public static void delOrder_Cancel_ID(String order_id) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			jedis.hdel("order_cancel_ID_"+order_id, "car_id");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
	}
	public static void setOrderRunTime_(String order_run_time,String order_id) {
		//Jedis jedis = JedisConn.getInstance();
		Map<String,String> map = new HashMap();
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			map.put("order_run_time:"+order_id,order_run_time);
			jedis.hmset("order_run_time", map);
			String content=order_id+":"+order_run_time;
	        Log_Steps le=new Log_Steps();
	        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
	        le=null;
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			//e.printStackTrace();
			String content=e+"";
	        Log_Steps le=new Log_Steps();
	        
	        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
			RedisPool.returnBrokenResource(jedis);
		}finally {
			map=null;
			RedisPool.returnResource(jedis);
			
		}
	}
	public static String getOrderRunTime(String order_id) {
		//Jedis jedis = JedisConn.getInstance();
		String result=null;
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			result = jedis.hget("order_run_time", "order_run_time:"+order_id);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
		return result;
	}	
	public static void delOrderRunTime_(String order_id) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			jedis.hdel("order_run_time", "order_run_time:"+order_id);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
		//return result;
	}
	public static void setCount_start(String area_code,String num) {
		//Jedis jedis =JedisConn.getInstance();
		Jedis jedis=null;
		Map<String,String> map = new HashMap();
		//System.out.println("area_code+start:"+area_code+start);
		try {
			jedis=RedisPool.getJedis();
			map.put(area_code+"_start",num);
			jedis.hmset(area_code,map);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
			map=null;
		}
		
	}	
	public static String getCount_start(String area_code) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		String result=null;
		try {
			jedis=RedisPool.getJedis();
			result = jedis.hget(area_code,area_code+"_start");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
		return result;
		
	}
	public static void delCount_start(String area_code) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			jedis.hdel(area_code, area_code+"_start");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
		
	}
	public static void setCount_end(String area_code,String num) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		Map<String,String> map = new HashMap();
		//System.out.println("area_code+start:"+area_code+start);
		try {
			jedis=RedisPool.getJedis();
			map.put(area_code+"_end",num);
			jedis.hmset(area_code,map);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			map=null;
			RedisPool.returnResource(jedis);
		}
		
	}	
	public static String getCount_end(String area_code) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		String result=null;
		try {
			jedis=RedisPool.getJedis();
			result = jedis.hget(area_code,area_code+"_end");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}

		return result;
		
	}	
	public static void save_end(String area_code,String num_end,String car_id) {
		//Jedis jedis = JedisConn.getInstance();
		Map<String,String> map = new HashMap();
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			map.put(num_end,car_id);
			jedis.hmset(area_code,map);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			map=null;
			RedisPool.returnResource(jedis);
		}
		
	}
	public static String get_end(String area_code,String num_end) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		List<String> endlist=null;
		try {
			jedis=RedisPool.getJedis();
			endlist = jedis.hmget(area_code, num_end);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}

		return endlist.get(0);
		
	}
	public static void delCount_end(String area_code) {
		//Jedis jedis =JedisConn.getInstance();
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			jedis.hdel(area_code, area_code+"_end");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
		
	}
	
	
	
	public static String get_dispatch_car(String area_code,String start) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		List<String> startlist=null;
		try {
			jedis=RedisPool.getJedis();
			startlist = jedis.hmget(area_code, start);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}

		return startlist.get(0);
		
	}
	public static void del_dispatch_car(String area_code,String start) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			jedis.hdel(area_code, start);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
	}
	
	public static void setCarState(String car_id,String carState,String area_code) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		Map<String,String> map = new HashMap();
		try {
			jedis=RedisPool.getJedis();
			map.put("carstate_"+car_id,carState);
			jedis.hmset(area_code,map);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			map=null;
			RedisPool.returnResource(jedis);
		}
		
	}
	public static String getCarState(String car_id,String area_code) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		List<String> carstate=null;
		try {
			jedis=RedisPool.getJedis();
			carstate = jedis.hmget(area_code, "carstate_"+car_id);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}

		return carstate.get(0);
	}
	public static String getCarid_Num(String car_id,String area_code) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		List<String> num=null;
		try {
			jedis=RedisPool.getJedis();
			num = jedis.hmget(area_code,car_id);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}

		return num.get(0);
	}
	public static String getNum_Carid(String num,String area_code) {
		//Jedis jedis = JedisConn.getInstance();
		List<String> carid=null;
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			carid = jedis.hmget(area_code,num);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}

		return carid.get(0);
	}
	public static void setPath_to_pickup_(String car_id,String path_to_pickup) {
		//Jedis jedis = JedisConn.getInstance();
		Map<String,String> map = new HashMap();
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			map.put("path_to_pickup",path_to_pickup);
			jedis.hmset(car_id,map);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
			map=null;
		}
		
	}
	public static String getPath_to_pickup_(String car_id) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		List<String> path_to_pickup=null;
		try {
			jedis=RedisPool.getJedis();
			path_to_pickup = jedis.hmget(car_id, "path_to_pickup");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			RedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		}finally {
			RedisPool.returnResource(jedis);
		}
		return path_to_pickup.get(0);
	}
	public static void setPath_to_dropoff_(String car_id,String path_to_dropoff) {
		//Jedis jedis = JedisConn.getInstance();
		Map<String,String> map = new HashMap();
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			map.put("path_to_dropoff",path_to_dropoff);
			jedis.hmset(car_id,map);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
			map=null;
		}
		
	}
	public static String getPath_to_dropoff_(String car_id) {
		//Jedis jedis = JedisConn.getInstance();
		List<String> path_to_dropoff=null;
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			path_to_dropoff = jedis.hmget(car_id, "path_to_dropoff");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
		return path_to_dropoff.get(0);
	}
	public static void setDisconInform(String order_id,String status,String content) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		Map<String,String> map = new HashMap();

		try {
			jedis=RedisPool.getJedis();
			map.put("status_"+order_id,status);
			map.put("content_"+order_id, content);
			jedis.hmset("discon_inform",map);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			map=null;
			RedisPool.returnResource(jedis);
		}
	}
	public static void setDisconInformContent(String order_id,String order_status,String content) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		Map<String,String> map = new HashMap();
		try {
			jedis=RedisPool.getJedis();
			map.put(order_id+"_"+order_status, content);
			jedis.hmset("discon_inform",map);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			map=null;
			RedisPool.returnResource(jedis);
		}
	}
	public static String getDisconInformContent(String order_id,String order_status) {
		//Jedis jedis = JedisConn.getInstance();
		List<String> list=null;
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			list = jedis.hmget("discon_inform", order_id+"_"+order_status);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
		return list.get(0);
	}
	public static void delDisconInformContent(String order_id) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			jedis.hdel("discon_inform", "content_"+order_id);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
		
	}
	public static void delDisconInform(String order_id) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			jedis.hdel("discon_inform", "status_"+order_id);
			jedis.hdel("discon_inform", "content_"+order_id);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
	}
	public static void setOrderStatus(String order_id,String status) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		Map<String,String> map = new HashMap();
		map.put("status", status);
		try {
			jedis=RedisPool.getJedis();
			jedis.hmset("order_status:"+order_id,map);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			System.out.println("setorderflag出现异常："+e);
			RedisPool.returnBrokenResource(jedis);
		}finally {
			map=null;
			RedisPool.returnResource(jedis);
		}
	}
	public static String getOrderStatus(String order_id) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		String result=null;
		try {
			jedis=RedisPool.getJedis();
			result=jedis.hget("order_status:"+order_id, "status");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			System.out.println("setorderflag出现异常："+e);
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
		return result;
	}
	
	
	public static void setOrderFlag(String order_id,String status,String content) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		Map<String,String> map = new HashMap();
		map.put(order_id+"_"+status, content);
		try {
			jedis=RedisPool.getJedis();
			jedis.hmset("order_flag",map);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			System.out.println("setorderflag出现异常："+e);
			RedisPool.returnBrokenResource(jedis);
		}finally {
			map=null;
			RedisPool.returnResource(jedis);
		}
	}
	public static String getOrderFlag(String order_id,String status) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		List<String> list = null;
		try {
			jedis=RedisPool.getJedis();
			list = jedis.hmget("order_flag", order_id+"_"+status);
		} catch (Exception e) {
			RedisPool.returnBrokenResource(jedis);
			System.out.println("出现异常"+e);
		}finally {
			RedisPool.returnResource(jedis);
		}
		if(list==null) {
			return null;
		}
		return list.get(0);
	}
	public static void delOrderFlag(String order_id,String status) {
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		try {
			jedis=RedisPool.getJedis();
			jedis.hdel("order_flag", order_id+"_"+status);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
	}
	
	
	public static void batchDel_order_run_time(){
		//Jedis jedis = JedisConn.getInstance();
		Jedis jedis=null;
		Set<String> set = jedis.keys("order_run_time" +"*");
		Iterator<String> it = set.iterator();
		try {
			jedis=RedisPool.getJedis();
			while(it.hasNext()){
				String keyStr = it.next();
				System.out.println(keyStr);
				jedis.del(keyStr);
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
		}finally {
			RedisPool.returnResource(jedis);
		}
	}
	
	
	
}

