package com.imooc.o2o.web.superadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//import org.jivesoftware.smack.XMPPException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.o2o.entity.Area;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.util.RedisDataUtil;
//import com.imooc.o2o.util.TestPacket;
import com.imooc.o2o.util.RequestUtils;

import redis.clients.jedis.Jedis;

@Controller
@RequestMapping("/superadmin")

public class OrderInformationAccept {
	@Autowired
	private AreaService areaService;
	
	@RequestMapping(value = "/getorderinformation",method=RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> getOrderinformation(String order_id)  {
		Map<String, Object> modelMap_out = new HashMap<String, Object>();
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			//order_id="9867719955e86d00beba679000134625";
			System.out.println("order_id:"+order_id);
			//Jedis jedis = new Jedis("192.168.1.210");
			
			
			//根据具体的order_id查找对应的记录的未发到的信息
			String content=null;
			int[] b=new int[5];
			b[0]=311;
			b[1]=400;
			b[2]=500;
			b[3]=600;
			b[4]=650;
			
			for (int j = 0; j < b.length; j++) {
				
				String flag=RedisDataUtil.getOrderFlag( order_id, b[j]+"");
				System.out.println("flag:"+flag);
			
				content=RedisDataUtil.getDisconInformContent(order_id,b[j]+"");
				System.out.println("result:"+content);
				String order_status=null;
				if(content!=null) {
					//modelMap.put("result", "success");
						String[] strs=content.split("&");
						for (int i = 0; i < strs.length; i++) {
							if(!(strs[i].split("=")[0].equals("hexdigit")||strs[i].split("=")[0].equals("key")||strs[i].split("=")[0].equals("app_id"))) {
								if(strs[i].split("=")[0].equals("order_status")) {
									order_status=strs[i].split("=")[1];
								}
								if(strs[i].split("=")[0].equals("timestamp")) {
									if((b[j]+"").equals("500")) {
										System.out.println("500");
										String start_station_time=RequestUtils.timeStamp2Date(strs[i].split("=")[1].substring(0, 10), null);
										modelMap.put("start_station_time", start_station_time);
									}
									if((b[j]+"").equals("600")) {
										System.out.println("600");
										String end_station_time=RequestUtils.timeStamp2Date(strs[i].split("=")[1].substring(0, 10), null);
										modelMap.put("end_station_time",end_station_time);
									}
								}
								modelMap.put(strs[i].split("=")[0], strs[i].split("=")[1]);
							}
								
						}
				}
				if(order_status!=null) {
					modelMap.put("result", "success");
					//modelMap_out.put(order_status, modelMap);
				}
			
			}
			System.out.println(modelMap.toString().equals("{}"));
			if(modelMap.toString().equals("{}")) {
				modelMap.put("result", "Not Found");
			}
			System.out.println("modelMap:"+modelMap.toString());
			return modelMap;
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			modelMap=null;
			modelMap_out=null;
		}
		return null;
	}
	
	
}
