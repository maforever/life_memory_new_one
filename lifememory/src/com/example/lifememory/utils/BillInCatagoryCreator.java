package com.example.lifememory.utils;

import android.database.sqlite.SQLiteDatabase;

public class BillInCatagoryCreator {

	public void initInCatagorys(SQLiteDatabase db) {
		String[] inCatagorys = {"����", "��ְ", "��Ʊ", "����", "�ֺ�", "��Ϣ", "����", "����", "���", "���", "Ӧ�տ�", "���ۿ�", "������", "����"         };
		for(String inCatagory : inCatagorys) {
			db.execSQL("insert into bill_incatagory (name) values (?)", new String[]{inCatagory});
		}
	}
}
