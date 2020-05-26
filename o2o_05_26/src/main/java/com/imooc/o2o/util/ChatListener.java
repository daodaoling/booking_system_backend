package com.imooc.o2o.util;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import com.imooc.o2o.util.copy.Thread4;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

public class ChatListener {
	public static void main(String args[])  {
		
	}
	
	public static void chatListen() {
		//新建连接配置对象，设置服务器IP和监听端口
		ConnectionConfiguration config = new ConnectionConfiguration(Config.xmppIP, Config.xmppPort);
		//新建一个XMPP连接对象
		XMPPConnection connection = new XMPPConnection(config);
		//与openfire做XMPP连接
		try {
			connection.connect();
			System.out.println("连接成功！！！！");
		} catch (XMPPException e2) {
			System.out.println("XMPP连接openfire发生异常"+e2);
		}
		//连接成功后做登录
		try {
			connection.login(Config.listenerUsr, Config.listenerPwd);
			System.out.println("登录无异常！！！");
		} catch (XMPPException e1) {
			System.out.println("XMPP登录openfire账号发生异常"+e1);
		}
		//取得聊天管理器
		ChatManager cm = connection.getChatManager(); 	
		//得到与另一个帐号的连接，这里是一对一,@后面是你安装openfire时注册的域
		Chat chat = cm.createChat(Config.chatIP, null);
		cm.addChatListener(
			new ChatManagerListener() {
			@Override
			public void chatCreated(Chat chat, boolean create) {
				chat.addMessageListener(new MessageListener() {
					@Override
					public void processMessage(Chat chat, Message msg) {
						proMessage(chat, msg, connection);
					}
				});
			}
		});
	
	}
	
	public static void proMessage(Chat chat, Message msg,XMPPConnection connection) {

		if(null!=msg.getBody()) {
			
			String message=msg.getBody();
			messageReSend(chat,message);
			JSONObject json=mesJsonCheck(message);
			
			if(json.get("order")!=null)                 {
				proOrder(json, connection, message);
			}
			
			if(json.get("arrive_at")!=null)             {
				proArriveAt(json);
			}
			
			if(json.get("car_state")!=null)             {
				AcceptInfo.proCarState(json,message,chat);
			}

			if(json.get("order_cancel")!=null)          {
				proOrderCancel(json);
			}

			if(json.get("cancel_from_bk")!=null)        {
				proCancelFromBk(json, connection, message);
			}
			
			if(json.get("cancel_from_car")!=null)       {
				proCancelFromCar(json, message);
			}

			if(json.get("response_order_from_car")!=null) {
				proResOrderFromCar(json);
			}

			
		}
	
	}
	
	//消息原封不动返回给车辆
	public static void messageReSend(Chat chat,String message) {
		try {
			//将心跳原封不动返回给车辆端
			chat.sendMessage(message);
			//将收到的心跳在控制台打印
			System.out.println(message);
			//将对应的心跳信息写入日志
			Log_Steps log=new Log_Steps();
			String content=message;
			log.writeEror_to_txt(content,Config.logLocation+log.get_nowDay());
			log=null;
		} catch (XMPPException e1) {
			Log_Steps log=new Log_Steps();
			String content="发送消息给车辆发生异常:"+e1;
			log.writeEror_to_txt(content,Config.logLocation+log.get_nowDay());
			log=null;
		}
	}
	
