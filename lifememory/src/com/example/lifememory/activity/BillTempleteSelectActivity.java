package com.example.lifememory.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.Toast;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillTemplate;
import com.example.lifememory.adapter.BillTemplateGridViewAdapter;
import com.example.lifememory.db.service.BillTemplateService;
import com.example.lifememory.dialog.DialogAlertBill;
import com.example.lifememory.dialog.DialogAlertListener;

public class BillTempleteSelectActivity extends Activity {
	private GridView gridView;
	private List<BillTemplate> templates;
	private BillTemplateService templateService;
	private BillTemplateGridViewAdapter adapter;
	public List<Integer> templateIdx = new ArrayList<Integer>();     //账单模版的idx
	private CheckBox deleteCheckBox;
	private Intent intent;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				//读取模版数据成功
				initGridView();
				break;
			case 1:
				//删除模版成功
				adapter.templateIds.clear();
				
				new InitDatasThread().start();
				
				Toast.makeText(BillTempleteSelectActivity.this, "删除模版成功！", 0).show();
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.bill_template_select_layout);
		
		templateService = new BillTemplateService(this);
		findViews();
		new InitDatasThread().start();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(templateService != null) {
			new InitDatasThread().start();
		}
	}
	
	private void findViews() {
		gridView = (GridView) this.findViewById(R.id.gridView);
		deleteCheckBox = (CheckBox) this.findViewById(R.id.deleteCheckBox);
		
		deleteCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					deleteCheckBox.setText("完成");
					adapter.showDeleteTag(true);
				}else {
					deleteCheckBox.setText("删除");
					adapter.showDeleteTag(false);
					
					new DialogAlertBill(BillTempleteSelectActivity.this, listener, "您确定删除所选择的模版吗?").show();
				}
			}
		});
		
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				intent = new Intent(BillTempleteSelectActivity.this, BillInputActivity.class);
				intent.putExtra("flag", "template");
				intent.putExtra("template", templates.get(position));
				startActivity(intent);
				overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
			}
		});
	}
	
	private DialogAlertListener listener = new DialogAlertListener () {
		@Override
		public void onDialogCreate(Dialog dlg, Object param) {
			
		}
		@Override
		public void onDialogOk(Dialog dlg, Object param) {
			new DeleteTemplatesThread().start();
		}
		@Override
		public void onDialogCancel(Dialog dlg, Object param) {
			
		}
		@Override
		public void onDialogSave(Dialog dlg, Object param) {
			
		}
		@Override
		public void onDialogUnSave(Dialog dlg, Object param) {
			
		}
	};
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			back();
			break;
		}
	}
	
	private class InitDatasThread extends Thread {
		@Override
		public void run() {
			templates = templateService.findAllTemplates();
			if(templates.size() > 0) {
				handler.sendEmptyMessage(0);
			}
		}
	}
	
	private class DeleteTemplatesThread extends Thread {
		@Override
		public void run() {
			if(adapter.templateIds.size() > 0) {
				templateService.deleteTemplatesByIds(adapter.templateIds);
				handler.sendEmptyMessage(1);
			}
		}
	}
	
	private void initGridView() {
		adapter = new BillTemplateGridViewAdapter(this, templates);
		gridView.setAdapter(adapter);
	}
	
	private void back() {
		BillTempleteSelectActivity.this.finish();
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


















