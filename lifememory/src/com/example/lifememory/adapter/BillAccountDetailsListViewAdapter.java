package com.example.lifememory.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillAccountItem;

public class BillAccountDetailsListViewAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private Context context;
	private List<BillAccountItem> items;
	public BillAccountDetailsListViewAdapter(Context context, List<BillAccountItem> items) {
		this.inflater = LayoutInflater.from(context);
		this.context = context;
		this.items = items;
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView title;
		TextView bizhong;
		TextView jine;
		ViewHolder vh;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.bill_account_details_listview_item, null);
			title = (TextView) convertView.findViewById(R.id.title);
			bizhong = (TextView) convertView.findViewById(R.id.bizhong);
			jine = (TextView) convertView.findViewById(R.id.jine);
			vh = new ViewHolder();
			vh.title = title;
			vh.bizhong = bizhong;
			vh.jine = jine;
			convertView.setTag(vh);
		}else {
			vh = (ViewHolder) convertView.getTag();
			title = vh.title;
			bizhong = vh.bizhong;
			jine = vh.jine;
		}
		
		title.setText(items.get(position).getName());
		bizhong.setText("гд");
		jine.setText(items.get(position).getDangqianyue() + "");
		return convertView;
	}

	static class ViewHolder {
		TextView title;
		TextView bizhong;
		TextView jine;
	}
}







