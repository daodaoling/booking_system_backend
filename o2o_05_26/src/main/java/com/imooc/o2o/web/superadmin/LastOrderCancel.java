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

public class LastOrderCancel {
	//@Autowired
	//private AreaService areaService;
	@RequestMapping(value = "/lastOrderCancel",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> lastOrderCancel(String order_id){
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			
			System.out.println(order_id);
		    String content="The last order from FangYao "+order_id;
		    Log_Steps le=new Log_Steps();
		    le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
		    le=null;
		} catch (Exception e) {
		    String content="Exception when process order from FangYao "+order_id;
		    Log_Steps le=new Log_Steps();
		    le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
		    le=null;
		}finally {
			modelMap=null;
		}
		return null;
	}
	
	
}

