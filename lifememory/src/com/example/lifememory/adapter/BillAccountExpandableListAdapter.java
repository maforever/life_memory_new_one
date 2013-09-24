package com.example.lifememory.adapter;

import java.util.List;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillAccountExpandableListViewItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BillAccountExpandableListAdapter extends BaseExpandableListAdapter {
	private LayoutInflater inflater;
	private List<BillAccountExpandableListViewItem> expandableItems;
	private int currentGroupId = -1;
	private int currentChildId = -1;
	private Context context;
	public BillAccountExpandableListAdapter(Context context, List<BillAccountExpandableListViewItem> expandableItems) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.expandableItems = expandableItems;
	}
	
	@Override
	public int getGroupCount() {
		return this.expandableItems.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this.expandableItems.get(groupPosition).getAccountItems().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this.expandableItems.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return this.expandableItems.get(groupPosition).getAccountItems().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}
	
	public void setSelected(int groupId, int childId) {
		this.currentGroupId = groupId;
		this.currentChildId = childId;
		this.notifyDataSetChanged();
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		ImageView imageView;
		TextView title;
		ImageView indicator;
		ViewHolder vh;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.bill_account_expandablelist_parentitem, null);
			imageView = (ImageView) convertView.findViewById(R.id.imageView);
			title = (TextView) convertView.findViewById(R.id.title);
			indicator = (ImageView) convertView.findViewById(R.id.indicator);
			vh = new ViewHolder();
			vh.imageView = imageView;
			vh.title = title;
			vh.indicator = indicator;
			convertView.setTag(vh);
		}else {
			vh = (ViewHolder) convertView.getTag();
			imageView = vh.imageView;
			title = vh.title;
			indicator = vh.indicator;
//			imageView = (ImageView) convertView.findViewById(R.id.imageView);
//			title = (TextView) convertView.findViewById(R.id.title);
//			indicator = (ImageView) convertView.findViewById(R.id.indicator);
		}
		title.setText(this.expandableItems.get(groupPosition).getTitle());
		imageView.setImageResource(this.expandableItems.get(groupPosition).getImageId());
		if(isExpanded) {
			indicator.setImageResource(R.drawable.bill_account_indicator_down);
		}else {
			indicator.setImageResource(R.drawable.bill_account_indicator_up);
		}
		
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ImageView selectedTag;
		TextView name;
		TextView yue;
		ViewHolder vh;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.bill_account_listview_item, null);
			selectedTag = (ImageView) convertView.findViewById(R.id.selectedTag);
			name = (TextView) convertView.findViewById(R.id.name);
			yue = (TextView) convertView.findViewById(R.id.yue);
			vh = new ViewHolder();
			vh.selectedTag = selectedTag;
			vh.name = name;
			vh.yue = yue;
			convertView.setTag(vh);
		}else {
			vh = (ViewHolder) convertView.getTag();
			selectedTag = vh.selectedTag;
			name = vh.name;
			yue = vh.yue;
		}
		name.setText(this.expandableItems.get(groupPosition).getAccountItems().get(childPosition).getName());
		yue.setText(this.expandableItems.get(groupPosition).getAccountItems().get(childPosition).getDangqianyue() + "");
		selectedTag.setVisibility(ViewGroup.GONE);
		
		if(currentGroupId == groupPosition && currentChildId == childPosition) {
			selectedTag.setVisibility(ViewGroup.VISIBLE);
		}
		
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	static class ViewHolder {
		ImageView imageView;
		ImageView indicator;
		ImageView selectedTag;
		TextView title;
		TextView name;
		TextView yue;
	}
	
	
	
	
}
