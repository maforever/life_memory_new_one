package com.example.lifememory.activity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

import com.example.lifememory.R;
import com.example.lifememory.activity.bill.calender.CalendarGridView;
import com.example.lifememory.activity.bill.calender.CalendarGridViewAdapter;
import com.example.lifememory.activity.bill.calender.CalendarUtil;
import com.example.lifememory.activity.bill.calender.NumberHelper;
import com.example.lifememory.activity.model.Bill;
import com.example.lifememory.adapter.BillCalendarListViewAdapter;
import com.example.lifememory.db.service.BillInfoService;
import com.example.lifememory.utils.DateFormater;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.LinearLayout.LayoutParams;

public class BillCalenderActivity extends Activity implements OnTouchListener{
	 /**
     * ��������ID
     */
    private static final int CAL_LAYOUT_ID = 55;
    //�ж�������
    private static final int SWIPE_MIN_DISTANCE = 120;

    private static final int SWIPE_MAX_OFF_PATH = 250;

    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    /**
     * ���ڴ���ѡ�е�����
     */
    private static final String MESSAGE = "msg";

    //����
    private Animation slideLeftIn;
    private Animation slideLeftOut;
    private Animation slideRightIn;
    private Animation slideRightOut;
    private ViewFlipper viewFlipper;
    GestureDetector mGesture = null;

    /**
     * ���찴ť
     */
    private Button mTodayBtn;
    /**
     * ���ذ�ť
     */
    private Button backBtn;
    /**
     * ��һ���°�ť
     */
    private ImageView mPreMonthImg;

    /**
     * ��һ���°�ť
     */
    private ImageView mNextMonthImg;

    /**
     * ������ʾ���������
     */
    private TextView mDayMessage;

    /**
     * ����װ��������View
     */
    private RelativeLayout mCalendarMainLayout;

    // ��������
    private Context mContext = BillCalenderActivity.this;
    /**
     * ��һ����View
     */
    private GridView firstGridView;

    /**
     * ��ǰ��View
     */
    private GridView currentGridView;

    /**
     * ��һ����View
     */
    private GridView lastGridView;

    /**
     * ��ǰ��ʾ������
     */
    private Calendar calStartDate = Calendar.getInstance();

    /**
     * ѡ�������
     */
    private Calendar calSelected = Calendar.getInstance();

    /**
     * ����
     */
    private Calendar calToday = Calendar.getInstance();

    /**
     * ��ǰ����չʾ������Դ
     */
    private CalendarGridViewAdapter currentGridAdapter;

    /**
     * Ԥװ����һ����չʾ������Դ
     */
    private CalendarGridViewAdapter firstGridAdapter;

    /**
     * Ԥװ����һ����չʾ������Դ
     */
    private CalendarGridViewAdapter lastGridAdapter;


    //
    /**
     * ��ǰ��ͼ��
     */
    private int mMonthViewCurrentMonth = 0;

    /**
     * ��ǰ��ͼ��
     */
    private int mMonthViewCurrentYear = 0;

    /**
     * ��ʼ��
     */
    private int iFirstDayOfWeek = Calendar.MONDAY;

    private BillInfoService infoService;
    private ListView listView;
    private LinearLayout emptyLayout;
    private List<Bill> bills;
    private String ymd;
    private BillCalendarListViewAdapter adapter;
    private Handler handler = new Handler() {
    	public void handleMessage(android.os.Message msg) {
    		switch (msg.what) {
			case 1:
				//������
				listView.setVisibility(ViewGroup.GONE);
				emptyLayout.setVisibility(ViewGroup.VISIBLE);
				break;
			case 2:
				//������
				listView.setVisibility(ViewGroup.VISIBLE);
				emptyLayout.setVisibility(ViewGroup.GONE);
				adapter = new BillCalendarListViewAdapter(BillCalenderActivity.this, bills);
				listView.setAdapter(adapter);
				break;

			}
    	};
    };
    public boolean onTouch(View v, MotionEvent event) {
        return mGesture.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.calendar_main);
        
        infoService = new BillInfoService(this);
        initView();
        updateStartDateForMonth();

