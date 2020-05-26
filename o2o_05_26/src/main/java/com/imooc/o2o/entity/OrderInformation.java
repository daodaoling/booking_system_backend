package com.imooc.o2o.entity;

public class OrderInformation {
	private String vechicle;
	private String order_id;
	private String app_id;
	private String actionUrl;
	private String order_status;
	private String information;


	public String getVechicle() {
		return vechicle;
	}
	public void setVechicle(String vechicle) {
		this.vechicle = vechicle;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getActionUrl() {
		return actionUrl;
	}
	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	
}
