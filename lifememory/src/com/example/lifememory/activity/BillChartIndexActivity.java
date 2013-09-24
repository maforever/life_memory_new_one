package com.example.lifememory.activity;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillCatagoryItem;
import com.example.lifememory.db.service.BillCatagoryService;
import com.example.lifememory.db.service.BillInfoService;
import com.example.lifememory.fragments.chart.FR_BillBarChartFragment;
import com.example.lifememory.fragments.chart.FR_BillListChartFragment;
import com.example.lifememory.fragments.chart.FR_BillNoDataChartFragment;
import com.example.lifememory.fragments.chart.FR_BillPieChartFragment;
import com.example.lifememory.utils.AbstractMyActivityGroup;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BillChartIndexActivity extends FragmentActivity {
	private String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
	private String[] months_little = { "4", "6", "9", "11" };
	final List<String> list_big = Arrays.asList(months_big);
	final List<String> list_little = Arrays.asList(months_little);
	private Calendar calendar;
	private int year, month, day;
	private TextView title;
	private String dateYM; // 数据库查找当月数据的参数，格式yyyy-MM
	private BillInfoService billService;
	private BillCatagoryService catagoryService;
	private ImageView pieBtn, barBtn, lineBtn, listBtn;
	public static final String CHART_PIE_ACTIVITY = "pieChart";
	public static final String CHART_BAR_ACTIVITY = "barChart";
	public static final String CHART_LINE_ACTIVITY = "lineChart";
	public static final String CHART_LIST_ACTIVITY = "listChart";
	public static final String CHART_NODATA_ACTIVITY = "nodata";
	public List<BillCatagoryItem> items = null; // 当月的账单
	private int billType = 2;
	private FragmentManager fm = null;
	private FragmentTransaction ft;
	private Fragment fragment;
	private int chartType = 1; // 1 饼型， 2柱状型， 3线型
	private PopupWindow popWindow;
	private LayoutInflater inflater;
	private RelativeLayout popWindowParent;
	private int screenHeight; // 屏幕的高度
	private int[] locations = new int[2];
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				// 当月有收入账单 饼型
				// setContainerView(CHART_PIE_ACTIVITY,
				// BillChartPieActivity.class, billType, dateYM);
				fragment = new FR_BillPieChartFragment(dateYM, billType);
				ft = fm.beginTransaction();
				ft.replace(R.id.container, fragment);
				ft.commit();

				break;
			case 1:
				// 柱状型
				fragment = new FR_BillBarChartFragment(dateYM, billType);
				ft = fm.beginTransaction();
				ft.replace(R.id.container, fragment);
				ft.commit();
				break;
			case 3:
				fragment = new FR_BillListChartFragment(dateYM, billType);
				ft = fm.beginTransaction();
				ft.replace(R.id.container, fragment);
				ft.commit();
				break;
			case 10:
				// 没有数据显示没数据提示的activity
				// setContainerView(CHART_NODATA_ACTIVITY,
				// BillChartNoDataActivity.class);
				Fragment fragment = new FR_BillNoDataChartFragment();
				ft = fm.beginTransaction();
				ft.replace(R.id.container, fragment);
				ft.commit();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.bill_chart_index);

		DisplayMetrics dm = this.getResources().getDisplayMetrics();
		screenHeight = dm.heightPixels;

		inflater = LayoutInflater.from(this);
		billService = new BillInfoService(this);
		catagoryService = new BillCatagoryService(this);
		findViews();
		title.setText(initDateTitle());
		fm = this.getSupportFragmentManager();
		pieBtn.setBackgroundResource(R.drawable.catagory_pie_press);
		Log.i("a", "activity " + dateYM);
		initPopWindow();
		new isHaveDatas().start();
	}

	private void initPopWindow() {
		View contentView = inflater.inflate(
				R.layout.bill_chart_index_popwindow, null);
		popWindow = new PopupWindow(contentView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		popWindow.setFocusable(true);
		popWindow.setBackgroundDrawable(new BitmapDrawable());
		TextView incomeChart = (TextView) contentView
				.findViewById(R.id.incomeChart);
		TextView spendChart = (TextView) contentView
				.findViewById(R.id.spendChart);
		incomeChart.setOnClickListener(new PopwindowBtnClickListener());
		spendChart.setOnClickListener(new PopwindowBtnClickListener());

	}

	private class PopwindowBtnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.incomeChart:
				if (popWindow.isShowing()) {
					popWindow.dismiss();
					billType = 2;
					new isHaveDatas().start();
				}
				// Toast.makeText(BillChartIndexActivity.this, "incomeChart",
				// 0).show();
				break;
			case R.id.spendChart:
				if (popWindow.isShowing()) {
					popWindow.dismiss();
					billType = 1;
					new isHaveDatas().start();
				}
				// Toast.makeText(BillChartIndexActivity.this, "spendChart",
				// 0).show();
				break;
			}
		}
	}

	/**
	 * 判断当前月份是否有数据， 首先判断时候有收入，在判断时候有支出，如果都没有，就跳转到没信息的fragment
	 * 
	 * @author Administrator
	 * 
	 */
	private class isHaveDatas extends Thread {
		@Override
		public void run() {
			if (billService.isHaveIncomeDatas(dateYM, billType)) {
				Log.i("a", "dateYM = " + dateYM + " haveDatas");
				switch (chartType) {
				case 1:
					// 饼形图
					handler.sendEmptyMessage(0);
					break;
				case 2:
					// 柱状图
					handler.sendEmptyMessage(1);
					break;
//				case 3:
//					// 线形图
//					break;
				case 4:
					// 列表
					handler.sendEmptyMessage(3);
					break;
				}
			} else {
				handler.sendEmptyMessage(10);
			}
		}
	}

	// /**
	// * 找到自定义id的加载Activity的View
	// */
	// @Override
	// protected ViewGroup getContainer() {
	// return (ViewGroup) findViewById(R.id.container);
	// }

	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			back();
			break;
		case R.id.catagory:
			popWindowParent.getLocationInWindow(locations);
			popWindow.showAtLocation(popWindowParent, Gravity.BOTTOM, 0,
					screenHeight - locations[1]);
			break;
		case R.id.arrowLeft:
			arrowLeftDonw();
			break;
		case R.id.arrowRight:
			arrowRightDonw();
			break;
		case R.id.pieBtn:
			freshTabBarBackGround();
			pieBtn.setBackgroundResource(R.drawable.catagory_pie_press);
			chartType = 1;
			new isHaveDatas().start();
			break;
		case R.id.barBtn:
			freshTabBarBackGround();
			barBtn.setBackgroundResource(R.drawable.catagory_bar_press);
			chartType = 2;
			new isHaveDatas().start();
			// setContainerView(CHART_BAR_ACTIVITY, BillChartBarActivity.class);
			break;
