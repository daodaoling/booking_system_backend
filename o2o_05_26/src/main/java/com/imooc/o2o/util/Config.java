package com.imooc.o2o.util;

//import org.jivesoftware.smack.XMPPConnection;

public class Config {
	//本地和云服务器上都是一样的127.0.0.1，无特殊情况不用修改
	public static String redisIP="127.0.0.1";
	//public static String redisIP="192.168.1.210";
	//public static String redisIP="122.112.215.198";
	
	
	//openfire上的账号相关信息，无特殊情况不用修改
	public static int xmppPort=5222;
	public static String acceptHttpUsr="20181001-0004";
	public static String acceptHttpPwd="123456";
	public static String listenerUsr="daodaoling";
	public static String listenerPwd="123456";
	
	//本地windows下的xmppIP相关参数
	
	public static String xmppIP="127.0.0.1";
	public static String dominXmppIP="127.0.0.1";
	public static String chatIP="127.0.0.1";//暂时未发现作用,实验证明这个数据乱填也没关系
	
	//云服务器上的xmppIP相关参数
	/*
	public static String xmppIP="122.112.215.198";
	public static String dominXmppIP="122.112.215.198";
	public static String chatIP="122.112.215.198";//暂时未发现作用
	*/
	//四楼服务器上的xmppIP相关参数
	/*
	public static String xmppIP="192.168.1.210";
	public static String dominXmppIP="192.168.1.210";
	public static String chatIP="192.168.1.210";//暂时未发现作用
	*/

	
	//与方瑶线下测试通信访问URL

	public static String postIP="192.168.2.111";
	public static String orderChangeStatusURL="http://"+postIP+"/backendapi/api/order/changeStatus";
	public static String vehicleChangeStatusURL="http://"+postIP+"/backendapi/api/vehicle/changeStatus";
	public static String saveVehicleProblemURL="http://"+postIP+"/backendapi/api/vehicle/saveVehicleProblem";
	public static String saveVehicleLocationURL="http://"+postIP+"/backendapi/api/vehicle/saveVehicleLocation";
	
	
	//与方瑶云服务器内网通信URL
	/*
	public static String postIP="127.0.0.1";
	public static String orderChangeStatusURL="http://"+postIP+":82/api/order/changeStatus";
	public static String vehicleChangeStatusURL="http://"+postIP+":82/api/vehicle/changeStatus";
	public static String saveVehicleProblemURL="http://"+postIP+":82/api/vehicle/saveVehicleProblem";
	public static String saveVehicleLocationURL="http://"+postIP+":82/api/vehicle/saveVehicleLocation";
	*/
	//与方瑶云服务器外网通信URL(已废弃)
	/*
	public static String postIP="122.112.215.198:82";//122.112.215.198
	public static String orderChangeStatusURL="http://"+postIP+"/api/order/changeStatus";
	public static String vehicleChangeStatusURL="http://"+postIP+"/api/vehicle/changeStatus";
	public static String saveVehicleProblemURL="http://"+postIP+"/api/vehicle/saveVehicleProblem";
	public static String saveVehicleLocationURL="http://"+postIP+"/api/vehicle/saveVehicleLocation";
	*/
	
	//本地测试log地址
	public static String logLocation="D:pengzhao_log_";
	//四楼服务器log地址
	//public static String logLocation="/home/venti/tomcat/apache-tomcat-8.5.50/o2o_logs/log_";
	//云服务器log地址
	//public static String logLocation="/usr/tomcat/apache-tomcat-8.5.54/o2o_logs/log_";
	
}
