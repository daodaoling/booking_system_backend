package com.imooc.o2o.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import ch.qos.logback.core.net.SyslogOutputStream;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

public class RedisDataUtil_orignal {
	public static void main(String[] args) {
		Jedis jedis = new Jedis("127.0.0.1");
		String vehicle_code="SL201902002";
		String area="hhh";
		//setVehicleHeartbeat_area(jedis, vehicle_code, area);
		String are=getVehicleHeartbeat_area(jedis, vehicle_code);
		//System.out.println("are:"+are);
		String order_id="4931241295dfb27ba99a823028998388";
		String status="600";
		boolean flag=true;
		String content=flag+"";
		String car_id="001";
		String timestamp="12321221";
		String response_status="1";
		String cancel_status="1";
		String order_status="400";
		String area_code="nanning";
		String queue="2";
		//setOrderQueue(jedis, area_code, queue);
		String num="1";
		setCount_end(jedis, area_code, num);
		
	}
	public static void setCancelOrder(Jedis jedis,String vehicle_code,String order_id,String cancel_status ) {
		Map<String,String> map = new HashMap();
		map.put(order_id+"_"+vehicle_code,cancel_status);
		jedis.hmset("cancel_order",map);
		map=null;
		
		

	}
	
	public static String  getCancelOrder(Jedis jedis,String vehicle_code,String order_id ) {
		String list=jedis.hget("cancel_order", order_id+"_"+vehicle_code);
		System.out.println(list);
		
		return list;
		
	}
	public static void  delCancelOrder(Jedis jedis,String vehicle_code,String order_id,String cancel_status ) {
		jedis.del("cancel_order", order_id+"_"+vehicle_code);
		
	}
	
	public static void setVehicleError(Jedis jedis,String vehicle_code,String error_code ) {
		Map<String,String> map = new HashMap();
		map.put(vehicle_code,error_code);
		jedis.hmset("vehicle_error",map);

	}
	
	public static void setVehicleOrder(Jedis jedis,String vehicle_code,String order_id) {
		Map<String,String> map = new HashMap();
		map.put(vehicle_code,order_id);
		jedis.hmset("vehicle_order",map);

	}
	public static void delVehicleOrder(Jedis jedis,String vehicle_code) {
		jedis.hdel("vehicle_order", vehicle_code);

	}
	public static void setOrderVehicle(Jedis jedis,String order_id,String vehicle_code) {
		Map<String,String> map = new HashMap();
		
		map.put(order_id,vehicle_code);
		System.out.println("order_id:"+order_id);
		System.out.println("vehicle_code:"+vehicle_code);
		jedis.hmset("order_vehicle",map);

	}
	public static String getOrderVehicle(Jedis jedis,String order_id) {

		List<String> order_vehicle = jedis.hmget("order_vehicle", order_id);
		System.out.println("order_vehicle:"+order_vehicle.get(0));
		return order_vehicle.get(0);
	}
	public static void delOrderVehicle(Jedis jedis,String order_id) {
		jedis.hdel("order_vehicle", order_id);

	}
	public static void setOrderStation(Jedis jedis ,String order_id,String from_station,String to_station) {
		Map<String,String> map = new HashMap();
		String station="{\"from_station\":\""+from_station+"\",\"to_station\":\""+to_station+"\"}";
		map.put("station", station);
		jedis.hmset("order_station:"+order_id,map);
	
	}
	public static String getOrderStation(Jedis jedis ,String order_id) {

		String result=jedis.hget("order_station:"+order_id, "station");
		return result;

	}
	public static void delOrderStation(Jedis jedis ,String order_id) {
		jedis.hdel("order_station:"+order_id, "station");

	}
	public static void setVehicleHeartbeat(Jedis jedis ,String vehicle_code,String information) {
		Map<String,String> map = new HashMap();
		map.put("information", information);
		jedis.hmset("vehicle_heartbeat:"+vehicle_code,map);

	}
	public static void setOrderQueue(Jedis jedis ,String area_code,String queue) {
		Map<String,String> map = new HashMap();
		map.put("queue", queue);
		jedis.hmset("order_queue:"+area_code,map);
	}
	public static String getOrderQueue(Jedis jedis ,String area_code) {
		String result=jedis.hget("order_queue:"+area_code, "queue");
		return result;
	}
	
	
	
	
	public static void setPathToPickup(Jedis jedis ,String order_id,String path_to_pickup) {
		Map<String,String> map = new HashMap();
		map.put("path_to_pickup", path_to_pickup);
		jedis.hmset("order_path:"+order_id,map);

	}
	
	
	
