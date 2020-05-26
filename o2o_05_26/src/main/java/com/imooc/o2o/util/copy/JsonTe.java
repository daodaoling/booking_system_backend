package com.imooc.o2o.util.copy;

import net.sf.json.JSONObject;

public class JsonTe {
	public static void main(String[] args) {
		String message= "{\"car_state\":{\"order_id\":\"123\",\"order_status\":\"600\"}}";
		//String message_2= "{\"car_id\":\"001\",\"order_status\":\"600\"}";
		try {
			JSONObject json = JSONObject.fromObject(message);
			//System.out.println(json.get("ca_state"));
			if(json.get("car_state")!=null) {
				
			}else {
				
			}
			//String order_id = json.getString("order_i");
			//System.out.println("order_id:"+order_id);
		} catch (Exception e) {
			// TODO: handle exception
			//JSONObject json = JSONObject.fromObject(message);
			//String order_id = json.getString("car_id");
			//System.out.println("order_id:"+order_id);
		}

		
	}
}
