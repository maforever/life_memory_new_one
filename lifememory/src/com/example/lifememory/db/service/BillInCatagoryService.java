package com.example.lifememory.db.service;

import java.util.ArrayList;
import java.util.List;

import com.example.lifememory.activity.model.BillInCatagory;
import com.example.lifememory.db.PregnancyDiaryOpenHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BillInCatagoryService {
	private SQLiteDatabase db;
	public BillInCatagoryService(Context context) {
		PregnancyDiaryOpenHelper helper = new PregnancyDiaryOpenHelper(context);
		db = helper.getReadableDatabase();
	}
	
	//查找所有收入类别信息
	public List<BillInCatagory> getAllItems() {
		List<BillInCatagory> ics = new ArrayList<BillInCatagory>();
		BillInCatagory ic = null;
		Cursor cursor = db.rawQuery("select * from bill_incatagory", null);
		while(cursor.moveToNext()) {
			ic = new BillInCatagory();
			ic.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
			ic.setName(cursor.getString(cursor.getColumnIndex("name")));
			ics.add(ic);
		}
		return ics;
	}
	
	//添加收入类别信息
	public boolean addItem(BillInCatagory item) {
		String name = item.getName().trim();
		Cursor cursor = db.rawQuery("select count(*) from bill_incatagory where name = ?", new String[]{name});
		cursor.moveToFirst();
		long count = cursor.getLong(0);
		if(count > 0) {
			return false;
		}
		db.execSQL("insert into bill_incatagory (name) values (?)", new String[]{item.getName()});
		return true;
	}
	
	//删除收入类别信息
	public void deleteItem(int idx) {
		db.execSQL("delete from bill_incatagory where idx = ?", new String[]{String.valueOf(idx)});
	}
	
	
	public void closeDB() {
		db.close();
	}
}
