package com.imooc.o2o.entity;

import net.sf.json.JSONObject;

public class Veh {
	Vehicle vehicle;

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	@Override
	public String toString() {
		JSONObject json = JSONObject.fromObject(this);
		String strJson=json.toString();
		//System.out.println("strJson:"+strJson);
		return strJson;
	}
}