	public static String getPathToPickup(Jedis jedis ,String order_id) {
		String result=jedis.hget("order_path:"+order_id, "path_to_pickup");
		return result;
	}
	
	public static void setPathToDropoff(Jedis jedis ,String order_id,String path_to_dropoff) {
		Map<String,String> map = new HashMap();
		map.put("path_to_dropoff", path_to_dropoff);
		jedis.hmset("order_path:"+order_id,map);

	}
	public static String getPathToDropoff(Jedis jedis ,String order_id) {
		String result=jedis.hget("order_path:"+order_id, "path_to_dropoff");
		return result;
	}
	
	
	
	
	public static void setVehicleHeartbeat_flag(Jedis jedis ,String vehicle_code,String flag) {
		Map<String,String> map = new HashMap();
		map.put("flag", flag);
		jedis.hmset("vehicle_heartbeat:"+vehicle_code,map);

	}
	public static String getVehicleHeartbeat_flag(Jedis jedis ,String vehicle_code) {

		List<String> vehicle_heartbeat_flag = jedis.hmget("vehicle_heartbeat:"+vehicle_code, "flag");
		//System.out.println("vehicle_heartbeat:"+vehicle_heartbeat.get(0));
		return vehicle_heartbeat_flag.get(0);
	}
	
	public static void setVehicleHeartbeat_area(Jedis jedis ,String vehicle_code,String area) {
		Map<String,String> map = new HashMap();
		map.put("area", area);
		jedis.hmset("vehicle_heartbeat:"+vehicle_code,map);

	}
	public static String getVehicleHeartbeat_area(Jedis jedis ,String vehicle_code) {

		List<String> vehicle_heartbeat_flag = jedis.hmget("vehicle_heartbeat:"+vehicle_code, "area");
		//System.out.println("vehicle_heartbeat:"+vehicle_heartbeat.get(0));
		return vehicle_heartbeat_flag.get(0);
	}
	
	
	
	
	
	
	
	
	
	
	public static void delVehicleHeartbeat_flag(Jedis jedis ,String vehicle_code) {

		jedis.hdel("vehicle_heartbeat:"+vehicle_code, "flag");
	}
	public static void setVehicleHeartbeat_0(Jedis jedis ,String vehicle_code,String timestamp) {
		Map<String,String> map = new HashMap();
		map.put("heartbeat_0", timestamp);
		jedis.hmset("vehicle_heartbeat:"+vehicle_code,map);
		//List<String> vehicle_heartbeat = jedis.hmget("vehicle_heartbeat:"+vehicle_code, "information");
		//System.out.println("vehicle_heartbeat:"+vehicle_heartbeat.get(0));
	}
	public static String getVehicleHeartbeat_0(Jedis jedis ,String vehicle_code) {

		List<String> vehicle_heartbeat_0 = jedis.hmget("vehicle_heartbeat:"+vehicle_code, "heartbeat_0");
		//System.out.println("vehicle_heartbeat:"+vehicle_heartbeat.get(0));
		return vehicle_heartbeat_0.get(0);
	}
	public static void delVehicleHeartbeat_0(Jedis jedis ,String vehicle_code) {

		jedis.hdel("vehicle_heartbeat:"+vehicle_code, "heartbeat_0");
	}
	public static void setOrderReceived(Jedis jedis ,String order_id,String car_id,String response_status) {
		Map<String,String> map = new HashMap();

		map.put(order_id+"_"+car_id,response_status);

		jedis.hmset("order_received",map);

	}
	public static String getOrderReceived(Jedis jedis ,String order_id,String car_id) {
		List<String> OrderReceived= jedis.hmget("order_received", order_id+"_"+car_id);

		return OrderReceived.get(0);

	}
	
