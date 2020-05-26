package com.imooc.o2o.util;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class ChatUtil {
	public static XMPPConnection conAndLogin(String user ,String pwd,String IP,int port) {
		ConnectionConfiguration config = new ConnectionConfiguration(IP, port);	//新建连接配置对象，设置服务器IP和监听端口
		XMPPConnection connection = new XMPPConnection(config);								//得到基于xmpp协议的连接对象
		try {
			connection.connect();
		} catch (XMPPException e2) {
			String content=" connecting exception: "+e2;
			Log_Steps le=new Log_Steps();
	        //le.writeEror_to_txt(content);
	        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
			// TODO 自动生成的 catch 块
			e2.printStackTrace();
		}		//连接服务器
		if(connection.isConnected()) {
			System.out.println("连接成功！！！！");
		}
		try {
			connection.login(user, pwd);
		} catch (XMPPException e1) {
			String content=" login exception: "+e1;
			Log_Steps le=new Log_Steps();
	        //le.writeEror_to_txt(content);
	        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}		//利用用户名和密码登录
		System.out.println("登录无异常！！！");
		return connection;
	}
}