        generateContetView(mCalendarMainLayout);
        slideLeftIn = AnimationUtils.loadAnimation(this, R.anim.slide_left_in);
        slideLeftOut = AnimationUtils.loadAnimation(this, R.anim.slide_left_out);
        slideRightIn = AnimationUtils.loadAnimation(this, R.anim.slide_right_in);
        slideRightOut = AnimationUtils.loadAnimation(this, R.anim.slide_right_out);

        slideLeftIn.setAnimationListener(animationListener);
        slideLeftOut.setAnimationListener(animationListener);
        slideRightIn.setAnimationListener(animationListener);
        slideRightOut.setAnimationListener(animationListener);

        mGesture = new GestureDetector(this, new GestureListener());
        
        ymd = DateFormater.getInstatnce().getY_M_D();
        new InitDatasThread().start();
        
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	if(infoService != null) {
    		ymd = DateFormater.getInstatnce().getY_M_D();
            updateStartDateForMonth();
            generateContetView(mCalendarMainLayout);
            new InitDatasThread().start();
    	}
    }
    
    private class InitDatasThread extends Thread {
    	@Override
    	public void run() {
    		bills = infoService.findBillByYMDInDetails(ymd);
    		if(bills.size() > 0) {
    			//������
    			handler.sendEmptyMessage(2);
    		}else {
    			//û����
    			handler.sendEmptyMessage(1);
    		}
    		
    	}
    }
    
    public void btnClick(View view) {
    	switch (view.getId()) {
		case R.id.emptyLayout:
			Intent intent = new Intent(this, BillInputActivity.class);
			intent.putExtra("flag", "fromCalendar");
			intent.putExtra("ymd", ymd);
			this.startActivity(intent);
			this.overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
//			Toast.makeText(this, "�������", 0).show();
			break;
		}
    }

    /**
     * ���ڳ�ʼ���ؼ�
     */
    private void initView() {
        mTodayBtn = (Button) findViewById(R.id.today_btn);
        backBtn = (Button) findViewById(R.id.back_btn);
        mDayMessage = (TextView) findViewById(R.id.day_message);
        mCalendarMainLayout = (RelativeLayout) findViewById(R.id.calendar_main);
        mPreMonthImg = (ImageView) findViewById(R.id.left_img);
        mNextMonthImg = (ImageView) findViewById(R.id.right_img);
        listView = (ListView) findViewById(R.id.listView);
        emptyLayout = (LinearLayout) findViewById(R.id.emptyLayout);
        mTodayBtn.setOnClickListener(onTodayClickListener);
        backBtn.setOnClickListener(onBackBtnClickListener);
        mPreMonthImg.setOnClickListener(onPreMonthClickListener);

        mNextMonthImg.setOnClickListener(onNextMonthClickListener);
    }

    /**
     * ���ڼ��ص���ǰ�����ڵ��¼�
     */
    private View.OnClickListener onTodayClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            calStartDate = Calendar.getInstance();
            calSelected = Calendar.getInstance();
            updateStartDateForMonth();
            generateContetView(mCalendarMainLayout);
            
            ymd = DateFormater.getInstatnce().getY_M_D();
            new InitDatasThread().start();            
        }
    };

    /**
     * ���ڼ�����һ�������ڵ��¼�
     */
    private View.OnClickListener onPreMonthClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            viewFlipper.setInAnimation(slideRightIn);
            viewFlipper.setOutAnimation(slideRightOut);
            viewFlipper.showPrevious();
            setPrevViewItem();
        }
    };

    /**
     * ���ڷ����ϲ�activity
     */
    private View.OnClickListener onBackBtnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			back();
		}
	};
    
	private void back() {
		BillCalenderActivity.this.finish();
		overridePendingTransition(R.anim.activity_steady, R.anim.activity_down);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			back();
		}
		return true;
	}
	
	
    /**
     * ���ڼ�����һ�������ڵ��¼�
     */
    private View.OnClickListener onNextMonthClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            viewFlipper.setInAnimation(slideLeftIn);
            viewFlipper.setOutAnimation(slideLeftOut);
            viewFlipper.showNext();
            setNextViewItem();
        }
    };

    /**
     * ��Ҫ�������ɷ�ǰչʾ������View
     *
     * @param layout ��Ҫ����ȥ���صĲ���
     */
    private void generateContetView(RelativeLayout layout) {
        // ����һ����ֱ�����Բ��֣��������ݣ�
        viewFlipper = new ViewFlipper(this);
        viewFlipper.setId(CAL_LAYOUT_ID);
        calStartDate = getCalendarStartDate();
        CreateGirdView();
        RelativeLayout.LayoutParams params_cal = new RelativeLayout.LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        layout.addView(viewFlipper, params_cal);

        LinearLayout br = new LinearLayout(this);
        RelativeLayout.LayoutParams params_br = new RelativeLayout.LayoutParams(
                LayoutParams.FILL_PARENT, 1);
        params_br.addRule(RelativeLayout.BELOW, CAL_LAYOUT_ID);
        br.setBackgroundColor(getResources().getColor(R.color.calendar_background));
        layout.addView(br, params_br);
    }

    /**
     * ���ڴ�����ǰ��Ҫ����չʾ��View
     */
    private void CreateGirdView() {

        Calendar firstCalendar = Calendar.getInstance(); // ��ʱ
        Calendar currentCalendar = Calendar.getInstance(); // ��ʱ
        Calendar lastCalendar = Calendar.getInstance(); // ��ʱ
        firstCalendar.setTime(calStartDate.getTime());
        currentCalendar.setTime(calStartDate.getTime());
        lastCalendar.setTime(calStartDate.getTime());

        firstGridView = new CalendarGridView(mContext);
        firstCalendar.add(Calendar.MONTH, -1);
        firstGridAdapter = new CalendarGridViewAdapter(this, firstCalendar, false, infoService);
        firstGridView.setAdapter(firstGridAdapter);// ���ò˵�Adapter
        firstGridView.setId(CAL_LAYOUT_ID);

        currentGridView = new CalendarGridView(mContext);
        currentGridAdapter = new CalendarGridViewAdapter(this, currentCalendar, true, infoService);
        currentGridView.setAdapter(currentGridAdapter);// ���ò˵�Adapter
        currentGridView.setId(CAL_LAYOUT_ID);

        lastGridView = new CalendarGridView(mContext);
        lastCalendar.add(Calendar.MONTH, 1);
        lastGridAdapter = new CalendarGridViewAdapter(this, lastCalendar, false, infoService);
        lastGridView.setAdapter(lastGridAdapter);// ���ò˵�Adapter
        lastGridView.setId(CAL_LAYOUT_ID);

        currentGridView.setOnTouchListener(this);
        firstGridView.setOnTouchListener(this);
        lastGridView.setOnTouchListener(this);

        if (viewFlipper.getChildCount() != 0) {
            viewFlipper.removeAllViews();
        }

        viewFlipper.addView(currentGridView);
        viewFlipper.addView(lastGridView);
        viewFlipper.addView(firstGridView);

        String s = calStartDate.get(Calendar.YEAR)
                + "��"
                + NumberHelper.LeftPad_Tow_Zero(calStartDate
                .get(Calendar.MONTH) + 1);
        mDayMessage.setText(s);
    }

    /**
     * ��һ����
     */
    private void setPrevViewItem() {
        mMonthViewCurrentMonth--;// ��ǰѡ����--
        // �����ǰ��Ϊ�����Ļ���ʾ��һ��
        if (mMonthViewCurrentMonth == -1) {
            mMonthViewCurrentMonth = 11;
            mMonthViewCurrentYear--;
        }
        calStartDate.set(Calendar.DAY_OF_MONTH, 1); // ������Ϊ����1��
        calStartDate.set(Calendar.MONTH, mMonthViewCurrentMonth); // ������
        calStartDate.set(Calendar.YEAR, mMonthViewCurrentYear); // ������

    }

    /**
     * ��һ����
     */
    private void setNextViewItem() {
        mMonthViewCurrentMonth++;
        if (mMonthViewCurrentMonth == 12) {
            mMonthViewCurrentMonth = 0;
            mMonthViewCurrentYear++;
        }
        calStartDate.set(Calendar.DAY_OF_MONTH, 1);
        calStartDate.set(Calendar.MONTH, mMonthViewCurrentMonth);
        calStartDate.set(Calendar.YEAR, mMonthViewCurrentYear);

    }

    /**
     * ���ݸı�����ڸ�������
     * ��������ؼ���
     */
    private void updateStartDateForMonth() {
        calStartDate.set(Calendar.DATE, 1); // ���óɵ��µ�һ��
        mMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);// �õ���ǰ������ʾ����
        mMonthViewCurrentYear = calStartDate.get(Calendar.YEAR);// �õ���ǰ������ʾ����

        String s = calStartDate.get(Calendar.YEAR)
                + "-"
                + NumberHelper.LeftPad_Tow_Zero(calStartDate
                .get(Calendar.MONTH) + 1);
        mDayMessage.setText(s);
        // ����һ��2 ��������1 ���ʣ������
        int iDay = 0;
        int iFirstDayOfWeek = Calendar.MONDAY;
        int iStartDay = iFirstDayOfWeek;
        if (iStartDay == Calendar.MONDAY) {
            iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
            if (iDay < 0)
                iDay = 6;    //6
        }
        if (iStartDay == Calendar.SUNDAY) {
            iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
            if (iDay < 0)
                iDay = 6;    //6
        }
        calStartDate.add(Calendar.DAY_OF_WEEK, -iDay);

    }

    /**
     * ���ڻ�ȡ��ǰ��ʾ�·ݵ�ʱ��
     *
     * @return ��ǰ��ʾ�·ݵ�ʱ��
     */
    private Calendar getCalendarStartDate() {
        calToday.setTimeInMillis(System.currentTimeMillis());
        calToday.setFirstDayOfWeek(iFirstDayOfWeek);

        if (calSelected.getTimeInMillis() == 0) {
            calStartDate.setTimeInMillis(System.currentTimeMillis());
            calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
        } else {
            calStartDate.setTimeInMillis(calSelected.getTimeInMillis());
            calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
        }

        return calStartDate;
    }

    AnimationListener animationListener = new AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            //��������ɺ����
            CreateGirdView();
        }
    };

    class GestureListener extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    viewFlipper.setInAnimation(slideLeftIn);
                    viewFlipper.setOutAnimation(slideLeftOut);
                    viewFlipper.showNext();
                    setNextViewItem();
                    return true;

                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    viewFlipper.setInAnimation(slideRightIn);
                    viewFlipper.setOutAnimation(slideRightOut);
                    viewFlipper.showPrevious();
                    setPrevViewItem();
                    return true;

                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            //�õ���ǰѡ�е��ǵڼ�����Ԫ��
            int pos = currentGridView.pointToPosition((int) e.getX(), (int) e.getY());
