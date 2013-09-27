package com.example.lifememory.adapter;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.Bill;

public class BillMonthDetailsListViewListViewAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<Bill> bills;
	private Bill bill;
	int txtLineCount;
	public BillMonthDetailsListViewListViewAdapter(Context context, List<Bill> bills) {
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
		ImageView icon;
		TextView catagoryName;
		TextView money;
		final TextView beizhu;
		TextView account;
		ViewHolder vh;
		final int lines;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.bill_monthdetails_listview_listview_item, null);
			icon = (ImageView) convertView.findViewById(R.id.icon);
			catagoryName = (TextView) convertView.findViewById(R.id.catagoryName);
			money = (TextView) convertView.findViewById(R.id.money);
			beizhu = (TextView) convertView.findViewById(R.id.beizhu);
			account = (TextView) convertView.findViewById(R.id.account);
			vh = new ViewHolder();
			vh.icon = icon;
			vh.catagoryName = catagoryName;
			vh.money = money;
			vh.beizhu = beizhu;
			vh.account = account;
			convertView.setTag(vh);
		}else {
			vh = (ViewHolder) convertView.getTag();
			icon = vh.icon;
			catagoryName = vh.catagoryName;
			money = vh.money;
			beizhu = vh.beizhu;
			account = vh.account;
		}
		
		bill = bills.get(position);
		if(bill.getBillType() == 1) {
			//支出
			icon.setImageResource(R.drawable.icon_spend);
			catagoryName.setText(bill.getOutCatagory());
			money.setTextColor(context.getResources().getColor(R.color.spendColor));
		}else if(bill.getBillType() == 2) {
			//收入
			icon.setImageResource(R.drawable.icon_income);
			catagoryName.setText(bill.getInCatagory());
			money.setTextColor(context.getResources().getColor(R.color.incomeColor));
		}else if(bill.getBillType() == 3) {
			icon.setImageResource(R.drawable.icon_transfer);
			money.setTextColor(context.getResources().getColor(R.color.transferColor));
			catagoryName.setText(bill.getTransferOut() + " > " + bill.getTransferIn());
		}
		
		money.setText(bill.getJine());
		if(bill.getBeizhu() == null || "".equals(bill.getBeizhu())) {
			beizhu.setText("木有备注");
		}else 
			beizhu.setText(bill.getBeizhu());
		
		account.setText(bill.getAccount());
		
		
		return convertView;
	}
	
	static class ViewHolder {
		ImageView icon;
		TextView catagoryName;
		TextView money;
		TextView beizhu;
		TextView account;
	}


}












