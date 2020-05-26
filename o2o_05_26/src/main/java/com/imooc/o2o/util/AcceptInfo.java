package com.imooc.o2o.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;
import org.springframework.beans.factory.support.GenericTypeAwareAutowireCandidateResolver;

import com.imooc.o2o.entity.HeartbeatInformation;
import com.imooc.o2o.util.copy.Thread1;
import com.imooc.o2o.util.copy.Thread2;
import com.imooc.o2o.util.copy.Thread4;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

public class AcceptInfo {
	public static void main(String[] args) {
		
	}
	public static void proCarState(JSONObject json,String message,Chat chat) {
		
		String speed           = null;
		String car_id          = null;
		String order_id        = null;
		String latitude        = null;
		String heartbeat       = null;
		String area_code       = null;
		String longitude       = null;
		String car_state       = null;
		String time_stamp      = null;
		String error_code      = null;
		String heartbeat_again = null;
		String time_to_pickup  = null;
		String dist_to_pickup  = null;
		String dist_to_dropoff = null;
		String time_to_dropoff = null;
		JSONObject msg_obj     = null;
		JSONObject content_Obj = null;
		
		try {
			msg_obj         = json.getJSONObject("car_state");
			content_Obj     = msg_obj.getJSONObject("content");
			time_stamp      = msg_obj.getString("time_stamp");
			area_code       = msg_obj.getString("area_code");
			time_to_pickup  = msg_obj.getString("time_to_pickup");
			time_to_dropoff = msg_obj.getString("time_to_dropoff");
			dist_to_pickup  = msg_obj.getString("dist_to_pickup");
			dist_to_dropoff = msg_obj.getString("dist_to_dropoff");
			car_state       = msg_obj.getString("carState");
			car_id          = msg_obj.getString("car_id");
			order_id        = msg_obj.getString("order_id");
			error_code      = content_Obj.getString("errorCode");
			heartbeat       = msg_obj.getString("time_stamp");
			heartbeat_again = heartbeat;
			latitude        = content_Obj.getString("latitude");
			longitude       = content_Obj.getString("longitude");
			speed           = content_Obj.getString("speed");
		} catch (Exception e1) {
			Log_Steps le    = new Log_Steps();
			String content  = "parse heartbeat exception:"+e1;
			le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
			le=null;
		}
		System.out.println("chuli");
		vehicleProblemOrderExceptionCheck(error_code, car_id, order_id);
		System.out.println("1");
		errCodeAndCarStateJointCheck(car_state, error_code);
		System.out.println("2");
		errCodeRangeCheck(error_code,car_id);
		System.out.println("3");
		areaChangeCheck(car_id, area_code);
		System.out.println("4");
		boolean flag=carStateUpdate(car_id, area_code, car_state, error_code, time_stamp);
		System.out.println("5");
		String information="{\"heartbeat\":\""+timeStamp2Date(heartbeat, null)+"\",\"heartbeat_0\":\""+heartbeat_again+"\",\"time_to_pickup\":\""+time_to_pickup+"\",\"time_to_dropoff\":\""+time_to_dropoff+"\",\"dist_to_pickup\":\""+dist_to_pickup+"\",\"dist_to_dropoff\":\""+dist_to_dropoff+"\",\"latitude\":\""+latitude+"\",\"longitude\":\""+longitude+"\",\"speed\":\""+speed+"\"}";
		saveVehichleHeartBeat(heartbeat_again, information, car_id);
		System.out.println("6");
		checkVehicleOnline(car_id, order_id, area_code);
		System.out.println("before");
		
		errorCodeCheck(error_code, car_id, time_stamp, heartbeat_again,flag,order_id);
		
		

	}
	
	public static void errCodeAndCarStateJointCheck(String car_state,String error_code) {
		//如果获取的error_code和carstate的值不匹配,比如说carstate为1,
		//但是error_code不是0,自动忽略这条心跳信息,并将此错误记入日志
		if("1".equals(car_state)&&!error_code.equals("0")) {
			System.out.println("车状态良好时候,错误码不得为非0值");
			String content = "error: carState"+car_state+",errorCode"+error_code;
	        Log_Steps le   = new Log_Steps();
	        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
	        le = null;
		}
	}
	
