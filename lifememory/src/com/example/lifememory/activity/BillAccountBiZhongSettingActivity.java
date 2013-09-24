package com.example.lifememory.activity;

import java.util.Arrays;

import com.example.lifememory.R;
import com.example.lifememory.adapter.BillAccountBiZhongListViewAdapter;
import com.example.lifememory.utils.ConstantUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class BillAccountBiZhongSettingActivity extends Activity {
	private ListView listView;
	private Intent intent;
//	private String[] names1 = {
//			"�����", "��Ԫ", "ŷԪ", "�Ĵ�����Ԫ", "�������Ƕ�", "���ô�Ԫ", "�������", "��ʿ����",
//			"Ӣ��", "��Ԫ", "��Ԫ", "��Ԫ", "����Ԫ", "���������ּ���","������Ԫ","Ų������",
//			"���ɱ�����","�¼���Ԫ","������","��̨��","̩��","˹������¬��"
//	};
//	private String[] names2 = {
//			"CNY","USD","EUR","AUD","BRL","CAD","DKK","CHF",
//			"GBP","HKD","JPY","KRW","MOP","MYR","NZD","NOK",
//			"PHP","SGD","SEK","TWD","THB","LKR"
//			
//	};
//	private int[] imageIds = {
//			R.drawable.currency_icon_cny,R.drawable.currency_icon_usd,R.drawable.currency_icon_eur,R.drawable.currency_icon_aud,
//			R.drawable.currency_icon_brl,R.drawable.currency_icon_cad,R.drawable.currency_icon_dkk,R.drawable.currency_icon_chf,
//			R.drawable.currency_icon_gbp,R.drawable.currency_icon_hkd,R.drawable.currency_icon_jpy,R.drawable.currency_icon_krw,
//			R.drawable.currency_icon_mop,R.drawable.currency_icon_myr,R.drawable.currency_icon_nzd,R.drawable.currency_icon_nok,
//			R.drawable.currency_icon_php,R.drawable.currency_icon_sgd,R.drawable.currency_icon_sek,R.drawable.currency_icon_twd,
//			R.drawable.currency_icon_thb,R.drawable.currency_icon_lkr
//	};
	
	private BillAccountBiZhongListViewAdapter adapter;
	private int currentSelectedIndex = 0;
//	private final static int EDIT_BIZHONGFINISHED = 96;     //����ѡ�����
//	private final static int EDIT_NOCHANGE_BACK = 90;       //ֱ�ӵ��back��������Ϣ
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.bill_account_bizhong_select_layout);
		
		
		
		findViews();
		initDatas();
		listAdapter();
		setListeners();
	}
	
	
	private void setListeners() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String bizhong = ConstantUtil.BILL_ACCOUNT_BIZHONG_NAMES1[position] + "-" + ConstantUtil.BILL_ACCOUNT_BIZHONG_NAMES2[position];
				currentSelectedIndex = position;
				intent = new Intent();
				intent.putExtra("bizhong", bizhong);
				intent.putExtra("currentSelectedIndex", currentSelectedIndex);
				BillAccountBiZhongSettingActivity.this.setResult(ConstantUtil.EDIT_BIZHONGFINISHED, intent);
				BillAccountBiZhongSettingActivity.this.finish();
				overridePendingTransition(R.anim.activity_steady, R.anim.activity_down);
			}
		});
	}


	private void initDatas() {
		currentSelectedIndex = this.getIntent().getIntExtra("currentSelectedIndex", 0);
	}


	private void findViews() {
		listView = (ListView) this.findViewById(R.id.listView);
	}
	
	private void listAdapter() {
		adapter = new BillAccountBiZhongListViewAdapter(this, ConstantUtil.BILL_ACCOUNT_BIZHONG_NAMES1, ConstantUtil.BILL_ACCOUNT_BIZHONG_NAMES2, ConstantUtil.BILL_ACCOUNT_BIZHONG_IMAGEIDS);
		listView.setAdapter(adapter);
		listView.setSelection(currentSelectedIndex);
		adapter.setSelected(currentSelectedIndex);
		Log.i("a", "listAdapter " + currentSelectedIndex);
		adapter.notifyDataSetChanged();
	}
	
	
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			back();
			break;
		}
	}
	
	private void back() {
		intent = new Intent();
		this.setResult(ConstantUtil.EDIT_NOCHANGE_BACK, intent);
		BillAccountBiZhongSettingActivity.this.finish();
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
