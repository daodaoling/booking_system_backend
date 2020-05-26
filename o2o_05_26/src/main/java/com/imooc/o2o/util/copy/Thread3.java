package com.imooc.o2o.util.copy;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import com.imooc.o2o.util.ChatUtil;
import com.imooc.o2o.util.DispatherUtil;
import com.imooc.o2o.util.MessageSend;
import com.imooc.o2o.util.RedisDataUtil;
import com.imooc.o2o.util.RequestUtils;
import com.imooc.o2o.util.XmppConnet;
import com.imooc.o2o.web.superadmin.OrderController;

import redis.clients.jedis.Jedis;

class Thread0 extends Thread{  
    private String order_id;
    private String from_station;
    private String to_station;
    public Thread0(String order_id,String from_station,String to_station) {  
    	this.order_id=order_id; 
    	this.from_station=from_station; 
    	this.to_station=to_station;  
    } 

    public void run() {  
        System.out.println(Thread.currentThread().getName() + " 线程运行开始!");  
        System.out.println(this.order_id);
        System.out.println(this.from_station);
        System.out.println(this.to_station);
		//连接redis获取jedis客户端
		//Jedis jedis = new Jedis("127.0.0.1");
		//order_id="4931241295dfb27ba99a823028998388";
		//from_station="1";
		//to_station="3";
		//System.out.println("order_id:"+order_id);
		//System.out.println("from_station:"+from_station);
		//System.out.println("to_station:"+to_station);
		//计算应该派哪辆车，无车可派则返回null
		String area_code="huanggang";
		DispatherUtil dispatherutil=new DispatherUtil();
		String car_id=dispatherutil.vehDispather( area_code);
		//如果无车可派则通知后台，                   如果有车可派则将订单信息存入redis，然后通知对应的车，然后通知后台
		if(car_id==null||car_id.equals(null)) {
			//while(true) {
			/*
				String received=RequestUtils.sendRequest_order(
						"http://10.0.0.80/backendapi/api/order/changeStatus", 
						order_id, 
						"centerControlSystem", 
						null, 
						"311", 
						"failed",
						null);*/
				//JSONObject json = JSONObject.fromObject(received);
				//String code=json.getString("code");
				//if(code.equals("9999")) {
				//	break;
				//}
			//}
				//modelMap.put("received", "success");
				//return modelMap;
			
		}else {
			//存储相关信息进入redis
			RedisDataUtil.setVehicleOrder(car_id,order_id);
			RedisDataUtil.setOrderVehicle(order_id,car_id);
			RedisDataUtil.setOrderStation(order_id,from_station,to_station);
			//通知对应的车
			MessageSend packet=new MessageSend();
			//XMPPConnection conn=ChatUtil.conAndLogin("20181001-0004", "venticar", "127.0.0.1", 5222);
			
			XMPPConnection conn=XmppConnet.getInstance();
			String info="{\"order_id\":\""+order_id+"\",\"from_station\":\""+from_station+"\",\"to_station\":\""+to_station+"\"}";
			try {
				packet.sendInfo(conn, info, "daodaoling","127.0.0.1");
			} catch (XMPPException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			System.out.println("发送车消息结束！！！");
			//通知后台
			String actionUrl="http://10.0.0.80/backendapi/api/order/changeStatus";
			//如果后台回复的code不对，就继续给它发请求
			//while(true) {
			/*
				String received=RequestUtils.sendRequest_order(
						actionUrl, 
						order_id, 
						"centerControlSystem", 
						"{\"vehicle_code\":\"001\"}", 
						"400", 
						"success",
						null);*/
				//JSONObject json = JSONObject.fromObject(received);
				//String code=json.getString("code");
				//if(code.equals("9999")) {
				//	break;
				//}
			//}

			//modelMap.put("received", "success");
		}
		//MessageSend testpacket=new MessageSend();
		//testpacket.sendMessage("hello world !");
		//Map<String, Object> modelMap = new HashMap<String, Object>();
		//List<Area> list = new ArrayList<Area>();
	//try {
			//list = areaService.getAreaList();
			//modelMap.put("rows", list);
			//modelMap.put("total", list.size());

		//} catch (Exception e) {
			//e.printStackTrace();
			//modelMap.put("success", false);
			//modelMap.put("errMsg", e.toString());
			//System.out.println();
		//}
		for (int i = 0; i < 100000000; i++) {
			System.out.println("hello !!!");
		}
        System.out.println(Thread.currentThread().getName() + " 线程运行结束!");  
    }  
}  
public class Thread3 extends Thread{  
    private String name;  
    public Thread3(String name) {  
        super(name);  
       this.name=name;  
    }  
    public void run() {  
        System.out.println(Thread.currentThread().getName() + " 线程运行开始!");  
        OrderController areacon=new OrderController();
        /*
        try {
        	while(true) {
        		//areacon.setOrder(null, null, null);
        	}
			
		} catch (XMPPException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		*/
        System.out.println(Thread.currentThread().getName() + " 线程运行结束!");  
    }  
}
/* 
public class LLL {  
    public static void main(String[] args) {  
    System.out.println(Thread.currentThread().getName()+"主线程运行开始!");  
        Thread1 mTh1=new Thread1("89","1","3");  
       Thread1 mTh2=new Thread1("20","6","8");  
        mTh1.start();  
        mTh2.start();  
        System.out.println(Thread.currentThread().getName()+ "主线程运行结束!");  

    }  
} 
*/ 