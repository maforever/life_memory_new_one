package com.example.lifememory.utils;

import android.database.sqlite.SQLiteDatabase;

public class BillMemberCreator {

	public void initMember(SQLiteDatabase db) {
		String[] names = {"�޳�Ա", "�Լ�", "�ְ�", "����", "����", "�Ϲ�", "����", "Ů��", "ͬ��", "����"};
		for(String name : names) {
			db.execSQL("insert into bill_member (name) values (?)", new String[]{name}); 
		}
	}
}
