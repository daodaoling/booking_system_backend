package com.imooc.o2o.web.superadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

//import org.apache.catalina.ha.tcp.SendMessageData;

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
import com.imooc.o2o.util.DispatherUtil;
import com.imooc.o2o.util.RedisDataUtil;
import com.imooc.o2o.util.RequestUtils;
//import com.imooc.o2o.util.copy.Thread1;

import net.sf.json.JSONObject;

import com.imooc.o2o.util.MessageSend;

import redis.clients.jedis.Jedis;

@Controller
@RequestMapping("/superadmin")

public class ThreadTestControler {
	@Autowired
	private AreaService areaService;
	
	@RequestMapping(value = "/test",method=RequestMethod.GET)
	@ResponseBody

	private Map<String, Object> cancelOrder(String order_id) throws XMPPException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//System.out.println("主线程开始！！！");
	    //System.out.println(Thread.currentThread().getName()+"主线程运行开始!");  
        //Thread1 mTh1=new Thread1(order_id);  
        //Thread1 mTh2=new Thread1("B");  
        //mTh1.start();  
        //mTh2.start();  
       // System.out.println(Thread.currentThread().getName()+ "主线程运行结束!");  
		
		
		System.out.println("主线程结束！！！");
		return modelMap;
		
	}
	
	
}