	public static void errCodeRangeCheck(String error_code,String car_id) {
		int error_code_int = Integer.parseInt(error_code);
		if(car_id.contains("SL")) {
			//如果发过来的error_code值不在指定的范围0-2**15-1,
			//将此错误信息记入日志
			if(error_code_int < 0 || error_code_int > 32767) {
				System.out.println("错误码值不在指定范围内");
				String content = "error: error_code"+error_code+"can not be translated,not in the range "+car_id;
		        Log_Steps le   = new Log_Steps();
		        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
		        le=null;
			}
		}
		else if(car_id.contains("LZWADAGA")) {
			//如果发过来的error_code值不在指定的范围0-2**16-1,
			//并将此错误信息记入日志
			if(error_code_int < 0 || error_code_int > 65535) {
				System.out.println("错误码值不在指定范围内");
				String content="error: error_code"+error_code+"can not be translated,not in the range "+car_id;
		        Log_Steps le=new Log_Steps();
		        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
		        le=null;
			}
		}
		else {
			    System.out.println("无效的车辆信息");
			    String content=car_id + "is valid !";
	            Log_Steps   le=new Log_Steps();
	            le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
	            le=null;
		}

	}
	
	public static void areaChangeCheck(String car_id,String area_code) {
		//取出车辆之前所在区域信息，如果之前为空，则通知方瑶位置改变；
		//如果之前的区域跟当前区域相同，则啥也不做；
		//如果当前区域与之前区域不同，则通知方瑶并把新的区域覆盖旧的区域信息
		String area_bef=RedisDataUtil.getVehicleHeartbeatArea(car_id);
		RedisDataUtil.setVehicleHeartbeatArea(car_id, area_code);
		if(area_bef==null || (area_bef!=null&&!area_bef.equals(area_code))) {
			RequestUtils.sendRequest_vehicle_location_change( Config.saveVehicleLocationURL, "centerControlSystem", car_id, area_code, area_bef);
			
			String content="area changed: area_now "+area_code+",area_before "+area_bef;
	        Log_Steps le=new Log_Steps();
	        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
	        le=null;
		}
	}
	
	public static boolean carStateUpdate(String car_id,String area_code,String car_state,String error_code,String time_stamp) {
		System.out.println(car_id+area_code+car_state+error_code+time_stamp);
		//先获取数据库中的carstate，如果数据库中没有存carstate，则取得的值为空
		String carstate_bef = RedisDataUtil.getCarState(car_id,area_code);
		RedisDataUtil.setCarState(car_id, car_state, area_code);
		//如果以前没有carstate相关信息，并且刚才收到的carstate值为1，车可用，则将车加入可用队列，并通知php后台
		//如果以前有carstate相关信息。并且以前的carstate的值为0，现在值变为1，则将车加入可用车队列，并通知php后台
		boolean flag=false;

		if((carstate_bef==null&&"1".equals(car_state))||("0".equals(carstate_bef)&&"1".equals(car_state))) {
			System.out.println("x");
			addVechileToQueue(area_code, car_id);
			RequestUtils.sendRequest_online(Config.vehicleChangeStatusURL, "centerControlSystem", car_id, null, time_stamp,"1",null);

			flag=true;
		
			String content="carstate changed from "+carstate_bef+" to 1,so I send it to FangYao!";
	        Log_Steps le=new Log_Steps();
	        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
	        le=null;
		}
		//String carstate_before=RedisDataUtil.getCarState( car_id, area_code);
		//如果车以前的carstate为空，但是现在的carstate为0,说明车上线了但是不可用，此时应该干什么，待定。
		if(carstate_bef==null&&car_state.equals("0")) {
			
		}
		//如果以前carstate为0，现在为0则啥也不做
		//如果以前为1，现在为1则啥也不做
		//如果以前为1，现在为0则说明车从可用变为了不可用，需要将车从可用队列中移除，移除后要将空隙位置补齐，移动每个元素
		
		String errorcode_bef=RedisDataUtil.getVehicle_error(car_id);
		if(("1".equals(carstate_bef)&&"0".equals(car_state))||("0".equals(errorcode_bef)&&!"0".equals(error_code))) {
			System.out.println("y");
			//String  car_state_=RedisDataUtil.getCarState(car_id, area_code);
			String car_num=RedisDataUtil.get_dispatch_car(area_code, car_id);
			if(car_num!=null) {
				delVehicleFromQueue(car_id, area_code);
			}
			String content="carstate changed from 1 to 0 ,so I remove the car from avaliable  sequence ! "+car_id;
	        Log_Steps le=new Log_Steps();
	        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
	        le=null;
		}
		System.out.println("z");
		return flag;
	}
	public static void addVechileToQueue(String area_code,String car_id) {
		//先看下以前有没有存这个区域的开始信息，
		String num_start=RedisDataUtil.getCount_start( area_code);
		//如果没有存就将开始处设为1
		if(num_start==null) {
			RedisDataUtil.setCount_start(area_code, "1");
		}
		
		//再看看有没有存这个区域的结束信息。
		String num_end=RedisDataUtil.getCount_end( area_code);
		//如果这个区域的结束信息不存在则将其设置为1,如果存在则设置为目前值加1
		if(num_end==null) {
			num_end="1";
			RedisDataUtil.setCount_end( area_code, num_end);
			
		}else {
			int num=Integer.parseInt(num_end);
			num_end=num+1+"";
			RedisDataUtil.setCount_end( area_code,num_end);
		}
		
		//将本辆车的相关排队信息存储到redis数据库
		RedisDataUtil.save_end( area_code, num_end,car_id);
		//将车id和对应的队列中位置编号也存一份，方便以后取值，删除对应值的方便
		RedisDataUtil.save_end( area_code, car_id,num_end);
	}
	
