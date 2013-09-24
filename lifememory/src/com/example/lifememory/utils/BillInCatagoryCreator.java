package com.example.lifememory.utils;

import android.database.sqlite.SQLiteDatabase;

public class BillInCatagoryCreator {

	public void initInCatagorys(SQLiteDatabase db) {
		String[] inCatagorys = {"工资", "兼职", "股票", "基金", "分红", "利息", "奖金", "补贴", "礼金", "租金", "应收款", "销售款", "报销款", "其他"         };
		for(String inCatagory : inCatagorys) {
			db.execSQL("insert into bill_incatagory (name) values (?)", new String[]{inCatagory});
		}
	}
}
