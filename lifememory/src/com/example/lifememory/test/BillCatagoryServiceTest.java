package com.example.lifememory.test;

import java.util.List;

import com.example.lifememory.activity.model.BillCatagoryItem;
import com.example.lifememory.db.service.BillCatagoryService;

import android.test.AndroidTestCase;
import android.util.Log;

public class BillCatagoryServiceTest extends AndroidTestCase {
	private BillCatagoryService dbService;
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		dbService = new BillCatagoryService(this.getContext());    //在setUp方法中初始化
	}
	
	public void testFindDeafaultCatagoryItem() {
//		BillCatagoryItem item= dbService.findDeafaultCatagoryItem();
//		Log.i("a", item.toString());
	}
	
	public void testFindBudgetInfos() {
		List<BillCatagoryItem> items = dbService.findBudgetInfos();
		for(BillCatagoryItem item : items) {
			Log.i("a", item.toString());
		}
	}
}
