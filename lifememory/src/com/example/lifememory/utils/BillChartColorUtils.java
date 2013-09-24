package com.example.lifememory.utils;

import android.content.Context;

import com.example.lifememory.R;


public class BillChartColorUtils {
	private Context context;
	
	public BillChartColorUtils(Context context) {
		this.context = context;
	}

	public int[] initColors() {
		int[] colors = {
			context.getResources().getColor(R.color.color1), context.getResources().getColor(R.color.color2), context.getResources().getColor(R.color.color3), 
			context.getResources().getColor(R.color.color4), context.getResources().getColor(R.color.color5), context.getResources().getColor(R.color.color6), 
			context.getResources().getColor(R.color.color7), context.getResources().getColor(R.color.color8), context.getResources().getColor(R.color.color9), 
			context.getResources().getColor(R.color.color10), context.getResources().getColor(R.color.color11), context.getResources().getColor(R.color.color12), 
			context.getResources().getColor(R.color.color13), context.getResources().getColor(R.color.color14), context.getResources().getColor(R.color.color15), 
			context.getResources().getColor(R.color.color16), context.getResources().getColor(R.color.color17), context.getResources().getColor(R.color.color18), 
			context.getResources().getColor(R.color.color19), context.getResources().getColor(R.color.color20), context.getResources().getColor(R.color.color21), 
			context.getResources().getColor(R.color.color22)
		};
		return colors;
	}
}
