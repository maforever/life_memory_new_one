package com.example.lifememory.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillCatagoryItem;
import com.example.lifememory.activity.views.VoteProgressBar;

public class BillBudgetListViewAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<BillCatagoryItem> catagoryItems;
	
	public BillBudgetListViewAdapter(Context context, List<BillCatagoryItem> catagoryItems) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.catagoryItems = catagoryItems;
	}
	
	@Override
	public int getCount() {
		return catagoryItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		return catagoryItems.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView image;
		TextView catagoryName;
		TextView budget;
		VoteProgressBar progressBar;
		TextView leftContent;
		ViewHolder vh;
//		if(convertView == null) {
		convertView = inflater.inflate(R.layout.bill_budget_listiew_item, null);
			image = (ImageView) convertView.findViewById(R.id.image);
			catagoryName = (TextView) convertView.findViewById(R.id.catagoryname);
			budget = (TextView) convertView.findViewById(R.id.budget);
			progressBar = (VoteProgressBar) convertView.findViewById(R.id.progressBar);
			leftContent = (TextView) convertView.findViewById(R.id.left);
//			vh = new ViewHolder();
//			vh.image = image;
//			vh.catagoryName = catagoryName;
//			vh.budget = budget;
//			vh.progressBar = progressBar;
//			vh.leftContent = leftContent;
//			convertView.setTag(vh);
//		}else {
//			view = convertView;
//			vh = (ViewHolder) view.getTag();
//			image = vh.image;
//			catagoryName = vh.catagoryName;
//			budget = vh.budget;
//			progressBar = vh.progressBar;
//			leftContent = vh.leftContent;
//		}
		
		catagoryName.setText(catagoryItems.get(position).getName());
		image.setImageResource(catagoryItems.get(position).getImageId());
		double budgetValue = catagoryItems.get(position).getBugget();
		double spendValue =  catagoryItems.get(position).getSpendValue();
		double budgetYue = budgetValue - spendValue;
		String leftContentStr = "Óà¶î:£¤" + budgetYue;
		budget.setText(catagoryItems.get(position).getBugget() + "");
		if(budgetYue > 0) {
			leftContent.setTextColor(context.getResources().getColor(R.color.incomeColor));
		}else {
			leftContent.setTextColor(context.getResources().getColor(R.color.spendColor));
		}
		
		if(spendValue != 0) {
			if(budgetValue == 0.0) {
				progressBar.startRoll(0, 100);
			}else {
				Integer percent = (int) (spendValue * 100 / budgetValue);
				progressBar.startRoll(0, percent);
			}
		}
		
		
		leftContent.setText(leftContentStr);
		
		
		return convertView;
	}

	static class ViewHolder {
		ImageView image;
		TextView catagoryName;
		TextView budget;
		VoteProgressBar progressBar;
		TextView leftContent;
	}
	
	
}















