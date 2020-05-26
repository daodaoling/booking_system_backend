package com.imooc.o2o.util;
/*
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import net.sf.json.JSONObject;
public class Test2 {
	public static void main(String[] args) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Map<String, Object> modelMap2 = new HashMap<String, Object>();
		Map<String, String> modelMap3 = new HashMap<String, String>();

		
		for (int i = 0; i < 100000; i++) {
			String time_stamp=(System.currentTimeMillis()/1000)+"";
			String car_id="LZWADAGA0KB998183";
			String serial=i+"";
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
			modelMap2.put("time_to_dropoff", "245.333");
			modelMap2.put("time_stamp", time_stamp);
			modelMap2.put("car_id",car_id);
			modelMap2.put("serial", serial);
			modelMap2.put("version", "1001");
			modelMap.put("car_state", modelMap2);
			JSONObject json = JSONObject.fromObject(modelMap);
			System.out.println(json.toString());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			XMPPConnection conn=XmppConnet.getInstance();
			MessageSend packet=new MessageSend();
			System.out.println("conn.getUser()"+conn.getUser());
			System.out.println("conn.getConnectionID():"+conn.getConnectionID());
			System.out.println("conn.getHost():"+conn.getHost());
			System.out.println("conn.getUser():"+conn.getRoster());
			
			
			try {
				packet.sendInfo(conn, json.toString(), Config.listenerUsr, Config.xmppIP);
			} catch (XMPPException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		
		
		
		
	}
}
*/