	public static void delVehicleFromQueue(String car_id,String area_code) {

		//通过车的id找到车辆在队列中的序号
		String num=RedisDataUtil.getCarid_Num( car_id,  area_code);
		String start=RedisDataUtil.getCount_start( area_code);

		//从车编号开始向前逐个查询，用前一个覆盖后一个数据，一直到开始指针位置为止
		for (int i = Integer.parseInt(num); i >=Integer.parseInt(start)+1; i--) {
			//找到i和i-1位置编号对应的车
			String car0=RedisDataUtil.getNum_Carid( (i-1)+"", area_code);
			String car1=RedisDataUtil.getNum_Carid( i+"", area_code);
			//删除i位置数据
			if( i == Integer.parseInt(num)) {
				RedisDataUtil.del_dispatch_car(area_code, car1);
			}
			RedisDataUtil.del_dispatch_car(area_code, i+"");
			//将i位置的数据用i-1位置对应数据填充
			RedisDataUtil.save_end(area_code, car0, i+"");
			RedisDataUtil.save_end(area_code, i+"", car0);
		}
		//如果i位置车和最开始的开始指针一致，删除初始位置对应的车辆。
		if(num.equals(start)) {
			System.out.println("num==start");
			RedisDataUtil.del_dispatch_car( area_code, car_id);
		}
		
		//删除开始指针对应位置的序号-车编号 数据并把初始指针后移一位
		if(!RedisDataUtil.getCount_end( area_code).equals(RedisDataUtil.getCount_start(area_code))) {
			RedisDataUtil.del_dispatch_car( area_code, start);
		}
		
		RedisDataUtil.setCount_start(area_code, (Integer.parseInt(start)+1)+"");

		String end=RedisDataUtil.getCount_end( area_code);
		String car_end=RedisDataUtil.getNum_Carid( end, area_code);

		//如果初始指针大于结束指针
		if(Integer.parseInt(RedisDataUtil.getCount_start( area_code))>Integer.parseInt(RedisDataUtil.getCount_end( area_code))) {
			RedisDataUtil.del_dispatch_car( area_code, car_end);
			RedisDataUtil.del_dispatch_car( area_code, end);
			RedisDataUtil.delCount_start(area_code);
			RedisDataUtil.delCount_end(area_code);
		}
		RedisDataUtil.setCarState( car_id, "0", area_code);
	}
	public static void saveVehichleHeartBeat(String heartbeat_again, String information,String car_id) {
		//将经度，纬度，心跳，速度等存入redis
		RedisDataUtil.setVehicleHeartbeat( car_id, information);
		RedisDataUtil.setVehicleHeartbeat_0( car_id, heartbeat_again);
	}
	
