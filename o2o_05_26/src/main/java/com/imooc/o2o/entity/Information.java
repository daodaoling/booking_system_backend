package com.imooc.o2o.entity;

//import ch.qos.logback.core.net.SyslogOutputStream;
import net.sf.json.JSONObject;

public class Information {
	private String order_distance;
	private String order_duration;
	
	public String getOrder_distance() {
		return order_distance;
	}
	public void setOrder_distance(String order_distance) {
		this.order_distance = order_distance;
	}
	public String getOrder_duration() {
		return order_duration;
	}
	public void setOrder_duration(String order_duration) {
		this.order_duration = order_duration;
	}
	@Override
	public String toString() {
		JSONObject json = JSONObject.fromObject(this);
		String strJson=json.toString();
		//System.out.println("strJson:"+strJson);
		return strJson;
	}
	
	
}
