package com.imooc.o2o.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

//demo
public class ServletMain {

    public static void main(String[] args) {
        //TestBean tb=new TestBean("age","25");
        //Gson gson=new Gson();
        //传入的参数
        //String param=gson.toJson(tb);
    	String str = "{\"result\":\"success\",\"message\":\"成功！\",\"data\":[{\"name\":\"Tom\",\"age\":\"20\"}]}";
    	JSONObject param = JSONObject.fromObject(str);
        String url="http://10.0.0.80/backendapi/api/order/changeStatus";
        String data=sendPostRequest(url,param.toString());
        //请求回来的数据
        System.out.println(data);
    }
    
    public static String sendPostRequest(String url,String param){
        HttpURLConnection httpURLConnection = null;
        OutputStream out = null; //写
        InputStream in = null;   //读
        int responseCode = 0;    //远程主机响应的HTTP状态码
        String result="";
        try{
            URL sendUrl = new URL(url);
            httpURLConnection = (HttpURLConnection)sendUrl.openConnection();
            //post方式请求
            httpURLConnection.setRequestMethod("POST");
            //设置头部信息
            httpURLConnection.setRequestProperty("headerdata", "ceshiyongde");
            //一定要设置 Content-Type 要不然服务端接收不到参数
            httpURLConnection.setRequestProperty("Content-Type",  "application/x-www-form-urlencoded");
            //指示应用程序要将数据写入URL连接,其值默认为false（是否传参）
            httpURLConnection.setDoOutput(true);
            //httpURLConnection.setDoInput(true); 
            
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setConnectTimeout(30000); //30秒连接超时
            httpURLConnection.setReadTimeout(30000);    //30秒读取超时
            //获取输出流
            out = httpURLConnection.getOutputStream();
            //输出流里写入POST参数
            out.write(param.getBytes());
            out.flush();
            out.close();
            responseCode = httpURLConnection.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8"));
            result =br.readLine();
        }catch(Exception e) {
            e.printStackTrace();
          
      }
    return result;
      
  }
}