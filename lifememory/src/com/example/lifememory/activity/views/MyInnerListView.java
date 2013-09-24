package com.example.lifememory.activity.views;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.ListView;

public class MyInnerListView extends ListView{

	public MyInnerListView(Context context) {
		super(context);
	}
	
	
	
	public MyInnerListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}



	public MyInnerListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}



	
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                        MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
}
}
	
