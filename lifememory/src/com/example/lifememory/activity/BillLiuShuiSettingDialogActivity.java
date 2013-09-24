package com.example.lifememory.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lifememory.R;
import com.example.lifememory.db.service.BillInfoService;
import com.example.lifememory.dialog.DialogAlertBill;
import com.example.lifememory.dialog.DialogAlertListener;

public class BillLiuShuiSettingDialogActivity extends Activity {
	private int idx;
	private BillInfoService billService;
	private String flag;                   //spend, income, transfer
	private LinearLayout changeToIncomeLayout, changeToSpendLayout, changeToTransferLayout;
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_bill_liushui);
		
		billService = new BillInfoService(this);
		idx = this.getIntent().getIntExtra("idx", 0);
		flag = this.getIntent().getStringExtra("flag");
		findViews();
		if("income".equals(flag)) {
			changeToIncomeLayout.setVisibility(ViewGroup.GONE);
		}else if("spend".equals(flag)) {
			changeToSpendLayout.setVisibility(ViewGroup.GONE);
		}else if("transfer".equals(flag)) {
			changeToTransferLayout.setVisibility(ViewGroup.GONE);
		}
		
	}
	
	private void findViews() {
		changeToIncomeLayout = (LinearLayout) this.findViewById(R.id.changetoincomelayout);
		changeToSpendLayout = (LinearLayout) this.findViewById(R.id.changetospendlayout);
		changeToTransferLayout = (LinearLayout) this.findViewById(R.id.changetotransferlayout);
	}
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.edit:
			Intent intent = new Intent(BillLiuShuiSettingDialogActivity.this, BillInputActivity.class);
			intent.putExtra("flag", "view");
			intent.putExtra("idx", idx);
			BillLiuShuiSettingDialogActivity.this.finish();
			this.startActivity(intent);
			this.overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
			break;
		case R.id.delete:
			new DialogAlertBill(this, new MyDialogListener(1), "确定删除所选账单信息吗？").show();
			break;
		case R.id.changetoincome:
			new DialogAlertBill(BillLiuShuiSettingDialogActivity.this, new MyDialogListener(2), "此操作将会删除原账单，并建立新的收入账单，您确定要进行吗？").show();
			break;
		case R.id.changetospend:
			new DialogAlertBill(BillLiuShuiSettingDialogActivity.this, new MyDialogListener(3), "此操作将会删除原账单，并建立新的支出账单，您确定要进行吗？").show();
			break;
		case R.id.changetotransfer:
			new DialogAlertBill(BillLiuShuiSettingDialogActivity.this, new MyDialogListener(4), "此操作将会删除原账单，并建立新的转账账单，您确定要进行吗？").show();
			break;
		case R.id.close:
			BillLiuShuiSettingDialogActivity.this.finish();
			break;
		}
	}
	
	private class MyDialogListener implements DialogAlertListener {
		private int dialogType = 0;    //1 删除 , 2 changetoincome, 3 changetospend, 4 changetotransfer
		public MyDialogListener(int type) {
			this.dialogType = type;
		}
		
		@Override
		public void onDialogCreate(Dialog dlg, Object param) {
			
		}

		@Override
		public void onDialogOk(Dialog dlg, Object param) {
			switch (dialogType) {
			case 1:
				billService.deleteBillByIdx(idx);
				BillLiuShuiSettingDialogActivity.this.finish();
				break;
			case 2:
				intent = new Intent(BillLiuShuiSettingDialogActivity.this, BillInputActivity.class);
				intent.putExtra("flag", "viewchange");
				intent.putExtra("idx", idx);
				intent.putExtra("toType", 2);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
				break;
			case 3:
				intent = new Intent(BillLiuShuiSettingDialogActivity.this, BillInputActivity.class);
				intent.putExtra("flag", "viewchange");
				intent.putExtra("idx", idx);
				intent.putExtra("toType", 1);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
				break;
			case 4:
				intent = new Intent(BillLiuShuiSettingDialogActivity.this, BillInputActivity.class);
				intent.putExtra("flag", "viewchange");
				intent.putExtra("idx", idx);
				intent.putExtra("toType", 3);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
				break;
			}
			BillLiuShuiSettingDialogActivity.this.finish();
		}

		@Override
		public void onDialogCancel(Dialog dlg, Object param) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onDialogSave(Dialog dlg, Object param) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onDialogUnSave(Dialog dlg, Object param) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
//	private DialogAlertListener listener = new DialogAlertListener() {
//		
//		@Override
//		public void onDialogUnSave(Dialog dlg, Object param) {
//			
//		}
//		
//		@Override
//		public void onDialogSave(Dialog dlg, Object param) {
//			
//		}
//		
//		@Override
//		public void onDialogOk(Dialog dlg, Object param) {
//			billService.deleteBillByIdx(idx);
//			BillLiuShuiSettingDialogActivity.this.finish();
//		}
//		
//		@Override
//		public void onDialogCreate(Dialog dlg, Object param) {
//			
//		}
//		
//		@Override
//		public void onDialogCancel(Dialog dlg, Object param) {
//			
//		}
//	};
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		billService.closeDB();
	}
}
