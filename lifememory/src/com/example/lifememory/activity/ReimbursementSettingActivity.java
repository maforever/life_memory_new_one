package com.example.lifememory.activity;

import com.example.lifememory.R;
import com.example.lifememory.fragments.FR_Bill_WeiBaoXiao;
import com.example.lifememory.fragments.FR_Bill_YiBaoXiao;
import com.example.lifememory.utils.DateFormater;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class ReimbursementSettingActivity extends FragmentActivity {
	String year;
	TextView yearTv;
	int yearNum;
	FragmentManager fm;
	FragmentTransaction ft;
	Fragment fragment;
	TextView baoxiaoedBtn, unBaoxiaoBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.bill_reimbursement_setting_layout);
		
		year = DateFormater.getInstatnce().getYear();
		fm = this.getSupportFragmentManager();
		
		fragment = new FR_Bill_YiBaoXiao(year);
		ft = fm.beginTransaction();
		ft.replace(R.id.container, fragment);
		ft.commit();
		
		
		
		findViews();
		
	}
	
	private void findViews() {
		yearTv = (TextView) this.findViewById(R.id.year);
		yearTv.setText(year);
		baoxiaoedBtn = (TextView) this.findViewById(R.id.baoxiaoed);
		baoxiaoedBtn.setBackgroundResource(R.drawable.bill_common_titlebar_common_btn_selected);
		unBaoxiaoBtn = (TextView) this.findViewById(R.id.unbaoxiao);
		unBaoxiaoBtn.setBackgroundResource(R.drawable.bill_common_titlebar_common_btn_normal);
	}
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			back();
			break;
		case R.id.arrowLeft:
			arrowOperation(R.id.arrowLeft);
			break;
		case R.id.arrowRight:
			arrowOperation(R.id.arrowRight);
			break;
		case R.id.baoxiaoed:
			//已报销
			refreshBtn(R.id.baoxiaoed);
			break;
		case R.id.unbaoxiao:
			//未报销
			refreshBtn(R.id.unbaoxiao);
			break;
		}
	}
	
	private void refreshBtn(int viewId) {
		baoxiaoedBtn.setBackgroundResource(R.drawable.bill_common_titlebar_common_btn_normal);
		unBaoxiaoBtn.setBackgroundResource(R.drawable.bill_common_titlebar_common_btn_normal);
		switch (viewId) {
		case R.id.baoxiaoed:
			baoxiaoedBtn.setBackgroundResource(R.drawable.bill_common_titlebar_common_btn_selected);
			fragment = new FR_Bill_YiBaoXiao(year);
			ft = fm.beginTransaction();
			ft.replace(R.id.container, fragment);
			ft.commit();
			break;
		case R.id.unbaoxiao:
			unBaoxiaoBtn.setBackgroundResource(R.drawable.bill_common_titlebar_common_btn_selected);
			fragment = new FR_Bill_WeiBaoXiao(year);
			ft = fm.beginTransaction();
			ft.replace(R.id.container, fragment);
			ft.commit();
			break;
		}
	}
	
	private void arrowOperation(int viewId) {
		switch (viewId) {
		case R.id.arrowLeft:
			yearNum = Integer.parseInt(yearTv.getText().toString());
			yearNum--;
			year = yearNum + "";
			yearTv.setText(year);
			
			break;
		case R.id.arrowRight:
			yearNum = Integer.parseInt(yearTv.getText().toString());
			yearNum++;
			year = yearNum + "";
			yearTv.setText(year);
			break;
		}
	}
	
	private void back() {
		ReimbursementSettingActivity.this.finish();
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
