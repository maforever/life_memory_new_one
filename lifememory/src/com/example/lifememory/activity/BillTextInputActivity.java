package com.example.lifememory.activity;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillInCatagory;
import com.example.lifememory.activity.model.BillMember;
import com.example.lifememory.db.service.BillInCatagoryService;
import com.example.lifememory.db.service.BillMemberService;
import com.example.lifememory.utils.ConstantUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SyncStateContract.Constants;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BillTextInputActivity extends Activity {
	private Intent intent;
	private TextView titleTv, editNumTv;
	private EditText contentEt;
	private String title;
	private BillMemberService dbService;
	private BillInCatagoryService inCatagoryService;
	private int currentFenLei = 0;
	private int editNum = 0; // 可编辑的文本字数
	private String content; // 输入的文本内容
	private BillMember member;
	private BillInCatagory ic;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				//添加成员
				Toast.makeText(BillTextInputActivity.this, "成员添加成功!", 0).show();
				break;
			case 1:
				//添加收入类别
				boolean flag = (Boolean) msg.obj;
				if(!flag) {
					Toast.makeText(BillTextInputActivity.this, "收入类别重名，请另取名称!", 0).show();
				}
				Toast.makeText(BillTextInputActivity.this, "成员类别成功!", 0).show();
				break;
				
			}
			BillTextInputActivity.this.finish();
			overridePendingTransition(R.anim.activity_steady, R.anim.activity_down);
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.bill_text_input_layout);

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

		findViews();
		initDatas();
		initViews();

	}

	private void findViews() {
		titleTv = (TextView) this.findViewById(R.id.title);
		editNumTv = (TextView) this.findViewById(R.id.editNum);
		contentEt = (EditText) this.findViewById(R.id.content);
		contentEt.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// Log.i("a", "count = " + count);
				content = contentEt.getText().toString();
				if (content.length() <= editNum) {
					editNumTv.setText((editNum - content.length()) + "");
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	private void initDatas() {
		title = this.getIntent().getStringExtra("title");
		currentFenLei = this.getIntent().getIntExtra("fenlei", 0);
		editNum = this.getIntent().getIntExtra("editNum", 0);
		content = this.getIntent().getStringExtra("content");
	}

	private void initViews() {
		titleTv.setText(title);
		contentEt.setText(content);
		int contentLength = content.length();
		Log.i("a", "contentLength = " + contentLength);
		editNumTv.setText((editNum - contentLength) + "");

		InputFilter[] filters = new InputFilter[1];
		filters[0] = new InputFilter.LengthFilter(editNum);
		contentEt.setFilters(filters);

	}

	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			back();
			break;
		case R.id.ok:
			content = contentEt.getText().toString();
			if (content == null || "".equals(content)) {
				Toast.makeText(BillTextInputActivity.this, "您还没有输入任何内容,无法保存", 0)
						.show();
				return;
			}

			intent = this.getIntent();
			intent.putExtra("content", content);
			int resultCode = 0;
			if (currentFenLei == ConstantUtil.EDIT_MEMBER_FINISHED) {
				dbService = new BillMemberService(this);
				member = new BillMember();
				member.setName(content);
				new Thread(new Runnable() {
					@Override
					public void run() {
						dbService.addMember(member);
						handler.sendEmptyMessage(0);
					}
				}).start();
				
			}else if(currentFenLei == ConstantUtil.EDIT_INCATAGORY_FINISHED) {
				inCatagoryService = new BillInCatagoryService(this);
				ic = new BillInCatagory();
				ic.setName(content);
				new Thread(new Runnable() {
					@Override
					public void run() {
						boolean flag = inCatagoryService.addItem(ic);
						handler.sendMessage(handler.obtainMessage(1, flag));
					}
				}).start();
			}else {
				switch (currentFenLei) {
				case ConstantUtil.EDIT_NAME_FINISHED:
					resultCode = ConstantUtil.EDIT_NAME_FINISHED;
					break;
				case ConstantUtil.EDIT_NOTICE_FINISHED:
					resultCode = ConstantUtil.EDIT_NOTICE_FINISHED;
					break;
				case ConstantUtil.EDIT_BEIZHU:
					resultCode = ConstantUtil.EDIT_BEIZHU;
					break;
				}

				Log.i("a", "resultCode = " + resultCode + " content = "
						+ content);

				this.setResult(resultCode, intent);
				BillTextInputActivity.this.finish();
				overridePendingTransition(R.anim.activity_steady,
						R.anim.activity_down);

				break;
			}
		}
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

	private void back() {
		intent = new Intent();
		this.setResult(ConstantUtil.EDIT_NOCHANGE_BACK, intent);
		BillTextInputActivity.this.finish();
		overridePendingTransition(R.anim.activity_steady, R.anim.activity_down);
	}

	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(dbService != null) {
			dbService.closeDB();
		}
	}
}
