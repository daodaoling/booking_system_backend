package com.imooc.o2o.util.copy;



import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

/**
 * XMPP Client
 * @author    HZ
 * @since    2014-09-01
 * @version    1.0.0
 */
public class Client {
    
    public static XMPPConnection xmpp_conn;
    public static ConnectionConfiguration xmpp_conf;
    
    // 服务器IP
    public static String xmpp_ip = new String("192.168.1.210");
    // 服务器名
    public static String xmpp_host = new String("tsung213");
    // 服务器端口
    public static int xmpp_port = 5222;
    
    // 用户名和密码
    public static String user = new String("venti");
    public static String pass = new String("ventiserver");

    public static void main(String[] args) {        
        conn();
    }
    
    // 连接服务器
    public static void conn(){
        
        try{
            // 配置连接 
            xmpp_conf = new ConnectionConfiguration(xmpp_ip, xmpp_port, xmpp_host);
        	//xmpp_conf = new ConnectionConfiguration(xmpp_ip, xmpp_port);
            xmpp_conf.setReconnectionAllowed(true);      
            xmpp_conf.setSecurityMode(SecurityMode.disabled);       
            xmpp_conf.setSASLAuthenticationEnabled(false);
            xmpp_conf.setCompressionEnabled(false);
        
            // 连接，并根据用户名和密码登录
            xmpp_conn = new XMPPConnection(xmpp_conf);
            xmpp_conn.DEBUG_ENABLED = true;
            xmpp_conn.connect();
            xmpp_conn.login(user, pass);
            
            // 获取相关变量
            String tmp;
            tmp = xmpp_conn.getConnectionID();
            System.out.println("ConnectionID:" + tmp);
            tmp = xmpp_conn.getHost();
            System.out.println("Host:" + tmp);
            tmp = xmpp_conn.getServiceName();
            System.out.println("ServiceName:" + tmp);
            tmp = xmpp_conn.getUser();
            System.out.println("User:" + tmp);
        }
        catch (XMPPException e){
            System.out.println("Error:" + e.toString());
        }
        
    }

}