package com.example.lifememory.activity;

import java.util.List;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillMember;
import com.example.lifememory.adapter.BillMemberListViewAdapter;
import com.example.lifememory.db.service.BillMemberService;
import com.example.lifememory.utils.ConstantUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BillMemberSelectActivity extends Activity {
	private ListView listView;
	private Intent intent;
	private List<BillMember> members;
	private BillMemberService memberService;
	private int currentSelectedIndex = 0;
	private BillMemberListViewAdapter adapter;
	private int currentLongClickIndex = 0;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				initViews();
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.bill_member_select_layout);
		
		memberService = new BillMemberService(this);
		currentSelectedIndex = this.getIntent().getIntExtra("currentSelectedIndex", 0);
		findViews();
		new InitDatasThread().start();
		setListeners();
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		if(memberService != null) {
			new InitDatasThread().start();
		}
	}
	
	private class InitDatasThread extends Thread {
		@Override
		public void run() {
			members = memberService.listAllMember();
			handler.sendEmptyMessage(0);
		}
	}
	
	private void findViews() {
		listView = (ListView) this.findViewById(R.id.listView);
	}
	
	private void initViews() {
		adapter = new BillMemberListViewAdapter(BillMemberSelectActivity.this, members, memberService);
		listView.setAdapter(adapter);
		adapter.setSelected(currentSelectedIndex);
	}
	
	private void setListeners() {
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				intent = new Intent();
				intent.putExtra("member", members.get(position).getName());
				intent.putExtra("currentSelectedIndex", position);
				BillMemberSelectActivity.this.setResult(ConstantUtil.SELECT_MEMEBER_FINISHED, intent);
				BillMemberSelectActivity.this.finish();
				overridePendingTransition(R.anim.activity_steady, R.anim.activity_down);
			}
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView deleteTag = (TextView) view.findViewById(R.id.deleteTag);
				if(deleteTag.getVisibility() == ViewGroup.VISIBLE) {
					deleteTag.setVisibility(ViewGroup.GONE);
				}else {
					adapter.setShowDeleteTag(position);
				}
				return true;
			}
		});
	}
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			back();
			break;
		case R.id.add:
			intent = new Intent(BillMemberSelectActivity.this, BillTextInputActivity.class);
			intent.putExtra("title", "≥…‘±…Ë÷√");
			intent.putExtra("fenlei", ConstantUtil.EDIT_MEMBER_FINISHED);
			intent.putExtra("editNum", 50);
			startActivity(intent);
			overridePendingTransition(R.anim.activity_steady, R.anim.activity_down);
			break;
		}
	}
	
	
	private void back() {
		intent = new Intent();
		this.setResult(ConstantUtil.EDIT_NOCHANGE_BACK, intent);
		BillMemberSelectActivity.this.finish();
		overridePendingTransition(R.anim.activity_steady, R.anim.activity_down);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			back();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		memberService.closeDB();
	}
}























