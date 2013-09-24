package com.example.lifememory.db.service;

import java.util.ArrayList;
import java.util.List;

import com.example.lifememory.activity.model.BillMember;
import com.example.lifememory.db.PregnancyDiaryOpenHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BillMemberService {
	private SQLiteDatabase db;
	public BillMemberService(Context context) {
		PregnancyDiaryOpenHelper helper = new PregnancyDiaryOpenHelper(context);
		db = helper.getReadableDatabase();
	}
	
	//查询左右成员信息
	public List<BillMember> listAllMember() {
		List<BillMember> members = new ArrayList<BillMember>();
		BillMember member;
		Cursor cursor = db.rawQuery("select * from bill_member", null);
		while(cursor.moveToNext()) {
			member = new BillMember();
			member.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
			member.setName(cursor.getString(cursor.getColumnIndex("name")));
			members.add(member);
		}
		return members;
	}
	//根据idx删除
	public void deleteItemByIdx(int idx) {
		db.execSQL("delete from bill_member where idx = ?", new String[]{String.valueOf(idx)});
	}
	
	//添加member信息
	public void addMember(BillMember member) {
		db.execSQL("insert into bill_member (name) values (?)", new String[]{member.getName()});
	}
	
	public void closeDB() {
		db.close();
	}
	
}
