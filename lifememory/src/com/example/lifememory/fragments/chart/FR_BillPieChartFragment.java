package com.example.lifememory.fragments.chart;

import com.example.lifememory.R;
import com.example.lifememory.activity.BudgetPieChart;
import com.example.lifememory.db.service.BillCatagoryService;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FR_BillPieChartFragment extends Fragment {
	private String ym;
	private int billType;
	private BillCatagoryService catagoryService;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		catagoryService = new BillCatagoryService(getActivity());
		super.onCreate(savedInstanceState);
	}
	
	public FR_BillPieChartFragment(String ym, int billType) {
		this.ym = ym;
		
		this.billType = billType;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.bill_chart_pie_layout, container, false);
		LinearLayout layout = (LinearLayout)view.findViewById(R.id.container);
		BudgetPieChart chart = new BudgetPieChart(getActivity(),layout, ym, billType, catagoryService);
		
		TextView totalName = (TextView) view.findViewById(R.id.totalName);
		TextView totalValue = (TextView) view.findViewById(R.id.totalValue);
		if(billType == 1) {
			totalName.setTextColor(getActivity().getResources().getColor(R.color.spendColor));
			totalName.setText("开销总计: ");
			totalValue.setTextColor(getActivity().getResources().getColor(R.color.spendColor));
		}else if(billType == 2) {
			totalName.setTextColor(getActivity().getResources().getColor(R.color.incomeColor));
			totalName.setText("收入总计: ");
			totalValue.setTextColor(getActivity().getResources().getColor(R.color.incomeColor));
		}
		
		totalValue.setText(chart.totalValue + "");
		
		return view;
	}
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		catagoryService.closeDB();
		catagoryService = null;
	}
	
}
