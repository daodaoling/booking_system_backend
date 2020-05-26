package com.imooc.o2o.util.copy;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.imooc.o2o.entity.Information;

public class RequestUtils {

	public static void main(String[] args) {
		String actionUrl="http://10.0.0.80/backendapi/api/order/changeStatus";
		String order_id="4931241295dfb27ba99a823028998388";
		String app_id="centerControlSystem";
		String vehile="vechicle_id:123";
		//Long timeStamp=System.currentTimeMillis();
		//String timeStamp=""+timeStamp_raw;
		String order_status="600";
		String information="{\"order_distance\":\"12345\",\"order_duration\":\"12345\"}";
		//String vechicle= "";
		RequestUtils.sendRequest_order(actionUrl, order_id, app_id, vehile, order_status, information);
	}
	public static void sendRequest_problem(String actionUrl,String app_id,String vehicle_code,String problem,String createtime
			) {
		System.out.println("sendRequest_problem enter");
		SortedMap<String, String> parameters =new TreeMap<String, String>();
		parameters.put("vehicle_code", vehicle_code);
		//System.out.println("createtime:"+createtime);
		parameters.put("createtime", createtime);
		parameters.put("app_id", app_id);
		parameters.put("problem", problem);
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
		try {
			String strs=post(actionUrl, parameters);
			System.out.println("post:"+strs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void sendRequest_vehicle(String actionUrl,String app_id,String vehicle_code,String status,String problem
			) {
		System.out.println("sendRequest_vehicle  enter !");
		SortedMap<String, String> parameters =new TreeMap<String, String>();
		parameters.put("vehicle_code", vehicle_code);
		parameters.put("status", status);
		parameters.put("app_id", app_id);
		parameters.put("problem", problem);
		Long timeStamp=System.currentTimeMillis();
		//String seconds=""+(timeStamp/1000);
		//String time=timeStamp2Date(seconds, null);
		parameters.put("timestamp", ""+timeStamp);
		String str=getRandomString2(16);
		parameters.put("hexdigit", str);
		String sign=createSign(parameters);
		//System.out.println("sign:"+sign);
		parameters.put("sign", sign);
		try {
			String strs=post(actionUrl, parameters);
			System.out.println("post:"+strs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void sendRequest_order(String actionUrl,String order_id,String app_id,String vehicle
			,String order_status,String information) {
		
		SortedMap<String, String> parameters =new TreeMap<String, String>();
		if(vehicle!=null) {
			parameters.put("vehicle", vehicle);
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
		try {
			String strs=post(actionUrl, parameters);
			System.out.println("post:"+strs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public static String createSign(SortedMap<String,String> parameters){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=0d9e6a2c9b5e66372d21609ccac5206f" );
        System.out.println("sb:"+sb.toString());
        String sign = md5Password(sb.toString()).toUpperCase();
        return sign;
    }
	
	public static String md5Password(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
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
        String BOUNDARY = java.util.UUID.randomUUID().toString();  
        String PREFIX = "--", LINEND = "\r\n";  
        String MULTIPART_FROM_DATA = "multipart/form-data";  
        String CHARSET = "UTF-8";  
        URL uri = new URL(actionUrl);  
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();  
        conn.setReadTimeout(30 * 1000); // ������ʱ��  
        conn.setDoInput(true);// ��������  
        conn.setDoOutput(true);// �������  
        conn.setUseCaches(false); // ������ʹ�û���  
        conn.setRequestMethod("POST");  
        conn.setRequestProperty("connection", "keep-alive");  
        conn.setRequestProperty("Charsert", "UTF-8");  
        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA  
                + ";boundary=" + BOUNDARY);
      
        if(headParams!=null){  
            for(String key : headParams.keySet()){  
                conn.setRequestProperty(key, headParams.get(key));  
            }  
        }  
        StringBuilder sb = new StringBuilder();  
  
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
            outStream.write(sb.toString().getBytes());  
        //}  
            
        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
        outStream.write(end_data);  
        outStream.flush();  
        int res = conn.getResponseCode();  
        InputStream in = conn.getInputStream();  
        if (res == 200) {  
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));    
                   StringBuffer buffer = new StringBuffer();    
                 String line = "";    
             while ((line = bufferedReader.readLine()) != null){    
                   buffer.append(line);    
             }    
            return buffer.toString();  
        }  
        outStream.close();  
        conn.disconnect();  
        return in.toString();  
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
        }  
        SimpleDateFormat sdf = new SimpleDateFormat(format); 
        return sdf.format(new Date(Long.valueOf(seconds+"000"))); 
    }
}
