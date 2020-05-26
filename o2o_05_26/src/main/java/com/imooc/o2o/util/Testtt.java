package com.imooc.o2o.util;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
public class Testtt {
	public static void main(String[] args) {
		String IP_xmpp="180.117.162.174";
		String domin_xmpp="192.168.1.210";
		ConnectionConfiguration config = new ConnectionConfiguration(IP_xmpp, 5222);	//新建连接配置对象，设置服务器IP和监听端口
		XMPPConnection connection = new XMPPConnection(config);
		 //SASLAuthentication.supportSASLMechanism("PLAIN");
		try {
			connection.connect();
		} catch (XMPPException e2) {
			// TODO 自动生成的 catch 块
			e2.printStackTrace();
		}		//连接服务器
		if(connection.isConnected()) {
			System.out.println("连接成功！！！！");
		}
		try {
			connection.login("admin","123456");

			
		} catch (XMPPException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}		//利用用户名和密码登录
		
		
		
		
	}
}
