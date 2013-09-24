package com.example.lifememory.fragments.chart;

import java.util.List;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillCatagoryItem;
import com.example.lifememory.adapter.BillListChartListViewAdapter;
import com.example.lifememory.db.service.BillCatagoryService;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class FR_BillListChartFragment extends Fragment {
  ListView listView;
  List<BillCatagoryItem> items;
  BillCatagoryService catagoryService;
  String ym;
  int billType;
  double totalValue;
  TextView titleTv, valueTv;
  BillListChartListViewAdapter adapter;
  @Override
  public void onCreate(Bundle savedInstanceState) {
    catagoryService = new BillCatagoryService(getActivity());
    super.onCreate(savedInstanceState);
  }
  
  public FR_BillListChartFragment(String ym, int billType) {
    this.ym = ym;
    this.billType = billType;
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.bill_listchart_layout, container, false);
    listView = (ListView) view.findViewById(R.id.listView);
    titleTv = (TextView) view.findViewById(R.id.title);
    valueTv = (TextView) view.findViewById(R.id.value);
    if(billType == 1) {
      items = catagoryService.findSpendValueByYMForChart(ym);
      titleTv.setTextColor(getActivity().getResources().getColor(R.color.spendColor));
      valueTv.setTextColor(getActivity().getResources().getColor(R.color.spendColor));
      titleTv.setText("总支出:");
    }else if(billType == 2) {
      items = catagoryService.findIncomeValueByYMForChart(ym);
      titleTv.setTextColor(getActivity().getResources().getColor(R.color.incomeColor));
      valueTv.setTextColor(getActivity().getResources().getColor(R.color.incomeColor));
      titleTv.setText("总收入:");
    }
    for(BillCatagoryItem item : items) {
      totalValue = item.getSpendValue();
    }
    valueTv.setText("" + totalValue);
    adapter = new BillListChartListViewAdapter(getActivity(), items, totalValue);
    listView.setAdapter(adapter);
    
    
    return view;
  }
  
}