//		case R.id.lineBtn:
//			freshTabBarBackGround();
//			lineBtn.setBackgroundResource(R.drawable.catagory_line_press);
//			chartType = 3;
//			// setContainerView(CHART_LINE_ACTIVITY,
//			// BillChartLineActivity.class);
//			break;
		case R.id.listBtn:
			freshTabBarBackGround();
			listBtn.setBackgroundResource(R.drawable.catagory_list_press);
			chartType = 4;
			new isHaveDatas().start();
			// setContainerView(CHART_LIST_ACTIVITY,
			// BillChartListActivity.class);
			break;
		}
	}

	private void freshTabBarBackGround() {
		pieBtn.setBackgroundResource(R.drawable.bill_chart_pie_selector);
		barBtn.setBackgroundResource(R.drawable.bill_chart_bar_selector);
//		lineBtn.setBackgroundResource(R.drawable.bill_chart_line_selector);
		listBtn.setBackgroundResource(R.drawable.bill_chart_list_selector);
	}

	private void findViews() {
		title = (TextView) this.findViewById(R.id.title);
		pieBtn = (ImageView) this.findViewById(R.id.pieBtn);
		barBtn = (ImageView) this.findViewById(R.id.barBtn);
		listBtn = (ImageView) this.findViewById(R.id.listBtn);
		popWindowParent = (RelativeLayout) this
				.findViewById(R.id.popwindowParent);
	}

	private String initDateTitle() {
		String dateTitleStr = "";
		calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH) + 1;
		day = calendar.get(Calendar.DATE);

		dateTitleStr = year + "年" + month + "月流水";
		if (month < 10) {
			dateYM = year + "-0" + month;
		} else {
			dateYM = year + "-" + month;
		}
		// Log.i("a", "dataYM = " + dataYM);
		return dateTitleStr;
	}

	private void arrowLeftDonw() {
		String dateTitleStr = "";
		if (month > 1) {
			month--;
		} else {
			month = 12;
			year--;
		}
		dateTitleStr = year + "年" + month + "月流水";

		if (month < 10) {
			dateYM = year + "-0" + month;
		} else {
			dateYM = year + "-" + month;
		}
		Log.i("a", "dataYM = " + dateYM);
		title.setText(dateTitleStr);
		new isHaveDatas().start();
	}

	private void arrowRightDonw() {
		String dateTitleStr = "";
		if (month < 12) {
			month++;
		} else {
			month = 1;
			year++;
		}
		if (month < 10) {
			dateYM = year + "-0" + month;
		} else {
			dateYM = year + "-" + month;
		}
		dateTitleStr = year + "年" + month + "月流水";
		title.setText(dateTitleStr);
		Log.i("a", "dataYM = " + dateYM);

		new isHaveDatas().start();
	}

	private void back() {
		BillChartIndexActivity.this.finish();
		overridePendingTransition(R.anim.activity_steady, R.anim.activity_down);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back();
		}
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		catagoryService.closeDB();
		billService.closeDB();
		catagoryService = null;
		billService = null;
	}

}
