package com.imooc.o2o.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by CXC on 2017/6/5.
 */
public class Log_Steps {
    /**
     * @将错误信息输入到txt中
     * @param path
     * @throws IOException
     */
    public void writeEror_to_txt(String content,String logPath){
    	//String path="D:pengzhao_log.txt";
    	//System.out.println("Config.logPath:"+Config.logPath);
        File F=new File(logPath+".txt");
        //如果文件不存在,就动态创建文件
        if(!F.exists()){
            try {
				F.createNewFile();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
        }
        FileWriter fw=null;
        //System.out.println(System.currentTimeMillis());
        //long seconds=System.currentTimeMillis()/1000;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss:SSS");

        String formatStr =formatter.format(new Date());
        //String time=RequestUtils.timeStamp2Date(seconds+"", null);
        String writeDate=formatStr+"---"+"information:"+content;
        try {
            //设置为:True,表示写入的时候追加数据
            fw=new FileWriter(F, true);
            //回车并换行
            fw.write(writeDate+"\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(fw!=null){
                try {
					fw.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
            }
        }

    }
    /**
     * @获取系统当前时间
     * @return
     */
    public  String get_nowDate(){

        Calendar D=Calendar.getInstance();
        int year=0;
        int moth=0;
        int day=0;
        int hour=0;
        int minute=0;
        int second=0;
        int minisecond=0;
        year=D.get(Calendar.YEAR);
        moth=D.get(Calendar.MONTH)+1;
        day=D.get(Calendar.DAY_OF_MONTH);
        hour=D.get(Calendar.HOUR);
        minute=D.get(Calendar.MINUTE);
        second=D.get(Calendar.SECOND);
        minisecond=D.get(Calendar.MILLISECOND);
        String now_date=String.valueOf(year)+"-"+String.valueOf(moth)+"-"+String.valueOf(day)+" "+String.valueOf(hour)+":"+String.valueOf(minute)+":"+String.valueOf(second)+":"+String.valueOf(minisecond);
        return now_date;
    }
    public  static String get_nowDay(){

        Calendar D=Calendar.getInstance();
        int year=0;
        int moth=0;
        int day=0;
        int hour=0;
        int minute=0;
        int second=0;
        int minisecond=0;
        year=D.get(Calendar.YEAR);
        moth=D.get(Calendar.MONTH)+1;
        day=D.get(Calendar.DAY_OF_MONTH);
        //hour=D.get(Calendar.HOUR);
        //minute=D.get(Calendar.MINUTE);
        //second=D.get(Calendar.SECOND);
        //minisecond=D.get(Calendar.MILLISECOND);
        String now_day=String.valueOf(year)+"-"+String.valueOf(moth)+"-"+String.valueOf(day);
        return now_day;
    }
    //测试方法
    public static void main(String[] args)  {
    	//int count=19;
    	String content=Log_Steps.get_nowDay();
    	System.out.println(Config.logLocation);
      
        for (int i = 1; i <= 500; i++) {
            System.out.println(Log_Steps.get_nowDay());
            Log_Steps le=new Log_Steps();
            if(i%5==0) {
                le.writeEror_to_txt(content,Config.logLocation+"2019-4-20");
            }else if(i%3==0) {
            	le.writeEror_to_txt(content,Config.logLocation+"2019-4-21");
            }else if(i%2==0) {
            	le.writeEror_to_txt(content,Config.logLocation+"2019-4-22");
            }else {
            	le.writeEror_to_txt(content,Config.logLocation+get_nowDay());
            }
			
		}

        
        

    }
}