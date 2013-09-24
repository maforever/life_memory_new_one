package com.example.lifememory.utils;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillAccountItem;
import com.example.lifememory.activity.model.BillCatagoryItem;

public class BillAccountCreator {
	BillAccountItem item = null;

	public BillAccountCreator() {
	};

	public synchronized void initAccountDatas(SQLiteDatabase db) {
		List<BillAccountItem> items = new ArrayList<BillAccountItem>();
		item = new BillAccountItem(1, "现金", "人民币-CNY", 0.0,false, 0.0, R.drawable.xianjin);
		items.add(item);
		item = new BillAccountItem(2, "信用卡", "人民币-CNY",  0.0,false, 0.0, R.drawable.icon_yhsr);
		items.add(item);
		item = new BillAccountItem(3, "储蓄", "人民币-CNY",  0.0,false, 0.0, R.drawable.waihui);
		items.add(item);
		item = new BillAccountItem(4, "网上支付", "人民币-CNY",  0.0,false, 0.0, R.drawable.shuifei);
		items.add(item);

		for (BillAccountItem bcItem : items) {
			db.execSQL(
					"insert into bill_account (catagoryname, name, bizhong, dangqianyue, isnotice, noticevalue, imageid) values (?, ?, ?, ?, ?, ?, ?)",
					new String[] {
							String.valueOf(bcItem.getCatagoryname()),
							bcItem.getName(),
							bcItem.getBizhong(),
							String.valueOf(bcItem.getDangqianyue()),
							String.valueOf(bcItem.isNotice()),
							String.valueOf(bcItem.getNoticeValue()),
							String.valueOf(bcItem.getImageId())
					});
		}

		

	}
}
