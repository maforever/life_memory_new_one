package com.example.lifememory.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormater {
	public static DateFormater instance = new DateFormater();

	public DateFormater() {
	};

	public static DateFormater getInstatnce() {
		return instance;
	}
	
	//返回年月日小时分钟  2013-09-23 13:88
	public String getYMDHT() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	public String getCurrentDate() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	// 获取年月日
	public String getYMD() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	// 获得年月
	public String getYM() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	// 获取年月日 格式yyyy-MM-dd
	public String getY_M_D() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	// 获取年月 格式yyyy-MM
	public String getY_M() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	// 根据yyyy-MM-dd获得当前天是星期几
	// 必须yyyy-MM-dd
	public  String getWeekday(String date){
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdw = new SimpleDateFormat("E");
		Date d = null;
		try {
			d = sd.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdw.format(d);
	}
	
	  //返回年份 yyyy
	  public String getYear() {
	    Date date = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	    String dateStr = sdf.format(date);
	    return dateStr;
	  }
}
