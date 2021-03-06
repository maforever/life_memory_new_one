package com.example.lifememory.activity.bill.calender;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.example.lifememory.R;
import com.example.lifememory.db.service.BillInfoService;

import android.app.Activity;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class CalendarGridViewAdapter extends BaseAdapter {

	private Calendar calStartDate = Calendar.getInstance();// 当前显示的日历
	private Calendar calSelected = Calendar.getInstance(); // 选择的日历
	private LayoutInflater inflater;
	private boolean isCurrentDate;
	private BillInfoService infoService;
	private String ymd;
	TextView tag;
	public void setSelectedDate(Calendar cal) {
		calSelected = cal;
	}

	private Calendar calToday = Calendar.getInstance(); // 今日
	private int iMonthViewCurrentMonth = 0; // 当前视图月
	// 根据改变的日期更新日历
	// 填充日历控件用
	private void UpdateStartDateForMonth() {
		calStartDate.set(Calendar.DATE, 1); // 设置成当月第一天
		iMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);// 得到当前日历显示的月

		// 星期一是2 星期天是1 填充剩余天数
		int iDay = 0;
		int iFirstDayOfWeek = Calendar.MONDAY;
		int iStartDay = iFirstDayOfWeek;
		if (iStartDay == Calendar.MONDAY) {
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
			if (iDay < 0)
				iDay = 6; // 6
		}
		if (iStartDay == Calendar.SUNDAY) {
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
			if (iDay < 0)
				iDay = 6; // 6
		}
		calStartDate.add(Calendar.DAY_OF_WEEK, -iDay);

		calStartDate.add(Calendar.DAY_OF_MONTH, -1);// 周日第一位

	}

	ArrayList<java.util.Date> titles;

	private ArrayList<java.util.Date> getDates() {

		UpdateStartDateForMonth();

		ArrayList<java.util.Date> alArrayList = new ArrayList<java.util.Date>();

		for (int i = 1; i <= 42; i++) { // 42
			alArrayList.add(calStartDate.getTime());
			calStartDate.add(Calendar.DAY_OF_MONTH, 1);
		}

		return alArrayList;
	}

	private Activity activity;
	Resources resources;

	// construct
	public CalendarGridViewAdapter(Activity a, Calendar cal,
			boolean isCurrentDate, BillInfoService infoService) {
		calStartDate = cal;
		activity = a;
		resources = activity.getResources();
		titles = getDates();
		inflater = a.getLayoutInflater();
		this.isCurrentDate = isCurrentDate;
		this.infoService = infoService;
	}

	public CalendarGridViewAdapter(Activity a) {
		activity = a;
		resources = activity.getResources();
		inflater = a.getLayoutInflater();
	}

	@Override
	public int getCount() {
		return titles.size();
	}

	@Override
	public Object getItem(int position) {
		return titles.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		FrameLayout fl;
		TextView txtDay;
		TextView txtToDay;
//		TextView tag;
		ViewHolder vh;
		if (convertView == null) {
			convertView = inflater.inflate(
					R.layout.bill_calender_gridview_item, null);
			txtDay = (TextView) convertView.findViewById(R.id.txtDay);
			txtToDay = (TextView) convertView.findViewById(R.id.txtToDay);
			tag = (TextView) convertView.findViewById(R.id.haveDataTag);
			fl = (FrameLayout) convertView.findViewById(R.id.main);
			vh = new ViewHolder();
			vh.txtDay = txtDay;
			vh.txtToDay = txtToDay;
			vh.tag = tag;
			vh.fl = fl;
			convertView.setTag(R.id.tagFirst, vh);
		} else {
			vh = (ViewHolder) convertView.getTag(R.id.tagFirst);
			txtDay = vh.txtDay;
			txtToDay = vh.txtToDay;
			fl = vh.fl;
			tag = vh.tag;
		}
		convertView.setId(position + 5000);
		Date myDate = (Date) getItem(position);
		Calendar calCalendar = Calendar.getInstance();
		calCalendar.setTime(myDate);
		final int iMonth = calCalendar.get(Calendar.MONTH);
		final int iDay = calCalendar.get(Calendar.DAY_OF_WEEK);
		// 判断周六周日
		convertView.setBackgroundColor(resources.getColor(R.color.white));
		convertView.setTag(R.id.tagSecond, myDate);
		if (iDay == 7) {
			// 周六
			convertView.setBackgroundColor(resources.getColor(R.color.text_6));
		} else if (iDay == 1) {
			// 周日
			convertView.setBackgroundColor(resources.getColor(R.color.text_7));
		}
		// 判断周六周日结束

		CalendarUtil calendarUtil = new CalendarUtil(calCalendar);
		if (equalsDate(calToday.getTime(), myDate)) {
			// 当前日期
			convertView.setBackgroundColor(resources
					.getColor(R.color.event_center));
			txtToDay.setText(calendarUtil.toString());
		} else {
			txtToDay.setText(calendarUtil.toString());
		}

		// 这里用于比对是不是比当前日期小，如果比当前日期小就高亮

		// 设置背景颜色
		if (equalsDate(calSelected.getTime(), myDate)) {
			// 选择的
			convertView.setBackgroundColor(resources
					.getColor(R.color.selection));
		} else {
			if (equalsDate(calToday.getTime(), myDate)) {
				// 当前日期
				convertView.setBackgroundColor(resources
						.getColor(R.color.calendar_zhe_day));
			}
		}

		// 判断是否是当前月
		if (iMonth == iMonthViewCurrentMonth) {
			txtToDay.setTextColor(resources.getColor(R.color.ToDayText));
			txtDay.setTextColor(resources.getColor(R.color.Text));
		} else {
			txtDay.setTextColor(resources.getColor(R.color.noMonth));
			txtToDay.setTextColor(resources.getColor(R.color.noMonth));
		}
		if (isCurrentDate) {
			String dateStr = CalendarUtil.getDay(calCalendar);
//			 Log.i("a", "dateStr = " + dateStr);
//			if (infoService.ifHaveDatasByYMD(dateStr)) {
//				tag.setVisibility(ViewGroup.VISIBLE);
//			}
			new TagAsyncTask(tag).execute(dateStr);
		}

		int day = myDate.getDate(); // 日期

		txtDay.setText(String.valueOf(day));

		return convertView;

	}

	
	private class TagAsyncTask extends AsyncTask<String, Integer, Boolean> {
		private TextView tag;
		public TagAsyncTask(TextView tag) {
			this.tag = tag;
		}
		@Override
		protected Boolean doInBackground(String... params) {
			String ymd = params[0];
			if (infoService.ifHaveDatasByYMD(ymd)) {
				return true;
			}
			return false;
		}
		
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if(result) {
				tag.setVisibility(ViewGroup.VISIBLE);
			}else {
				tag.setVisibility(ViewGroup.GONE);
			}
		}
	}
	
	
	
	
	
	static class ViewHolder {
		FrameLayout fl;
		TextView txtDay;
		TextView txtToDay;
		TextView tag;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	private Boolean equalsDate(Date date1, Date date2) {

		if (date1.getYear() == date2.getYear()
				&& date1.getMonth() == date2.getMonth()
				&& date1.getDate() == date2.getDate()) {
			return true;
		} else {
			return false;
		}

	}
	

}
