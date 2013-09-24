package com.example.lifememory.db.service;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.lifememory.activity.model.BillTemplate;
import com.example.lifememory.db.PregnancyDiaryOpenHelper;

public class BillTemplateService {
	private SQLiteDatabase db;

	public BillTemplateService(Context context) {
		PregnancyDiaryOpenHelper helper = new PregnancyDiaryOpenHelper(context);
		db = helper.getReadableDatabase();
	}

	/**
	 * idx integer primary key autoincrement, name text,incatagoryname text, outcatagoryname text,
	 * outcatagorychildid integer, outcatagoryparentid integer
	 * accountid integer, member text, canbaoxiao, transferinaccountdid integer,
	 * transferoutaccountid integer, billtype integer
	 * 
	 */
	public void addTemplate(BillTemplate template) {
		db.execSQL(
				"insert into bill_template (name, incatagoryname, outcatagoryname,outcatagorychildid,outcatagoryparentid,  accountid, member, canbaoxiao, transferinaccountdid, transferoutaccountid, billtype) values (?,?,?,?,?,?,?,?,?,?,?)",
				new String[] { template.getName(), template.getInCatagoryName(), template.getOutCatagoryName(),
						String.valueOf(template.getOutCatagoryChildId()), String.valueOf(template.getOutCatagoryParentId()),
						String.valueOf(template.getAccountid()),
						template.getMember(),
						String.valueOf(template.isCanBaoXiao()),
						String.valueOf(template.getTransferInAccountId()),
						String.valueOf(template.getTransferOutAccountId()),
						String.valueOf(template.getBillType()) });
	}
	
	public List<BillTemplate> findAllTemplates() {
		List<BillTemplate> templates = new ArrayList<BillTemplate>();
		BillTemplate template;
		Cursor cursor = db.rawQuery("select * from bill_template", null);
		while(cursor.moveToNext()) {
			template = new BillTemplate();
			template.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
			template.setName(cursor.getString(cursor.getColumnIndex("name")));
			template.setInCatagoryName(cursor.getString(cursor.getColumnIndex("incatagoryname")));
			template.setOutCatagoryName(cursor.getString(cursor.getColumnIndex("outcatagoryname")));
			template.setAccountid(cursor.getInt(cursor.getColumnIndex("accountid")));
			template.setMember(cursor.getString(cursor.getColumnIndex("member")));
			String canbaoxiaoStr = cursor.getString(cursor.getColumnIndex("canbaoxiao"));
			if("true".equals(canbaoxiaoStr)) {
				template.setCanBaoXiao(true);
			}else {
				template.setCanBaoXiao(false);
			}
			template.setTransferInAccountId(cursor.getInt(cursor.getColumnIndex("transferinaccountdid")));
			template.setTransferOutAccountId(cursor.getInt(cursor.getColumnIndex("transferoutaccountid")));
			template.setBillType(cursor.getInt(cursor.getColumnIndex("billtype")));
			template.setOutCatagoryChildId(cursor.getInt(cursor.getColumnIndex("outcatagorychildid")));
			template.setOutCatagoryParentId(cursor.getInt(cursor.getColumnIndex("outcatagoryparentid")));
			templates.add(template);
		}
		return templates;
	}
	
	public boolean isExists(String name) {
		Cursor cursor = db.rawQuery("select count(*) from bill_template where name = ?", new String[]{name});
		cursor.moveToFirst();
		Long count = cursor.getLong(0);
		return count > 0 ? true : false;
	}
	
	//根据idx批量删除数据
	public void deleteTemplatesByIds(List<Integer> ids) {
		for(Integer id : ids) {
			Log.i("a", "id = " + id);
			db.execSQL("delete from bill_template where idx = ?", new String[]{String.valueOf(id)});
		}
	}
	
	//传入的账户是否被模版使用，如果使用就无法删除
	public boolean isRelatedWithAccount(int accountId) {
		Cursor cursor = db.rawQuery("select count(*) from bill_template where accountid = ? or transferinaccountdid = ? or transferoutaccountid = ?", new String[]{String.valueOf(accountId),String.valueOf(accountId),String.valueOf(accountId)});
		cursor.moveToFirst();
		Long count = cursor.getLong(0);
		return count > 0 ? true : false;
	}
	
	
	public void closeDB() {
		this.db.close();
	}

	
	
}


















