package com.imooc.o2o.web.superadmin;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

//import sun.rmi.runtime.Log;



public class testtttt {
	public static void main(String[] args) {
		SortedMap<String, String> parameters =new TreeMap<String, String>();
		parameters.put("vehicle_id", "123");
		parameters.put("username", "pengzhao");
		parameters.put("app_id", "centerControlSystem");
		Long timeStamp=System.currentTimeMillis();
		System.out.println("timestamp:"+timeStamp);
		parameters.put("timestamp", ""+timeStamp);
		String str=getRandomString2(16);
		parameters.put("hexdigit", str);
		String sign=createSign(parameters);
		System.out.println("sign:"+sign);

		
		
		
		
		
		
		//String actionUrl="http://192.168.10.107/backendapi/api/vehicle/safetyLogin";
		String actionUrl="http://10.0.0.80/backendapi/api/order/changeStatus";
		Map<String, String> headParams=new HashMap<String,String>();
		headParams.put("vehicle_id", "123");
		headParams.put("username", "pengzhao");
		headParams.put("app_id", "centerControlSystem");
		headParams.put("timestamp", ""+timeStamp);
		headParams.put("hexdigit", str);
		headParams.put("sign", sign);
		//System.out.println("sign:"+sign);
		Map<String, String> params=new HashMap<String,String>();
		params.put("vehicle_id", "123");
		params.put("username", "pengzhao");
		params.put("app_id", "centerControlSystem");
		params.put("timestamp", ""+timeStamp);
		params.put("hexdigit",str);
		params.put("sign", sign);
		Map<String, File> files=null;
		try {
			String strs=post(actionUrl, headParams, params, files);
			System.out.println(strs);
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
	}
	
    public static String createSign(SortedMap<String,String> parameters){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();//���в��봫�εĲ�������accsii��������
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
 
        sb.append("key=0d9e6a2c9b5e66372d21609ccac5206f" ); //�������̻��������õ�key
        System.out.println("ǩ���ַ���:"+sb.toString());
//        System.out.println("ǩ��MD5δ���д��" + MD5Util.MD5Encode(sb.toString(), characterEncoding));
        String sign = md5Password(sb.toString()).toUpperCase();
        return sign;
    }
    /**
     * ����32λmd5��
     *
     * @param key
     * @return
     */
    public static String md5Password(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            System.out.println("btInput:"+btInput);
            // ���MD5ժҪ�㷨�� MessageDigest ����
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // ʹ��ָ�����ֽڸ���ժҪ
            mdInst.update(btInput);
            // �������
            byte[] md = mdInst.digest();
            // ������ת����ʮ�����Ƶ��ַ�����ʽ
            //System.out.println("md:"+md);
            int j = md.length;
            //System.out.println("j:"+j);
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
	
	
	public static String post(String actionUrl, Map<String, String> headParams,  
            Map<String, String> params,  
            Map<String, File> files) throws IOException {  
  
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
  
        if (params!=null) {  
            // ������ƴ�ı����͵Ĳ���  
            for (Map.Entry<String, String> entry : params.entrySet()) {  
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
        //System.out.println("sb:"+sb); 
        DataOutputStream outStream = new DataOutputStream(  
                conn.getOutputStream());  
        //if (!TextUtils.isEmpty(sb.toString())) {  
            outStream.write(sb.toString().getBytes());  
        //}  
 
        // ���������־  
        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
        //System.out.println("end_data:"+end_data);
        outStream.write(end_data);  
        outStream.flush();  
        //Log.i("HttpUtil", "conn.getContentLength():"+conn.getContentLength());  
  
        // �õ���Ӧ��  
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
	
	
	
	
	 public static String getRandomString2(int length){
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
}