	public static void delOrderReceived(Jedis jedis,String order_id,String car_id ) {
		jedis.del("order_received",order_id+"_"+car_id);

	}
	public static void setOrder_Cancel_ID(Jedis jedis ,String order_id,String car_id) {
		Map<String,String> map = new HashMap();
		//map.put("order_id",order_id);
		map.put("car_id", car_id);

		jedis.hmset("order_cancel_ID_"+order_id,map);

	}
	public static String getOrder_Cancel_ID(Jedis jedis ,String order_id) {

		System.out.println("order_cancel_ID_"+order_id);
		
		String result=jedis.hget("order_cancel_ID_"+order_id, "car_id");
		System.out.println("result:"+result);

		return result;
	}
	public static void delOrder_Cancel_ID(Jedis jedis ,String order_id) {

		jedis.hdel("order_cancel_ID_"+order_id, "car_id");

	}
	public static void setOrderRunTime(Jedis jedis,String order_run_time,String order_id) {
		Map<String,String> map = new HashMap();
		map.put("order_run_time:"+order_id,order_run_time);
		jedis.hmset("order_run_time", map);
	}
	public static String getOrderRunTime(Jedis jedis,String order_id) {
		String result=jedis.hget("order_run_time", "order_run_time:"+order_id);
		return result;
	}	
	public static void delOrderRunTime(Jedis jedis,String order_id) {
		//String result=jedis.hget("order_run_time:"+order_id, "order_run_time");
		jedis.hdel("order_run_time", "order_run_time:"+order_id);
		//return result;
	}
	public static void setCount_start(Jedis jedis,String area_code,String num) {
		Map<String,String> map = new HashMap();
		//System.out.println("area_code+start:"+area_code+start);
		map.put(area_code+"_start",num);
		jedis.hmset(area_code,map);
		
	}	
	public static String getCount_start(Jedis jedis,String area_code) {

		String result=jedis.hget(area_code,area_code+"_start");

		return result;
		
	}
	public static void delCount_start(Jedis jedis,String area_code) {

		jedis.hdel(area_code, area_code+"_start");
		
	}
	public static void setCount_end(Jedis jedis,String area_code,String num) {
		Map<String,String> map = new HashMap();
		//System.out.println("area_code+start:"+area_code+start);
		map.put(area_code+"_end",num);
		jedis.hmset(area_code,map);
		
	}	
	public static String getCount_end(Jedis jedis,String area_code) {

		String result=jedis.hget(area_code,area_code+"_end");
		System.out.println("area_code_end:"+area_code+"_end");
		System.out.println("result:"+result);
		return result;
		
	}	
	public static void save_end(Jedis jedis,String area_code,String num_end,String car_id) {
		Map<String,String> map = new HashMap();
		map.put(num_end,car_id);

		jedis.hmset(area_code,map);
		
	}
	public static String get_end(Jedis jedis,String area_code,String num_end) {
		
		List<String> endlist=jedis.hmget(area_code, num_end);
		//List<String> order_vehicle = jedis.hmget("order_vehicle", order_id);
		System.out.println("endlist:"+endlist.get(0));
		return endlist.get(0);
		
	}
	public static void delCount_end(Jedis jedis,String area_code) {

		jedis.hdel(area_code, area_code+"_end");
		
	}
	
	
	
	public static String get_dispatch_car(Jedis jedis,String area_code,String start) {
		
		List<String> startlist=jedis.hmget(area_code, start);
		//List<String> order_vehicle = jedis.hmget("order_vehicle", order_id);
		System.out.println("endlist:"+startlist.get(0));
		return startlist.get(0);
		
	}
	public static void del_dispatch_car(Jedis jedis,String area_code,String start) {
		
		jedis.hdel(area_code, start);

	}
	
