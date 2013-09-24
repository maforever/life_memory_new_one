package com.example.lifememory.activity;

import java.util.List;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillAccountItem;
import com.example.lifememory.activity.views.MyInnerListView;
import com.example.lifememory.adapter.BillAccountDetailsListViewAdapter;
import com.example.lifememory.db.service.BillAccountService;
import com.example.lifememory.utils.SetListViewHeight;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BillAccountDetailsActivity extends Activity {
	private ListView moneyListView, creditCardListView, depositListView, netListView;
	private RelativeLayout zongzichanLayout, moneyLayout, creditCardLayout, depositCardLayout ,netLayout;
	private BillAccountService accountService;
	private List<BillAccountItem> moneyItems, creditCardItems, depositItems, netItems;
	private BillAccountDetailsListViewAdapter moneyAdatper, creditCardAdapter, depositAdapter, netAdapter;
	private TextView bizhongTv, jingzichanTv;
	private double jingzichan;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				//读取账户信息成功
				initViews();
				break;

			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.bill_account_details_layout);
		
		accountService = new BillAccountService(this);
		findViews();
		new InitDatasThread().start();
	}
	
	
	private void findViews() {
		zongzichanLayout = (RelativeLayout) this.findViewById(R.id.zongzichanLayout);
		moneyLayout = (RelativeLayout) this.findViewById(R.id.moneyLayout);
		creditCardLayout = (RelativeLayout) this.findViewById(R.id.creditcardLayout);
		depositCardLayout = (RelativeLayout) this.findViewById(R.id.depositcardLayout);
		netLayout = (RelativeLayout) this.findViewById(R.id.netLayout);
		zongzichanLayout.getBackground().setAlpha(95);
		moneyLayout.getBackground().setAlpha(95);
		creditCardLayout.getBackground().setAlpha(95);
		depositCardLayout.getBackground().setAlpha(95);
		netLayout.getBackground().setAlpha(95);
		
		moneyListView = (ListView) this.findViewById(R.id.moneyListView);
		creditCardListView = (ListView) this.findViewById(R.id.creditCardListView);
		depositListView = (ListView) this.findViewById(R.id.depositListView);
		netListView = (ListView) this.findViewById(R.id.netListView);
		
		bizhongTv = (TextView) this.findViewById(R.id.bizhong);
		jingzichanTv = (TextView) this.findViewById(R.id.jingzichan);
	}
	
	private class InitDatasThread extends Thread {
		@Override
		public void run() {
			moneyItems = accountService.findItemsByAccountCatagory(1);
			creditCardItems = accountService.findItemsByAccountCatagory(2);
			depositItems = accountService.findItemsByAccountCatagory(3);
			netItems = accountService.findItemsByAccountCatagory(4);
			jingzichan = accountService.getJingZiChan();
			handler.sendEmptyMessage(0);
		}
	}
	
	private void initViews() {
		moneyAdatper = new BillAccountDetailsListViewAdapter(this, moneyItems);
		creditCardAdapter = new BillAccountDetailsListViewAdapter(this, creditCardItems);
		depositAdapter = new BillAccountDetailsListViewAdapter(this, depositItems);
		netAdapter = new BillAccountDetailsListViewAdapter(this, netItems);
		moneyListView.setAdapter(moneyAdatper);
		creditCardListView.setAdapter(creditCardAdapter);
		depositListView.setAdapter(depositAdapter);
		netListView.setAdapter(netAdapter);
		new SetListViewHeight().setListViewHeightBasedOnChildren(moneyListView);
		new SetListViewHeight().setListViewHeightBasedOnChildren(creditCardListView);
		new SetListViewHeight().setListViewHeightBasedOnChildren(depositListView);
		new SetListViewHeight().setListViewHeightBasedOnChildren(netListView);
		
		bizhongTv.setText("￥");
		jingzichanTv.setText(jingzichan + "");
	}
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			back();
			break;
		}
	}
	
	private void back() {
		BillAccountDetailsActivity.this.finish();
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
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		accountService.closeDB();
	}
}

















