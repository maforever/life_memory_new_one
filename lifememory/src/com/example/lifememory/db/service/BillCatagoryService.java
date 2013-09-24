package com.example.lifememory.db.service;

import java.util.ArrayList;
import java.util.List;

import com.example.lifememory.activity.model.BillCatagoryItem;
import com.example.lifememory.db.PregnancyDiaryOpenHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BillCatagoryService {
	
	private SQLiteDatabase db;
	
	public BillCatagoryService(Context context) {
		PregnancyDiaryOpenHelper helper = new PregnancyDiaryOpenHelper(context);
		db = helper.getReadableDatabase();
	}
	
	//查找所有一级列表
	public List<BillCatagoryItem> findFirstLevel() {
		List<BillCatagoryItem> items = new ArrayList<BillCatagoryItem>();
		BillCatagoryItem item = null;
		Cursor cursor = db.rawQuery("select * from bill_catagory where parentid = 0", null);
		while(cursor.moveToNext()) {
			item = new BillCatagoryItem();
			item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
			item.setName(cursor.getString(cursor.getColumnIndex("name")));
			item.setImageId(cursor.getInt(cursor.getColumnIndex("imageid")));
			items.add(item);
		}
		return items;
	}
	
	//根据父id查找子类别
	public List<BillCatagoryItem> findSecondaryLevelByParentId(int parentId) {
		List<BillCatagoryItem> items = new ArrayList<BillCatagoryItem>();
		BillCatagoryItem item = null;
		Cursor cursor = db.rawQuery("select * from bill_catagory where parentid = ?", new String[]{String.valueOf(parentId)});
		while(cursor.moveToNext()) {
			item = new BillCatagoryItem();
			item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
			item.setName(cursor.getString(cursor.getColumnIndex("name")));
			item.setImageId(cursor.getInt(cursor.getColumnIndex("imageid")));
			item.setParentId(parentId);
			items.add(item);
		}
		return items;
	}
	//添加类别信息
	public boolean addCatagory(BillCatagoryItem item) {
		Cursor cursor = db.rawQuery("select count(*) from bill_catagory where name = ? and parentid = ?", new String[]{item.getName().trim(), String.valueOf(item.getParentId())});
		cursor.moveToFirst();
		Long count = cursor.getLong(0);
		if(count > 0) {
			return false;
		}
		db.execSQL("insert into bill_catagory (name, imageid, parentid) values (?, ?, ?)", new String[]{item.getName(), String.valueOf(item.getImageId()), String.valueOf(item.getParentId())});
		return true;
	}
	
	public void closeDB() {
		db.close();
	}
	
	
	//根据二级类别id查找类别信息     一级类比-二级类比 如  常用-打车
	public String getCatagoryStr(int childid) {
		StringBuffer sb = new StringBuffer();
		
		Cursor cursor = db.rawQuery("select * from bill_catagory where idx = ?", new String[]{String.valueOf(childid)});
		cursor.moveToFirst();
		String childName = cursor.getString(cursor.getColumnIndex("name"));
		int parentid = cursor.getInt(cursor.getColumnIndex("parentid"));
		cursor = db.rawQuery("select name from bill_catagory where idx = ?", new String[]{String.valueOf(parentid)});
		cursor.moveToFirst();
		String parentName = cursor.getString(0);
		sb.append(parentName).append("-").append(childName);
		return sb.toString();
	}
	
	
	/*
	 * 目前由于没有删除类型的功能，这个方法可以舍去，因为可以直接固定写死父类别为0， 子类别为1
	 * 此方法的主要功能是超找默认类别的父类别idx与子类别idx
	 */
//	public BillCatagoryItem findDeafaultCatagoryItem() {
//		BillCatagoryItem item = new BillCatagoryItem();
//		List<Integer> parentIds = new ArrayList<Integer>();
//		
//		//按idx的降序查找所有父类别的idx  , 如果parentIds的大小小于等于0，就是没有父类别，提示不能添加账单信息
//		Cursor cursor = db.rawQuery("select idx from bill_catagory where parentid = 0 order by idx asc", null);
//		while(cursor.moveToNext()) {
//			parentIds.add(cursor.getInt(0));
//		}
//		
//		if(parentIds.size() <= 0) {
//			return item;
//		}
//		
//		for(Integer parentId : parentIds) {
//			cursor = db.rawQuery("select * from bill_catagory where parentid = ?", new String[]{String.valueOf(parentId)});
//			if(cursor.moveToFirst()) {
//				item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
//				item.setName(cursor.getString(cursor.getColumnIndex("name")));
//				item.setParentId(cursor.getInt(cursor.getColumnIndex("parentid")));
//				return item;
//			}else {
//				return item;
//			}
//		}
//		return item;
//	}
//	
	
	//查找所有父类别的信息，名称，预算，图片，已消费金额作为预算listview信息
	public List<BillCatagoryItem> findBudgetInfos() {
		List<BillCatagoryItem> items = new ArrayList<BillCatagoryItem>();
		BillCatagoryItem item;
		Cursor cursor = db.rawQuery("select * from bill_catagory where parentid = 0", null);
		while(cursor.moveToNext()) {
			double totalSpendValue = 0;
			item = new BillCatagoryItem();
			item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
//			Log.i("a", "" + item.getIdx());
			item.setName(cursor.getString(cursor.getColumnIndex("name")));
			item.setParentId(0);
			item.setBugget(cursor.getDouble(cursor.getColumnIndex("budget")));
			item.setImageId(cursor.getInt(cursor.getColumnIndex("imageid")));
			
			Cursor cursor2 = db.rawQuery("select jine from bill_info where outcatagoryparentid = ? and billType = 1", new String[]{String.valueOf(item.getIdx())});
			double spendValue = 0;
			while(cursor2.moveToNext()) {
				spendValue = cursor2.getDouble(0);
				totalSpendValue += spendValue;
			}
			item.setSpendValue(totalSpendValue);
			items.add(item);
		}
		return items;
	}
	
	
	//查找当前月ym的花费
	public List<BillCatagoryItem> findSpendValueByYMForChart(String ym) {
		List<BillCatagoryItem> items = new ArrayList<BillCatagoryItem>();
		BillCatagoryItem item;
		Cursor cursor = db.rawQuery("select idx, name from bill_catagory where parentid = 0", null);
		String dateParam = "%" + ym + "%";
		while(cursor.moveToNext()) {
			double totalValue = 0;
			item = new BillCatagoryItem();
			item.setIdx(cursor.getInt(0));
			item.setName(cursor.getString(1));
			
			Cursor cursor2 = db.rawQuery("select jine from bill_info where outcatagoryparentid = ? and billType = 1 and dateymd like ?", new String[]{String.valueOf(item.getIdx()), dateParam});
			double spendValue = 0;
			while(cursor2.moveToNext()) {
				spendValue = cursor2.getDouble(0);
				totalValue += spendValue;
			}
			if(totalValue > 0) {
				item.setSpendValue(totalValue);
				items.add(item);
			}
		}
		return items;
	}
	
	//查找当前月ym的收入
	public List<BillCatagoryItem> findIncomeValueByYMForChart(String ym) {
		List<BillCatagoryItem> items = new ArrayList<BillCatagoryItem>();
		BillCatagoryItem item;
		Cursor cursor = db.rawQuery("select idx, name from bill_incatagory", null);
		String dateParam = "%" + ym + "%";
		while(cursor.moveToNext()) {
			double totalValue = 0;
			item = new BillCatagoryItem();
			item.setIdx(cursor.getInt(0));
			item.setName(cursor.getString(1));
			
			Cursor cursor2 = db.rawQuery("select jine from bill_info where incatagory = ? and billType = 2 and dateymd like ?", new String[]{item.getName(), dateParam});
			double spendValue = 0;
			while(cursor2.moveToNext()) {
				spendValue = cursor2.getDouble(0);
				totalValue += spendValue;
			}
			if(totalValue > 0) {
				item.setSpendValue(totalValue);
				items.add(item);
			}
		}
		return items;
	}
	//根据idx修改预算值
	public void updateBudgetByIdx(int idx, double budgetValue) {
		db.execSQL("update bill_catagory set budget = ? where idx = ?", new String[]{String.valueOf(budgetValue), String.valueOf(idx)});
	}
	
	//统计总预算值
	public double findTotalBudget() {
		double totalBudget = 0;
		
		Cursor cursor = db.rawQuery("select budget from bill_catagory", null);
		while(cursor.moveToNext()) {
			double currentBudget = cursor.getDouble(0);
			totalBudget += currentBudget;
		}
		
		return totalBudget;
	}
}









