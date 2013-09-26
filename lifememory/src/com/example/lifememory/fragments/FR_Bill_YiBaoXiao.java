package com.example.lifememory.fragments;

import java.util.List;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.Bill;
import com.example.lifememory.adapter.BillYiBaoXiaoAdapter;
import com.example.lifememory.db.service.BillInfoService;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class FR_Bill_YiBaoXiao extends Fragment {
	ListView listView;
	List<Bill> bills;
	BillInfoService infoService;
	LinearLayout msgLayout;
	String year;
	BillYiBaoXiaoAdapter adapter;
	TextView msgTv;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				listView.setVisibility(ViewGroup.VISIBLE);
				msgLayout.setVisibility(ViewGroup.GONE);
				adapter = new BillYiBaoXiaoAdapter(getActivity(), bills);
				listView.setAdapter(adapter);
				break;
			case 2:
				listView.setVisibility(ViewGroup.GONE);
				msgLayout.setVisibility(ViewGroup.VISIBLE);
				msgTv.setText(year + "年" + "还没有任何报销!");
				break;
			}
		};
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		infoService = new BillInfoService(getActivity());
	}
	
	public FR_Bill_YiBaoXiao(String year) {
		this.year = year;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.bill_reimbursement_fragment_layout, container, false);
		listView = (ListView) view.findViewById(R.id.listView);
		msgLayout = (LinearLayout) view.findViewById(R.id.msgLayout);
		msgTv = (TextView) view.findViewById(R.id.msg);
		new InitDatasThread().start();
		
		return view;
	}
	
	private class InitDatasThread extends Thread {
		@Override
		public void run() {
			bills = infoService.findYiBaoXiao(year);
			if(bills.size() > 0) {
				handler.sendEmptyMessage(1);
			}else {
				handler.sendEmptyMessage(2);
			}
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		infoService.closeDB();
		infoService = null;
	}
}












