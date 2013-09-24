package com.example.lifememory.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.Bill;
import com.example.lifememory.db.service.BillInfoService;
import com.example.lifememory.utils.DateFormater;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BillIndexGridViewAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private String[] titles;
	private int[] imageIds;
	private boolean isFirstPage; // 用于标示是否是第一页的gridview数据，第一页有个特殊的显示界面
	private List<Bill> todayBills, monthIncomeBills, monthSpendBills;

	public BillIndexGridViewAdapter(Context context, String[] titles,
			int[] imageIds, boolean isFirstPage, List<Bill> todayBills,
			List<Bill> monthIncomeBills, List<Bill> monthSpendBills) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.titles = titles;
		this.imageIds = imageIds;
		this.isFirstPage = isFirstPage;
		this.todayBills = todayBills;
		this.monthIncomeBills = monthIncomeBills;
		this.monthSpendBills = monthSpendBills;
	}

	public BillIndexGridViewAdapter(Context context, String[] titles,
			int[] imageIds, boolean isFirstPage) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.titles = titles;
		this.imageIds = imageIds;
		this.isFirstPage = isFirstPage;
	}

	@Override
	public int getCount() {
		return titles.length;
	}

	@Override
	public Object getItem(int position) {
		return titles[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		TextView title;
		ViewHolder vh;
		if (isFirstPage) {
			// 是第一页的
			if (position == 3) {
				View view = inflater.inflate(
						R.layout.gridview_item_bill_index_today, null);
				TextView todayAccount = (TextView) view
						.findViewById(R.id.benriaccount);
				ImageView benriIcon = (ImageView) view
						.findViewById(R.id.benriicon);
				TextView monthSpend = (TextView) view
						.findViewById(R.id.benyuespendaccount);
				TextView monthIncome = (TextView) view
						.findViewById(R.id.benyueincomeaccount);

				double todayAccountResult = 0;
				if (todayBills != null && todayBills.size() > 0) {
					for (Bill bill : todayBills) {
						double todayNum = Double.parseDouble(bill.getJine());
						switch (bill.getBillType()) {
						case 1:
							// 支出
							todayAccountResult -= todayNum;
							break;
						case 2:
							// 收入
							todayAccountResult += todayNum;
							break;
						}
					}
				}

				double monthIncomeResult = 0;
				if (monthIncomeBills != null && monthIncomeBills.size() > 0) {
					for (Bill bill : monthIncomeBills) {
						double incomeNum = Double.parseDouble(bill.getJine());
						monthIncomeResult += incomeNum;
					}
				}

				double monthSpendResult = 0;
				if (monthSpendBills != null && monthSpendBills.size() > 0) {
					for (Bill bill : monthSpendBills) {
						double spendNum = Double.parseDouble(bill.getJine());
						// 收入
						monthSpendResult -= spendNum;
					}
				}

				if (todayAccountResult > 0) {
					benriIcon
							.setImageResource(R.drawable.icon_dailyincome);

				} else
					benriIcon
							.setImageResource(R.drawable.icon_dailyexpense);

				todayAccount.setText(todayAccountResult + "");
				monthIncome.setText(monthIncomeResult + "");
				monthSpend.setText(monthSpendResult + "");
				return view;
			}
		}

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.gridview_item_bill_index,
					null);
			imageView = (ImageView) convertView.findViewById(R.id.imageView);
			title = (TextView) convertView.findViewById(R.id.title);
			vh = new ViewHolder();
			vh.imageView = imageView;
			vh.title = title;
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
			title = vh.title;
			imageView = vh.imageView;
		}
		title.setText(titles[position]);
		imageView.setImageResource(imageIds[position]);

		return convertView;
	}

	static class ViewHolder {
		ImageView imageView;
		TextView title;
	}

}
