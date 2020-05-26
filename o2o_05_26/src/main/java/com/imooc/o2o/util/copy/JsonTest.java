package com.imooc.o2o.util.copy;

import com.imooc.o2o.entity.CarState;
import com.imooc.o2o.entity.Car_Detail;

//import ch.qos.logback.core.net.SyslogOutputStream;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonTest {
	public static void main(String[] args) {
		//convertObject();
		jsonStrToJava();
	}
	public static void convertObject() {
	Student stu=new Student();
	stu.setName("JSON");
	stu.setAge("23");
	stu.setAddress("北京市西城区");
	//1、使用JSONObject
	JSONObject json = JSONObject.fromObject(stu);
	//2、使用JSONArray
	JSONArray array=JSONArray.fromObject(stu);
	String strJson=json.toString();
	String strArray=array.toString();
	System.out.println("strJson:"+strJson);
	System.out.println("strArray:"+strArray);
	}
	public static void jsonStrToJava(){
		String str = "{\"code\":\"0000\", \"msg\":{\"balance\":\"31503079.02\"}}";
		String str2="{\"car_state\":{\"name\":\"pengzhao\",\"content\":{\"jinru\":\"zhangsan\"}}}";
		String str3=null;
		String str4="nihao";
		JSONObject json = JSONObject.fromObject(str4); 
		JSONObject msgObj = json.getJSONObject("car_state");
		String name = msgObj.getString("name");
		JSONObject llobj=msgObj.getJSONObject("content");
		String jinru=llobj.getString("jinru");
		//System.out.println("jinru:"+jinru);
		System.out.println(JSONObject.fromObject(str2).getJSONObject("car_state").getJSONObject("content").getString("jinru"));
		
/*
 * 
 
		String availableBalance = msgObj.getString("balance");
		System.out.println("availableBalance:"+availableBalance);
		//定义两种不同格式的字符串
		String objectStr="{\"name\":\"JSON\",\"age\":\"24\",\"address\":\"北京市西城区\"}";
		String arrayStr="[{\"name\":\"JSON\",\"age\":\"24\",\"address\":\"北京市西城区\"}]";
		String message="{\"car_state\":{\"name\":\"pengzhao\",\"content\":\"nihao\"}}";
		//1、使用JSONObject
		JSONObject jsonObject=JSONObject.fromObject(objectStr);
		Student carstate=(Student)JSONObject.toBean(jsonObject, Student.class);
		//System.out.println(carstate.getAddress());
		
		JSONObject jsonobject=JSONObject.fromObject(message);
		CarState carstate1=(CarState)JSONObject.toBean(jsonobject, CarState.class);
		
		JSONObject jsonobject_2=JSONObject.fromObject(carstate1.getCar_state());
		Car_Detail Car_Detail=(Car_Detail)JSONObject.toBean(jsonobject_2, Car_Detail.class);
		
		System.out.println("carstate:"+Car_Detail.getContent());
		
		
		
		
		
		//2、使用JSONArray
		JSONArray jsonArray=JSONArray.fromObject(arrayStr);
		//获得jsonArray的第一个元素
		Object o=jsonArray.get(0);
		JSONObject jsonObject2=JSONObject.fromObject(o);
		Student stu2=(Student)JSONObject.toBean(jsonObject2, Student.class);
		//System.out.println(stu2.toString());
		//System.out.println("stu:"+stu);
		//System.out.println("stu2:"+stu2);
		 * 
		 */
		}
		
}

