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
import com.imooc.o2o.util.Config;
import com.imooc.o2o.util.DispatherUtil;
import com.imooc.o2o.util.Log_Steps;
import com.imooc.o2o.util.RedisDataUtil;
import com.imooc.o2o.util.RequestUtils;
import com.imooc.o2o.util.XmppConnet;

import net.sf.json.JSONObject;

import com.imooc.o2o.util.MessageSend;

import redis.clients.jedis.Jedis;

@Controller
@RequestMapping("/superadmin")

public class CancelOrderController {
	@Autowired
	private AreaService areaService;
	
	@RequestMapping(value = "/cancelorder",method=RequestMethod.POST)
	@ResponseBody

	private Map<String, Object> cancelOrder(String order_id) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		try {
			//创建一个hashmap，使用hashmap存储收到了订单
			
			//order_id="7c881a711d7c4e3daa25a5b1742303f6";
			System.out.println("order_id:"+order_id);
			String content="somebody want to cancel the order "+order_id;
			Log_Steps le=new Log_Steps();
			le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
			Thread.sleep(500);
			//根据订单编号取出对应的车编号
			String car_id=RedisDataUtil.getOrderVehicle( order_id);
			//通知对应的车去取消订单
			MessageSend packet=new MessageSend();

			XMPPConnection conn=XmppConnet.getInstance();
			Long timestamp=System.currentTimeMillis();
			timestamp=timestamp/1000;
			String info="{\"cancel_from_bk\":{\"car_id\":\""+car_id+"\",\"timestamp\":\""+timestamp+"\",\"order_id\":\""+order_id+"\"}}";
			System.out.println("info:"+info);
			content="I send cancel to chatlisener "+order_id+","+car_id;
			
			//le.writeEror_to_txt(content);
			le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
			packet.sendInfo(conn, info, Config.listenerUsr, Config.xmppIP);
			packet=null;

			
			long start=System.currentTimeMillis();
			int flag=0;
			while(true) {
				String result=RedisDataUtil.getCancelOrder( car_id, order_id);
				if(result!=null) {
					if(result.equals("0")) {
						System.out.println("0取消失败！！！");
						System.out.println("0cancel failed!");
						content="the car refuse to cancel  ,so cancel failed ! "+order_id+","+car_id;
				        
				        //le.writeEror_to_txt(content);
						le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
						flag=1;
						break;
					}
					if(result.equals("1")) {
						System.out.println("1取消成功！！！");
						System.out.println("1cancel success !");
						content="the car accept to cancel ,so cancel success, "+order_id+","+car_id;
				        
				        //le.writeEror_to_txt(content);
						le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
						flag=0;
						break;
					}
				}

				long now=System.currentTimeMillis();
				if(now-start>60000) {
					System.out.println("超时，取消失败！！！");
					System.out.println("time out cancel failed !");
					content="100 second passed ,but the car did not response the cancel message,so cancel failed  "+order_id+","+car_id;
					le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
					le=null;
					flag=1;
					break;
				}
				Thread.sleep(500);
			}
			//String vehicle_code=jedis.hget("cancel_order:"+order_id,"vehicle_code");
			System.out.println("循环结束！");
			System.out.println("circle end !");
			if(flag==1) {
				System.out.println("取消失败");
				System.out.println("failed !");
				
				//RedisDataUtil.delCancelOrder(jedis, car_id, order_id, "0");
				modelMap.put("cancel_result", "failed");

			}else if(flag==0) {
				System.out.println("取消成功");
				System.out.println("success !");
				//RedisDataUtil.setOrder_Cancel_ID(jedis, order_id, car_id);
				//删除取消订单，删除下单时候的相关信息
				RedisDataUtil.delOrderStation( order_id);
				RedisDataUtil.delOrderVehicle(order_id);
				RedisDataUtil.delVehicleOrder(car_id);
				
				//RedisDataUtil.delCancelOrder(jedis, car_id, order_id, "1");
				
				modelMap.put("cancel_result", "success");

			}
			
			//conn.disconnect();
			System.out.println("关闭结束！！！");
			//RedisDataUtil.delOrderRunTime(jedis, order_id);
			return modelMap;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			modelMap=null;
		}
		return null;
	}
	
	
}
