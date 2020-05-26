package com.imooc.o2o.entity;

import net.sf.json.JSONObject;

public class Vehicle {
	int vehicle_code;



	public int getVehicle_code() {
		return vehicle_code;
	}



	public void setVehicle_code(int vehicle_code) {
		this.vehicle_code = vehicle_code;
	}



	@Override
	public String toString() {
		JSONObject json = JSONObject.fromObject(this);
		String strJson=json.toString();
		System.out.println("strJson:"+strJson);
		return strJson;
	}
}
