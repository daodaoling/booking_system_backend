package com.imooc.o2o.util.copy;

import java.util.HashMap;
import java.util.Map;

//import org.apache.catalina.ha.tcp.SendMessageData;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Session;

import com.imooc.o2o.util.ChatUtil;

import org.jivesoftware.smack.packet.Message.Type;

import net.sf.json.JSONObject;

public class MessageSend {
	public static void main  (String[] args) throws XMPPException {
		//TestPacket testPacket=new TestPacket();
	    //testPacket.testPacket();
		/*
		ConnectionConfiguration config = new ConnectionConfiguration("127.0.0.1", 5222);	//新建连接配置对象，设置服务器IP和监听端口
		
		XMPPConnection connection = new XMPPConnection(config);								//得到基于xmpp协议的连接对象
 
		connection.connect();		//连接服务器
		if(connection.isConnected()) {
			System.out.println("连接成功！！！！");
		}
		connection.login("20181001-0004", "venticar");		//利用用户名和密码登录
		System.out.println("登录无异常！！！");
        //Packet packet = new Data(new DataPacketExtension("jojo@" + server, 2, "this is a message"));
        //connection.sendPacket(packet);
        */
        /** 更改用户状态，available=true表示在线，false表示离线，status状态签名；当你登陆后，在Spark客户端软件中就可以看到你登陆的状态 */
       /* Presence presence = new Presence(Presence.Type.available);
        presence.setStatus("Q我吧");
        connection.sendPacket(presence);
        
        Session session = new Session();
        String sessid = session.nextID();
        connection.sendPacket(session);
        /** 向jojo@192.168.8.32 发送聊天消息，此时你需要用Spark软件登陆jojo这个用户，
         * 这样代码就可以向jojo这个用户发送聊天消息，Spark登陆的jojo用户就可以接收到消息
         **/
        /** Type.chat 表示聊天，groupchat多人聊天，error错误，headline在线用户； 
        //Message message = new Message("jojo@" + server, Type.chat);
        String server = "127.0.0.1";
        Message message = new Message("admin@" + server, Type.chat);
        //Message message = new Message(sessid, Type.chat);
        message.setBody("你好!");
        connection.sendPacket(message);
        
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
		String order_id="4931241295dfb27ba99a823028998388";
		String from_station="1";
		String to_station="3";
		String car_id="001";
		MessageSend packet=new MessageSend();
		XMPPConnection connection=ChatUtil.conAndLogin("20181001-0004", "venticar", "127.0.0.1", 5222);
		String info="{\"cancel_from_bk\":{\"car_id\":\""+car_id+"\",\"order_id\":\""+order_id+"\",\"from_station\":\""+from_station+"\",\"to_station\":\""+to_station+"\"}}";
		System.out.println("info:"+info);
		//MessageSend packet=new MessageSend();
		packet.sendInfoToCar(connection, info, car_id, "127.0.0.1",true);
		//packet.sendMessage("hello world !");
	}
	public void sendInfoToCar(
			XMPPConnection connection,
			String info,
			String car_id,
			String server,
			boolean flag
			) throws XMPPException {
        /** 更改用户状态，available=true表示在线，false表示离线，status状态签名；当你登陆后，在Spark客户端软件中就可以看到你登陆的状态 */
        Presence presence = new Presence(Presence.Type.available);
        presence.setStatus("Q我吧");
        connection.sendPacket(presence);
        
        Session session = new Session();
        String sessid = session.nextID();
        connection.sendPacket(session);
        /** 向jojo@192.168.8.32 发送聊天消息，此时你需要用Spark软件登陆jojo这个用户，
         * 这样代码就可以向jojo这个用户发送聊天消息，Spark登陆的jojo用户就可以接收到消息
         **/
        /** Type.chat 表示聊天，groupchat多人聊天，error错误，headline在线用户； */
        //Message message = new Message("jojo@" + server, Type.chat);
        //String server = "127.0.0.1";
        //System.out.println("car_"+car_id+"@" + server);
        //Message message = new Message("car_"+car_id+"@" + server, Type.chat);
        
        String name=flag?"daodaoling@":"car_"+car_id+"@";
        Message message = new Message( name+ server, Type.chat);
        //Message message = new Message(sessid, Type.chat);
        message.setBody(info);
        connection.sendPacket(message);
        //boolean result=addListener(connection);
        //System.out.println("result:"+result);
        //connection.disconnect();
        
        
	}
	public void sendCancelToCar(
			XMPPConnection connection,
			String info,
			String car_id,
			String server
			) throws XMPPException {
        //Packet packet = new Data(new DataPacketExtension("jojo@" + server, 2, "this is a message"));
        //connection.sendPacket(packet);
        
        /** 更改用户状态，available=true表示在线，false表示离线，status状态签名；当你登陆后，在Spark客户端软件中就可以看到你登陆的状态 */
        Presence presence = new Presence(Presence.Type.available);
        presence.setStatus("Q我吧");
        connection.sendPacket(presence);
        
        Session session = new Session();
        String sessid = session.nextID();
        connection.sendPacket(session);
        /** 向jojo@192.168.8.32 发送聊天消息，此时你需要用Spark软件登陆jojo这个用户，
         * 这样代码就可以向jojo这个用户发送聊天消息，Spark登陆的jojo用户就可以接收到消息
         **/
        /** Type.chat 表示聊天，groupchat多人聊天，error错误，headline在线用户； */
        //Message message = new Message("jojo@" + server, Type.chat);
        //String server = "127.0.0.1";
        System.out.println("car_"+car_id+"@" + server);
        Message message = new Message("car_"+car_id+"@" + server, Type.chat);
        //Message message = new Message(sessid, Type.chat);
        message.setBody(info);
        connection.sendPacket(message);
        //boolean result=addListener(connection);
        //System.out.println("result:"+result);
        //connection.disconnect();
        
	}
	public void sendMessage(String str) throws XMPPException {
		//TestPacket testPacket=new TestPacket();
		//testPacket.testPacket();
		ConnectionConfiguration config = new ConnectionConfiguration("127.0.0.1", 5222);	//新建连接配置对象，设置服务器IP和监听端口
		
		XMPPConnection connection = new XMPPConnection(config);								//得到基于xmpp协议的连接对象
 
		connection.connect();		//连接服务器
		if(connection.isConnected()) {
			System.out.println("连接成功！！！！");
		}
		connection.login("20181001-0004", "venticar");		//利用用户名和密码登录
		System.out.println("登录无异常！！！");
        //Packet packet = new Data(new DataPacketExtension("jojo@" + server, 2, "this is a message"));
        //connection.sendPacket(packet);
        
        /** 更改用户状态，available=true表示在线，false表示离线，status状态签名；当你登陆后，在Spark客户端软件中就可以看到你登陆的状态 */
        Presence presence = new Presence(Presence.Type.available);
        presence.setStatus("Q我吧");
        connection.sendPacket(presence);
        
        Session session = new Session();
        String sessid = session.nextID();
        connection.sendPacket(session);
        /** 向jojo@192.168.8.32 发送聊天消息，此时你需要用Spark软件登陆jojo这个用户，
         * 这样代码就可以向jojo这个用户发送聊天消息，Spark登陆的jojo用户就可以接收到消息
         **/
        /** Type.chat 表示聊天，groupchat多人聊天，error错误，headline在线用户； */
        //Message message = new Message("jojo@" + server, Type.chat);
        String server = "127.0.0.1";
        Message message = new Message("admin@" + server, Type.chat);
        //Message message = new Message(sessid, Type.chat);
        message.setBody(str);
        connection.sendPacket(message);
       // System.out.println("zhunbeijinru！！！");
       // addListener(connection);
        //System.out.println("addListener结束！！！");
        connection.disconnect();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
	public static boolean addListener(XMPPConnection connection) {
		System.out.println("进入addlistener！！！");
		ChatManager cm = connection.getChatManager(); 	//取得聊天管理器
		//Chat chat = cm.createChat("127.0.0.1", null);	//得到与另一个帐号的连接，这里是一对一,@后面是你安装openfire时注册的域
		Chat chat = cm.createChat("192.168.1.210", null);
		Map<String, String> map3 = new HashMap<String, String>();
		Message msg;
		/*
		 * 添加监听器
		 */
		cm.addChatListener(new ChatManagerListener() {
			
			@Override
			public void chatCreated(Chat chat, boolean create) {
				chat.addMessageListener(new MessageListener() {
					
					@Override
					public void processMessage(Chat chat, Message msg) {
						if(null!=msg.getBody()) {
							Map<String,String> map = new HashMap();
							String message=msg.getBody();
							System.out.println("message:"+message);
							try {
								chat.sendMessage("您好！我收到了你的消息:"+message);
								map3.put("flag", "sucess");
							} catch (XMPPException e1) {
								// TODO 自动生成的 catch 块
								e1.printStackTrace();
							}
							
						}
					}
				});
			}
		});
		
		boolean flag=true;
        System.out.println(map3.get("flag"));
		///while(true) {
			//System.out.println(map3.get("flag"));
			//String result=map3.get("flag");
			//if("success".equals(result)) {
			//	flag=false;
				//connection.disconnect();
				//break;
			//	System.out.println("result:"+result);
				//return true;
			//}
		//} 			//死循环，维持该连接不中断
		//connection.disconnect();	//断开连接
		return false;
	}
	
    public  void testPacket() throws XMPPException{
		ConnectionConfiguration config = new ConnectionConfiguration("127.0.0.1", 5222);	//新建连接配置对象，设置服务器IP和监听端口
		XMPPConnection connection = new XMPPConnection(config);	
        try {

            //connection.login("hoojo", "hoojo");
        	connection.login("20181001-0004", "venticar");
        } catch (XMPPException e) {
            e.printStackTrace();
        }
        
        //Packet packet = new Data(new DataPacketExtension("jojo@" + server, 2, "this is a message"));
        //connection.sendPacket(packet);
        
        /** 更改用户状态，available=true表示在线，false表示离线，status状态签名；当你登陆后，在Spark客户端软件中就可以看到你登陆的状态 */
        Presence presence = new Presence(Presence.Type.available);
        presence.setStatus("Q我吧");
        connection.sendPacket(presence);
        
        Session session = new Session();
        String sessid = session.nextID();
        connection.sendPacket(session);
        /** 向jojo@192.168.8.32 发送聊天消息，此时你需要用Spark软件登陆jojo这个用户，
         * 这样代码就可以向jojo这个用户发送聊天消息，Spark登陆的jojo用户就可以接收到消息
         **/
        /** Type.chat 表示聊天，groupchat多人聊天，error错误，headline在线用户； */
        //Message message = new Message("jojo@" + server, Type.chat);
        String server = "127.0.0.1";
        Message message = new Message("admin@" + server, Type.chat);
        //Message message = new Message(sessid, Type.chat);
        message.setBody("h!~ jojo, I'am is hoojo!");
        connection.sendPacket(message);
        
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
