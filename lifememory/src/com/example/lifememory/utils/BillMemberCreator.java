package com.example.lifememory.utils;

import android.database.sqlite.SQLiteDatabase;

public class BillMemberCreator {

	public void initMember(SQLiteDatabase db) {
		String[] names = {"无成员", "自己", "爸爸", "妈妈", "老婆", "老公", "儿子", "女儿", "同事", "朋友"};
		for(String name : names) {
			db.execSQL("insert into bill_member (name) values (?)", new String[]{name}); 
		}
	}
}
