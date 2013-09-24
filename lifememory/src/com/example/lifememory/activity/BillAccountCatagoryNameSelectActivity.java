package com.example.lifememory.activity;

import com.example.lifememory.R;
import com.example.lifememory.adapter.BillAccountBiZhongListViewAdapter;
import com.example.lifememory.adapter.BillAccountCatagoryNameListViewAdapter;
import com.example.lifememory.utils.ConstantUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class BillAccountCatagoryNameSelectActivity extends Activity{
	private ListView listView;
	private String[] names = {"现金", "信用卡", "储蓄", "网上支付"};
	private int[] imageIds = {R.drawable.xianjin, R.drawable.icon_yhsr, R.drawable.waihui, R.drawable.shuifei};
	private BillAccountCatagoryNameListViewAdapter adapter;
	private Intent intent;
//	private final static int EDIT_NOCHANGE_BACK = 90;       //直接点击back不保存信息
//	private final static int EDIT_CATAGORYNAMEFINISHED = 95;       
	private int catagorynameCurrentSelectedIndex = 0;
	private String catagoryName;
	private String type;    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.bill_account_catagoryname_select_layout);
		
		
		initDatas();
		findViews();
		listAdapter();
		setListeners();
	}
	
	private void findViews() {
		listView = (ListView) this.findViewById(R.id.listView);
	}
	
	private void listAdapter() {
		adapter = new BillAccountCatagoryNameListViewAdapter(this, names, imageIds);
		listView.setAdapter(adapter);
		adapter.setSelected(catagorynameCurrentSelectedIndex);
	}
	
	private void initDatas() {
		catagorynameCurrentSelectedIndex = this.getIntent().getIntExtra("catagorynameCurrentSelectedIndex", 0);
	}
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			back();
			break;
		}
	}
	
	private void setListeners() {
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				catagoryName = names[position];
				catagorynameCurrentSelectedIndex = position;
				intent = new Intent();
				intent.putExtra("catagorynameCurrentSelectedIndex", catagorynameCurrentSelectedIndex);
				intent.putExtra("catagoryName", catagoryName);
				BillAccountCatagoryNameSelectActivity.this.setResult(ConstantUtil.EDIT_CATAGORYNAMEFINISHED, intent);
				BillAccountCatagoryNameSelectActivity.this.finish();
				overridePendingTransition(R.anim.activity_steady, R.anim.activity_down);
			}
		});
	}
	
	private void back() {
		intent = new Intent();
		this.setResult(ConstantUtil.EDIT_NOCHANGE_BACK, intent);
		BillAccountCatagoryNameSelectActivity.this.finish();
		overridePendingTransition(R.anim.activity_steady, R.anim.activity_down);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			back();
			break;

		default:
			break;
		}
		return true;
	}
}


















