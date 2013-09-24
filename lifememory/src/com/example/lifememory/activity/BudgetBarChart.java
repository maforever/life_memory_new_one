package com.example.lifememory.activity;

import java.util.List;
import java.util.Random;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.BasicStroke;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillCatagoryItem;
import com.example.lifememory.chart.AbstractDemoChart;
import com.example.lifememory.db.service.BillCatagoryService;
import com.example.lifememory.utils.BillChartColorUtils;

public class BudgetBarChart extends AbstractDemoChart {
	private BillCatagoryService catagoryService;
	private List<BillCatagoryItem> items;
	private Context context;
	private String ym;
	private int billType;
	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getDesc() {
		return null;
	}

	@Override
	public Intent execute(Context context) {
		return null;
	}

	public BudgetBarChart(Context context, LinearLayout layout, String ym,
			int billType, BillCatagoryService catagoryService) {
		super();
		this.catagoryService = catagoryService;
		this.ym = ym;
		this.billType = billType;
		this.context = context;
		myExecute(context, layout, ym, billType);
	}

	public void myExecute(Context context, LinearLayout layout, String ym,
			int billType) {

		XYMultipleSeriesRenderer renderer = getBarDemoRenderer(context);
		GraphicalView view = ChartFactory.getBarChartView(context,
				getBarDemoDataset(context, renderer, billType, ym), renderer,
				Type.DEFAULT);
		setChartSettings(renderer);
		layout.addView(view, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
	}

	public XYMultipleSeriesRenderer getBarDemoRenderer(Context context) {
		
		switch (billType) {
		case 1:
			// 支出
			items = catagoryService.findSpendValueByYMForChart(ym);
			break;
		case 2:
			// 收入
			items = catagoryService.findIncomeValueByYMForChart(ym);
			break;
		}
		int[] totalColors = new BillChartColorUtils(context).initColors();
		int[] colors = new int[items.size()];
		for(int i=0; i<items.size(); i++) {
			colors[i] = totalColors[i];
		}
		PointStyle[] styles = new PointStyle[] {PointStyle.SQUARE, PointStyle.SQUARE};
		
		
		
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(30);
		renderer.setLegendTextSize(30);
		renderer.setMargins(new int[] { 0, 30, 0, 0 });
		renderer.setBarSpacing(0.5);
		
		renderer.setXLabels(items.size());// 设置X轴显示12个点，根据setChartSettings的最大值和最小值自动计算点的间隔
		renderer.setYLabels(1);// 设置y轴显示10个点,根据setChartSettings的最大值和最小值自动计算点的间隔
		renderer.setApplyBackgroundColor(true);// 使背景色生效
		renderer.setBackgroundColor(context.getResources().getColor(
				android.R.color.transparent));
		renderer.setMarginsColor(Color.argb(0, 0xff, 0, 0)); // 设置4边留白透明
		renderer.setFitLegend(true);
		renderer.setPanEnabled(false, false);
		setRenderer(renderer, colors, styles);
		return renderer;
	}

	private XYMultipleSeriesDataset getBarDemoDataset(Context context,
			XYMultipleSeriesRenderer renderer, int billType, String ym) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		int[] totalColors = new BillChartColorUtils(context).initColors();
		switch (billType) {
		case 1:
			// 支出
			items = catagoryService.findSpendValueByYMForChart(ym);
			break;
		case 2:
			// 收入
			items = catagoryService.findIncomeValueByYMForChart(ym);
			break;
		}

		if (items.size() > 0) {
			for (int i = 0; i < items.size(); i++) {
				CategorySeries series = new CategorySeries(items.get(i)
						.getName());
				series.add(items.get(i).getName(), items.get(i).getSpendValue());
				PointStyle[] styles = new PointStyle[] { PointStyle.SQUARE,
						PointStyle.SQUARE };
				XYSeriesRenderer r = new XYSeriesRenderer();
				r.setColor(totalColors[i]);
				renderer.addSeriesRenderer(r);
				dataset.addSeries(series.toXYSeries());
			}
		}

		return dataset;
	}

	private void setChartSettings(XYMultipleSeriesRenderer renderer) {

		renderer.setXAxisMin(0);
		renderer.setXAxisMax(10);
		renderer.setYAxisMin(0);
		renderer.setYAxisMax(1);
		// 设置x,y轴上的刻度的颜色
		renderer.setLabelsColor(Color.BLACK);
		renderer.setXTitle("类别名称");
		renderer.setYTitle("设计金额");
	}

}
