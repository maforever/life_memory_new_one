package com.example.lifememory.activity;

import java.util.List;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillInCatagory;
import com.example.lifememory.adapter.BillInCatagoryListViewAdapter;
import com.example.lifememory.db.service.BillInCatagoryService;
import com.example.lifememory.utils.ConstantUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class BillInCatagorySettingActivity extends Activity {
	private ListView listView;
	private int currentSelectedIndex;
	private BillInCatagoryService dbService;
	private BillInCatagoryListViewAdapter adapter;
	private List<BillInCatagory> bics;
	private Intent intent;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if(msg.what == 0) {
				initViews();
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.bill_incatagory_select_layout);
		
		dbService = new BillInCatagoryService(this);
		findViews();
		setListeners();
		currentSelectedIndex = this.getIntent().getIntExtra("currentSelectedIndex", 0);
		new LoadDatasThread().start();
	}
	
	private void setListeners() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				intent = new Intent();
				intent.putExtra("currentSelectedIndex", position);
				intent.putExtra("catagoryname", bics.get(position).getName());
				BillInCatagorySettingActivity.this.setResult(ConstantUtil.SELECTED_INCATAGORY_FINISHED, intent);
				BillInCatagorySettingActivity.this.finish();
				overridePendingTransition(R.anim.activity_steady, R.anim.activity_down);
			}
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView deleteTag = (TextView) view.findViewById(R.id.deleteTag);
				if(deleteTag.getVisibility() == ViewGroup.VISIBLE) {
					deleteTag.setVisibility(ViewGroup.GONE);
				}else {
					adapter.setShowDeleteTag(position);
				}
				return true;
			}
		});
		
	}

	private void findViews() {
		this.listView = (ListView) this.findViewById(R.id.listView);
	}
	
	private class LoadDatasThread extends Thread {
		@Override
		public void run() {
			bics = dbService.getAllItems();
			handler.sendEmptyMessage(0);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(dbService != null) {
			new LoadDatasThread().start();
		}
	}
	
	private void initViews() {
		adapter = new BillInCatagoryListViewAdapter(this, bics, dbService);
		adapter.setSelected(currentSelectedIndex);
		listView.setAdapter(adapter);
		listView.setSelection(currentSelectedIndex);
	}
	
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			back();
			break;
		case R.id.add:
			
			intent = new Intent(BillInCatagorySettingActivity.this, BillTextInputActivity.class);
			intent.putExtra("title", "Àà±ðÌí¼Ó");
			intent.putExtra("fenlei", ConstantUtil.EDIT_INCATAGORY_FINISHED);
			intent.putExtra("editNum", 10);
			startActivityForResult(intent, 100);
			overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
			break;
		}
	}
	

	
	
	private void back() {
		BillInCatagorySettingActivity.this.finish();
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
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbService.closeDB();
	}
}






























