package com.example.lifememory.adapter;

import java.util.List;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillCatagoryItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BillListChartListViewAdapter extends BaseAdapter {
  double totalValue;
  Context context;
  LayoutInflater inflater;
  List<BillCatagoryItem> items;
  public BillListChartListViewAdapter(Context context, List<BillCatagoryItem> items, double totalValue) {
    this.context = context;
    this.items = items;
    this.inflater = LayoutInflater.from(context);
    this.totalValue = totalValue;
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
    TextView value;
    TextView percent;
    ViewHolder vh;
    if(convertView == null) {
      convertView = inflater.inflate(R.layout.bill_listchart_listview_item, null);
      title = (TextView) convertView.findViewById(R.id.title);
      value = (TextView) convertView.findViewById(R.id.value);
      percent = (TextView) convertView.findViewById(R.id.valuepercent);
      vh = new ViewHolder();
      vh.title = title;
      vh.value = value;
      vh.percent = percent;
      convertView.setTag(vh);
    }else {
      vh = (ViewHolder) convertView.getTag();
      title = vh.title;
      value = vh.value;
      percent = vh.percent;
    }
    int percentNum = (int) Math.rint((items.get(position).getSpendValue() / totalValue) * 100);
    title.setText(items.get(position).getName());
    percent.setText(percentNum + "%");
    value.setText("￥" + items.get(position).getSpendValue());
    
    return convertView;
  }

  static class ViewHolder {
    TextView title;
    TextView percent;
    TextView value;
  }
  
  
  
  
  
}