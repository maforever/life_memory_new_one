package com.example.lifememory.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillTemplate;
import com.example.lifememory.db.service.BillTemplateService;

public class BillTemplateNameInputDialogActivity extends Activity {
	private TextView titleTv;
	private EditText contentTv;
	private BillTemplateService templateService;
	private BillTemplate template;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				//添加成功
				Toast.makeText(BillTemplateNameInputDialogActivity.this, "模版添加成功!", 0).show();
				BillTemplateNameInputDialogActivity.this.finish();
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_bill_template_name_input);
		
		templateService = new BillTemplateService(this);
		findViews();
	}
	
	private void findViews() {
		titleTv = (TextView) this.findViewById(R.id.title);
		contentTv = (EditText) this.findViewById(R.id.content);
		template = (BillTemplate) this.getIntent().getSerializableExtra("template");
	}
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.sure:
			if(validateContents(contentTv.getText().toString())) {
				
				if(!templateService.isExists(contentTv.getText().toString().trim())) {
					template.setName(contentTv.getText().toString());
					new AddTemplateThread().start();
					handler.sendEmptyMessage(0);
				}else {
					Toast.makeText(BillTemplateNameInputDialogActivity.this, "模版名称" + contentTv.getText().toString() + "已经存在!", 0).show();
				}

			}else {
				Toast.makeText(BillTemplateNameInputDialogActivity.this, "请填写模版的名称!", 0).show();
			}
			break;
		case R.id.cancel:
			BillTemplateNameInputDialogActivity.this.finish();
			break;
		}
	}
	
	private class AddTemplateThread extends Thread {
		@Override
		public void run() {
			templateService.addTemplate(template);
		}
	}
	
	private boolean validateContents(String content) {
		if(content != null && !"".equals(content)) {
			return true;
		}
		return false;
	}
	
}
