package com.example.lifememory.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillTemplate;

public class BillTemplateGridViewAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<BillTemplate> templates;
	private boolean isShowDeleteTag = false;
	public List<Integer> templateIds = new ArrayList<Integer>();     //账单模版的idx
	public BillTemplateGridViewAdapter(Context context, List<BillTemplate> templates) {
		this.inflater = LayoutInflater.from(context);
		this.templates = templates;
	}
	
	@Override
	public int getCount() {
		return templates.size();
	}

	@Override
	public Object getItem(int position) {
		return templates.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void showDeleteTag(boolean isShow) {
		isShowDeleteTag = isShow;
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RelativeLayout background;
		CheckBox deleteTag;
		TextView name;
		ViewHolder vh;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.bill_templates_gridview_item, null);
			background = (RelativeLayout) convertView.findViewById(R.id.background);
			name = (TextView) convertView.findViewById(R.id.name);
			deleteTag = (CheckBox) convertView.findViewById(R.id.deleteTag);
			vh = new ViewHolder();
			vh.background = background;
			vh.deleteTag = deleteTag;
			vh.name = name;
			convertView.setTag(vh);
		}else {
			vh = (ViewHolder) convertView.getTag();
			background = vh.background;
			deleteTag = vh.deleteTag;
			name = vh.name;
		}
		
		switch (templates.get(position).getBillType()) {
		case 1:
			//支出
			convertView.setBackgroundResource(R.drawable.bill_out_template_selector);
			break;
		case 2:
			//收入
			convertView.setBackgroundResource(R.drawable.bill_in_template_selector);
			break;
		case 3:
			//转账
			convertView.setBackgroundResource(R.drawable.bill_transfer_template_selector);
			break;
		}
		
		name.setText(templates.get(position).getName());
		final Integer templateId = templates.get(position).getIdx();
		deleteTag.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					if(!templateIds.contains(templateId)) {
						templateIds.add(templateId);
					}
				}else {
					if(templateIds.contains(templateId)) {
						templateIds.remove(templateId);
					}
				}
			}
		});
		
		if(isShowDeleteTag) {
			deleteTag.setVisibility(ViewGroup.VISIBLE);
		}else {
			deleteTag.setVisibility(ViewGroup.GONE);
		}
		
		if(templateIds.contains(templateId)) {
			deleteTag.setChecked(true);
		}
		
		return convertView;
	}
	
	static class ViewHolder {
		RelativeLayout background;
		CheckBox deleteTag;
		TextView name;
	}

}














