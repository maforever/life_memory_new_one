package com.example.lifememory.activity;

import com.example.lifememory.R;
import com.example.lifememory.db.service.BillAccountService;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class BillAccountNoticeDialogActivity extends Activity {
	private String accountName;
	private String accountYue;
	private TextView contentTv;
	private int accountId;
	private String content;
	private BillAccountService accountService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_bill_account_noticevalue);
	
		accountService = new BillAccountService(this);
		
		findViews();
		initViews();
	}
	
	
	private void findViews() {
		contentTv = (TextView) this.findViewById(R.id.content);
	}
	
	private void initViews() {
		accountName = this.getIntent().getStringExtra("accountName");
		accountYue = this.getIntent().getStringExtra("accountYue");
		accountId = this.getIntent().getIntExtra("accountId", 0);
//		String content = "您的账户" + accountName + "当前的余额已经小于或等于" + accountYue + "元";
		String content = this.getIntent().getStringExtra("content");
		contentTv.setText(content);
	}
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.stopNotice:
			accountService.cancelNotice(accountId);
			BillAccountNoticeDialogActivity.this.finish();
			break;
		case R.id.sure:
			BillAccountNoticeDialogActivity.this.finish();
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		accountService.closeDB();
	}
}
