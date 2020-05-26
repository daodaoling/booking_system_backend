package com.imooc.o2o.util.copy;

//import org.apache.catalina.ha.tcp.SendMessageData;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Session;
import org.jivesoftware.smack.packet.Message.Type;

public class TestPacket {
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
		TestPacket testpacket=new TestPacket();
		testpacket.sendMessage("hello world !");
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
        connection.disconnect();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