	public static void setCarState(Jedis jedis,String car_id,String carState,String area_code) {
		Map<String,String> map = new HashMap();
		//System.out.println("area_code+start:"+area_code+start);
		map.put("carstate_"+car_id,carState);
		jedis.hmset(area_code,map);
		
	}
	public static String getCarState(Jedis jedis,String car_id,String area_code) {
		List<String> carstate=jedis.hmget(area_code, "carstate_"+car_id);

		return carstate.get(0);
	}
	public static String getCarid_Num(Jedis jedis,String car_id,String area_code) {
		List<String> num=jedis.hmget(area_code,car_id);

		return num.get(0);
	}
	public static String getNum_Carid(Jedis jedis,String num,String area_code) {
		List<String> carid=jedis.hmget(area_code,num);

		return carid.get(0);
	}
	public static void setPath_to_pickup_(Jedis jedis,String car_id,String path_to_pickup) {
		Map<String,String> map = new HashMap();
		//System.out.println("area_code+start:"+area_code+start);
		map.put("path_to_pickup",path_to_pickup);
		jedis.hmset(car_id,map);
		
	}
	public static String getPath_to_pickup_(Jedis jedis,String car_id) {
		List<String> path_to_pickup=jedis.hmget(car_id, "path_to_pickup");
		return path_to_pickup.get(0);
	}
	public static void setPath_to_dropoff_(Jedis jedis,String car_id,String path_to_dropoff) {
		Map<String,String> map = new HashMap();
		//System.out.println("area_code+start:"+area_code+start);
		map.put("path_to_dropoff",path_to_dropoff);
		jedis.hmset(car_id,map);
		
	}
	public static String getPath_to_dropoff_(Jedis jedis,String car_id) {
		List<String> path_to_dropoff=jedis.hmget(car_id, "path_to_dropoff");
		return path_to_dropoff.get(0);
	}
	public static void setDisconInform(Jedis jedis,String order_id,String status,String content) {
		Map<String,String> map = new HashMap();
		//System.out.println("area_code+start:"+area_code+start);
		map.put("status_"+order_id,status);
		map.put("content_"+order_id, content);
		jedis.hmset("discon_inform",map);
	}
	public static void setDisconInformContent(Jedis jedis,String order_id,String order_status,String content) {
		Map<String,String> map = new HashMap();
		System.out.println("实际传入的order_id"+order_id);
		System.out.println("实际传入的order_status:"+order_status);
		map.put(order_id+"_"+order_status, content);
		jedis.hmset("discon_inform",map);
	}
	public static String getDisconInformContent(Jedis jedis,String order_id,String order_status) {
		List<String> list=jedis.hmget("discon_inform", order_id+"_"+order_status);
		return list.get(0);
	}
	public static void delDisconInformContent(Jedis jedis,String order_id) {
		jedis.hdel("discon_inform", "content_"+order_id);
		
	}
	public static void delDisconInform(Jedis jedis,String order_id) {
		jedis.hdel("discon_inform", "status_"+order_id);
		jedis.hdel("discon_inform", "content_"+order_id);
	}
	public static void setOrderFlag(Jedis jedis,String order_id,String status,String content) {
		Map<String,String> map = new HashMap();
		map.put(order_id+"_"+status, content);
		try {
			jedis.hmset("order_flag",map);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			System.out.println("setorderflag出现异常："+e);
		}
	}
	public static String getOrderFlag(Jedis jedis,String order_id,String status) {
		//System.out.println("jedis.hmget(\"order_flag\", order_id+\"_\"+status)"+jedis.hmget("order_flag", order_id+"_"+status));
		
		List<String> list = null;
		try {
			list = jedis.hmget("order_flag", order_id+"_"+status);
		} catch (Exception e) {

			System.out.println("出现异常"+e);
		}
		if(list==null) {
			return null;
		}
		return list.get(0);
	}
	public static void delOrderFlag(Jedis jedis,String order_id,String status) {
		jedis.hdel("order_flag", order_id+"_"+status);
	}
	
	
	public static void batchDel_order_run_time(Jedis jedis){
		Set<String> set = jedis.keys("order_run_time" +"*");
		Iterator<String> it = set.iterator();
		while(it.hasNext()){
			String keyStr = it.next();
			System.out.println(keyStr);
			jedis.del(keyStr);
		}
	}
	
	
	
}
