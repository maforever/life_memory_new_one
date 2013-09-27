package com.example.lifememory.activity;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillAccountItem;
import com.example.lifememory.db.service.BillAccountService;
import com.example.lifememory.utils.ConstantUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BillReimbursementValueInputDialog extends Activity {
	EditText valueEt;
	TextView accountTv;
	Intent intent;
	BillAccountService accountService;
	BillAccountItem accountItem;
	String accountName;
	int accountId;
	int currentSelectedIndex = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.bill_reimbursement_input_dialog);
		
		accountService = new BillAccountService(this);
		accountItem = accountService.findItemsForAddViews();
		if(accountItem != null) {
			accountName = accountItem.getName();
			accountId = accountItem.getIdx();
		}else {
			accountName = "无";
			accountId = -1;
		}
		
		findViews();
		initViews();
	}
	
	private void findViews() {
		valueEt = (EditText) this.findViewById(R.id.value);
		accountTv = (TextView) this.findViewById(R.id.account);
	}
	
	private void initViews() {
		accountTv.setText(accountName);
	}
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.selectAccount:
			if(accountItem != null) {
				intent = new Intent(this, BillReimbursementInputAccountSelectDialog.class);
				intent.putExtra("currentSelectedIndex", currentSelectedIndex);
				startActivityForResult(intent, 100);
			}else {
				Toast.makeText(this, "没有任何账户信息，请先添加账户!" , 0).show();
			}
			break;
		case R.id.cancel:
			cancel();
			break;
		case R.id.sure:
			if(validateDatas()) {
				if(accountItem == null) {
					Toast.makeText(this, "没有任何账户信息，请先添加账户!" , 0).show();
				}else {
					intent = new Intent();
					intent.putExtra("value", valueEt.getText().toString());
					intent.putExtra("accountId", accountId);
					intent.putExtra("accountName", accountName);
					this.setResult(ConstantUtil.REIMBURSEMENT_VALUE_INPUT, intent);
					BillReimbursementValueInputDialog.this.finish();
				}
			}else {
				Toast.makeText(this, "请填写报销金额,不能为0!", 0).show();
			}
			
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == ConstantUtil.SELECT_ACCOUNT_FINISHED) {
			currentSelectedIndex = data.getIntExtra("currentSelectedIndex", 0);
			accountName = data.getStringExtra("name");
			accountId = data.getIntExtra("accountId", 0);
			accountTv.setText(accountName);
		}
	}
	
	public boolean validateDatas() {
		String valueStr = valueEt.getText().toString();
		if(valueStr == null || "".equals(valueStr) || "0".equals(valueStr)) {
			return false;
		}
		return true;
	}
	
	public void cancel() {
//		intent = new Intent();
//		this.setResult(ConstantUtil.REIMBURSEMENT_VALUE_INPUT, intent);
		BillReimbursementValueInputDialog.this.finish();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			cancel();
		}
		return true;
	}
	
}
