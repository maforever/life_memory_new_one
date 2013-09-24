package com.example.lifememory.adapter;

import java.util.List;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillCatagoryItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BillCatatorySecondaryListAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<BillCatagoryItem> items;
	private int currentIndex = 0;

	public BillCatatorySecondaryListAdapter(Context context,
			List<BillCatagoryItem> items) {
		inflater = LayoutInflater.from(context);
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

	public void setDatas(List<BillCatagoryItem> items) {
		this.items = items;
		this.notifyDataSetChanged();
	}

	public void setSelected(int selectId) {
		currentIndex = selectId;
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		TextView textView;
		ImageView selectedImage;
		ViewHolder vh;
		if (convertView == null) {
			convertView = inflater.inflate(
					R.layout.bill_catagory_secondary_list_item, null);
			imageView = (ImageView) convertView.findViewById(R.id.imageView);
			textView = (TextView) convertView.findViewById(R.id.name);
			selectedImage = (ImageView) convertView
					.findViewById(R.id.imageSelected);
			vh = new ViewHolder();
			vh.imageView = imageView;
			vh.textView = textView;
			vh.selectedImage = selectedImage;
			convertView.setTag(vh);

		} else {
			vh = (ViewHolder) convertView.getTag();
			imageView = vh.imageView;
			textView = vh.textView;
			selectedImage = vh.selectedImage;
		}

		imageView.setImageResource(items.get(position).getImageId());
		textView.setText(items.get(position).getName());

		if (currentIndex == position) {
			selectedImage.setVisibility(ViewGroup.VISIBLE);

		} else {
			selectedImage.setVisibility(ViewGroup.GONE);

		}

		return convertView;
	}

	static class ViewHolder {
		ImageView imageView;
		TextView textView;
		ImageView selectedImage;
	}

}