	public static JSONObject mesJsonCheck(String message) {
		JSONObject json=null;
		try {
			json = JSONObject.fromObject(message);
		} catch (Exception e1) {
			System.out.println("发生异常:"+e1);
			Log_Steps log=new Log_Steps();
			String content="JSON格式发生异常:"+e1;
			log.writeEror_to_txt(content,Config.logLocation+log.get_nowDay());
			log=null;
		}	
		return json;
	}
	public static void proArriveAt(JSONObject json) {
		//解析发过来的arrive_at数据
		JSONObject msgObj=null;
		String time_stamp=null;
		String car_id=null;
		String order_id=null;
		String station_status=null;
		String order_distance=null;
		try {
			msgObj = json.getJSONObject("arrive_at");
			time_stamp = msgObj.getString("time_stamp");
			car_id = msgObj.getString("car_id");
			order_id = msgObj.getString("order_id");
			station_status = msgObj.getString("station_status");
			order_distance = msgObj.getString("order_distance");
			String stop_id = msgObj.getString("stop_id");
		} catch (Exception e) {
			System.out.println("解析arrive_at数据出现异常"+e);
			Log_Steps log=new Log_Steps();
			String content="解析arrive_at数据出现异常"+e;
			log.writeEror_to_txt(content,Config.logLocation+log.get_nowDay());
			log=null;
		}
		
		if(station_status.equals("start")) {
			proStart(order_id, car_id, time_stamp);
		}else if(station_status.equals("end")) {
			proEnd(order_id, order_distance, car_id, time_stamp);
		}
		
		//使用完毕后将变量都置空
		msgObj=null;
		time_stamp=null;
		car_id=null;
		order_id=null;
		station_status=null;
		order_distance=null;
	}
	
	public static void proStart(String order_id,String car_id,String time_stamp) {
		//将此时的订单状态记录为当前订单状态
		RedisDataUtil.setOrderStatus(order_id, "500");
		RedisDataUtil.setOrderOnOff(order_id, "500");
		//通知后台车到了起点
		Thread4 thread4=new Thread4(Config.orderChangeStatusURL, order_id, "centerControlSystem", null, "500", null, time_stamp);
		thread4.start();
		
		//将发送500信息记录进日志
		System.out.println("start");
		String content=car_id+" arrived at start station !I send 500 to FangYao";
        Log_Steps le=new Log_Steps();
        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
        le=null;
	}
	
	public static void proEnd(String order_id,String order_distance,String car_id,String time_stamp) {
		//将此时的订单状态记录为当前订单状态
		RedisDataUtil.setOrderStatus(order_id, "600");
		RedisDataUtil.setOrderOnOff(order_id, "false");
		RedisDataUtil.delVehicleOrder(car_id);
		//通知后台车到了终点
		Thread4 thread4=new Thread4( Config.orderChangeStatusURL  , order_id, "centerControlSystem", null, "600", "{\"order_distance\":\""+order_distance+"\"}", time_stamp);
        thread4.start();
		
		//将发送600信息记录进日志
		System.out.println("end");
		String content=car_id+" arrived at end station !I send 600 to FangYao";
        Log_Steps le=new Log_Steps();
        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
        le=null;
		
	}
	
	public static void proOrderCancel(JSONObject json) {
		//解析order_cancel数据
		JSONObject msgObj=null;
		String car_id=null;
		String order_id=null;
		String cancel_status=null;
		try {
			msgObj = json.getJSONObject("order_cancel");
			String time_stamp = msgObj.getString("time_stamp");
			car_id = msgObj.getString("car_id");
			order_id = msgObj.getString("order_id");
			cancel_status = msgObj.getString("cancel_status");
		} catch (Exception e) {
			System.out.println("解析order_cancel数据出现异常"+e);
			Log_Steps log=new Log_Steps();
			String content="解析order_cancel数据出现异常"+e;
			log.writeEror_to_txt(content,Config.logLocation+log.get_nowDay());
			log=null;
		}
		RedisDataUtil.setCancelOrder( car_id, order_id,cancel_status);
		
		String content="Chatlistener received the response from car about the cancel of order, cancel_status :"+cancel_status;
        Log_Steps le=new Log_Steps();
        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
        le=null;
        System.out.println(content);
	
	}
	
