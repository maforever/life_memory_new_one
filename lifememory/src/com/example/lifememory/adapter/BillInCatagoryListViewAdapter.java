package com.example.lifememory.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillInCatagory;
import com.example.lifememory.db.service.BillInCatagoryService;

public class BillInCatagoryListViewAdapter extends BaseAdapter{
	private Context context;
	private List<BillInCatagory> bics;
	private LayoutInflater inflater;
	private int curretnSelectedIndex = -1;
	private int currentLongClickIndex = -1;
	private BillInCatagoryService dbService;
	public BillInCatagoryListViewAdapter(Context context, List<BillInCatagory> bics, BillInCatagoryService dbService) {
		this.context = context;
		this.bics = bics;
		inflater = LayoutInflater.from(context);
		this.dbService = dbService;
	}
	
	@Override
	public int getCount() {
		return bics.size();
	}

	@Override
	public Object getItem(int position) {
		return bics.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setSelected(int selectedIndex) {
		this.curretnSelectedIndex = selectedIndex;
		this.notifyDataSetChanged();
	}
	
	public void setShowDeleteTag(int longClickIndex) {
		currentLongClickIndex = longClickIndex;
		this.notifyDataSetChanged();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView name;
		final TextView deleteTag;
		ImageView selectedTag;
		ViewHolder vh;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.bill_member_listview_item, null);
			name = (TextView) convertView.findViewById(R.id.name);
			deleteTag = (TextView) convertView.findViewById(R.id.deleteTag);
			selectedTag = (ImageView) convertView.findViewById(R.id.selectedTag);
			vh = new ViewHolder();
			vh.name = name;
			vh.deleteTag = deleteTag;
			vh.selectedTag = selectedTag;
			convertView.setTag(vh);
		}else {
			vh = (ViewHolder) convertView.getTag();
			name = vh.name;
			deleteTag = vh.deleteTag;
			selectedTag = vh.selectedTag;
		}
		
		selectedTag.setVisibility(ViewGroup.GONE);
		name.setText(bics.get(position).getName());
		if(position == curretnSelectedIndex) {
			selectedTag.setVisibility(ViewGroup.VISIBLE);
		}
		
		
		deleteTag.setVisibility(ViewGroup.GONE);
		final int idx = bics.get(position).getIdx();
		final int location = position;
		if(position == currentLongClickIndex) {
			deleteTag.setVisibility(ViewGroup.VISIBLE);
		}
		deleteTag.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(curretnSelectedIndex == currentLongClickIndex) {
					Toast.makeText(context, "当前成员选项正被使用无法删除!", 0).show();
					deleteTag.setVisibility(ViewGroup.GONE);
				}else {
					dbService.deleteItem(idx);
					bics.remove(location);
					currentLongClickIndex = -1;
					BillInCatagoryListViewAdapter.this.notifyDataSetChanged();
					deleteTag.setVisibility(ViewGroup.GONE);
					Toast.makeText(context, "员选项删除成功!", 0).show();
				}
			}
		});
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView name;
		TextView deleteTag;
		ImageView selectedTag;
	}
}



























