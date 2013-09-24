package com.example.lifememory.fragments.chart;

import com.example.lifememory.R;
import com.example.lifememory.chart.line.XYChartView;
import com.example.lifememory.db.service.BillInfoService;
import com.example.lifememory.utils.DateFormater;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Activity_BillLineChartActivity2 extends Activity {
  XYChartView xyChart;
  BillInfoService infoService;
  int[] inValues, outValues;
  int totalInValue = 0;
  int totalSpendValue = 0;
  TextView yearTv, totalIncomeTv, totalSpendTv, totalMinusTv;
  String[] coordinateX;
  String year;
  int yearNum;
  LinearLayout container;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.bill_chart_line_layout_bak);
    
    
    infoService = new BillInfoService(this);
    findViews();
        
        coordinateX = new String[]{"1月", "2月", "3月", "4月", "5月", "6月",
            "7月", "8月", "9月", "10月", "11月", "12月"  };
        
        year = DateFormater.getInstatnce().getYear();
        yearTv.setText(year);
        
        refreshViews(year);
    
  }
  
  private void refreshViews(String year) {
        inValues = infoService.findIncomeOrSpendByYear(2, year);
        outValues = infoService.findIncomeOrSpendByYear(1, year);
    
//    inValues = new int[]{0,0,0,0,0,0,0,0,0,0,0,0};
//    outValues = new int[]{0,0,0,0,0,0,0,0,0,0,0,0};
        
        for(int i : inValues) {
//          Log.i("a", "inValues = "  i);
          totalInValue += i;
        }
        
        for(int i : outValues) {
//          Log.i("a", "outValues = "  i);
          totalSpendValue += i;
        }
        xyChart = new XYChartView(this);
        xyChart.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        xyChart.setUnitX("月");
        xyChart.addLine("收入", coordinateX, inValues, 2);
        xyChart.addLine("支出", coordinateX, outValues, 1);
//        container.setPadding(10, 0, 0, 20);
        if(container.getChildCount() > 0) {
          container.removeAllViews();
        }
        container.addView(xyChart);
        
        totalIncomeTv.setText(totalInValue  + "元");
        totalSpendTv.setText(totalSpendValue  + "元");
        totalMinusTv.setText((totalInValue - totalSpendValue)+  "元");
  }
  
  private void findViews() {
//        xyChart = (XYChartView)findViewById(R.id.xyChart);
        yearTv = (TextView) this.findViewById(R.id.year);
        totalIncomeTv = (TextView) this.findViewById(R.id.totalIncome);
        totalSpendTv = (TextView) this.findViewById(R.id.totalSpend);
        totalMinusTv = (TextView) this.findViewById(R.id.totalMinus);
        container = (LinearLayout) this.findViewById(R.id.container);
  }
  
  public void btnClick(View view) {
    switch (view.getId()) {
    case R.id.back:
      back();
      break;
    case R.id.arrowLeft:
      arrowOperation(R.id.arrowLeft);
      break;
    case R.id.arrowRight:
      arrowOperation(R.id.arrowRight);
      break;
    }
  }

  private void arrowOperation(int viewId) {
    yearNum = Integer.parseInt(yearTv.getText().toString());
    switch (viewId) {
    case R.id.arrowLeft:
      yearNum--;
      break;
    case R.id.arrowRight:
      yearNum++;
      break;
    }
    
    year = String.valueOf(yearNum);
    yearTv.setText(year);
    refreshViews(year);
  }
  
  
  private void back() {
    Activity_BillLineChartActivity2.this.finish();
    overridePendingTransition(R.anim.activity_steady, R.anim.activity_down);
  }
  
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if(keyCode == KeyEvent.KEYCODE_BACK) {
      back();
    }
    return true;
  }
}
