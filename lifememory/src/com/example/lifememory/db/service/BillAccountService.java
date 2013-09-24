package com.example.lifememory.db.service;

import java.util.ArrayList;
import java.util.List;

import com.example.lifememory.activity.model.Bill;
import com.example.lifememory.activity.model.BillAccountItem;
import com.example.lifememory.db.PregnancyDiaryOpenHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BillAccountService {
	private SQLiteDatabase db = null;
	public BillAccountService(Context context) {
		PregnancyDiaryOpenHelper helper = new PregnancyDiaryOpenHelper(context);
		db = helper.getReadableDatabase();
	}
	
	//���������˻���Ϣ
	public List<BillAccountItem> getAllAccounts() {
		List<BillAccountItem> items = new ArrayList<BillAccountItem>();
		BillAccountItem item = null;
		Cursor cursor = db.rawQuery("select * from bill_account", null);
		while(cursor.moveToNext()) {
			item = new BillAccountItem();
			item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
			item.setCatagoryname(cursor.getInt(cursor.getColumnIndex("catagoryname")));
			item.setName(cursor.getString(cursor.getColumnIndex("name")));
			item.setImageId(cursor.getInt(cursor.getColumnIndex("imageid")));
			item.setDangqianyue(cursor.getDouble(cursor.getColumnIndex("dangqianyue")));
			items.add(item);
		}
		return items;
	}
	

	
	public double getJingZiChan() {
		double jingzichan = 0;
		Cursor cursor = db.rawQuery("select dangqianyue from bill_account", null);
		while(cursor.moveToNext()) {
			jingzichan += cursor.getDouble(0);
		}
		return jingzichan;
	}
	
	
	//����idx�����˻���Ϣ��������ʾ�����˻����б�ֻ��Ҫidx��name����
	public BillAccountItem findItemById(int idx) {
		BillAccountItem item = new BillAccountItem();
		Cursor cursor = db.rawQuery("select * from bill_account where idx = ? ", new String[]{String.valueOf(idx)});
		cursor.moveToFirst();
		item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
		item.setName(cursor.getString(cursor.getColumnIndex("name")));
		item.setImageId(cursor.getInt(cursor.getColumnIndex("imageid")));
		return item;
	}
	
	//����idx�����˻�����ϸ��Ϣ,�����޸�ǰ��ʾ����
	public BillAccountItem findItemDetailById(int idx) {
		BillAccountItem item = new BillAccountItem();
		Cursor cursor = db.rawQuery("select * from bill_account where idx = ? ", new String[]{String.valueOf(idx)});
		cursor.moveToFirst();
		item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
		item.setCatagoryname(cursor.getInt(cursor.getColumnIndex("catagoryname")));
		item.setName(cursor.getString(cursor.getColumnIndex("name")));
		item.setBizhong(cursor.getString(cursor.getColumnIndex("bizhong")));
		item.setDangqianyue(cursor.getDouble(cursor.getColumnIndex("dangqianyue")));
		String booleanTemp = cursor.getString(cursor.getColumnIndex("isnotice"));
		if("false".equals(booleanTemp)) {
			item.setNotice(false);
		}else {
			item.setNotice(true);
		}
		item.setNoticeValue(cursor.getDouble(cursor.getColumnIndex("noticevalue")));
		item.setBeizhu(cursor.getString(cursor.getColumnIndex("beizhu")));
		return item;
	}

	
	
	public boolean addAccount(BillAccountItem item) {
		String accountName = item.getName().trim();
		Cursor cursor = db.rawQuery("select count(*) from bill_account where name = ? and catagoryname = ?", new String[]{accountName, String.valueOf(item.getCatagoryname())});
		cursor.moveToFirst();
		Long count = cursor.getLong(0);
		if(count > 0) {
			return false;
		}
		db.execSQL("insert into bill_account (catagoryname, name, bizhong, dangqianyue, isnotice, noticevalue, imageid, beizhu) values (?, ?, ?, ?, ?, ?, ?, ?)", new String[]{
				String.valueOf(item.getCatagoryname()), item.getName(), item.getBizhong(), String.valueOf(item.getDangqianyue()),
				String.valueOf(item.isNotice()), String.valueOf(item.getNoticeValue()), String.valueOf(item.getImageId()), item.getBeizhu()
				
		});
		return true;
	}
	
	//����idɾ���˻���Ϣ
	public void deleteItemById(int idx) {
		db.execSQL("delete from bill_account where idx = ?", new String[]{String.valueOf(idx)});
	}
	
	public void updateItem(BillAccountItem item) {
		db.execSQL("update bill_account set catagoryname = ?, name = ?, bizhong = ?, dangqianyue = ?, isnotice = ?, noticevalue = ?, imageid =?, beizhu = ? where idx = ?", 
				new String[]{
				String.valueOf(item.getCatagoryname()), item.getName(), item.getBizhong(), String.valueOf(item.getDangqianyue()),
				String.valueOf(item.isNotice()), String.valueOf(item.getNoticeValue()), String.valueOf(item.getImageId()), item.getBeizhu(), String.valueOf(item.getIdx())
		});
	}
	
	//֧����������ӽ����Ĭ��account��Ϣ
	public BillAccountItem findItemsForAddViews() {
		BillAccountItem item = null;
		Cursor cursor = db.rawQuery("select * from bill_account where catagoryname = 1", null);
		if(cursor.moveToFirst()) {
			item = new BillAccountItem();
			item.setCatagoryname(1);
			item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
			item.setName(cursor.getString(cursor.getColumnIndex("name")));
		}else {
			cursor = db.rawQuery("select * from bill_account where catagoryname = 2", null);
			if(cursor.moveToFirst()) {
				item = new BillAccountItem();
				item.setCatagoryname(1);
				item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
				item.setName(cursor.getString(cursor.getColumnIndex("name")));
				return item;
			}else {
				cursor = db.rawQuery("select * from bill_account where catagoryname = 3", null);
				if(cursor.moveToFirst()) {
					item = new BillAccountItem();
					item.setCatagoryname(1);
					item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
					item.setName(cursor.getString(cursor.getColumnIndex("name")));
					return item;
				}else {
					cursor = db.rawQuery("select * from bill_account where catagoryname = 4", null);
					if(cursor.moveToFirst()) {
						item = new BillAccountItem();
						item.setCatagoryname(1);
						item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
						item.setName(cursor.getString(cursor.getColumnIndex("name")));
						return item;
					}
				}
			}
		}
		return item;
	}
	
	//ת����ӽ����Ĭ��ת��account��Ϣ  ˳���Ǵ�� ���ÿ��� ����֧���� �ֽ�
	public BillAccountItem findItemsForAddTransferInViews() {
		BillAccountItem item = null;
		Cursor cursor = db.rawQuery("select * from bill_account where catagoryname = 3", null);
		if(cursor.moveToFirst()) {
			item = new BillAccountItem();
			item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
			item.setCatagoryname(3);
			item.setName(cursor.getString(cursor.getColumnIndex("name")));
		}else {
			cursor = db.rawQuery("select * from bill_account where catagoryname = 2", null);
			if(cursor.moveToFirst()) {
				item = new BillAccountItem();
				item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
				item.setCatagoryname(3);
				item.setName(cursor.getString(cursor.getColumnIndex("name")));
			}else {
			    cursor = db.rawQuery("select * from bill_account where catagoryname = 4", null);
				if(cursor.moveToFirst()) {
					item = new BillAccountItem();
					item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
					item.setCatagoryname(3);
					item.setName(cursor.getString(cursor.getColumnIndex("name")));
				}else {
					cursor = db.rawQuery("select * from bill_account where catagoryname = 1", null);
					if(cursor.moveToFirst()) {
						item = new BillAccountItem();
						item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
						item.setCatagoryname(3);
						item.setName(cursor.getString(cursor.getColumnIndex("name")));
					}
				}
			}
		}
		return item;
	}
//	//ת����ӽ����Ĭ��ת��account��Ϣ    ˳�����ֽ����ÿ��� ��� ����֧��
//	public BillAccountItem findItemsForAddTransferOutViews() {
//		BillAccountItem item = null;
//		Cursor cursor = db.rawQuery("select * from bill_account where catagoryname = 1", null);
//		if(cursor.moveToFirst()) {
//			item = new BillAccountItem();
//			item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
//			item.setCatagoryname(1);
//			item.setName(cursor.getString(cursor.getColumnIndex("name")));
//		}
//		return item;
//	}
	
	public void closeDB() {
		db.close();
	}
	
	/*
	 * �����˻����Ͳ�����Ӧ���˻���Ϣ  // 1�ֽ� �� 2���ÿ��� 3��� 4����֧��
	 */
	//�ֽ����͵��˻�
	public List<BillAccountItem> findItemsByAccountCatagory(int catagoryType) {
		List<BillAccountItem> items = new ArrayList<BillAccountItem>();
		BillAccountItem item = null;
		Cursor cursor = db.rawQuery("select * from bill_account where catagoryname = ?", new String[]{String.valueOf(catagoryType)});
		while(cursor.moveToNext()) {
			item = new BillAccountItem();
			item.setName(cursor.getString(cursor.getColumnIndex("name")));
			item.setBizhong(cursor.getString(cursor.getColumnIndex("bizhong")));
			item.setDangqianyue(cursor.getDouble(cursor.getColumnIndex("dangqianyue")));
			items.add(item);
		}
//		Log.i("a", "items size =" + items.toString());
		return items;
	}
	
	//�Ƿ���ʾ��������ʾ�Ի���
	public String ifShowNotice(Bill bill) {
		StringBuilder content = new StringBuilder();
		BillAccountItem item = null;
		
		double jine = Double.parseDouble(bill.getJine());
		double resultValue = 0;
		double inValue = 0;
		double outValue = 0;
		double inResultValue = 0;
		double outResultValue = 0;
		double inNoticeValue = 0;
		double outNoticeValue = 0;
		String inName, outName;
		if(bill.getBillType() == 1) {
			//֧��
			int accountId = bill.getAccountid();
			item = this.findItemDetailById(accountId);
			double accountYue = item.getDangqianyue();
			resultValue = accountYue - jine;
			if(item.isNotice() && (resultValue <= item.getNoticeValue())) {
				content.append("�����˻�").append(item.getName()).append("�ĵ�ǰ����Ѿ�С�ڻ����").append(item.getNoticeValue());
				return content.toString();
			}
			
		}else if(bill.getBillType() == 2) {
			//����
			int accountId = bill.getAccountid();
			item = this.findItemDetailById(accountId);
			double accountYue = item.getDangqianyue();
			resultValue = accountYue + jine;
			if(item.isNotice() && (resultValue <= item.getNoticeValue())) {
				content.append("�����˻�").append(item.getName()).append("�ĵ�ǰ����Ѿ�С�ڻ����").append(item.getNoticeValue());
				return content.toString();
			}
		}else if(bill.getBillType() == 3) {
			//ת��
			item = this.findItemDetailById(bill.getTransferInAccountId());
			inValue = item.getDangqianyue();
			inNoticeValue = item.getNoticeValue();
			inName = item.getName();
			item = this.findItemDetailById(bill.getTransferOutAccountId());
			outValue = item.getDangqianyue();
			outNoticeValue = item.getNoticeValue();
			outName = item.getName();
			inResultValue = inValue + jine;
			outResultValue = outValue - jine;
			String inAccountStr = "";
			String outAccountStr = "";
			if(item.isNotice() && (inResultValue <= inNoticeValue)) {
				inAccountStr = inName;
				content.append("��ת����˻�").append(inAccountStr).append("������Ѿ�С�ڻ����").append(inNoticeValue);
			}
			if(item.isNotice() && (outResultValue <= outNoticeValue)) {
				outAccountStr = outName;
				if(content.length() > 0) {
					content.append(",ת�����˻�").append(outAccountStr).append("������Ѿ�С�ڻ����").append(outNoticeValue);
				}else {
					content.append("��ת�����˻�").append(outAccountStr).append("������Ѿ�С�ڻ����").append(outNoticeValue);
				}
			}
			
//			Log.i("a", "invalue = " + inValue + " inNoticeValue = " + inNoticeValue + " inname = " + inName);
//			Log.i("a", "outvalue = " + outValue + " outNoticeValue = " + outNoticeValue + " outname = " + outName);
//			Log.i("a", "inResultValue = " + inResultValue + " outResultValue = " + outResultValue);
			
			return content.toString();
			
		}
		
		Log.i("a", "billtype = " + bill.getBillType() + " resultValue = " + resultValue + " noticeValue = " + item.getNoticeValue());
		
		
		
		return content.toString();
	}
	
	//ȡ������
	public void cancelNotice(int idx) {
		db.execSQL("update bill_account set isnotice = ? where idx = ?", new String[]{String.valueOf(false), String.valueOf(idx)});
	}
	
	
	/**
	 * ���˵���Ϣ�޸�ʱ����������ͣ�֧�������룬ת�ˣ��� �˻���ѡ��ʱ��ͬʱ�޸���������˻���Ϣ
	 */
	
	public void updateOutAccount(Bill bill) {
		double accountYue = 0;
		Cursor cursor = db.rawQuery("select dangqianyue from bill_account where idx = ?", new String[]{String.valueOf(bill.getAccountid())});
		cursor.moveToFirst();
		accountYue = cursor.getDouble(0);
		double currentAccountYue = accountYue - Float.parseFloat(bill.getJine());
		db.execSQL("update bill_account set dangqianyue = ? where idx = ?", new String[]{String.valueOf(currentAccountYue), String.valueOf(bill.getAccountid())});
		
	}
	
	
	public void updateInAccount(Bill bill) {
		double accountYue = 0;
		Cursor cursor = db.rawQuery("select dangqianyue from bill_account where idx = ?", new String[]{String.valueOf(bill.getAccountid())});
		cursor.moveToFirst();
		accountYue = cursor.getDouble(0);
		double currentAccountYue = accountYue + Float.parseFloat(bill.getJine());
		db.execSQL("update bill_account set dangqianyue = ? where idx = ?", new String[]{String.valueOf(currentAccountYue), String.valueOf(bill.getAccountid())});
	}
	
	public void updateTransferAccount(Bill bill) {
		double inAccountYue = 0;
		double outAccountYue = 0;
		Cursor cursor = db.rawQuery("select dangqianyue from bill_account where idx = ?", new String[]{String.valueOf(bill.getTransferInAccountId())});
		cursor.moveToFirst();
		inAccountYue = cursor.getDouble(0);
		cursor = db.rawQuery("select dangqianyue from bill_account where idx = ?", new String[]{String.valueOf(bill.getTransferOutAccountId())});
		cursor.moveToFirst();
		outAccountYue = cursor.getDouble(0);
		double IncurrentAccountYue = inAccountYue + Float.parseFloat(bill.getJine());
		double outCurrentAccountYue = outAccountYue - Float.parseFloat(bill.getJine());
		db.execSQL("update bill_account set dangqianyue = ? where idx = ?", new String[]{String.valueOf(IncurrentAccountYue), String.valueOf(bill.getTransferInAccountId())});
		db.execSQL("update bill_account set dangqianyue = ? where idx = ?", new String[]{String.valueOf(outCurrentAccountYue), String.valueOf(bill.getTransferOutAccountId())});
	}
	
	
	public void updateLastInAccount(Bill bill) {
		Cursor cursor = db.rawQuery("select dangqianyue from bill_account where idx = ?", new String[]{String.valueOf(bill.getAccountLastIdx())});
		cursor.moveToFirst();
		double lastValue = cursor.getDouble(0) + Math.abs(Double.parseDouble(bill.getLastJine()));
		db.execSQL("update bill_account set dangqianyue = ? where idx = ?", new String[]{String.valueOf(lastValue), String.valueOf(bill.getAccountLastIdx())});
	}
	
	public void updateLastOutAccount(Bill bill) {
		Cursor cursor = db.rawQuery("select dangqianyue from bill_account where idx = ?", new String[]{String.valueOf(bill.getAccountLastIdx())});
		cursor.moveToFirst();
		double lastValue = cursor.getDouble(0) - Math.abs(Double.parseDouble(bill.getLastJine()));
		db.execSQL("update bill_account set dangqianyue = ? where idx = ?", new String[]{String.valueOf(lastValue), String.valueOf(bill.getAccountLastIdx())});
	}
	
	public void updateLastTransferInAccount(Bill bill) {
		Cursor cursor = db.rawQuery("select dangqianyue from bill_account where idx = ?", new String[]{String.valueOf(bill.getLastTransferOutAccountId())});
		cursor.moveToFirst();
		double outLastValue = cursor.getDouble(0) + Math.abs(Double.parseDouble(bill.getLastJine()));
		db.execSQL("update bill_account set dangqianyue  = ? where idx = ?", new String[]{String.valueOf(outLastValue) ,String.valueOf(bill.getLastTransferOutAccountId())});
	}
	
	public void updateLastTransferOutAccount(Bill bill) {
		Cursor cursor = db.rawQuery("select dangqianyue from bill_account where idx = ?", new String[]{String.valueOf(bill.getLastTransferInAccountId())});
		cursor.moveToFirst();
		double inLastValue = cursor.getDouble(0) - Math.abs(Double.parseDouble(bill.getLastJine()));
		db.execSQL("update bill_account set dangqianyue = ? where idx = ?", new String[]{String.valueOf(inLastValue), String.valueOf(bill.getLastTransferInAccountId())});
	}
	

}

























