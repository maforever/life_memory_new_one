package com.example.lifememory.adapter;

import java.util.List;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.Bill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BillYiBaoXiaoAdapter extends BaseAdapter {
	Context context;
	List<Bill> bills;
	LayoutInflater inflater;
	public BillYiBaoXiaoAdapter(Context context, List<Bill> bills) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.bills = bills;
	}
	
	@Override
	public int getCount() {
		return bills.size();
	}

	@Override
	public Object getItem(int position) {
		return bills.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView name;
		TextView spend;
		TextView reimburseValue;
		TextView baoxiaoxiangqing;
		ViewHolder vh;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.bill_reimbursement_yibaoxiao_item, null);
			name = (TextView) convertView.findViewById(R.id.name);
			spend = (TextView) convertView.findViewById(R.id.spend);
			reimburseValue = (TextView) convertView.findViewById(R.id.reimburseValue);
			baoxiaoxiangqing = (TextView) convertView.findViewById(R.id.baoxiaoxiangqing);
			vh = new ViewHolder();
			vh.name = name;
			vh.spend = spend;
			vh.reimburseValue = reimburseValue;
			vh.baoxiaoxiangqing = baoxiaoxiangqing;
			convertView.setTag(vh);
		}else {
			vh = (ViewHolder) convertView.getTag();
			name = vh.name;
			spend = vh.spend;
			reimburseValue = vh.reimburseValue;
			baoxiaoxiangqing = vh.baoxiaoxiangqing;
		}
		name.setText(bills.get(position).getOutCatagory());
		spend.setText(bills.get(position).getJine() + "Ԫ");
		reimburseValue.setText(bills.get(position).getBaoxiaojine() + "Ԫ");
		baoxiaoxiangqing.setText(bills.get(position).getBeizhu());
		return convertView;
	}
	
	static class ViewHolder {
		TextView name;
		TextView spend;
		TextView reimburseValue;
		TextView baoxiaoxiangqing;
	}
}















