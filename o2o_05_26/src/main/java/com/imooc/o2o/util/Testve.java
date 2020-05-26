package com.imooc.o2o.util;

import java.util.LinkedList;

import com.imooc.o2o.entity.Veh;
import com.imooc.o2o.entity.Vehicle;

public class Testve {
	public static void main(String[] args) {
		LinkedList<String> list=new LinkedList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		System.out.println(list);
		list.remove("c");
		System.out.println(list);
		list.remove(1);
		System.out.println(list);
		list.add(1, "m");
		System.out.println(list);
		
	}
}
