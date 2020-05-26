package com.imooc.o2o.web.superadmin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeTest {
	public static void main(String[] args) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Integer> list = new ArrayList<Integer>();
		list.add(12);
		list.add(23);
	try {
			//list = areaService.getAreaList();
			modelMap.put("rows", list);
			modelMap.put("total", list.size());

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			System.out.println();
		}
		System.out.println(modelMap.toString());

	}
	
}