	public static void proCancelFromBk(JSONObject json ,XMPPConnection connection,String message) {
		String car_id=null;
		try {
			//解析php后端来的取消信息
			JSONObject msgObj = json.getJSONObject("cancel_from_bk");
			car_id = msgObj.getString("car_id");
		} catch (Exception e1) {
	        String content="cancel_from_bk parse exception "+car_id+":"+e1;
			Log_Steps le=new Log_Steps();
			le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
			le=null;
		}
		MessageSend packet=new MessageSend();
		try {
			packet.sendInfo(connection, message, car_id.toLowerCase(), Config.dominXmppIP);
			
	        String content="I send cancel_from_bk to car "+car_id;
			Log_Steps le=new Log_Steps();
			le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
			le=null;
			
			packet=null;
		} catch (XMPPException e) {
	        String content="I send cancel_from_bk to car "+car_id+",but it get exception when it was sent to car "+e;
			Log_Steps le=new Log_Steps();
			le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
			le=null;
			
			packet=null;
		}
		

	
	}
	
	public static void proOrder(JSONObject json,XMPPConnection connection,String message) {
		//解析来自php后端的下单信息
		String car_id=null;
		try {
			JSONObject msgObj = json.getJSONObject("order");
			car_id = msgObj.getString("car_id");
		} catch (Exception e1) {
			String content="parse order from bk exception :"+e1;
	        Log_Steps le=new Log_Steps();
	        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
	        le=null;
		}
		MessageSend packet=new MessageSend();
		try {
			packet.sendInfo(connection, message,  car_id.toLowerCase(),Config.dominXmppIP);
			
			String content="The chatlisener get the order from  Thread1,and send it to "+car_id+","+message;
	        Log_Steps le=new Log_Steps();
	        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
	        le=null;
	        
	        packet=null;
		} catch (XMPPException e) {
			
			String content="The chatlisener get the order from  Thread1,and send it to "+car_id+","+message+"but it got exception when it was sent to car "+e;
	        Log_Steps le=new Log_Steps();
	        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
	        le=null;
	        
	        packet=null;
		}
	
	}
	public static void proResOrderFromCar(JSONObject json) {
		JSONObject msgObj=null;
		String car_id=null;
		String order_id=null;
		String response_status=null;
		String path_to_pickup=null;
		String path_to_dropoff=null;
		try {
			msgObj = json.getJSONObject("response_order_from_car");
			car_id = msgObj.getString("car_id");
			order_id = msgObj.getString("order_id");
			String time_stamp = msgObj.getString("time_stamp");
			response_status = msgObj.getString("response_status");
			path_to_pickup = msgObj.getString("path_to_pickup");
			path_to_dropoff = msgObj.getString("path_to_dropoff");
		} catch (Exception e) {
			String content="parse response_order_from_car  exception :"+e;
	        Log_Steps le=new Log_Steps();
	        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
	        le=null;
		}
        RedisDataUtil.setPathToPickup(order_id, path_to_pickup);
        RedisDataUtil.setPathToDropoff(order_id, path_to_dropoff);
		RedisDataUtil.setOrderReceived( order_id, car_id,response_status);
		
		String content="I get the response form car "+car_id+","+order_id;
        Log_Steps le=new Log_Steps();
        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
        le=null;
        
	}
	public static void proCancelFromCar(JSONObject json,String message) {
		JSONObject msgObj=null;
		String car_id=null;
		String order_id=null;
		String time_stamp=null;
		try {
			msgObj = json.getJSONObject("cancel_from_car");
			car_id = msgObj.getString("car_id");
			order_id = msgObj.getString("order_id");
			time_stamp = msgObj.getString("time_stamp");
		} catch (Exception e) {
			String content="parse cancel_from_car  exception :"+e;
	        Log_Steps le=new Log_Steps();
	        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
	        le=null;
		}
        //通知后台取消成功
		Thread4 thread4=new Thread4( Config.orderChangeStatusURL, order_id, "centerControlSystem", null, "650", null, time_stamp);
		thread4.start();
		RedisDataUtil.setOrderOnOff(order_id, "false");
		RedisDataUtil.delVehicleOrder(car_id);
		String content="I get the cancel from car,so I send 650 to FangYao "+car_id+","+message;
        Log_Steps le=new Log_Steps();
        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
        le=null;
	}
	
}
