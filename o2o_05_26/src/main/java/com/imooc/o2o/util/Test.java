package com.imooc.o2o.util;

import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import com.imooc.o2o.util.copy.Thread4;

import net.sf.json.JSONObject;

public class Test {
	public static void main(String[] args) {
		System.out.println("nihao");
		System.out.println("Hello start !");
		for(int i=0;i<10;i++) {
			System.out.println(i);
		}
		System.out.println("end !");
		
	}
	
	public static void  test(String serial,XMPPConnection conn,MessageSend packet) {
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Map<String, Object> modelMap2 = new HashMap<String, Object>();
		Map<String, String> modelMap3 = new HashMap<String, String>();
		String time_stamp=(System.currentTimeMillis()/1000)+"";
		String car_id="LZWADAGA0KB998183";
		//serial="1";
		String errorCode="0";
		modelMap3.put("location", "12");
		modelMap3.put("latitude", "39.9152108931");
		modelMap3.put("longitude", "116.4039006839");
		modelMap3.put("speed", "13");
		modelMap3.put("battery", "92.5");
		modelMap3.put("remaining_range", "23100");
		modelMap3.put("doorState", "0");
		modelMap3.put("autoState", "0");
		modelMap3.put("errorCode", errorCode);
		modelMap2.put("content", modelMap3);
		modelMap2.put("order_id", "123311");
		modelMap2.put("area_code", "nanning");
		modelMap2.put("carState", "1");
		modelMap2.put("dist_to_dropoff", "480.024");
		modelMap2.put("dist_to_pickup", "980.002");
		modelMap2.put("time_to_pickup", "245.333");
		modelMap2.put("time_stamp", time_stamp);
		modelMap2.put("car_id",car_id);
		modelMap2.put("serial", serial);
		modelMap2.put("version", "1001");
		modelMap.put("car_state", modelMap2);
		JSONObject json = JSONObject.fromObject(modelMap);
		System.out.println(json.toString());
		
		//MessageSend packet=new MessageSend();
		
		//XMPPConnection conn=ChatUtil.conAndLogin(Config.acceptHttpUsr, Config.acceptHttpPwd, Config.xmppIP, Config.xmppPort);
		//Long time_stamp=System.currentTimeMillis()/1000;

		String info=json.toString();
		System.out.println(info);
		
		try {
			packet.sendInfo(conn, info, Config.listenerUsr, Config.xmppIP);
		} catch (XMPPException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		//conn.disconnect();
		
		
	
	}
}

