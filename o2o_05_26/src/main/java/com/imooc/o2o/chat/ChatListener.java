package com.imooc.o2o.chat;
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
import org.springframework.core.ErrorCoded;
import com.imooc.o2o.entity.CarState;
import com.imooc.o2o.entity.HeartbeatInformation;
import com.imooc.o2o.entity.Information;
import com.imooc.o2o.entity.OrderInformation;
import com.imooc.o2o.entity.Vehicle;
import com.imooc.o2o.util.RequestUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;
//throws XMPPException 
public class ChatListener {
 
	public static void main(String args[])  {
		Jedis jedis = new Jedis("127.0.0.1");
		ConnectionConfiguration config = new ConnectionConfiguration("127.0.0.1", 5222);	//新建连接配置对象，设置服务器IP和监听端口
		XMPPConnection connection = new XMPPConnection(config);								//得到基于xmpp协议的连接对象
		try {
			connection.connect();
		} catch (XMPPException e2) {
			// TODO 自动生成的 catch 块
			e2.printStackTrace();
		}		//连接服务器
		if(connection.isConnected()) {
			System.out.println("连接成功！！！！");
		}
		try {
			connection.login("daodaoling", "123456");
		} catch (XMPPException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}		//利用用户名和密码登录
		System.out.println("登录无异常！！！");
		ChatManager cm = connection.getChatManager(); 	//取得聊天管理器
		//Chat chat = cm.createChat("127.0.0.1", null);	//得到与另一个帐号的连接，这里是一对一,@后面是你安装openfire时注册的域
		Chat chat = cm.createChat("192.168.1.210", null);
		/*
		 * 添加监听器
		 */
		cm.addChatListener(new ChatManagerListener() {
			
			@Override
			public void chatCreated(Chat chat, boolean create) {
				chat.addMessageListener(new MessageListener() {
					
					@Override
					public void processMessage(Chat chat, Message msg) {
						if(null!=msg.getBody()) {
							Map<String,String> map = new HashMap();
							String message=msg.getBody();
							System.out.println("message:"+message);
							try {
								chat.sendMessage("您好！我收到了你的消息:"+message);
							} catch (XMPPException e1) {
								// TODO 自动生成的 catch 块
								e1.printStackTrace();
							}
							try {	
									//Json解析获取数据
									JSONObject json = JSONObject.fromObject(message);
									JSONObject msgObj = json.getJSONObject("car_state");
									JSONObject content_Obj = msgObj.getJSONObject("content");
									String car_id = msgObj.getString("car_id");
									String heartbeat = msgObj.getString("time_stamp");
									String latitude=content_Obj.getString("latitude");
									//System.out.println("latitude:"+latitude);
									String longitude=content_Obj.getString("longitude");
									//System.out.println("longitude:"+longitude);
									System.out.println("car_id:"+car_id);
									String errorCode=content_Obj.getString("errorCode");
									String speed=content_Obj.getString("speed");
									//将解析出来的heartbeat心跳转化为年月日时分秒格式
									heartbeat=timeStamp2Date(heartbeat, null);
									//将经度，纬度，心跳，速度等存入heartbeatinformation对象
									HeartbeatInformation heartbeatinformation=new HeartbeatInformation();
									heartbeatinformation.setHeartbeat(heartbeat);
									heartbeatinformation.setLatitude(latitude);
									heartbeatinformation.setLongitude(longitude);
									heartbeatinformation.setSpeed(speed);
									//通过一个heartbeatinformation对象的toString()方法拿到整体的数据并存入map
									map.put("information", heartbeatinformation.toString());
									//将数据存入redis
									jedis.hmset("heartbeat:"+car_id,map);
									//String information=jedis.hget("heartbeat:"+car_id, "information");
									//取出之前本车辆的errorCode作为errorcode_before
									String errorcode_before=jedis.hget("vehicle_error:"+car_id, "errorCode");
									//System.out.println("information:"+information);
									//System.out.println("errorcode_before:"+errorcode_before);
									//将用户发过来的errorCode存入redis并将以前的errorCode覆盖
									Map<String,String> map0 = new HashMap();
									map0.put("errorCode", errorCode);
									jedis.hmset("vehicle_error:"+car_id,map0);
									
									//System.out.println(jedis.hmget("heartbeat:"+car_id, "information"));
									chat.sendMessage(car_id+"您好！我收到了你的消息:"+message);
									//System.out.println("Hello World !");
									//String errorcode_before=null;
									//System.out.println("errorcode_before:"+errorcode_before+", errorCode:"+errorCode);
									if(errorcode_before.equals("0")&&!errorcode_before.equals(errorCode)) {
										//System.out.println("problem ocured !!!");
										System.out.println("problem ocured !!!"+"car_id:"+car_id+" errorCode:"+errorCode+"  "+"errorcode_before:"+errorcode_before);
										RequestUtils.sendRequest_vehicle(
												"http://10.0.0.80/backendapi/api/vehicle/changeStatus",
												"centerControlSystem", 
												car_id,
												"9",
												errorCode);
									}else if(!errorcode_before.equals(errorCode)) {
										//System.out.println("status change!!!");
										System.out.println("status change!!!"+"car_id:"+car_id+" errorCode:"+errorCode+"  "+"errorcode_before:"+errorcode_before);
										if(errorCode.equals("0")) {
											errorCode="1";
										}else {
											errorCode="9";
										}
										RequestUtils.sendRequest_problem(
												"http://10.0.0.80/backendapi/api/vehicle/saveVehicleProblem",
												"centerControlSystem",
												car_id,
												errorCode, 
												heartbeat);


									}
								} catch (Exception e) {
									// TODO 自动生成的 catch 块
									e.printStackTrace();
							}

						}
					}
				});
			}
		});
		
		try {
			chat.sendMessage("こんにちは");
		} catch (XMPPException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}		//发送消息
		
		while(true); 			//死循环，维持该连接不中断
		
		//connection.disconnect();	//断开连接
		
 
	}
    public static String timeStamp2Date(String seconds,String format) { 
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){ 
            return ""; 
        } 
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }  
        SimpleDateFormat sdf = new SimpleDateFormat(format); 
        return sdf.format(new Date(Long.valueOf(seconds+"000"))); 
    }
}
