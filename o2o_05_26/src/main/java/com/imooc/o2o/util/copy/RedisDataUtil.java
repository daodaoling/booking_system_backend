package com.imooc.o2o.util.copy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

public class RedisDataUtil {
	public static void main(String[] args) {

		
	}
	public static void setVehicleError() {
		Map<String,String> map = new HashMap();
		Jedis jedis = new Jedis("127.0.0.1");
		//String str="{\"error_code\":\"2\"}";
		String vehicle_code="002";
		map.put("error_code","3");
		jedis.hmset("vehicle_error:"+vehicle_code,map);
		List<String> vehicle_error = jedis.hmget("vehicle_error:"+vehicle_code, "error_code");
		System.out.println("vehicle_error:"+vehicle_error.get(0));
	}
	public static void setVehicleOrder(String vehicle_code,String order_id) {
		Map<String,String> map = new HashMap();
		Jedis jedis = new Jedis("127.0.0.1");
		//String str="{\"order_id\":\"12\"}";
		//String vehicle_code="002";
		map.put("order_id",order_id);
		jedis.hmset("vehicle_order:"+vehicle_code,map);
		List<String> vehicle_order = jedis.hmget("vehicle_order:"+vehicle_code, "order_id");
		System.out.println("vehicle_code:"+vehicle_order.get(0));
	}
	public static void setOrderVehicle(String order_id,String vehicle_code) {
		Map<String,String> map = new HashMap();
		Jedis jedis = new Jedis("127.0.0.1");
		//String str="{\"vehicle_code\":\"11\"}";
		//String order_id="4931241295dfb27ba99a823028998388";
		map.put("vehicle_code",vehicle_code);
		jedis.hmset("order_vehicle:"+order_id,map);

		List<String> order_vehicle = jedis.hmget("order_vehicle:"+order_id, "vehicle_code");
		System.out.println("order_vehicle:"+order_vehicle.get(0));
	}
	public static void setOrderStation(String order_id,String from_station,String to_station) {
		Map<String,String> map = new HashMap();
		Jedis jedis = new Jedis("127.0.0.1");
		String station="{\"from_station\":\""+from_station+"\",\"to_station\":\""+to_station+"\"}";
		map.put("station", station);
		jedis.hmset("order_station:"+order_id,map);
		List<String> station0 = jedis.hmget("order_station:"+order_id, "station");
		System.out.println("order_station:"+station0.get(0));
	}
	
}
