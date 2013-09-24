package com.example.lifememory.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillAccountExpandableListViewItem;
import com.example.lifememory.activity.model.BillAccountItem;
import com.example.lifememory.adapter.BillAccountExpandableListAdapter;
import com.example.lifememory.db.service.BillAccountService;
import com.example.lifememory.db.service.BillInfoService;
import com.example.lifememory.db.service.BillTemplateService;
import com.example.lifememory.dialog.DialogAlertBill;
import com.example.lifememory.dialog.DialogAlertListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BillAccountSettingActivity extends Activity {
	private ExpandableListView listView = null;
	private CheckBox updateCb, deleteCb;
	private BillAccountService dbService;
	private BillInfoService infoService;
	private BillTemplateService templateService;
	private List<BillAccountItem> accountItems =  new ArrayList<BillAccountItem>();
	private List<BillAccountItem> accountItems1 = new ArrayList<BillAccountItem>();  //存放现金数据
	private List<BillAccountItem> accountItems2 = new ArrayList<BillAccountItem>();	 //存放信用卡数据
	private List<BillAccountItem> accountItems3 = new ArrayList<BillAccountItem>();  //存放储蓄数据
	private List<BillAccountItem> accountItems4 = new ArrayList<BillAccountItem>();  //存放网上支付数据
	private BillAccountExpandableListViewItem expandableItem;
	private List<BillAccountExpandableListViewItem> expandableItems = new ArrayList<BillAccountExpandableListViewItem>();
	private BillAccountExpandableListAdapter adapter = null;
	private int currentGroupSelectedIndex = 0;			//用于记录账户expandablelistview中组的索引
	private int currentChildSelectedIndex = 0;  		//用于记录账户expandablelistview中子的索引
	private int groupLocation, childLocation;
	private Intent intent;
	private String accountFlag;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				listAdapter();
				break;
			case 1:
				//删除失败
				Toast.makeText(BillAccountSettingActivity.this, "账户信息不允许删除，清先清空使用了该账户的账单记录或模版信息!", 0).show();
				break;
			case 2:
				//删除成功
				new InitDatasThread().start();
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.bill_account_setting_layout);
		
		dbService = new BillAccountService(this);
		infoService = new BillInfoService(this);
		templateService = new BillTemplateService(this);
		this.currentGroupSelectedIndex = this.getIntent().getIntExtra("currentGroupSelectedIndex", 0);
		this.currentChildSelectedIndex = this.getIntent().getIntExtra("currentChildSelectedIndex", 0);
		accountFlag = this.getIntent().getStringExtra("accountFlag");
		findViews();
		new InitDatasThread().start();
	}
	

	private void findViews() {
		updateCb = (CheckBox) this.findViewById(R.id.updateCb);
		deleteCb = (CheckBox) this.findViewById(R.id.deleteCb);
		
		updateCb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					deleteCb.setChecked(false);
				}
			}
		});
		
		deleteCb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					updateCb.setChecked(false);
				}
			}
		});
		
		listView = (ExpandableListView) this.findViewById(R.id.listView);
		listView.setOnChildClickListener(new OnChildClickListener() {
				 
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				groupLocation = groupPosition;
				childLocation = childPosition;
				if(!updateCb.isChecked() && !deleteCb.isChecked()) {
					String name = expandableItems.get(groupPosition).getAccountItems().get(childPosition).getName();
					int idx = expandableItems.get(groupPosition).getAccountItems().get(childPosition).getIdx();
					intent = new Intent();
					intent.putExtra("currentGroupSelectedIndex", groupPosition);
					intent.putExtra("currentChildSelectedIndex", childPosition);
					intent.putExtra("accountStr", name);
					intent.putExtra("accountFlag", accountFlag);
					intent.putExtra("accountid", idx);
					BillAccountSettingActivity.this.setResult(98, intent);
					BillAccountSettingActivity.this.finish();
					overridePendingTransition(R.anim.activity_steady, R.anim.activity_down);
				}else if(updateCb.isChecked()) {
					//修改
					intent = new Intent(BillAccountSettingActivity.this, BillAccountUpdateActivity.class);
					intent.putExtra("accountId", expandableItems.get(groupPosition).getAccountItems().get(childPosition).getIdx());
					startActivity(intent);
					overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
				}else if(deleteCb.isChecked()) {
					//删除
					new DialogAlertBill(BillAccountSettingActivity.this, listener, "确定删除该账户信息吗?").show();
				}
				return false;
			}
		});
		
	}
	
	private DialogAlertListener listener = new DialogAlertListener() {
		
		@Override
		public void onDialogUnSave(Dialog dlg, Object param) {
			
		}
		
		@Override
		public void onDialogSave(Dialog dlg, Object param) {
			
		}
		
		@Override
		public void onDialogOk(Dialog dlg, Object param) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					int accountIdx = expandableItems.get(groupLocation).getAccountItems().get(childLocation).getIdx();
					if(infoService.isRelatedWithAccount(accountIdx) || templateService.isRelatedWithAccount(accountIdx)) {
						handler.sendEmptyMessage(1);
					}else {
						dbService.deleteItemById(expandableItems.get(groupLocation).getAccountItems().get(childLocation).getIdx());
						handler.sendEmptyMessage(2);
					}
				}
			}).start();
		}
		
		@Override
		public void onDialogCreate(Dialog dlg, Object param) {
			
		}
		
		@Override
		public void onDialogCancel(Dialog dlg, Object param) {
			
		}
	};
	
	public void btnClick(View view) {
		switch (view.getId()) {
//		case R.id.back:
//			back();
//			break;
		case R.id.add:
			intent = new Intent(BillAccountSettingActivity.this, BillAccountAddActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
			break;
		case R.id.updateBtn:
			if(updateCb.isChecked()) {
				updateCb.setChecked(false);
			}else {
				updateCb.setChecked(true);
				deleteCb.setChecked(false);
			}
			break;
		case R.id.deleteBtn:
			if(deleteCb.isChecked()) {
				deleteCb.setChecked(false);
			}else {
				deleteCb.setChecked(true);
				updateCb.setChecked(false);
			}
			break;
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(dbService != null) {
			new InitDatasThread().start();
		}
	}
	
	private class InitDatasThread extends Thread {
		@Override
		public void run() {
			accountItems1 = new ArrayList<BillAccountItem>();  
			accountItems2 = new ArrayList<BillAccountItem>();	
			accountItems3 = new ArrayList<BillAccountItem>(); 
			accountItems4 = new ArrayList<BillAccountItem>();  
			expandableItems = new ArrayList<BillAccountExpandableListViewItem>();
			accountItems = dbService.getAllAccounts();
			for(BillAccountItem item : accountItems) {
				switch (item.getCatagoryname()) {
				case 1:
					accountItems1.add(item);
					break;
				case 2:
					accountItems2.add(item);
					break;
				case 3:
					accountItems3.add(item);
					break;
				case 4:
					accountItems4.add(item);
					break;
				}
			}
			
			expandableItem = new BillAccountExpandableListViewItem();
			expandableItem.setTitle("现金");
			expandableItem.setAccountItems(accountItems1);
			expandableItem.setImageId(R.drawable.xianjin);
			expandableItems.add(expandableItem);
			
			expandableItem = new BillAccountExpandableListViewItem();
			expandableItem.setTitle("信用卡");
			expandableItem.setAccountItems(accountItems2);
			expandableItem.setImageId(R.drawable.icon_yhsr);
			expandableItems.add(expandableItem);
			
			expandableItem = new BillAccountExpandableListViewItem();
			expandableItem.setTitle("储蓄");
			expandableItem.setAccountItems(accountItems3);
			expandableItem.setImageId(R.drawable.waihui);
			expandableItems.add(expandableItem);
			
			expandableItem = new BillAccountExpandableListViewItem();
			expandableItem.setTitle("网上支付");
			expandableItem.setAccountItems(accountItems4);
			expandableItem.setImageId(R.drawable.shuifei);
			expandableItems.add(expandableItem);
			
			handler.sendEmptyMessage(0);
		}
	}
	
	
	private void listAdapter() {
		adapter = new BillAccountExpandableListAdapter(BillAccountSettingActivity.this, expandableItems);
		listView.setAdapter(adapter);
		adapter.setSelected(currentGroupSelectedIndex, currentChildSelectedIndex);
//		Log.i("a", currentGroupSelectedIndex + "-------" + currentChildSelectedIndex);
		int groupCount = adapter.getGroupCount();
		for (int i = 0; i < groupCount; i++) {
			listView.expandGroup(i);
		}
		listView.setSelectedChild(currentGroupSelectedIndex, currentChildSelectedIndex, true);
	};
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbService.closeDB();
		infoService.closeDB();
		templateService.closeDB();
		dbService = null;
		infoService = null;
		templateService = null;
	}
	
	private void back() {
		BillAccountSettingActivity.this.finish();
		overridePendingTransition(R.anim.activity_steady, R.anim.activity_down);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			back();
			break;
		}
		return true;
	}
}





















