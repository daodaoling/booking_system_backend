
package com.imooc.o2o.util;
 
import java.util.*;
 
/*
* 根据书本2.4.1章节的代码书写
* VM args: -Xms20m -Xmx20M -XX:+HeapDumpOnOutOfMemoryError
*/
 
public class HeapOOM {
int num;
 
public HeapOOM() {
num = 100000;
// TODO Auto-generated constructor stub
}
 
public static void main(String[] args) {
List<HeapOOM> list = new ArrayList<HeapOOM>();
 
while(true)
list.add(new HeapOOM());
 
}
 
}