//            LinearLayout txtDay = (LinearLayout) currentGridView.findViewById(pos + 5000);
            Log.i("a", "pos = " + pos);
            FrameLayout txtDay = (FrameLayout) currentGridView.findViewById(pos + 5000);
            if (txtDay != null) {
                if (txtDay.getTag(R.id.tagSecond) != null) {
                    Date date = (Date) txtDay.getTag(R.id.tagSecond);
//                    if (CalendarUtil.compare(date, Calendar.getInstance().getTime())) {
                        calSelected.setTime(date);
                        currentGridAdapter.setSelectedDate(calSelected);
                        currentGridAdapter.notifyDataSetChanged();
                        firstGridAdapter.setSelectedDate(calSelected);
                        firstGridAdapter.notifyDataSetChanged();

                        lastGridAdapter.setSelectedDate(calSelected);
                        lastGridAdapter.notifyDataSetChanged();
                        String week = CalendarUtil.getWeek(calSelected);
                        String message = CalendarUtil.getDay(calSelected) + " ũ��" +
                                new CalendarUtil(calSelected).getDay() + " " + week;
                        ymd = CalendarUtil.getDay(calSelected);
                        new InitDatasThread().start();
//                        Toast.makeText(getApplicationContext(), "" + dateStr, 0).show();
//                        Toast.makeText(getApplicationContext(), "��ѡ�������Ϊ:" + message, Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getApplicationContext(), "ѡ������ڲ���С�ڽ��������", Toast.LENGTH_SHORT).show();
//                    }
                }
            }

            Log.i("TEST", "onSingleTapUp -  pos=" + pos);

            return false;
        }
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	infoService.closeDB();
    }
}
