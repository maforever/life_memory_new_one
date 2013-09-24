package com.example.lifememory.activity;

import java.util.Currency;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillAccountItem;
import com.example.lifememory.db.service.BillAccountService;
import com.example.lifememory.utils.ConstantUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BillAccountUpdateActivity extends Activity {
	private Intent intent;
	private BillAccountItem accountItem;
	private CheckBox noticeCb;
	private String title;								   //传送到BillTextInputActivity的标题值
	private RelativeLayout accountNoticeValueLayout;       //警戒线金额layout
	private int editNum = 0;								//传送到BillTextInputActivity的可编辑的字数
	private String content;									//用于接收BillTextInputActivity传来的编辑内容
	private String bizhong;                                 //用于接收BillAccountBiZhongSettingActivity传来的币种信息
	private String catagoryName = "";                             //用于接收BillAccountCatagoryNameSelectActivity传来的类别名称信息
	private String accountName, accountYue, accountBiZhong, accountBeiZhu;
	private String accountNoticeValue = "0";
	private TextView accountNameTv, accountBiZhongTv, accountcatagorynameTv, accountyueTv, accountNoticeValueTv, accountBeiZhuTv;
	private int bizhongCurrentSelectedIndex = 0;                   //用户接收和传送当前选择的货币索引
	private int catagorynameCurrentSelectedIndex = 0;
	LinearLayout cal_equal;
	LinearLayout cal_del;
	ImageView cal_sure;
	private BillAccountService dbService;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				//修改账户信息完成
				Toast.makeText(BillAccountUpdateActivity.this, "账户信息修改成功!", 0).show();
				BillAccountUpdateActivity.this.finish();
				overridePendingTransition(R.anim.activity_steady, R.anim.activity_down);
				break;
			case 1:
				//初始化数据完成
				initViews();
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.bill_account_update_layout);
		dbService = new BillAccountService(this);
		
		
		
		findViews();
		new InitDatasThread().start();
		setListeners();
	}
	
	
	private void findViews() {
		noticeCb = (CheckBox) this.findViewById(R.id.noticeCheckbox);
		accountNoticeValueLayout = (RelativeLayout) this.findViewById(R.id.accountNoticeValueLayout);
		accountNameTv = (TextView) this.findViewById(R.id.accountname);
		accountBiZhongTv = (TextView) this.findViewById(R.id.accountbizhong);
		accountcatagorynameTv = (TextView) this.findViewById(R.id.accountcatagoryname);
		accountyueTv = (TextView) this.findViewById(R.id.accountyue);
		accountNoticeValueTv = (TextView) this.findViewById(R.id.accountNoticeValue);
		accountBeiZhuTv = (TextView) this.findViewById(R.id.beizhu);
	}
	
	private class InitDatasThread extends Thread {
		@Override
		public void run() {
			int idx = BillAccountUpdateActivity.this.getIntent().getIntExtra("accountId", 0);
			accountItem = dbService.findItemDetailById(idx);
			Log.i("a", accountItem.toString());
			handler.sendEmptyMessage(1);
		}
	}
	
	private void initViews() {

		int accountCatagoryId = accountItem.getCatagoryname();
		switch (accountCatagoryId) {
		case 1:
			accountcatagorynameTv.setText("现金");
			
			break;
		case 2:
			accountcatagorynameTv.setText("信用卡");
			break;
		case 3:
			accountcatagorynameTv.setText("储蓄");
			break;
		case 4:
			accountcatagorynameTv.setText("网上支付");
			break;
		}
		catagorynameCurrentSelectedIndex = accountCatagoryId - 1;
		String bizhongStr = accountItem.getBizhong().substring(0, accountItem.getBizhong().lastIndexOf("-"));
		bizhongCurrentSelectedIndex = getBizhongCurrentSelectedIndex(bizhongStr);
		
		accountNameTv.setText(accountItem.getName());
		accountBiZhongTv.setText(accountItem.getBizhong());
//		accountyueTv.setText(accountItem.getDangqianyue() + "");
		
		accountyueTv.setText(accountItem.getDangqianyue() == 0 ? "0" : accountItem.getDangqianyue() + "");
		boolean isNotice = accountItem.isNotice();
//		accountNoticeValueTv.setText(accountItem.getNoticeValue() + "");
		accountNoticeValueTv.setText(accountItem.getNoticeValue() == 0 ? "0" : accountItem.getNoticeValue() + "");
		if(isNotice) {
			accountNoticeValueLayout.setVisibility(ViewGroup.VISIBLE);
			noticeCb.setChecked(true);
		}else {
			accountNoticeValueLayout.setVisibility(ViewGroup.GONE);
			noticeCb.setChecked(false);
		}
		accountBeiZhuTv.setText(accountItem.getBeizhu());
		
	}
	
	private void setListeners() {
		noticeCb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					accountNoticeValueLayout.setVisibility(ViewGroup.VISIBLE);
				}else {
					accountNoticeValueLayout.setVisibility(ViewGroup.GONE);
//					accountNoticeValueTv.setText("0");
				}
			}
		});
	}
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			back();
			break;
		case R.id.update:
			
			if(validateDatas()) {
				int catagoryNameId = 0;
				if(catagoryName.equals("现金")) {
					catagoryNameId = 1;
				}else if(catagoryName.equals("信用卡")) {
					catagoryNameId = 2;
				}else if(catagoryName.equals("储蓄")) {
					catagoryNameId = 3;
				}else if(catagoryName.equals("网上支付")) {
					catagoryNameId = 4;
				}
				accountItem.setCatagoryname(catagoryNameId);
				accountItem.setName(accountName);
				accountItem.setBizhong(accountBiZhong);
				accountItem.setDangqianyue(Double.parseDouble(accountYue));
				accountItem.setNotice(noticeCb.isChecked());
				accountItem.setNoticeValue(Double.parseDouble(accountNoticeValue));
				accountItem.setBeizhu(accountBeiZhu);
				
				new Thread(new Runnable() {
					@Override
					public void run() {
						dbService.updateItem(accountItem);
						handler.sendEmptyMessage(0);
					}
				}).start();
				
				
				
			}
			
			break;
		case R.id.accountCatagoryLayout:
			intent = new Intent(BillAccountUpdateActivity.this, BillAccountCatagoryNameSelectActivity.class);
			intent.putExtra("catagorynameCurrentSelectedIndex", catagorynameCurrentSelectedIndex);
			startActivityForResult(intent, 100);
			overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
			break;
		case R.id.accountNameLayout:
			intent = new Intent(BillAccountUpdateActivity.this, BillTextInputActivity.class);
			title = "编辑账户名称";
			editNum = 10;
			intent.putExtra("title", title);
			intent.putExtra("fenlei", ConstantUtil.EDIT_NAME_FINISHED);
			intent.putExtra("editNum", editNum);
			intent.putExtra("content", accountNameTv.getText().toString());
			startActivityForResult(intent, 100);
			overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
			break;
		case R.id.accountBizhongLayout:
			intent = new Intent(BillAccountUpdateActivity.this, BillAccountBiZhongSettingActivity.class);
			intent.putExtra("currentSelectedIndex", bizhongCurrentSelectedIndex);
			startActivityForResult(intent, 100);
			overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
			break;
		case R.id.accountyueLayout:
			intent = new Intent(BillAccountUpdateActivity.this, BillCalculatorActivity.class);
			intent.putExtra("num", accountyueTv.getText().toString());
			intent.putExtra("resultCode", ConstantUtil.EDIT_YUEFINISHED);
			startActivityForResult(intent, 100);
			break;
		case R.id.accountIsNoticeLayout:
			//是否开启警戒layout
			if(noticeCb.isChecked()) {
				noticeCb.setChecked(false);
				accountNoticeValueLayout.setVisibility(ViewGroup.GONE);
//				accountNoticeValueTv.setText("0");
			}else {
				noticeCb.setChecked(true);
				accountNoticeValueLayout.setVisibility(ViewGroup.VISIBLE);
			}
			break;
		case R.id.accountNoticeValueLayout:
			intent = new Intent(BillAccountUpdateActivity.this, BillCalculatorActivity.class);
			intent.putExtra("num", accountNoticeValueTv.getText().toString());
			intent.putExtra("resultCode", ConstantUtil.EDIT_NOTICEVALUEFINISHED);
			startActivityForResult(intent, 100);
			break;
		case R.id.beizhu:
			intent = new Intent(BillAccountUpdateActivity.this, BillTextInputActivity.class);
			title = "编辑账户备注";
			editNum = 100;
			intent.putExtra("title", title);
			intent.putExtra("fenlei", ConstantUtil.EDIT_BEIZHU);
			intent.putExtra("editNum", editNum);
			intent.putExtra("content", accountBeiZhuTv.getText().toString());
			startActivityForResult(intent, 100);
			overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		content = data.getStringExtra("content");
		switch (resultCode) {
		case ConstantUtil.EDIT_NAME_FINISHED:
			accountNameTv.setText(content);
			break;
		case ConstantUtil.EDIT_NOTICE_FINISHED:
			break;
		case ConstantUtil.EDIT_BEIZHU:
			accountBeiZhuTv.setText(content);
			break;
		case ConstantUtil.EDIT_BIZHONGFINISHED:
			bizhong = data.getStringExtra("bizhong");
			bizhongCurrentSelectedIndex = data.getIntExtra("currentSelectedIndex", 0);
			accountBiZhongTv.setText(bizhong);
			break;
		case ConstantUtil.EDIT_CATAGORYNAMEFINISHED:
			catagoryName = data.getStringExtra("catagoryName");
			catagorynameCurrentSelectedIndex = data.getIntExtra("catagorynameCurrentSelectedIndex", 0);
			accountcatagorynameTv.setText(catagoryName);
			break;
		case ConstantUtil.EDIT_NOTICEVALUEFINISHED:
			accountNoticeValueTv.setText(data.getStringExtra("num"));
			break;
		case ConstantUtil.EDIT_YUEFINISHED:
			accountyueTv.setText(data.getStringExtra("num"));
			break;
		}
	}
	
	private void back() {
		BillAccountUpdateActivity.this.finish();
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
	
	private boolean validateDatas() {
		catagoryName = accountcatagorynameTv.getText().toString();
		accountName = accountNameTv.getText().toString();
		accountBiZhong = accountBiZhongTv.getText().toString();
		accountYue = accountyueTv.getText().toString();
		accountBeiZhu = accountBeiZhuTv.getText().toString();
		if(catagoryName == null || "".equals(catagoryName)) {
			Toast.makeText(BillAccountUpdateActivity.this, "请选择类别名称!", 0).show();
			return false;
		}
		
		if(accountName == null || "".equals(accountName)) {
			Toast.makeText(BillAccountUpdateActivity.this, "请填写账户名称!", 0).show();
			return false;
		}
		
		if(accountYue == null || "".equals(accountYue)) {
			Toast.makeText(BillAccountUpdateActivity.this, "请填写账户余额!", 0).show();
			return false;
		}
		
		if(accountBiZhong == null || "".equals(accountBiZhong)) {
			Toast.makeText(BillAccountUpdateActivity.this, "请选择账户币种!", 0).show();
			return false;
		}
		
		if(noticeCb.isChecked()) {
			accountNoticeValue = accountNoticeValueTv.getText().toString();
			if(accountNoticeValue == null || "".equals(accountNoticeValue)) {
				Toast.makeText(BillAccountUpdateActivity.this, "请填写警戒线金额!", 0).show();
				return false;
			}
		}
		
		return true;
	}
	
	private int getBizhongCurrentSelectedIndex(String _bizhongStr) {
		for(int i=0; i<ConstantUtil.BILL_ACCOUNT_BIZHONG_NAMES1.length; i++) {
			if(_bizhongStr.equals(ConstantUtil.BILL_ACCOUNT_BIZHONG_NAMES1[i])) {
				return i;
			}
		}
		return 0;
	}
	
	
	
	
}
