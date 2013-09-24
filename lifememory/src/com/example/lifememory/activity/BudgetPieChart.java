/**
 * Copyright (C) 2009, 2010 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.lifememory.activity;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.renderer.DefaultRenderer;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillCatagoryItem;
import com.example.lifememory.chart.AbstractDemoChart;
import com.example.lifememory.db.service.BillCatagoryService;
import com.example.lifememory.utils.BillChartColorUtils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.InputFilter.LengthFilter;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

/**
 * Budget demo pie chart.
 */
public class BudgetPieChart extends AbstractDemoChart {
	/**
	 * Returns the chart name.
	 * 
	 * @return the chart name
	 */
	private List<BillCatagoryItem> items;
	private BillCatagoryService catagoryService;
	public double totalValue = 0;
	public String getName() {
		return "Budget chart";
	}

	/**
	 * Returns the chart description.
	 * 
	 * @return the chart description
	 */
	public String getDesc() {
		return "The budget per project for this year (pie chart)";
	}

	public BudgetPieChart() {
	};

	public BudgetPieChart(Context context, LinearLayout layout, String ym,
			int billType, BillCatagoryService catagoryService) {
		super();
		this.catagoryService = catagoryService;
		myExecute(context, layout, ym, billType);
	}

	/**
	 * Executes the chart demo.
	 * 
	 * @param context
	 *            the context
	 * @return the built intent
	 */
	public Intent execute(Context context) {
		double[] values = new double[] { 12, 14, 11, 10, 19 };
		int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.MAGENTA,
				Color.YELLOW, Color.CYAN };
		DefaultRenderer renderer = buildCategoryRenderer(colors);
		renderer.setZoomButtonsVisible(true);
		renderer.setZoomEnabled(true);
		renderer.setChartTitleTextSize(20);
		return ChartFactory.getPieChartIntent(context,
				buildCategoryDataset("Project budget", values), renderer,
				"Budget");
	}

	public void myExecute(Context context, LinearLayout layout, String ym,
			int billType) {
		String[] titles = new String[]{};
		double[] values = new double[]{};
		int[] colors = new int[] {};
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
		
		
		for(BillCatagoryItem item : items) {
			totalValue += item.getSpendValue();
		}
		
		if (items.size() > 0) {
			titles = new String[items.size()];
			values = new double[items.size()];
			colors = new int[items.size()];
			for(int i=0; i<titles.length; i++) {
				int value = (int)(Math.rint((items.get(i).getSpendValue() / totalValue) * 100));
				titles[i] = items.get(i).getName() + value + "%";
				values[i] = value;
				colors[i] = totalColors[i];
			}
		}
		
		DefaultRenderer renderer = buildCategoryRenderer(colors);
		renderer.setZoomButtonsVisible(true);
		renderer.setZoomEnabled(true);
		renderer.setLabelsTextSize(30);
		renderer.setLabelsColor(Color.BLACK);
		renderer.setShowLegend(true);
		renderer.setLegendTextSize(20);
		renderer.setFitLegend(false);
		renderer.setShowGrid(false);
		renderer.setShowAxes(false);
		
		
		GraphicalView view = ChartFactory.getPieChartView(
				context,
				buildCategoryDataset(titles, values), renderer);
		layout.addView(view, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
	}
	
}
