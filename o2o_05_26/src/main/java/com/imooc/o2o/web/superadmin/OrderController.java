package com.imooc.o2o.web.superadmin;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Session;

import org.jivesoftware.smack.packet.Message.Type;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.o2o.entity.Area;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.util.ChatUtil;
import com.imooc.o2o.util.Config;
import com.imooc.o2o.util.DispatherUtil;
import com.imooc.o2o.util.RedisDataUtil;
import com.imooc.o2o.util.RequestUtils;
import com.imooc.o2o.util.Log_Steps;
import com.imooc.o2o.util.copy.Thread1;
import com.imooc.o2o.util.copy.Thread7;

import net.sf.json.JSONObject;

import com.imooc.o2o.util.MessageSend;
import com.imooc.o2o.util.OrderQueue;

import redis.clients.jedis.Jedis;

@Controller
@RequestMapping("/superadmin")

public class OrderController {
	//@Autowired
	//private AreaService areaService;
	@RequestMapping(value = "/setorder",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> setOrder(String order_id,String from_station,String to_station,String order_source,String area_code){
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
		    order_id = UUID.randomUUID().toString().replaceAll("-","");
		    System.out.println(order_id);
			from_station="P1001";
			to_station="P1003";
			order_source="app";
			area_code="nanning_cw";

			//标记这些信息的来源也是为了更好的测试线程执行情况
		    Thread7 mTh7=new Thread7(order_id,from_station,to_station,order_source,area_code);  
		    mTh7.start(); 
			
		    String content="I get order  from  FangYao  :"+order_id+","+from_station+","+to_station+","+order_source+","+area_code;
		    Log_Steps le=new Log_Steps();
		    le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
		    le=null;
		    
			//收到了php端给的请求后，告诉后台成功收到
			modelMap.put("received", "success");
			return modelMap;
		} catch (Exception e) {
		    String content="Exception when process order from FangYao "+order_id+","+from_station+","+to_station+","+order_source+","+area_code+","+e;
		    Log_Steps le=new Log_Steps();
		    le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
		    le=null;
		}finally {
			modelMap=null;
		}
		return null;
	}
	
	
}
