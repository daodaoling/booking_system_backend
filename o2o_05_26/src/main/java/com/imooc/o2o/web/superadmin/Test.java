package com.imooc.o2o.web.superadmin;



import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import com.imooc.o2o.util.Config;
import com.imooc.o2o.util.OrderQueue;
import com.imooc.o2o.util.RedisDataUtil;

import redis.clients.jedis.Jedis;
 
public class Test {
 
	public static void main(String args[]) throws XMPPException {
		//Jedis jedis = new Jedis(Config.redisIP);
		OrderQueue guangzhou_instance=null;
		OrderQueue huanggang_instance=null;
		OrderQueue nanning_instance = null;
		for (int i = 0; i < 60; i++) {
			String area_code;
			if(i%3==0) {
				area_code="guangzhou";
			}else if(i%3==1) {
				area_code="huanggang";
			}else {
				area_code="nanning";
			}
			
			if("guangzhou".equals(area_code)) {
				guangzhou_instance=OrderQueue.getGuangZhouInstance();
				guangzhou_instance.guangzhouqueue.add(i+"");
				RedisDataUtil.setOrderQueue( area_code, guangzhou_instance.guangzhouqueue.toString());
			}else if("huanggang".equals(area_code)) {
				huanggang_instance=OrderQueue.getHuangGangInstance();
				huanggang_instance.huanggangqueue.add(i+"");
				RedisDataUtil.setOrderQueue( area_code, guangzhou_instance.huanggangqueue.toString());
			}else if("nanning".equals(area_code)) {
				nanning_instance=OrderQueue.getNanNingInstance();
				nanning_instance.nanningqueue.add(i+"");
				RedisDataUtil.setOrderQueue( area_code, guangzhou_instance.nanningqueue.toString());
			}
		}
		System.out.println("huanggangqueue"+huanggang_instance.huanggangqueue);
		System.out.println("guangzhouqueue"+guangzhou_instance.guangzhouqueue);
		System.out.println("nanningqueue"+nanning_instance.nanningqueue);
		
	}
 
}
