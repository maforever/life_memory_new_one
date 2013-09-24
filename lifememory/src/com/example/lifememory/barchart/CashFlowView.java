package com.example.lifememory.barchart;

import java.util.ArrayList;

import com.example.lifememory.utils.BillChartColorUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

public class CashFlowView extends View {
	private ArrayList<Cash> listCash = null;
	private Paint paint;
	private float screenW, screenH;
	private float halfScreenH;// 一半的屏幕高度
	private float Size_Text;//字体的大小，根据屏幕的宽度设置
	private float baseHeight;// 100对应的高度
	private float CashW;// 现金流的宽度
	private final int CashJ = 20;// 现金流的间隔
	private final int Start_Offset = 20;// 开始点的起初X位置
	private float widthDate;
	private float lastX;
	private float lastY;
	private Scroller scroller;
	private int[] colors;
	private float total_Width = 0;
	public CashFlowView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stu
		init(context);
	}

	public CashFlowView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		scroller=new Scroller(context);
		paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Style.FILL); // 设置画笔为填充
		colors = new BillChartColorUtils(context).initColors();
	}

	private void initDraw() {
		if(screenW>0){
			return;
		}
		screenW = this.getWidth();
		screenH = this.getHeight();
		Log.e("..............", "screenW=" + screenW);
		Log.e("..............", "screenH=" + screenH);
		halfScreenH = screenH / 1.2f;
		baseHeight = screenH / 12;
		CashW = screenW / 9;
		Size_Text = CashW / 3.0f;
		paint.setTextSize(Size_Text);
		widthDate = paint.measureText("测试");
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		initDraw();
		int start = 0;
		if (listCash != null && listCash.size() != 0) {

			int size = listCash.size();
			for (int i = 0; i < size; i++) {
				Cash cash = listCash.get(i);
				start = (int) (i * (CashW + CashJ) + Start_Offset);
				paint.setColor(colors[i]);
				if (cash.money >= 0) {
//					paint.setColor(Color.argb(255, 168, 18, 18));
					paint.setColor(colors[i]);
					int height = 0;
					if (cash.money <= 100) {
						height = (int) (halfScreenH - baseHeight);
						canvas.drawRect(start, halfScreenH - baseHeight, start
								+ CashW, halfScreenH, paint);
					} else {
						height = (int) (halfScreenH - 0.12 * baseHeight
								* cash.money / 100);
						if (height < Size_Text + 9) {
							height = (int) (Size_Text + 9);
						}
						canvas.drawRect(start, height, start + CashW,
								halfScreenH , paint);
					}
					paint.setColor(Color.argb(255, 41, 41, 43));
					widthDate = paint.measureText(cash.time);
					canvas.drawText(cash.time, start + (CashW - widthDate) / 2,
							halfScreenH + Size_Text + 9, paint);
//					paint.setColor(Color.argb(255, 221, 221, 221));
					paint.setColor(Color.argb(255, 41, 41, 43));
					String sMoney =  "" + (int) cash.money;
					float lengthMoney = paint.measureText(sMoney);
					canvas.drawText(sMoney, start + (CashW - lengthMoney) / 2,
							height - Size_Text / 2, paint);
				} else {
//					paint.setColor(Color.argb(255, 39, 151, 23));
					paint.setColor(colors[i]);
					int height = 0;
					if (cash.money >= -100) {
						height = (int) (halfScreenH + baseHeight) + 100;
						canvas.drawRect(start, halfScreenH, start + CashW,
								height, paint);
					} else {
						height = (int) (halfScreenH - 0.12 * baseHeight
								* cash.money / 100) + 100;
						if (height > screenH - Size_Text - 10) {
							height = (int) (screenH - Size_Text - 10);
						}
						canvas.drawRect(start, halfScreenH, start + CashW,
								height, paint);
					}

					paint.setColor(Color.argb(255, 41, 41, 43));
					widthDate = paint.measureText(cash.time);
					canvas.drawText(cash.time, start + (CashW - widthDate) / 2,
							halfScreenH - 12, paint);
//					paint.setColor(Color.argb(255, 221, 221, 221));
					paint.setColor(Color.argb(255, 41, 41, 43));
					String sMoney = "" + (int) cash.money;
					float lengthMoney = paint.measureText(sMoney);
					canvas.drawText(sMoney, start + (CashW - lengthMoney) / 2,
							(height + 1.2f * Size_Text), paint);

				}
			}
			paint.setColor(Color.argb(255, 41, 41, 43));
			if (start + CashW + Start_Offset > screenW) {
				total_Width=start + CashW + Start_Offset;
				canvas.drawLine(0, halfScreenH, total_Width,
						halfScreenH, paint);
			} else {
				total_Width=screenW;
				canvas.drawLine(0, halfScreenH, screenW, halfScreenH, paint);
			}

		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX=event.getX();
			lastY=event.getY();
			
			return true;
		case MotionEvent.ACTION_MOVE:
			float newX=event.getX();
			float newY=event.getY();
			
			scrollBy((int) ((lastX-newX)*0.5), (int) ((lastY-newY)*0.5));
			lastX=newX;
			lastY=newY;
			break;
		case MotionEvent.ACTION_UP:
			int scrollX=getScrollX();
			int scrollY=getScrollY();
			if((scrollX<0)&&(scrollX<-10||scrollY>10)){
				//XY方向超出左边位置
				scroller.startScroll(scrollX,scrollY, -scrollX, -scrollY);
				invalidate();
			}
			else if((scrollX>total_Width-screenW)&&(scrollY<-10||scrollY>10)){
				//XY方向超出右边位置
				scroller.startScroll(scrollX,scrollY, (int) (total_Width-screenW-scrollX), -scrollY);
				invalidate();
			}else if(scrollX<0){
				//X方向超出左边的位置
				scroller.startScroll(scrollX,scrollY, -scrollX, 0);
				invalidate();
			
			}else if(scrollX>total_Width-screenW){
				//X方向超出右边边的位置
				scroller.startScroll(scrollX,scrollY, (int) (total_Width-screenW-scrollX), 0);
				invalidate();
			}else if(scrollY<-10||scrollY>10){
				//Y方向超出了位置
				scroller.startScroll(scrollX,scrollY, 0, -scrollY);
				invalidate();
			}
			break;
		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	@Override
	public void computeScroll() {
		if (scroller.computeScrollOffset()) {
			// 调用这个下面的条件是由于scroller调用了滑动从而使它激发
			scrollTo(scroller.getCurrX(), scroller.getCurrY());
			invalidate();
			return;
		}
		super.computeScroll();
	}
	public void setCashFlow(ArrayList<Cash> listCash) {
		this.listCash = listCash;

	}
}
