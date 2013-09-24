package com.example.lifememory.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillCatagoryItem;
import com.example.lifememory.adapter.BillCatagoryGridViewAdapter;
import com.example.lifememory.adapter.BillCatatoryListAdapter;
import com.example.lifememory.adapter.BillCatatorySecondaryListAdapter;
import com.example.lifememory.db.service.BillCatagoryService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BillCatagorySettingActivity extends Activity {
	private ListView firstListView, secondaryListView;
	private BillCatagoryService dbService = null;
	private List<BillCatagoryItem> firstLevelItems = null;
	private List<BillCatagoryItem> secondaryLevelItems = null;
	private BillCatatoryListAdapter firstLevelAdapter;
	private BillCatatorySecondaryListAdapter secondaryLevelAdapter;
	private BillCatagoryItem addItem;    //��Ӱ�ť
	private int parentId = 1;         //��¼һ�������Ϣ��id��Ϊ��������parentid
	private int catagorySelectedId = 0;
	private int catagorySelectedChildId = 0;
	private int currentSatagorySelectedId = 0;
	private int catagoryChildId = 0;
	private int catagoryParentId = 1;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				// ��ȡһ����������
				listViewAdapter();
				break;
			case 1:
				//����parentid��ȡ��������
				secondaryLevelAdapter.setDatas(secondaryLevelItems);
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.bill_catagoryselect_layout);

		dbService = new BillCatagoryService(this);
		catagorySelectedId = this.getIntent().getIntExtra("catagorySelectedId", 0);
		currentSatagorySelectedId = catagorySelectedId;
		catagorySelectedChildId = this.getIntent().getIntExtra("catagorySelectedChildId", 0);
		parentId = this.getIntent().getIntExtra("parentId", 1);
		findViews();
		initDatas();
		setListeners();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
//		Log.i("a", "BillCatagorySettingActivity onResume catagorySelectedId = " + catagorySelectedId + " catagorySelectedChildId = " + catagorySelectedChildId);
		if(dbService != null) {
			initDatas();
//			new LoadDatasByParentIdThread(parentId).start();
		}
	}

	private void findViews() {
		firstListView = (ListView) this.findViewById(R.id.firstListView);
		secondaryListView = (ListView) this
				.findViewById(R.id.secondaryListView);
		addItem = new BillCatagoryItem();
		addItem.setName("����");
		addItem.setImageId(R.drawable.bill_category_add);
	}

	private void initDatas() {
		new LoadDatasThread().start();
	}

	private void listViewAdapter() {

		firstLevelAdapter = new BillCatatoryListAdapter(
				BillCatagorySettingActivity.this,
				BillCatagorySettingActivity.this.firstLevelItems);
		secondaryLevelAdapter = new BillCatatorySecondaryListAdapter(
				BillCatagorySettingActivity.this,
				BillCatagorySettingActivity.this.secondaryLevelItems);
		
		firstListView.setAdapter(firstLevelAdapter);
		secondaryListView.setAdapter(secondaryLevelAdapter);
		
		firstLevelAdapter.setSelected(catagorySelectedId);
		secondaryLevelAdapter.setSelected(catagorySelectedChildId);

	}

	private void setListeners() {
		firstListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				if(position != firstLevelItems.size() - 1) {
					parentId = firstLevelItems.get(position).getIdx();
					new LoadDatasByParentIdThread(parentId).start();
					firstLevelAdapter.setSelected(position);
					
					if(currentSatagorySelectedId != position) {
						secondaryLevelAdapter.setSelected(-1);
					}else {
						secondaryLevelAdapter.setSelected(catagorySelectedChildId);
					}
					catagorySelectedId = position;
					catagoryParentId = firstLevelItems.get(position).getIdx();
					
				}else {
					Intent intent = new Intent(BillCatagorySettingActivity.this, BillCatatoryAddActivity.class);
					intent.putExtra("title", "���һ�����");
					intent.putExtra("parentId", 0);
					startActivity(intent);
					overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
				}
			}
		});
		
		secondaryListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position != secondaryLevelItems.size() - 1) {
//					secondaryLevelAdapter.setSelected(position);
					catagorySelectedChildId = position;
					catagoryChildId = secondaryLevelItems.get(position).getIdx();
					Intent intent = new Intent();
					intent.putExtra("parentId", parentId);
					intent.putExtra("catagoryid", secondaryLevelItems.get(position).getIdx());
					intent.putExtra("catagorySelectedId", catagorySelectedId);
					intent.putExtra("catagorySelectedChildId", catagorySelectedChildId);
					intent.putExtra("catagoryParentId", catagoryParentId);
					intent.putExtra("catagoryChildId", catagoryChildId);
					Log.i("a", "setting catagoryChildId = " + catagoryChildId + " catagoryParentId = " + catagoryParentId);
//					Log.i("a", "BillCatagorySettingActivity onItemClick catagorySelectedId = " + catagorySelectedId + " catagorySelectedChildId = " + catagorySelectedChildId);
					BillCatagorySettingActivity.this.setResult(99, intent);
					BillCatagorySettingActivity.this.finish();
					overridePendingTransition(R.anim.activity_steady, R.anim.activity_down);
//					Toast.makeText(BillCatagorySettingActivity.this, secondaryLevelItems.get(position).getName(), 0).show();
				}else {
					Intent intent = new Intent(BillCatagorySettingActivity.this, BillCatatoryAddActivity.class);
					intent.putExtra("title", "��Ӷ������");
					intent.putExtra("parentId", parentId);
					startActivity(intent);
					overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
				}
			}
		});
	}

	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			back();
			break;
		}
	}

	// ��ȡ���ݿ���������߳�
	private class LoadDatasThread extends Thread {
		@Override
		public void run() {
			firstLevelItems = dbService.findFirstLevel();
			firstLevelItems.add(addItem);
//			Log.i("a", "catagoryId = " + firstLevelItems.get(catagorySelectedId).getIdx());
			secondaryLevelItems = dbService.findSecondaryLevelByParentId(firstLevelItems.get(catagorySelectedId).getIdx());
			secondaryLevelItems.add(addItem);
			handler.sendEmptyMessage(0);
		}
	}
	
	private class LoadDatasByParentIdThread extends Thread {
		private int index = 1;
		public LoadDatasByParentIdThread(int _index) {
			this.index = _index;
		}
		@Override
		public void run() {
			secondaryLevelItems = dbService.findSecondaryLevelByParentId(index);
			secondaryLevelItems.add(addItem);
			handler.sendEmptyMessage(1);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbService.closeDB();
	}

	
	private void back() {
		BillCatagorySettingActivity.this.finish();
		overridePendingTransition(R.anim.activity_steady,
				R.anim.activity_down);
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
