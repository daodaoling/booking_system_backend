/*package com.imooc.o2o.util;

import org.jivesoftware.smack.XMPPConnection;

import com.imooc.o2o.util.Config;
import com.imooc.o2o.util.Log_Steps;
import com.imooc.o2o.util.RedisDataUtil;

import redis.clients.jedis.Jedis;

public class testthread extends Thread{
	int count=0;
	
	public testthread(int count) {
		this.count=count;
	}
	public void run() {
		MessageSend packet=new MessageSend();
		XMPPConnection conn=XmppConnet.getInstance();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		Test.test(count+"",conn,packet);
		
		
	}
}
*/