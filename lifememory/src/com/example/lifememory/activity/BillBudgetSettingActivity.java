package com.example.lifememory.activity;

import java.util.List;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillCatagoryItem;
import com.example.lifememory.adapter.BillBudgetListViewAdapter;
import com.example.lifememory.db.service.BillCatagoryService;
import com.example.lifememory.db.service.BillInfoService;
import com.example.lifememory.utils.AppSharedPreference;
import com.example.lifememory.utils.ConstantUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BillBudgetSettingActivity extends Activity {
	private ListView listView;
	private TextView totalBudgetTv, spendTv, leftTv;
	private BillCatagoryService catagoryService;
	private BillInfoService infoService;
	private List<BillCatagoryItem> catagoryItems;
	private BillBudgetListViewAdapter adapter;
	private Intent intent;
	private int catagoryIdx;
	private AppSharedPreference asp;
	/**
	 * ���ݿ���ͳ�Ƶ��������͵�Ԥ��ͣ���Ԥ��
	 * ��ǰ��������ֶ�������Ԥ�㣬������õ���Ԥ��С�����ݿ���ͳ�Ƶ���Ԥ������ʾ���ݿ���ͳ�Ƶ���Ԥ�㣬����ʾ�û�   ��ǰ���õ���Ԥ�㲻��С����������Ԥ��͡�
	 * ������õ���Ԥ��������ݿ���ͳ�Ƶ���Ԥ�㣬��ֱ����ʾ���õ���Ԥ��ֵ
	 */
	private String totalBudgetDB;     
	private String totalBudgetSp;   //sharedpreference�б������Ԥ��ֵ,�����õ���Ԥ��ֵ
	private String totalSpendStr;   //����billTypeΪ1�� ֧���˵����ܻ���
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				//��ȡ���ݳɹ�!
				initListView();
				initTotalBudgetTv();
				new FindTotalSpendValueThread().start();
				break;
			case 1:
				//�޸�Ԥ��ֵ�ɹ�
				new InitDatasThread().start();
				break;
			case 2:
				//��������֧���˵����ɹ�
				spendTv.setText(totalSpendStr);
				initTotalSpendTv();
				break;
				
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.bill_budget_settting_layout);
		
		asp = new AppSharedPreference(this);
		catagoryService = new BillCatagoryService(this);
		infoService = new BillInfoService(this);
		findViews();
		new InitDatasThread().start();
	}
	
	private void findViews() {
		listView = (ListView) this.findViewById(R.id.listView);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				catagoryIdx = catagoryItems.get(position).getIdx();
				intent = new Intent(BillBudgetSettingActivity.this, BillCalculatorActivity.class);
				String numStr = catagoryItems.get(position).getBugget() == 0.0 ? "0" : catagoryItems.get(position).getBugget() + "";
				intent.putExtra("num", numStr);
				intent.putExtra("resultCode", ConstantUtil.EDIT_BUDGET_FINISHED);
				startActivityForResult(intent, 100);
			}
		});
		totalBudgetTv = (TextView) this.findViewById(R.id.totalBudget);
		spendTv = (TextView) this.findViewById(R.id.spend);
		leftTv = (TextView) this.findViewById(R.id.left);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case ConstantUtil.EDIT_BUDGET_FINISHED:
			String numStr = data.getStringExtra("num");
			new UpdateBudgetValueThread(numStr).start();
			break;
		case ConstantUtil.EDIT_YUEFINISHED:
			totalBudgetSp = data.getStringExtra("num");
			totalBudgetDB = String.valueOf(catagoryService.findTotalBudget() );
			double tbDB = Double.parseDouble(totalBudgetDB);
			double tbSP = Double.parseDouble(totalBudgetSp);
			if(tbSP < tbDB) {
				//���õ���Ԥ��ֵС�����ݿ���ͳ�Ƶ���Ԥ��ֵ(��������Ѿ����õ���Ԥ��ֵ)
				Toast.makeText(BillBudgetSettingActivity.this, "���õ���Ԥ��ֵ����С����������Ѿ����õ�Ԥ���ܺ�!", 0).show();
				totalBudgetTv.setText(totalBudgetDB + "");
				asp.setTotalBudget("0");
			}else {
				totalBudgetTv.setText(totalBudgetSp + "");
				asp.setTotalBudget(totalBudgetSp);
			}
			initTotalSpendTv();
			break;
		}
	}
	
	private class InitDatasThread extends Thread {
		@Override
		public void run() {
			catagoryItems = catagoryService.findBudgetInfos();
			totalBudgetDB = String.valueOf(catagoryService.findTotalBudget());
			totalBudgetSp = asp.getTotalBudget();
			
			Log.i("a", "totalBudgetDB = " + totalBudgetDB + " totalBudgetSp = " + totalBudgetSp);
			
			
			
			if(catagoryItems.size() > 0) {
				handler.sendEmptyMessage(0);
			}
		}
	}
	
	private class UpdateBudgetValueThread extends Thread {
		double budgetValue = 0;
		public UpdateBudgetValueThread(String numStr) {
			budgetValue = Double.parseDouble(numStr);
		}
		@Override
		public void run() {
			Log.i("a", "idx = " + catagoryIdx + " budgetValue = " + budgetValue);
			catagoryService.updateBudgetByIdx(catagoryIdx, budgetValue);
			handler.sendEmptyMessage(1);
		}
	}
	
	private void initListView() {
		adapter = new BillBudgetListViewAdapter(this, catagoryItems);
		listView.setAdapter(adapter);
	}
	
	private void initTotalBudgetTv() {
		double tbDB = Double.parseDouble(totalBudgetDB);
		double tbSP = Double.parseDouble(totalBudgetSp);
		if(tbSP < tbDB) {
			//���õ���Ԥ��ֵС�����ݿ���ͳ�Ƶ���Ԥ��ֵ(��������Ѿ����õ���Ԥ��ֵ)
			totalBudgetTv.setText(totalBudgetDB + "");
			asp.setTotalBudget("0");
		}else {
			totalBudgetTv.setText(totalBudgetSp + "");
			asp.setTotalBudget(totalBudgetSp);
		}
	}
	
	private void initTotalSpendTv() {
		double totalBudget = Double.parseDouble(totalBudgetTv.getText().toString());
		double spendValue = Double.parseDouble(spendTv.getText().toString());
		leftTv.setText(String.valueOf(totalBudget - spendValue));
		
	}
	
	private class FindTotalSpendValueThread extends Thread {
		@Override
		public void run() {
			totalSpendStr = infoService.findTotalSpendValue();
			handler.sendEmptyMessage(2);
		}
	}
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			back();
			break;
		case R.id.totalBudgetBtn:
		case R.id.totalBudgetLayout:
			intent = new Intent(BillBudgetSettingActivity.this, BillCalculatorActivity.class);
			String num = totalBudgetTv.getText().toString();
			intent.putExtra("num", num);
			intent.putExtra("resultCode", ConstantUtil.EDIT_YUEFINISHED);
			startActivityForResult(intent, 100);
			break;
			
		}
	}
	
	
	private void back() {
		BillBudgetSettingActivity.this.finish();
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
