package com.example.lifememory.activity.bill.calender;



import com.example.lifememory.R;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;

/**
 * ������������չʾ��GridView����
 *
 * @author zhouxin@easier.cn
 */
public class CalendarGridView extends GridView {

    /**
     * ��ǰ�����������Ķ���
     */
    private Context mContext;

    /**
     * CalendarGridView ������
     *
     * @param context ��ǰ�����������Ķ���
     */
    public CalendarGridView(Context context) {
        super(context);
        mContext = context;

        setGirdView();
    }

    /**
     * ��ʼ��gridView �ؼ��Ĳ���
     */
    private void setGirdView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

        setLayoutParams(params);
        setNumColumns(7);// ����ÿ������
        setGravity(Gravity.CENTER_VERTICAL);// λ�þ���
        setVerticalSpacing(1);// ��ֱ���
        setHorizontalSpacing(1);// ˮƽ���
        setBackgroundColor(getResources().getColor(R.color.calendar_background));

        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int i = display.getWidth() / 7;
        int j = display.getWidth() - (i * 7);
        int x = j / 2;
        setPadding(x, 0, 0, 0);// ����
    }
}
