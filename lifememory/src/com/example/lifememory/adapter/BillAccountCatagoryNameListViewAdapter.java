package com.example.lifememory.adapter;

import com.example.lifememory.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BillAccountCatagoryNameListViewAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private String[] names;
	private int[] imageIds;
	private int currentSelectedIndex = -1;
	public BillAccountCatagoryNameListViewAdapter(Context context, String[] names, int[] imageIds) {
		inflater = LayoutInflater.from(context);
		this.names = names;
		this.imageIds = imageIds;
	}
	@Override
	public int getCount() {
		return names.length;
	}

	@Override
	public Object getItem(int position) {
		return names[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setSelected(int selectedIndex) {
		this.currentSelectedIndex = selectedIndex;
		this.notifyDataSetChanged();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		TextView name1;
		ImageView selectedTag;
		ViewHolder vh;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.bill_account_bizhong_list_item, null);
			imageView = (ImageView) convertView.findViewById(R.id.imageView);
			name1 = (TextView) convertView.findViewById(R.id.name1);
			selectedTag = (ImageView) convertView.findViewById(R.id.selectedTag);
			vh = new ViewHolder();
			vh.imageView = imageView;
			vh.name1 = name1;
			vh.selectedTag = selectedTag;
			convertView.setTag(vh);
		}else {
			vh = (ViewHolder) convertView.getTag();
			imageView = vh.imageView;
			name1 = vh.name1;
			selectedTag = vh.selectedTag;
		}
		
		imageView.setImageResource(imageIds[position]);
		name1.setText(names[position]);
		convertView.setBackgroundResource(R.drawable.list_gb);
		selectedTag.setVisibility(ViewGroup.GONE);
		if(currentSelectedIndex == position) {
			convertView.setBackgroundResource(R.drawable.list_bg_h);
			selectedTag.setVisibility(ViewGroup.VISIBLE);
		}
		
		return convertView;
	}

	static class ViewHolder {
		ImageView imageView;
		TextView name1;
		ImageView selectedTag;
	}
	
}





















