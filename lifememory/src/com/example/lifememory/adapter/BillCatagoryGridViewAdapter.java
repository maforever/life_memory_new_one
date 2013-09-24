package com.example.lifememory.adapter;

import com.example.lifememory.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BillCatagoryGridViewAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private int[] imageIds;
	private int currentIndex = 0;
	public BillCatagoryGridViewAdapter(Context context, int[] imageIds) {
		inflater = LayoutInflater.from(context);
		this.imageIds = imageIds;
	}
	
	@Override
	public int getCount() {
		return imageIds.length;
	}

	@Override
	public Object getItem(int position) {
		return imageIds[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setSelected(int selectedId) {
		currentIndex = selectedId;
		this.notifyDataSetChanged();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout layout;
		ImageView imageView;
		ViewHolder vh;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.bill_catagory_image_select_gridview_item, null);
			layout = (LinearLayout) convertView.findViewById(R.id.bg);
			imageView = (ImageView) convertView.findViewById(R.id.imageView);
			vh = new ViewHolder();
			vh.layout = layout;
			vh.imageView = imageView;
			convertView.setTag(vh);
		}else {
			vh = (ViewHolder) convertView.getTag();
			layout = vh.layout;
			imageView = vh.imageView;
		}
		
		imageView.setImageResource(imageIds[position]);
		
		if(currentIndex == position) {
			layout.setBackgroundResource(R.drawable.bill_catagory_image_select_on);
		}else {
			layout.setBackgroundResource(R.drawable.bill_catagory_image_select_off);
		}
		
		return convertView;
	}

	static class ViewHolder {
		LinearLayout layout;
		ImageView imageView;
	}
	
}





























