package com.example.lifememory.adapter;

import java.util.List;

import com.example.lifememory.R;
import com.example.lifememory.activity.BillInputActivity;
import com.example.lifememory.activity.BillLiuShuiSettingDialogActivity;
import com.example.lifememory.activity.model.BillMonthDetailsListViewModel;
import com.example.lifememory.activity.views.MyInnerListView;
import com.example.lifememory.utils.SetListViewHeight;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BillMonthDetailsListViewAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private Context context;
	private BillMonthDetailsListViewListViewAdapter adapter;
	private List<BillMonthDetailsListViewModel> monthDetails;
	private Activity ac;
	public BillMonthDetailsListViewAdapter(Context context, List<BillMonthDetailsListViewModel> monthDetails, Activity ac) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.monthDetails = monthDetails;
		this.ac = ac;
	}
	@Override
	public int getCount() {
		return monthDetails.size();
	}

	@Override
	public Object getItem(int position) {
		return monthDetails.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView dayTv;
		TextView weekTv;
		TextView incomeTv;
		TextView spendTv;
		ListView listView;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.bill_monthdetails_listview_item, null);
			dayTv = (TextView) convertView.findViewById(R.id.day);
			weekTv = (TextView) convertView.findViewById(R.id.week);
			incomeTv = (TextView) convertView.findViewById(R.id.income);
			spendTv = (TextView) convertView.findViewById(R.id.spend);
			listView = (ListView) convertView.findViewById(R.id.listView);
		}else {
			dayTv = (TextView) convertView.findViewById(R.id.day);
			weekTv = (TextView) convertView.findViewById(R.id.week);
			incomeTv = (TextView) convertView.findViewById(R.id.income);
			spendTv = (TextView) convertView.findViewById(R.id.spend);
			listView = (ListView) convertView.findViewById(R.id.listView);
		}
		
		dayTv.setText(monthDetails.get(position).getDay());
		incomeTv.setText(monthDetails.get(position).getIncome());
		spendTv.setText(monthDetails.get(position).getSpend());
		weekTv.setText(monthDetails.get(position).getWeek());
		adapter = new BillMonthDetailsListViewListViewAdapter(context, monthDetails.get(position).getBills());
		
		if(monthDetails.get(position).getBills().size() == 1) {
			incomeTv.setVisibility(ViewGroup.GONE);
			spendTv.setVisibility(ViewGroup.GONE);
		}
		
		listView.setAdapter(adapter);
		final int parentListViewPosition = position;
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//Toast.makeText(context, monthDetails.get(parentListViewPosition).getBills().get(position).getJine(), 0).show();
				int idx = monthDetails.get(parentListViewPosition).getBills().get(position).getIdx();
				Intent intent = new Intent(ac, BillInputActivity.class);
				intent.putExtra("flag", "view");
				intent.putExtra("idx", idx);
				ac.startActivity(intent);
				ac.overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
			}
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				int idx = monthDetails.get(parentListViewPosition).getBills().get(position).getIdx();
				int billType = monthDetails.get(parentListViewPosition).getBills().get(position).getBillType();
				Intent intent = new Intent(ac, BillLiuShuiSettingDialogActivity.class);
				if(billType == 1) {
					//支出
					intent.putExtra("flag", "spend");
				}else if(billType ==2) {
					//收入
					intent.putExtra("flag", "income");
				}else if(billType ==3) {
					//转账
					intent.putExtra("flag", "transfer");
				}

				intent.putExtra("idx", idx);
				ac.startActivity(intent);
				return true;
			}
		});
		
		new SetListViewHeight().setListViewHeightBasedOnChildren(listView, 40);
		
		
		return convertView;
	}

}







