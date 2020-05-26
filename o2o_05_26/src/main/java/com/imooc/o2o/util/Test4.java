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
import redis.clients.jedis.Jedis;

public class Test4 {
	public static void main(String[] args) throws IOException {
		//Jedis jedis = new Jedis("122.112.215.198");
		String vehicle_code="SL200902002";
		String order_id="123456";
		String cancel_status="1";
		RedisDataUtil.setCancelOrder( vehicle_code, order_id, cancel_status);
	}
}