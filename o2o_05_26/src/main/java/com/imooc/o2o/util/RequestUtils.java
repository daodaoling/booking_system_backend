package com.imooc.o2o.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.swing.text.html.parser.Entity;



import com.imooc.o2o.entity.Information;
import com.imooc.o2o.util.copy.Thread4;

//import ch.qos.logback.core.net.SyslogOutputStream;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

public class RequestUtils {

	public static void main(String[] args) throws IOException {
		//for (int i = 0; i < 12; i++) {
            //注意replaceAll前面的是正则表达式
            //String order_id = UUID.randomUUID().toString().replaceAll("-","");
			String order_id="6525546715ec4e90bd91b42045002252";
            System.out.println(order_id);
    		//String order_id="8258285785e9beaaa9550d4067372478";
    		String actionUrl=Config.orderChangeStatusURL;
    		String from_station="P1001";
    		String to_station="P1003";
    		String order_source="app";
    		String area_code="nanning";
    		String vehicle="LZWADAGA0KB998183";
    		String order_status="650";
    		String order_distance="12";
    		String information="{\"order_distance\":\""+order_distance+"\"}";
    		String time="1921234567";
    		sendRequest_order(actionUrl, order_id, "centerControlSystem", vehicle, order_status, information, time);
    		
    		
    		//System.out.println(timeStamp2Date(time, null));
    		
    		System.out.println(RedisDataUtil.get_dispatch_car(area_code, "LZWADAGA6JB645011"));
       // }
/*

*/		
	}
	public static String sendRequest_test(String actionUrl,String order_id,String from_station,String to_station,String order_source,String area_code
			) {
		
		TreeMap<String, String> parameters =new TreeMap<String, String>();
		parameters.put("order_id", order_id);
		parameters.put("from_station", from_station);
		parameters.put("to_station", to_station);
		parameters.put("order_source", order_source);
		parameters.put("area_code", area_code);
		String str=getRandomString2(16);
		String sign=createSign(parameters);

		String strs=null;
		
		try {
			strs=post(actionUrl, parameters);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strs;
	}
	public static String sendRequest_problem(String actionUrl,String app_id,String vehicle_code,String problem,String createtime
			) {
		System.out.println("sendRequest_problem enter");
		SortedMap<String, String> parameters =new TreeMap<String, String>();
		parameters.put("vehicle_code", vehicle_code);
		//System.out.println("createtime:"+createtime);
		parameters.put("createtime", createtime);
		parameters.put("app_id", app_id);
		//parameters.put("status", "9");
		if(problem!=null) {
			parameters.put("problem", problem);
		}
		
		Long timeStamp=System.currentTimeMillis();
		//String seconds=""+(timeStamp/1000);
		//String time=timeStamp2Date(seconds, null);
		//System.out.println("time:"+time);
		parameters.put("timestamp", timeStamp+"");
		String str=getRandomString2(16);
		parameters.put("hexdigit", str);
		String sign=createSign(parameters);
		//System.out.println("sign:"+sign);
		//parameters.put("sign", sign);
		try {
			parameters.put("sign", new String(sign.getBytes("UTF-8"),"UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		String strs=null;
		
		try {
			System.out.println("befor_post");
			strs=post(actionUrl, parameters);
			System.out.println("post:"+strs);
			System.out.println("after_post");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strs;
	}
	public static String sendRequest_vehicle_location_change(String actionUrl,String app_id,String vehicle_code,String area_now,String area_before
			) {
		System.out.println("change location  enter !");
		SortedMap<String, String> parameters =new TreeMap<String, String>();
		parameters.put("vehicle_code", vehicle_code);
		parameters.put("area_code", area_now);
		parameters.put("app_id", app_id);
		if(area_before!=null) {
			parameters.put("area_before", area_before);
		}
		Long timeStamp=System.currentTimeMillis();
		//String seconds=""+(timeStamp/1000);
		//String time=timeStamp2Date(seconds, null);
		parameters.put("timestamp", ""+timeStamp);
		String str=getRandomString2(16);
		parameters.put("hexdigit", str);
		String sign=createSign(parameters);
		//System.out.println("sign:"+sign);
		parameters.put("sign", sign);
		String strs=null;
		try {
			strs=post(actionUrl, parameters);
			System.out.println("post:"+strs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strs;
	}
	public static String sendRequest_vehicle(String actionUrl,String app_id,String vehicle_code,String status,String problem
			) {
		System.out.println("sendRequest_vehicle  enter !");
		SortedMap<String, String> parameters =new TreeMap<String, String>();
		parameters.put("vehicle_code", vehicle_code);
		parameters.put("status", status);
		parameters.put("app_id", app_id);
		if(problem!=null) {
			parameters.put("problem", problem);
		}
		Long timeStamp=System.currentTimeMillis();
		//String seconds=""+(timeStamp/1000);
		//String time=timeStamp2Date(seconds, null);
		parameters.put("timestamp", ""+timeStamp);
		String str=getRandomString2(16);
		parameters.put("hexdigit", str);
		String sign=createSign(parameters);
		//System.out.println("sign:"+sign);
		parameters.put("sign", sign);
		String strs=null;
		try {
			strs=post(actionUrl, parameters);
			System.out.println("post:"+strs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strs;
	}
	public static String sendRequest_online(String actionUrl,String app_id,String vehicle_code,String area_code,String createtime
			,String status,String problem) {
		System.out.println("sendRequest_online enter");
		SortedMap<String, String> parameters =new TreeMap<String, String>();
		parameters.put("vehicle_code", vehicle_code);
		createtime=timeStamp2Date(createtime, null);
		parameters.put("createtime", createtime);
		parameters.put("app_id", app_id);
		parameters.put("status", status);
		if(area_code!=null) {
			parameters.put("area_code", area_code);
		}
		if(problem!=null) {
			parameters.put("problem", problem);
		}
		Long timeStamp=System.currentTimeMillis();
		//String seconds=""+(timeStamp/1000);
		//String time=timeStamp2Date(seconds, null);
		//System.out.println("time:"+time);
		parameters.put("timestamp", timeStamp+"");
		String str=getRandomString2(16);
		parameters.put("hexdigit", str);
		String sign=createSign(parameters);
		//System.out.println("sign:"+sign);
		parameters.put("sign", sign);
		String strs=null;
		try {
			strs=post(actionUrl, parameters);
			System.out.println("post:"+strs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strs;
	}
	
	public static String sendOrder_for_severtimes(String URL_post_order,String order_id,String app_id,String vehicle
			,String order_status,String information,String time) throws IOException {
		int[] a=new int[9];
		a[0]=10;
		a[1]=30;
		a[2]=1*60;
		a[3]=2*60;
		a[4]=5*60;
		a[5]=10*60;
		a[6]=30*60;
		a[7]=1*60*60;
		a[8]=2*60*60;

		int i=0;
		Thread th1=new Thread();
		String response=null;
		//boolean flag=true;
		String flag=null;
		
		//RedisDataUtil.getOrderFlag(jedis, order_id, order_status);
		int[] b=new int[5];
		b[0]=311;
		b[1]=400;
		b[2]=500;
		b[3]=600;
		b[4]=650;
		boolean del_flag=false;
		for (int j = b.length-1; j >=0; j--) {
			if(order_status.equals(b[j]+"")) {
				del_flag=true;
				continue;
			}
			if(del_flag) {
				RedisDataUtil.setOrderFlag( order_id, b[j]+"", "true");
				System.out.println("setorderflag"+b[j]);
			}
		}
		System.out.println("kaishiceshi");
		while(i<a.length) {
			System.out.println("getOrderFlag:"+RedisDataUtil.getOrderFlag( order_id, order_status));
			flag=RedisDataUtil.getOrderFlag(order_id, order_status);
			if(flag!=null) {
				if(flag.equals("true")) {
					System.out.println("进入while循环，true 导致 break");
					i=a.length;
					a=null;
					b=null;
					break;
				}
			}
			try {
				
				response=RequestUtils.sendRequest_order(URL_post_order, order_id, app_id, vehicle, order_status, information,time);
				System.out.println("right");
				JSONObject json = JSONObject.fromObject(response);
				String code=json.getString("code");
				json=null;
				//如果response正常啥也不做，如果不正常记入日志
				System.out.println("response:"+response);
				if(!code.equals("1000")) {
					String content=order_id+":"+response;
			        //Log_Steps le=new Log_Steps();
			        //le.writeEror_to_txt(content);
				}
				String content="Fangyao received my post"+","+order_status+","+order_id+","+URL_post_order+","+vehicle+","+information+","+time;
		        //Log_Steps le=new Log_Steps();
		        //le.writeEror_to_txt(content);
	
				break;
		
			} catch (Exception e) {
				try {
					System.out.println(i);
					System.out.println("URL:"+Config.orderChangeStatusURL);
					//记入数据库
					System.out.println("异常："+e);
					System.out.println("order_status:"+order_status);
					long seconds=System.currentTimeMillis()/1000;
					String time_current=timeStamp2Date(seconds+"", null);
					System.out.println("time_current:"+time_current);
					String content=RedisDataUtil.getDisconInformContent(order_id,order_status);
					System.out.println("content:"+content);
					RedisDataUtil.setDisconInform( order_id, order_status, content);
					
					//首先取出数据库的新的flag的值,如果flag为false则结束掉此进程，但是保留对应的车信息
					flag=RedisDataUtil.getOrderFlag( order_id, order_status);
					System.out.println("flag:"+flag);
					System.out.println("请你开机吧");
					//等一会儿再发
					th1.sleep(a[i]*1000);
					content="Fangyao did not received my post"+","+order_status+","+order_id+","+URL_post_order+","+vehicle+","+information+","+time+","+"So I send it for "+i+"times";
			        //Log_Steps le=new Log_Steps();
			        //le.writeEror_to_txt(content);
					//如果取出值为空则啥也不做
					if(flag!=null) {
						if(flag.equals("true")) {
							System.out.println("true 导致 break");
							
							i=a.length;
							a=null;
							b=null;
							break;
						}
					}else {
						i++;
					}
				} catch (InterruptedException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
			
		}
		System.out.println("i:"+i);
		/*
		if(flag==null) {
			if(i!=a.length) {
				//删除原来记得东西
				RedisDataUtil.delDisconInform(jedis, order_id);
				RedisDataUtil.delDisconInformContent(jedis, order_id);
			}
		}*/
		return null;
	}
	
	
	
	public static String sendRequest_order(String actionUrl,String order_id,String app_id,String vehicle
			,String order_status,String information,String time) throws IOException {
		
		SortedMap<String, String> parameters =new TreeMap<String, String>();
		if(vehicle!=null) {
			//String veh="vehicle"+"["+"vehicle_code"+"]";
			//parameters.put("vehicle", vehicle);
			parameters.put("vehicle", vehicle);
		}
		if(time!=null) {
			time=timeStamp2Date(time, null);
			System.out.println("time:"+time);
			parameters.put("createtime", time);
			
		}
		parameters.put("order_id", order_id);
		parameters.put("app_id", app_id);
		parameters.put("order_status", order_status);
		if(information!=null) {
			parameters.put("information", information);
		}
		
		Long timeStamp=System.currentTimeMillis();
		//String seconds=""+(timeStamp/1000);
		//timeStamp2Date(seconds, null);
		parameters.put("timestamp", ""+timeStamp);
		String str=getRandomString2(16);
		parameters.put("hexdigit", str);
		String sign=createSign(parameters);
		//System.out.println("sign:"+sign);
		parameters.put("sign", sign);
		String strs=null;
		/*
		try {
			strs=post(actionUrl, parameters);
			System.out.println("post:"+strs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		*/
		strs=post(actionUrl, parameters);
		System.out.println("post:"+strs);

		return strs;
	}
	
    public static String createSign(SortedMap<String,String> parameters){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        String order_id="";
        String order_status="";
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            if(k.equals("order_id")) {
            	order_id=(String)entry.getValue();
            	System.out.println("jinru :order_id:"+order_id);
            }
            if(k.equals("order_status")) {
            	order_status=(String)entry.getValue();
            	System.out.println("jinru :order_status:"+order_status);
            }
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=0d9e6a2c9b5e66372d21609ccac5206f" );
        System.out.println("sb:"+sb.toString());
		String content="So I log what I send to FangYao , sb:"+sb.toString();
        Log_Steps le=new Log_Steps();
        //le.writeEror_to_txt(content);
        String sign = md5Password(sb.toString()).toUpperCase();
		//Jedis jedis = new Jedis(Config.redisIP);
		System.out.println("order_id:"+order_id);
		RedisDataUtil.setDisconInformContent( order_id,order_status, sb.toString());
        System.out.println("sign:"+sign);
        return sign;
    }
	
	public static String md5Password(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes("UTF-8");
            //System.out.println("btInput:"+btInput);
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
	
	public static String post(String actionUrl, Map<String, String> headParams ) throws IOException {
		HttpURLConnection conn=null;
		try {
			System.out.println("进入post");
			String BOUNDARY = java.util.UUID.randomUUID().toString();  
			String PREFIX = "--", LINEND = "\r\n";  
			//String MULTIPART_FROM_DATA = "multipart/form-data";  //"application/x-www-form-urlencoded"
			String MULTIPART_FROM_DATA = "multipart/form-data";
			//String MULTIPART_FROM_DATA = "application/x-www-form-urlencoded";
			String CHARSET = "UTF-8";
			//actionUrl=URLEncoder.encode(actionUrl, CHARSET);
			URL uri = new URL(actionUrl);
			System.out.println("actionUrl:"+actionUrl);
			System.out.println("uri:"+uri);
			//uri=URLEncoder.encode(uri,"utf-8");
			
			conn = (HttpURLConnection) uri.openConnection();
			
			//conn.setChunkedStreamingMode(2);
			conn.setReadTimeout(30 * 1000); // ������ʱ��  
			conn.setDoInput(true);// ��������  
			conn.setDoOutput(true);// �������  
			conn.setUseCaches(false); // ������ʹ�û���  
			conn.setRequestMethod("POST");  
			conn.setRequestProperty("connection", "keep-alive");  
			conn.setRequestProperty("Charsert", "UTF-8"); 
			//conn.setRequestProperty("Content-Length", headParams.toString().length()+"");
			//conn.addRequestProperty("", value);
			//conn.setRequestProperty("Charsert", "gbk");
			//conn.setreque
			conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA  
			        + ";boundary=" + BOUNDARY);  
			//conn.setRequestProperty("accept", "application/x-www-form-urlencoded");
			if(headParams!=null){  
			    for(String key : headParams.keySet()){  
			    	//conn.setRequestProperty(key, URLEncoder.encode(headParams.get(key),"utf-8")); 
			        conn.setRequestProperty(key, headParams.get(key));  
			    }  
			} 
			/*
			System.out.println(conn.getHeaderFieldKey(0));
			System.out.println(conn.getHeaderFieldKey(1));
			System.out.println(conn.getHeaderFieldKey(2));
			
			Map<String, List<String>> map=conn.getHeaderFields();
			Iterator<Entry<String, List<String>>> it=map.entrySet().iterator();
			while(it.hasNext()) {
				Map.Entry<String, List<String>>  entry=it.next();
				 System.out.println(entry.getKey() + " :   " + entry.getValue());
			}
			*/
			System.out.println("--------------------------------------------------------------------");
			/*
			 * 
			
			Map<String, List<String>> map2 =conn.getRequestProperties();
			Iterator<Entry<String, List<String>>> it2=map.entrySet().iterator();
			while(it2.hasNext()) {
				Map.Entry<String, List<String>>  entry2=it2.next();
				 System.out.println(entry2.getKey() + " :   " + entry2.getValue());
			}
			
			 */
			
			/*
			Map<String, List<String>> map=conn.getHeaderFields();
			for (String key : map.keySet()) {
				System.out.println(key+":"+map.get(key));
			}
			*/
			StringBuilder sb = new StringBuilder();  
			System.out.println();
			if (headParams!=null) {  
			    for (Map.Entry<String, String> entry : headParams.entrySet()) {  
			        sb.append(PREFIX);  
			        sb.append(BOUNDARY);  
			        sb.append(LINEND);  
			        sb.append("Content-Disposition: form-data; name=\""  
			                + entry.getKey() + "\"" + LINEND);  
			        sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);  
			        sb.append("Content-Transfer-Encoding: 8bit" + LINEND);  
			        sb.append(LINEND);  
			        sb.append(entry.getValue());  
			        sb.append(LINEND);  
			    }  
			      
			}  
			DataOutputStream outStream = new DataOutputStream(  
			        conn.getOutputStream());  
			//if (!TextUtils.isEmpty(sb.toString())) {  
			    outStream.write(sb.toString().getBytes("UTF-8"));  
			//}
			sb=null; 
			System.out.println("开始执行sb");
			//System.out.println("test:"+sb.toString());  
			//byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes("UTF-8");
			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes("UTF-8");
			System.out.println("开始执行write");
			outStream.write(end_data);
			System.out.println("执行write结束");
			outStream.flush(); 
			System.out.println("执行flush结束");
			//conn.setRequestProperty("Accept-Encoding", "identity"); 
			int res = conn.getResponseCode();
			System.out.println("执行getResponseCode结束");
			InputStream in;

			in = conn.getInputStream();  

			
			System.out.println("执行getInputStream结束");
			//conn.setFixedLengthStreamingMode(1024);
			
			System.out.println("getcontentlength:"+conn.getContentLength());
			//if (res == 200) {  
			if (res == 200) { 
				System.out.println("check");
			     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));  
			     System.out.println("after BufferedReader");
			     StringBuffer buffer = new StringBuffer();    
			     String line = "";  
			     System.out.println("after buffer");
			     while ((line = bufferedReader.readLine()) != null){
			           buffer.append(line);    
			     } 
			    System.out.println("buffer.toString():"+buffer.toString());
			    return buffer.toString();  
			}
			
			//System.out.println("buffer.toString():"+buffer.toString());
			outStream.close();  
			//conn.disconnect();
			System.out.println("in.toString():"+in.toString());
			return in.toString();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}  finally {
			conn.disconnect();
			headParams=null;
			
		}
		return null;
	}
	
	private static String getRandomString2(int length){
		    Random random=new Random();
		    StringBuffer sb=new StringBuffer();
		    for(int i=0;i<length;i++){
		       int number=random.nextInt(3);
		       long result=0;
		       switch(number){
		          case 0:
		              result=Math.round(Math.random()*25+65);
		              sb.append(String.valueOf((char)result));
		              break;
		         case 1:
		             result=Math.round(Math.random()*25+97);
		             sb.append(String.valueOf((char)result));
		             break;
		         case 2:     
		             sb.append(String.valueOf(new Random().nextInt(10)));
		             break;
		        }
		     }
		     return sb.toString();
	}
    public static String timeStamp2Date(String seconds,String format) { 
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){ 
            return ""; 
        } 
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        	//format="dd-MMM-yyyy HH:mm:ss:SSS";
        }  
        SimpleDateFormat sdf = new SimpleDateFormat(format); 
        return sdf.format(new Date(Long.valueOf(seconds+"000"))); 
    }
	public static String  errorCodeToMean(String errorCode,String car_id) {
		String[] problem=null;
		String result=null;
		int error_code=0;
		StringBuffer sb = null;
		
		if("SL".equals(car_id.substring(0, 2))) {
			System.out.println("SL系列车辆！");
			problem=new String[15];
			problem[0]="0:底层线控系统问题";
			problem[1]="1:底下16线激光雷达断连";
			problem[2]="2:顶部16线激光雷达断连";
			problem[3]="3:左边16线激光雷达断连";
			problem[4]="4:右边16线激光雷达断连";
			problem[5]="5:底下单线激光雷达断连连";
			problem[6]="6:imu故障";
			problem[7]="7:encoderimu故障";
			problem[8]="8:gps故障";
			problem[9]="9:电池剩余里程少于三千米";
			problem[10]="10:未定义";
			problem[11]="11:底下16线激光雷达超过一半被覆盖";
			problem[12]="12:顶部16线激光雷达超过一半被覆盖";
			problem[13]="13:左边16线激光雷达超过一半被覆盖";
			problem[14]="14:右边16线激光雷达超过一半被覆盖";
			sb=new StringBuffer();
			error_code=Integer.parseInt(errorCode);
			if(error_code<0||error_code>32767) {
				System.out.println("errorCode值错误！！！");
				return null;
			}
			result=Integer.toBinaryString(error_code);
		}else if("LZWADAGA".equals(car_id.substring(0, 8))) {

			System.out.println("LZWADAGA系列车辆！");
			problem=new String[16];
			problem[0]="0:usbcan故障";
			problem[1]="1:前方16线激光雷达断连";
			problem[2]="2:顶部16线激光雷达断连";
			problem[3]="3:左侧16线激光雷达断连";
			problem[4]="4:右侧16线激光雷达断连";
			problem[5]="5:后方16线激光雷达断连";
			problem[6]="6:imu故障";
			problem[7]="7:encoderimu故障";
			problem[8]="8:gps故障";
			problem[9]="9:电池剩余里程少于三千米";
			problem[10]="10:未定义";
			problem[11]="11:前方16线激光雷达超过一半被覆盖";
			problem[12]="12:顶部16线激光雷达超过一半被覆盖";
			problem[13]="13:左边16线激光雷达超过一半被覆盖";
			problem[14]="14:右边16线激光雷达超过一半被覆盖";
			problem[15]="15:后方16线激光雷达超过一半被覆盖";
			sb=new StringBuffer();
			error_code=Integer.parseInt(errorCode);
			if(error_code<0||error_code>65535) {
				System.out.println("errorCode值错误！！！");
				return null;
			}
			result=Integer.toBinaryString(error_code);
		
		}else {
			System.out.println("车编号错误,请注意！");
		}

		int i=0;
		while(i<result.length()) {
			int curr=error_code&1;
			if(curr==1) {
				sb.append(problem[i]+"  ");
			}
			error_code=error_code>>1;
			i++;
		}
		
		System.out.println(sb.toString());
		return sb.toString();
	}
}
