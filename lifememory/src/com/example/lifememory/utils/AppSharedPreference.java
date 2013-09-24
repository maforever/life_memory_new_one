package com.example.lifememory.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.Editable;
import android.widget.ShareActionProvider;

public class AppSharedPreference {
	private Context context = null;
	public AppSharedPreference(Context context) {
		this.context = context;
	}
	
	public void setFirstUse() {
		SharedPreferences sp = context.getSharedPreferences("app_info", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean("isFirstUse", true);
		editor.commit();
	}
	
	public boolean isFirstUse() {
		SharedPreferences sp = context.getSharedPreferences("app_info", Context.MODE_PRIVATE);
		return sp.getBoolean("isFirstUse", false);
		
	}
	
	public void setTotalBudget(String budget) {
		SharedPreferences sp = context.getSharedPreferences("app_info", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("totalBudget", budget);
		editor.commit();
	}
	
	public String getTotalBudget() {
		SharedPreferences sp = context.getSharedPreferences("app_info", Context.MODE_PRIVATE);
		String totalBudget = sp.getString("totalBudget", "0");
		return totalBudget;
	}
}


















