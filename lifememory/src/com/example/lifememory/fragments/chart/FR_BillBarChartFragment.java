package com.example.lifememory.fragments.chart;

import java.util.ArrayList;
import java.util.List;

import com.example.lifememory.R;
import com.example.lifememory.activity.BudgetBarChart;
import com.example.lifememory.activity.BudgetPieChart;
import com.example.lifememory.activity.model.BillCatagoryItem;
import com.example.lifememory.barchart.Cash;
import com.example.lifememory.barchart.CashFlowView;
import com.example.lifememory.db.service.BillCatagoryService;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FR_BillBarChartFragment extends Fragment {
	private String ym;
	private int billType;
	private BillCatagoryService catagoryService;
	private CashFlowView cashView;
	ArrayList<Cash> list;
	List<BillCatagoryItem> items;
	TextView titleTv;
	TextView valueTv;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		catagoryService = new BillCatagoryService(getActivity());
		super.onCreate(savedInstanceState);
	}
	
	public FR_BillBarChartFragment(String ym, int billType) {
		this.ym = ym;
		this.billType = billType;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.bill_chart_bar_layout, container, false);
		
		
		cashView=(CashFlowView)view.findViewById(R.id.cash_view);
		titleTv = (TextView) view.findViewById(R.id.title);
		valueTv = (TextView) view.findViewById(R.id.value);
		
		if(billType == 1) {

		}else if(billType == 2) {

		}
		
		
		if(billType == 1) {
			items = catagoryService.findSpendValueByYMForChart(ym);
			titleTv.setTextColor(getActivity().getResources().getColor(R.color.spendColor));
			titleTv.setText("开销总计: ");
			valueTv.setTextColor(getActivity().getResources().getColor(R.color.spendColor));
		}else if(billType == 2) {
			items = catagoryService.findIncomeValueByYMForChart(ym);
			titleTv.setTextColor(getActivity().getResources().getColor(R.color.incomeColor));
			titleTv.setText("收入总计: ");
			valueTv.setTextColor(getActivity().getResources().getColor(R.color.incomeColor));
		}
		double totalValue = 0;
		for(BillCatagoryItem item : items) {
			totalValue += item.getSpendValue();
		}
		
		valueTv.setText(totalValue + "");
		
		list=new ArrayList<Cash>();
		Cash c=new Cash();
		for(BillCatagoryItem item : items) {
			c = new Cash();
			c.time = item.getName();
			c.money = item.getSpendValue();
			list.add(c);
		}
		
		
		cashView.setCashFlow(list);
		return view;
	}
}







