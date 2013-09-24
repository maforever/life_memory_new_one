package com.example.lifememory.activity;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillAccountItem;
import com.example.lifememory.db.service.BillAccountService;
import com.example.lifememory.utils.ConstantUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

public class BillAccountAddActivity extends Activity {
	private Intent intent;
	private BillAccountItem accountItem;
	private CheckBox noticeCb;
	private String title;								   //���͵�BillTextInputActivity�ı���ֵ
	private RelativeLayout accountNoticeValueLayout;       //�����߽��layout
	private int editNum = 0;								//���͵�BillTextInputActivity�Ŀɱ༭������
	private String content;									//���ڽ���BillTextInputActivity�����ı༭����
	private String bizhong;                                 //���ڽ���BillAccountBiZhongSettingActivity�����ı�����Ϣ
	private String catagoryName = "";                             //���ڽ���BillAccountCatagoryNameSelectActivity���������������Ϣ
	private String accountName, accountYue, accountBiZhong, accountBeiZhu;
	private String accountNoticeValue = "0";
	private TextView accountNameTv, accountBiZhongTv, accountcatagorynameTv, accountyueTv, accountNoticeValueTv, accountBeiZhuTv;
	private int bizhongCurrentSelectedIndex = 0;                   //�û����պʹ��͵�ǰѡ��Ļ�������
	private int catagorynameCurrentSelectedIndex = 0;
	LinearLayout cal_equal;
	LinearLayout cal_del;
	ImageView cal_sure;
	private BillAccountService dbService;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				//����˻���Ϣ���
				Toast.makeText(BillAccountAddActivity.this, "�˻���Ϣ������!", 0).show();
				BillAccountAddActivity.this.finish();
				overridePendingTransition(R.anim.activity_steady, R.anim.activity_down);
				break;
			case 1:
				//��������ظ������ʧ��
				Toast.makeText(BillAccountAddActivity.this, "������Ʋ����ظ�Ŷ~", 0).show();
				break;
				
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.bill_account_add_layout);
		dbService = new BillAccountService(this);
		findViews();
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
	
	private void setListeners() {
		noticeCb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					accountNoticeValueLayout.setVisibility(ViewGroup.VISIBLE);
				}else {
					accountNoticeValueLayout.setVisibility(ViewGroup.GONE);
					accountNoticeValueTv.setText("0");
				}
			}
		});
	}
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			back();
			break;
		case R.id.save:
			
			if(validateDatas()) {
				accountItem = new BillAccountItem();
				int catagoryNameId = 0;
				if(catagoryName.equals("�ֽ�")) {
					catagoryNameId = 1;
				}else if(catagoryName.equals("���ÿ�")) {
					catagoryNameId = 2;
				}else if(catagoryName.equals("����")) {
					catagoryNameId = 3;
				}else if(catagoryName.equals("����֧��")) {
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
						boolean result  = dbService.addAccount(accountItem);	
						if(result) {
							handler.sendEmptyMessage(0);
						}else 
							handler.sendEmptyMessage(1);
					}
				}).start();
				
				
				
			}
			
			break;
		case R.id.accountCatagoryLayout:
			intent = new Intent(BillAccountAddActivity.this, BillAccountCatagoryNameSelectActivity.class);
			intent.putExtra("catagorynameCurrentSelectedIndex", catagorynameCurrentSelectedIndex);
			startActivityForResult(intent, 100);
			overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
			break;
		case R.id.accountNameLayout:
			intent = new Intent(BillAccountAddActivity.this, BillTextInputActivity.class);
			title = "�༭�˻�����";
			editNum = 10;
			intent.putExtra("title", title);
			intent.putExtra("fenlei", ConstantUtil.EDIT_NAME_FINISHED);
			intent.putExtra("editNum", editNum);
			intent.putExtra("content", accountNameTv.getText().toString());
			startActivityForResult(intent, 100);
			overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
			break;
		case R.id.accountBizhongLayout:
			intent = new Intent(BillAccountAddActivity.this, BillAccountBiZhongSettingActivity.class);
			intent.putExtra("currentSelectedIndex", bizhongCurrentSelectedIndex);
			startActivityForResult(intent, 100);
			overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
			break;
		case R.id.accountyueLayout:
			intent = new Intent(BillAccountAddActivity.this, BillCalculatorActivity.class);
			intent.putExtra("num", accountyueTv.getText().toString());
			intent.putExtra("resultCode", ConstantUtil.EDIT_YUEFINISHED);
			startActivityForResult(intent, 100);
			break;
		case R.id.accountIsNoticeLayout:
			//�Ƿ�������layout
			if(noticeCb.isChecked()) {
				noticeCb.setChecked(false);
				accountNoticeValueLayout.setVisibility(ViewGroup.GONE);
				accountNoticeValueTv.setText("0");
			}else {
				noticeCb.setChecked(true);
				accountNoticeValueLayout.setVisibility(ViewGroup.VISIBLE);
			}
			break;
		case R.id.accountNoticeValueLayout:
			intent = new Intent(BillAccountAddActivity.this, BillCalculatorActivity.class);
			intent.putExtra("num", accountNoticeValueTv.getText().toString());
			intent.putExtra("resultCode", ConstantUtil.EDIT_NOTICEVALUEFINISHED);
			startActivityForResult(intent, 100);
			break;
		case R.id.beizhu:
			intent = new Intent(BillAccountAddActivity.this, BillTextInputActivity.class);
			title = "�༭�˻���ע";
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
		BillAccountAddActivity.this.finish();
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
			Toast.makeText(BillAccountAddActivity.this, "��ѡ���������!", 0).show();
			return false;
		}
		
		if(accountName == null || "".equals(accountName)) {
			Toast.makeText(BillAccountAddActivity.this, "����д�˻�����!", 0).show();
			return false;
		}
		
		if(accountYue == null || "".equals(accountYue)) {
			Toast.makeText(BillAccountAddActivity.this, "����д�˻����!", 0).show();
			return false;
		}
		
		if(accountBiZhong == null || "".equals(accountBiZhong)) {
			Toast.makeText(BillAccountAddActivity.this, "��ѡ���˻�����!", 0).show();
			return false;
		}
		
		if(noticeCb.isChecked()) {
			accountNoticeValue = accountNoticeValueTv.getText().toString();
			if(accountNoticeValue == null || "".equals(accountNoticeValue)) {
				Toast.makeText(BillAccountAddActivity.this, "����д�����߽��!", 0).show();
				return false;
			}
		}
		
		return true;
	}
	
	
	
	
	
	
}
