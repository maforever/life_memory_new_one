package com.example.lifememory.adapter;

import java.util.List;
import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillMember;
import com.example.lifememory.db.service.BillMemberService;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BillMemberListViewAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<BillMember> members;
	private int currentSelectedIndex = -1;
	private int currentLongClickIndex = -1;
	private BillMemberService dbService;
	private Context context;
	public BillMemberListViewAdapter(Context context, List<BillMember> members2, BillMemberService dbService) {
		this.inflater = LayoutInflater.from(context);
		this.members = members2;
		this.dbService = dbService;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return members.size();
	}

	@Override
	public Object getItem(int position) {
		return members.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setSelected(int selectedId) {
		this.currentSelectedIndex = selectedId;
		this.notifyDataSetChanged();
	}
	
	public void setShowDeleteTag(int longClickIndex) {
		currentLongClickIndex = longClickIndex;
		this.notifyDataSetChanged();
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final TextView deleteTag;
		ImageView selectedTag;
		TextView name;
		ViewHolder vh;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.bill_member_listview_item, null);
			deleteTag = (TextView) convertView.findViewById(R.id.deleteTag);
			selectedTag = (ImageView) convertView.findViewById(R.id.selectedTag);
			name = (TextView) convertView.findViewById(R.id.name);
			vh = new ViewHolder();
			vh.selectedTag = selectedTag;
			vh.deleteTag = deleteTag;
			vh.name = name;
			convertView.setTag(vh);
		}else {
			vh = (ViewHolder) convertView.getTag();
			deleteTag = vh.deleteTag;
			selectedTag = vh.selectedTag;
			name = vh.name;
		}
		name.setText(members.get(position).getName());
		selectedTag.setVisibility(ViewGroup.GONE);
		if(position == currentSelectedIndex) {
			selectedTag.setVisibility(ViewGroup.VISIBLE);
		}
		
		deleteTag.setVisibility(ViewGroup.GONE);
		final int idx = members.get(position).getIdx();
		final int location = position;
		if(position == currentLongClickIndex) {
			deleteTag.setVisibility(ViewGroup.VISIBLE);
		}
		deleteTag.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(currentSelectedIndex == currentLongClickIndex) {
					Toast.makeText(context, "当前成员选项正被使用无法删除!", 0).show();
					deleteTag.setVisibility(ViewGroup.GONE);
				}else {
					dbService.deleteItemByIdx(idx);
					members.remove(location);
					currentLongClickIndex = -1;
					BillMemberListViewAdapter.this.notifyDataSetChanged();
					deleteTag.setVisibility(ViewGroup.GONE);
					Toast.makeText(context, "员选项删除成功!", 0).show();
				}
			}
		});
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView deleteTag;
		ImageView selectedTag;
		TextView name;
	}
	
	
}
