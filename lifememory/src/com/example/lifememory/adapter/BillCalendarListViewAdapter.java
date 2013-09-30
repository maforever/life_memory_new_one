package com.example.lifememory.adapter;

import java.util.List;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.Bill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BillCalendarListViewAdapter extends BaseAdapter {
	private Context context;
	private List<Bill> bills;
	private LayoutInflater inflater;
	public BillCalendarListViewAdapter(Context context, List<Bill> bills) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
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
		ImageView icon;
		TextView catagoryName;
		TextView accountName;
		TextView value;
		ViewHolder vh;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.bill_calendar_listview_item, null);
			icon = (ImageView) convertView.findViewById(R.id.icon);
			catagoryName = (TextView) convertView.findViewById(R.id.catagoryName);
			accountName = (TextView) convertView.findViewById(R.id.accountName);
			value = (TextView) convertView.findViewById(R.id.value);
			vh = new ViewHolder();
			vh.icon = icon;
			vh.catagoryName = catagoryName;
			vh.accountName = accountName;
			vh.value = value;
			convertView.setTag(vh);
		}else {
			vh = (ViewHolder) convertView.getTag();
			icon = vh.icon;
			catagoryName = vh.catagoryName;
			accountName = vh.accountName;
			value = vh.value;
		}
		
		switch (bills.get(position).getBillType()) {
		case 1:
			//支出
			icon.setBackgroundResource(R.drawable.icon_spend);
			value.setTextColor(context.getResources().getColor(R.color.spendColor));
			catagoryName.setText(bills.get(position).getOutCatagory());
			accountName.setText(bills.get(position).getAccount());
			break;
		case 2:
			icon.setBackgroundResource(R.drawable.icon_income);
			value.setTextColor(context.getResources().getColor(R.color.incomeColor));
			catagoryName.setText(bills.get(position).getInCatagory());
			accountName.setText(bills.get(position).getAccount());
			break;
		case 3:
			icon.setBackgroundResource(R.drawable.icon_transfer);
			value.setTextColor(context.getResources().getColor(R.color.transferColor));
			accountName.setText(bills.get(position).getTransferOut() + " 转入 " + bills.get(position).getTransferIn());
			catagoryName.setText("转账");
			LayoutParams params = accountName.getLayoutParams();
//			params.width = LayoutParams.WRAP_CONTENT;
			params.width = 260;
			accountName.setLayoutParams(params);
			
			break;
		}
		value.setText(bills.get(position).getJine() + "元");
		
		return convertView;
	}

	static class ViewHolder {
		ImageView icon;
		TextView catagoryName;
		TextView accountName;
		TextView value;
	}
}







