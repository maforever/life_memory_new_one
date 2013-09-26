package com.example.lifememory.fragments;

import java.util.List;

import com.example.lifememory.R;
import com.example.lifememory.activity.BillReimbursementValueInputDialog;
import com.example.lifememory.activity.model.Bill;
import com.example.lifememory.adapter.BillWeiBaoxiaoAdapter;
import com.example.lifememory.db.service.BillInfoService;
import com.example.lifememory.utils.ConstantUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

public class FR_Bill_WeiBaoXiao extends Fragment {
	String year;
	BillInfoService infoService;
	LinearLayout msgLaytout;
	List<Bill> bills;
	ListView listView;
	BillWeiBaoxiaoAdapter adapter;
	Intent intent;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				listView.setVisibility(ViewGroup.VISIBLE);
				adapter = new BillWeiBaoxiaoAdapter(getActivity(), bills);
				listView.setAdapter(adapter);
				msgLaytout.setVisibility(ViewGroup.GONE);

				break;
			case 2:
				listView.setVisibility(ViewGroup.GONE);
				msgLaytout.setVisibility(ViewGroup.VISIBLE);
				break;
			}
		};
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		infoService = new BillInfoService(getActivity());
	}
	
	public FR_Bill_WeiBaoXiao(String year) {
		this.year = year;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.bill_reimbursement_fragment_layout, container, false);
		listView = (ListView) view.findViewById(R.id.listView);
		msgLaytout = (LinearLayout) view.findViewById(R.id.msgLayout);
		
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				intent = new Intent(getActivity(), BillReimbursementValueInputDialog.class);
				startActivityForResult(intent, 100);
			}
			
		});
		
		new InitDatasThread().start();
		
		return view;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case ConstantUtil.REIMBURSEMENT_VALUE_INPUT:
			
			break;
		}
	}

	private class InitDatasThread extends Thread {
		@Override
		public void run() {
			bills = infoService.findWeiBaoxiao(year);
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





