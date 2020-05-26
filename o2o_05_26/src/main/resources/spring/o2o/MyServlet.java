package o2o;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.o2o.util.ChatListener;
import com.imooc.o2o.util.Config;
import com.imooc.o2o.util.RequestUtils;
//import com.imooc.o2o.util.copy.Thread5;

import redis.clients.jedis.Jedis;

public class MyServlet extends HttpServlet {
	@Override
	public void init(ServletConfig config) throws ServletException {
		//Jedis jedis = new Jedis(Config.redisIP);
		//开启定时清理数据线程
		//Thread5 th5=new Thread5(jedis);
		//th5.start();
		ChatListener cl=new ChatListener();
		cl.chatListen();
		
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO �Զ����ɵķ������
		//super.doGet(req, resp);
		//System.out.println("Hello world !");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO �Զ����ɵķ������
		//super.doPost(req, resp);
		//System.out.println("post");
	}
}