	public static void checkVehicleOnline(String car_id,String order_id,String area_code) {
		String vehicleheartbeat_flag=RedisDataUtil.getVehicleHeartbeat_flag( car_id);
		if(vehicleheartbeat_flag==null) {
			RedisDataUtil.setVehicleHeartbeat_flag( car_id, "true");
	        
	        Thread2 mTh2=new Thread2(car_id,area_code,order_id);  
	        mTh2.start(); 

	        
			String content="the car has no VehicleHeartbeat_flag ,so I start to watch it about heartbeat";
	        Log_Steps le=new Log_Steps();
	        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
	        le=null;
			
		}
	}
	
	public static void errorCodeCheck(String error_code,String car_id,String time_stamp,String heartbeat,boolean flag,String order_id) {
		//boolean flag=false;
		System.out.println("jinru error");
		String errorcode_before=RedisDataUtil.getVehicle_error(car_id);
		//将用户发过来的errorCode存入redis并将以前的errorCode覆盖
		RedisDataUtil.setVehicleError(car_id, error_code);
		if(errorcode_before!=null&&("0".equals(errorcode_before)||"0".equals(error_code))&&!error_code.equals(errorcode_before)) {
			System.out.println("problem ocured !!!"+"car_id:"+car_id+" errorCode:"+error_code+"  "+"errorcode_before:"+errorcode_before);
			String status="0".equals(errorcode_before)?"9":"1";
			String problem=error_code.equals("0")?null:error_code;
			if(!(problem==null)) {
				System.out.println(car_id);
				problem=RequestUtils.errorCodeToMean(problem,car_id);
			}
			
			
			System.out.println("problem:"+problem);
			System.out.println("status:"+status);
			System.out.println("car_id:"+car_id);
			if(problem==null) {
				//problem="问题未知";//"问题未知"
			}

			if(!flag) {
				try {
					RequestUtils.sendRequest_online(
							Config.vehicleChangeStatusURL, 
							"centerControlSystem", 
							car_id, 
							null, 
							time_stamp,status,problem);//车辆出现故障，status为9
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
			
			String content="problem ocured !!!"+"car_id:"+car_id+" errorCode:"+error_code+"  "+"errorcode_before:"+errorcode_before;
	        Log_Steps le=new Log_Steps();
	        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
	        le=null;
		}else if(errorcode_before!=null&&!error_code.equals(errorcode_before)) {
			System.out.println("status change!!!"+"car_id:"+car_id+" errorCode:"+error_code+"  "+"errorcode_before:"+errorcode_before);

			String content="status change!!!"+"car_id:"+car_id+" errorCode:"+error_code+"  "+"errorcode_before:"+errorcode_before;
	        Log_Steps le=new Log_Steps();
	        le.writeEror_to_txt(content,Config.logLocation+le.get_nowDay());
	        le=null;
	        System.out.println(car_id);
	        error_code=RequestUtils.errorCodeToMean(error_code,car_id);
			System.out.println("errorCode:"+error_code);
			if(error_code==null) {
				error_code="问题未可知";
			}
			System.out.println("errorCode:"+error_code);
			RequestUtils.sendRequest_problem(
					Config.saveVehicleProblemURL,
					"centerControlSystem",
					car_id,
					error_code, 
					heartbeat);
			System.out.println("jinru");
			//Thread2.checkOrderException(car_id, order_id);
		}
	}
	public static void vehicleProblemOrderExceptionCheck(String error_code,String car_id,String order_id) {
		System.out.println("kjdfkjdk");
		String errorcode_before=RedisDataUtil.getVehicle_error(car_id);
		//将用户发过来的errorCode存入redis并将以前的errorCode覆盖
		//RedisDataUtil.setVehicleError(car_id, error_code);
		if(order_id!=null) {
			if("0".equals(errorcode_before)&&!"0".equals(error_code)) {
				System.out.println("errorcode_before:"+errorcode_before);
				System.out.println("error_code:"+error_code);
				Thread2.checkOrderException(car_id, order_id);
				
			}

			
		}
	}	

	
    public static String timeStamp2Date(String seconds,String format) { 
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){ 
            return ""; 
        } 
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }  
        SimpleDateFormat sdf = new SimpleDateFormat(format); 
        return sdf.format(new Date(Long.valueOf(seconds+"000"))); 
    }
}
