package com.example.lifememory.activity;

import java.text.DecimalFormat;

import com.example.lifememory.R;
import com.example.lifememory.utils.ConstantUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BillCalculatorActivity extends Activity {
	private boolean isFloat = false; // ���Ϊ�����ڱ���Ƿ����˼������е�С����
	private String temp1Str, temp2Str; // ����ʱ�������˵����ݻ��� temp1Str +(-, *,
										// /)temp2Str
	private boolean isClickFlag = false; // ����Ƿ����˼Ӽ��˳���ť
	private boolean isEqualBtnClick = true;  
	private int flagId; // 0��1��2��3��
	private TextView numTv;
	private String numString;
	private int resultCode = 0;
	LinearLayout cal_equal;
	LinearLayout cal_del;
	ImageView cal_sure;
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.bill_calculator_layout);
		
		findViews();
		initDatasAndViews();
		this.setFinishOnTouchOutside(false);     //����ⲿ���ر�activity
		
		
	}
	
	
	private void findViews() {
		numTv = (TextView) this.findViewById(R.id.num);
		cal_equal = (LinearLayout) this.findViewById(R.id.cal_equal);
		cal_del = (LinearLayout) this.findViewById(R.id.cal_del);
		cal_sure = (ImageView) this.findViewById(R.id.cal_sure);
	}
	
	private void initDatasAndViews() {
		numString = this.getIntent().getStringExtra("num");
		Log.i("a", numString);
		if(numString == null || "".equals(numString) || "0.0".equals(numString)) {
			numTv.setText("0");
		}if(numString.endsWith(".0")) {
			numString = numString.substring(0, numString.length() - 2);
			numTv.setText(numString);
		}else {
			numTv.setText(numString);
		}
		resultCode = this.getIntent().getIntExtra("resultCode", 0);
	}
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.cal_add:
			isEqualBtnClick = false;
			onClickFlag(0);
			break;
		case R.id.cal_minus:
			isEqualBtnClick = false;
			onClickFlag(1);
			break;
		case R.id.cal_multiply:
			isEqualBtnClick = false;
			onClickFlag(2);
			break;
		case R.id.cal_divide:
			isEqualBtnClick = false;
			onClickFlag(3);
			break;
		case R.id.cal_sure:
			break;
		case R.id.cal_equal:
			isEqualBtnClick = true;
			onClickEqual();
			break;
		case R.id.cal_one:
			onClickNum(1);
			break;
		case R.id.cal_two:
			onClickNum(2);
			break;
		case R.id.cal_three:
			onClickNum(3);
			break;
		case R.id.cal_four:
			onClickNum(4);
			break;
		case R.id.cal_five:
			onClickNum(5);
			break;
		case R.id.cal_six:
			onClickNum(6);
			break;
		case R.id.cal_seven:
			onClickNum(7);
			break;
		case R.id.cal_eight:
			onClickNum(8);
			break;
		case R.id.cal_nine:
			onClickNum(9);
			break;
		case R.id.cal_dot:
			onClickDot();
			break;
		case R.id.cal_zero:
			onClickNum(0);
			break;
		case R.id.cal_del:
			onClickDel();
			break;
		case R.id.back:
			back();
			break;
		case R.id.sure:
			intent = new Intent();
			if(isEqualBtnClick) {
				intent.putExtra("num", numTv.getText().toString());
			}else {
				intent.putExtra("num", temp1Str);
			}
			this.setResult(resultCode, intent);
			BillCalculatorActivity.this.finish();
			break;
		}
	}
	
	
	// ����˼������ϵ����ּ�
	private void onClickNum(int numStr) {
		String jie_str = numTv.getText().toString();
		Float jie_float = 0f;
		Long jie_long = 0l;
		if (jie_str.contains(".")) {
			// ��ǰֵ�Ǹ�����
			if (jie_str.substring(jie_str.indexOf("."), jie_str.length())
					.length() < 3) {
				// ����2λС��λ
				jie_float = Float.parseFloat(jie_str);
				numString = jie_float + "" + numStr;
			}
		} else {
			// ��ǰֵ������
			jie_long = Long.parseLong(jie_str);
			if (isFloat) {
				// �����С����
				if (jie_long == 0) {
					numString = "0." + numStr;
				} else {
					numString = jie_str + "." + numStr;
				}
			} else {
				// δ���С����
				if (jie_str.length() < 7) {
					// ����λ����������
					if (jie_long == 0) {
						numString = "" + numStr;
					} else {
						numString = numTv.getText().toString() + numStr;
					}
				}
			}
		}
		numTv.setText(numString);
	}

	// ����˼������ϵ�С����
	private void onClickDot() {
		isFloat = true;
	}

	// ����˼������ϵ�ɾ��
	private void onClickDel() {
		String jie_str = numTv.getText().toString();
		if (jie_str.length() > 1 ) {
			numString = jie_str.substring(0, jie_str.length() - 1); // ȥ�����һ���ַ�
			if (numString.endsWith(".")) {
				// �����С�����β
				numString = numString.substring(0, numString.length() - 1);
				isFloat = false;
			}
			
			if("-".equals(numString)) {
				numString = "0";
			}
			
		} else {
			numString = "0";
		}
		numTv.setText(numString);
	}

	// ����˼������ϵļӼ��˳�
	private void onClickFlag(int flagId) {
		cal_sure.setVisibility(ViewGroup.GONE);
		cal_equal.setVisibility(ViewGroup.VISIBLE);

		if (!isClickFlag) {
			temp1Str = numTv.getText().toString();
			numTv.setText("0");
			isClickFlag = true; // ����˼Ӽ��˳���ť
		}
		this.flagId = flagId;
		this.isFloat = false;    //������Ű�ť���Ƿ��Ǹ��������Ϊ��λ����Ȼ�ٵ������ʱ����С��
	}

	// ����˼������ϵĵȺ�
	private void onClickEqual() {
		cal_sure.setVisibility(ViewGroup.VISIBLE);
		cal_equal.setVisibility(ViewGroup.GONE);

		String jie_str = numTv.getText().toString();
		if (jie_str.length() > 0 && !"0".equals(jie_str)) {
			// ���������֣����Ҳ���0
			DecimalFormat df = new DecimalFormat("0.00");
			temp2Str = jie_str;
			String resultStr;
			Float temp1Float = 0f;
			Float temp2Float = 0f;
			Float resultFloat = 0f;
			switch (this.flagId) {
			case 0:
				// ��
				temp1Float = Float.parseFloat(temp1Str);
				temp2Float = Float.parseFloat(temp2Str);
				resultFloat = temp1Float + temp2Float;
				resultStr = resultStrDeleteZero(df.format(resultFloat));
				numTv.setText(resultStr);

				break;
			case 1:
				// ��
				temp1Float = Float.parseFloat(temp1Str);
				temp2Float = Float.parseFloat(temp2Str);
				resultFloat = temp1Float - temp2Float;
				resultStr = resultStrDeleteZero(df.format(resultFloat));
				numTv.setText(resultStr);
				break;
			case 2:
				// ��
				temp1Float = Float.parseFloat(temp1Str);
				temp2Float = Float.parseFloat(temp2Str);
				resultFloat = temp1Float * temp2Float;
				resultStr = resultStrDeleteZero(df.format(resultFloat));
				numTv.setText(resultStr);
				break;
			case 3:
				// ��
				temp1Float = Float.parseFloat(temp1Str);
				temp2Float = Float.parseFloat(temp2Str);
				resultFloat = temp1Float / temp2Float;
				resultStr = resultStrDeleteZero(df.format(resultFloat));
				numTv.setText(resultStr);
				break;
			}

		} else {
			numTv.setText(temp1Str);    //����������ź���û���������ֻ������������Ϊ0����ʱ���Ⱥ�ʱ��jineTv��ֵ����֮ǰ�����ֵtemp1Str
		}
		isClickFlag = false;
	}
	

	//�����Ľ���ַ��ͽ��ȥ��С��λ��0������12.30�� ȥ��0���12.3������12.00ȥ���Ϊ12
	private String resultStrDeleteZero(String resultStr) {
		String str = null;
		str = resultStr.substring(resultStr.lastIndexOf(".") + 1, resultStr.length());
		if(str.length() == 1){
			if(str.equals("0")) {
				return resultStr.substring(0, resultStr.length() - 2);
			}
		}else {
			String a = str.substring(1, 2);   //01 �е�1
			String b = str.substring(0, 1);	  //01�е�0
			if(a.equals("0")) {
				resultStr = resultStr.substring(0, resultStr.length() - 1);
				if(b.equals("0")) {
					return resultStr.substring(0, resultStr.length() - 2);
				}
			}
		}
		return resultStr;
	}
	
	private void back() {
		intent = new Intent();
		this.setResult(ConstantUtil.EDIT_NOCHANGE_BACK, intent);
		BillCalculatorActivity.this.finish();
	}

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			back();
			break;
		}
		return true;
	}
	
	
	
	
}
