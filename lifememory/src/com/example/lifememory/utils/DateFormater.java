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

	public String getCurrentDate() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	// ��ȡ������
	public String getYMD() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	// �������
	public String getYM() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	// ��ȡ������ ��ʽyyyy-MM-dd
	public String getY_M_D() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	// ��ȡ���� ��ʽyyyy-MM
	public String getY_M() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	// ����yyyy-MM-dd��õ�ǰ�������ڼ�
	// ����yyyy-MM-dd
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
}
