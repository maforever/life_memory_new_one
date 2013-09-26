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

public class BillWeiBaoxiaoAdapter extends BaseAdapter {
	private Context context;
	private List<Bill> bills;
	private LayoutInflater inflater;
	public BillWeiBaoxiaoAdapter(Context context, List<Bill> bills) {
		this.context = context;
		inflater = LayoutInflater.from(context);
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
		TextView account;
		ViewHolder vh;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.bill_reimbursement_weibaoxiao_item, null);
			name = (TextView) convertView.findViewById(R.id.name);
			spend = (TextView) convertView.findViewById(R.id.spend);
			account = (TextView) convertView.findViewById(R.id.account);
			vh = new ViewHolder();
			vh.name = name;
			vh.spend = spend;
			vh.account = account;
			convertView.setTag(vh);
		}else {
			vh = (ViewHolder) convertView.getTag();
			name = vh.name;
			spend = vh.spend;
			account = vh.account;
		}
		name.setText(bills.get(position).getOutCatagory());
		spend.setText(bills.get(position).getJine() + "Ԫ");
		account.setText(bills.get(position).getAccount());
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView name;
		TextView spend;
		TextView account;
	}
}





















