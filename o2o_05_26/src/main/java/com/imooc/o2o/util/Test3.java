package com.imooc.o2o.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class Test3 {
	public static void main(String[] args) throws IOException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Map<String, String> modelMap2 = new HashMap<String, String>();
		modelMap2.put("key4", "value4");
		modelMap.put("key", "value");
		modelMap.put("key2", "value2");
		modelMap.put("key3", modelMap2);
		JSONObject json = JSONObject.fromObject(modelMap);
		System.out.println(json.toString());
		System.out.println(Config.orderChangeStatusURL);
		
		
		
		
	}
}
