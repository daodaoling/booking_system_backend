package com.imooc.o2o.entity;

public class HeartbeatInformation {
	private String heartbeat;
	private String latitude;
	private String longitude;
	private String speed;
	public String getHeartbeat() {
		return heartbeat;
	}
	public void setHeartbeat(String heartbeat) {
		this.heartbeat = heartbeat;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	@Override
	public String toString() {
		// TODO 自动生成的方法存根
		String str="{\"heartbeat\":\""+this.getHeartbeat()+"\",\"latitude\":\""+this.getLatitude()+"\",\"longitude\":\""+this.getLongitude()+"\",\"speed\":\""+this.getSpeed()+"\"}";
		//System.out.println(str);
		return str;
	}
	
}
