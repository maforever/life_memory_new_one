package com.example.lifememory.adapter;

import java.util.List;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillAccountItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BillReimbursementSelectAccountDialogAdapter extends BaseAdapter {
	List<BillAccountItem> accountItems;
	Context context;
	LayoutInflater inflater;
	int currentSelectedIndex = -1;
	public BillReimbursementSelectAccountDialogAdapter(Context context, List<BillAccountItem> accountItems) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.accountItems = accountItems;
	}
	
	@Override
	public int getCount() {
		return accountItems.size();
	}

	@Override
	public Object getItem(int position) {
		return accountItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void showSelectedTag(int selectedIndex) {
		this.currentSelectedIndex = selectedIndex;
		this.notifyDataSetChanged();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView name;
		ImageView selectedTag;
		ViewHolder vh;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.bill_reimbursement_account_dialog_item, null);
			name = (TextView) convertView.findViewById(R.id.name);
			selectedTag = (ImageView) convertView.findViewById(R.id.selectedTag);
			vh = new ViewHolder();
			vh.name = name;
			vh.selectedTag = selectedTag;
			convertView.setTag(vh);
		}else {
			vh = (ViewHolder) convertView.getTag();
			name = vh.name;
			selectedTag = vh.selectedTag;
		}
		name.setText(accountItems.get(position).getName());
		selectedTag.setVisibility(ViewGroup.GONE);
		if(position == currentSelectedIndex) {
			selectedTag.setVisibility(ViewGroup.VISIBLE);
		}
		
		
		return convertView;
	}

	static class ViewHolder {
		TextView name;
		ImageView selectedTag;
	}
}













