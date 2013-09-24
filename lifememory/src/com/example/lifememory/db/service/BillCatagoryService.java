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
	
	//��������һ���б�
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
	
	//���ݸ�id���������
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
	//��������Ϣ
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
	
	
	//���ݶ������id���������Ϣ     һ�����-������� ��  ����-��
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
	 * Ŀǰ����û��ɾ�����͵Ĺ��ܣ��������������ȥ����Ϊ����ֱ�ӹ̶�д�������Ϊ0�� �����Ϊ1
	 * �˷�������Ҫ�����ǳ���Ĭ�����ĸ����idx�������idx
	 */
//	public BillCatagoryItem findDeafaultCatagoryItem() {
//		BillCatagoryItem item = new BillCatagoryItem();
//		List<Integer> parentIds = new ArrayList<Integer>();
//		
//		//��idx�Ľ���������и�����idx  , ���parentIds�Ĵ�СС�ڵ���0������û�и������ʾ��������˵���Ϣ
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
	
	//�������и�������Ϣ�����ƣ�Ԥ�㣬ͼƬ�������ѽ����ΪԤ��listview��Ϣ
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
	
	
	//���ҵ�ǰ��ym�Ļ���
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
	
	//���ҵ�ǰ��ym������
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
	//����idx�޸�Ԥ��ֵ
	public void updateBudgetByIdx(int idx, double budgetValue) {
		db.execSQL("update bill_catagory set budget = ? where idx = ?", new String[]{String.valueOf(budgetValue), String.valueOf(idx)});
	}
	
	//ͳ����Ԥ��ֵ
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









