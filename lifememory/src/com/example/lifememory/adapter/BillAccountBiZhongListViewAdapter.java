package com.example.lifememory.adapter;

import com.example.lifememory.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BillAccountBiZhongListViewAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private String[] names1;
	private String[] names2;
	private int[] imageIds;
	private int currentSelectedIndex = -1;
	public BillAccountBiZhongListViewAdapter(Context context, String[] names1, String[] names2, int[] imageIds) {
		inflater = LayoutInflater.from(context);
		this.names1 = names1;
		this.names2 = names2;
		this.imageIds = imageIds;
	}
	@Override
	public int getCount() {
		return names1.length;
	}

	@Override
	public Object getItem(int position) {
		return names1[position];
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
		TextView name2;
		ImageView selectedTag;
		ViewHolder vh;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.bill_account_bizhong_list_item, null);
			imageView = (ImageView) convertView.findViewById(R.id.imageView);
			name1 = (TextView) convertView.findViewById(R.id.name1);
			name2 = (TextView) convertView.findViewById(R.id.name2);
			selectedTag = (ImageView) convertView.findViewById(R.id.selectedTag);
			vh = new ViewHolder();
			vh.imageView = imageView;
			vh.name1 = name1;
			vh.name2 = name2;
			vh.selectedTag = selectedTag;
			convertView.setTag(vh);
		}else {
			vh = (ViewHolder) convertView.getTag();
			imageView = vh.imageView;
			name1 = vh.name1;
			name2 = vh.name2;
			selectedTag = vh.selectedTag;
		}
		
		imageView.setImageResource(imageIds[position]);
		name1.setText(names1[position]);
		name2.setText(names2[position]);
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
		TextView name2;
		ImageView selectedTag;
	}
	
}





















