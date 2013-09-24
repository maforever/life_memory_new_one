package com.example.lifememory.activity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.Bill;
import com.example.lifememory.activity.model.BillAccountItem;
import com.example.lifememory.activity.model.BillTemplate;
import com.example.lifememory.db.service.BillAccountService;
import com.example.lifememory.db.service.BillCatagoryService;
import com.example.lifememory.db.service.BillInfoService;
import com.example.lifememory.utils.AppAplication;
import com.example.lifememory.utils.ConstantUtil;
import com.example.lifememory.utils.CopyFileFromData;
import com.example.lifememory.utils.DateFormater;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class BillInputActivity extends Activity {
	private BillInfoService billService;
	private TextView zhichuBtn, shouruBtn, zhuanzhangBtn;
	private CheckBox baoxiaoCb = null;
	private ViewFlipper viewFlipper = null;
	private LayoutInflater inflater;
	private View childView1, childView2, childView3;
	private PopupWindow calculator = null; // 计算器
	private View popWinParentView = null;
	private TextView zhichuJine, shouruJine, zhuanzhangJine, jineTv;
	private String jie_txt;
	private Bill bill = null;
	private boolean isFloat = false; // 标记为，用于标记是否点击了计算器中的小数点
	private String temp1Str, temp2Str; // 计算时符号两端的数据缓存 temp1Str +(-, *,
										// /)temp2Str
	private boolean isClickFlag = false; // 标记是否点击了加减乘除按钮
	private int flagId; // 0加1减2乘3除
	private int parentId = 1; // 用于记录一级类别列表选中的数据的数据库id
	private int catagoryChildId = 0; // 用于记录级类别列表选中的数据的数据库id
	private BillCatagoryService dbService = null;
	private BillAccountService accountService = null;
	private String catagoryStr; // 用于保存界面上显示的类别内容
	private String accountStr; // 用于保存界面上显示的账户内容
	private String memberStr; // 用于保存界面上显示的成员内容
	private TextView currentJinETextView = null; // 用于指向当前viewflipper显示的界面上的显示金额内容的textView
	private TextView inCatagoryTextView = null; // 用于指向当前viewflipper显示的界面上的显示支出类别内容的textView
	private TextView outCatagoryTextView = null; // 用于指向当前viewflipper显示的界面上的显示收入类别内容的textView
	private TextView currentAccountTextView = null; // 用于指向当前viewflipper显示的界面上的显示账户内容的textView
	private TextView currentMemberTextView = null; // 用于指向当前viewflipper显示的界面上的显示成员内容的textView
	private TextView currentDateTextView = null; // 用于指向当前viewflipper显示的界面上的显示日期内容的textView
	private TextView currentBeizhuTextView = null; // 用于指向当前viewflipper显示的界面上的显示备注内容的textView
	private TextView transferInTextView = null; // 存放转入账号的名称
	private TextView transferOutTextView = null; // 存放转出账号的名称
	private int outCatagorySelectedId = 0; // 用于记录一级类别列表选中的索引数
	private int outCatagorySelectedChildId = 0; // 用于记录二级类别列表选中的索引数
	private int accountGroupSelectedIndex = 0; // 用于记录账户expandablelistview中组的索引
	private int accountChildSelectedIndex = 0; // 用于记录账户expandablelistview中子的索引
	private int transferOutGroupSelectedIndex = 2; // 用于记录转出expandablelistview中组的索引
	private int transferOutChildSelectedIndex = 0; // 用于记录转出expandablelistview中子的索引
	private int transferInGroupSelectedIndex = 0; // 用于记录转入expandablelistview中组的索引
	private int transferInChildSelectedIndex = 0; // 用于记录转入expandablelistview中子的索引
	private int memberSelectedIndex = 1; // 用于记录成员列别中的listview选中索引
	private int inCatagorySelectedIndex = 0; // 用于记录输入类别的数据索引
	private Intent intent;
	private String flag = "add"; // 用于标示是来自添加界面， view来自流水点击与长点击后选择编辑   viewchange来自流水长点击后选择的changetoxxx
	private int toType = 0;      //changeto的类型  1支出， 2收入 ， 3转账
	private int idx;
	private BillAccountItem commonAccount;
	private BillAccountItem commonTransferInAccount;
	private BillAccountItem commonTransferOutAccount;
	private BillTemplate template;
	private LinearLayout nextLayout;
	/*
	 * 用于纪录等号按钮是否点击了,当每次点击的+,-,*,/按钮后，将isEqualBtnClick=false
	 * 这样每当点击popwindow之外的或点击back关闭popwindow的时候
	 * ，就判断如果isEqualBtnClick=false，就将textview 的结果设为点击+，-,*,/之前的值
	 */
	private boolean isEqualBtnClick = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.bill_input_layout);

		billService = new BillInfoService(this);
		dbService = new BillCatagoryService(this);
		accountService = new BillAccountService(this);
		inflater = LayoutInflater.from(this);

		flag = this.getIntent().getStringExtra("flag");

		if ("add".equals(flag)) {
			
			bill = new Bill();
			
			commonAccount = accountService.findItemsForAddViews();
			commonTransferInAccount = accountService.findItemsForAddTransferInViews();
			commonTransferOutAccount = accountService.findItemsForAddViews();
			
			if(commonAccount == null) {
				//没有是现金类型下的账户
				bill.setAccount("无账户");
				bill.setAccountid(-1);
			}else {
				bill.setAccount(commonAccount.getName());
				bill.setAccountid(commonAccount.getIdx());
			}
			
			if(commonTransferInAccount == null) {
				bill.setTransferIn("现金");
				bill.setTransferInAccountId(-1);
			}else {
				bill.setTransferIn(commonTransferInAccount.getName());
				bill.setTransferInAccountId(commonTransferInAccount.getIdx());
			}
			
			if(commonTransferOutAccount == null) {
				bill.setTransferOut("储蓄");
				bill.setTransferOutAccountId(-1);
			}else {
				bill.setTransferOut(commonTransferOutAccount.getName());
				bill.setTransferOutAccountId(commonTransferOutAccount.getIdx());
			}

			bill.setOutCatagory("常用-打的");
			bill.setOutCatagoryParentId(1);    //默认选择常用父类别
			bill.setOutCatagoryChildId(10);     //默认选择打的子类别
//			bill.setAccount("现金");
			bill.setMember("自己");
			bill.setInCatagory("工资");
//			bill.setTransferOut("储蓄");
//			bill.setTransferIn("现金");

			
			
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			bill.setDate(sdf.format(d));
			bill.setDateYMD(DateFormater.getInstatnce().getY_M_D());

			findViews();
			currentAccountTextView = (TextView) viewFlipper.getCurrentView().findViewById(R.id.zhanghu);
			currentAccountTextView.setText(bill.getAccount());
			
			
			initViews();
			initCalculator();
		} else if ("view".equals(flag)) {
			idx = this.getIntent().getIntExtra("idx", 0);
			bill = billService.findBillByIdx(idx);
			findViews();
			nextLayout.setVisibility(ViewGroup.GONE);    //修改时不显示再记一笔按钮
			initViewsByViewFlag(bill.getBillType());
			initCalculator();
			
		}else if("viewchange".equals(flag)) {
			idx = this.getIntent().getIntExtra("idx", 0);
			toType = this.getIntent().getIntExtra("toType", 0);
			bill = billService.findBillByIdx(idx);
			bill.setBillType(toType);
			findViews();
			initViewsByViewChangeFlag(toType);   
			nextLayout.setVisibility(ViewGroup.GONE);     //修改时不显示再记一笔按钮
			
			commonAccount = accountService.findItemsForAddViews();
			commonTransferInAccount = accountService.findItemsForAddTransferInViews();
			commonTransferOutAccount = accountService.findItemsForAddViews();
			
			if(commonAccount == null) {
				//没有是现金类型下的账户
				bill.setAccount("无账户");
				bill.setAccountid(-1);
			}else {
				bill.setAccount(commonAccount.getName());
				bill.setAccountid(commonAccount.getIdx());
			}
			
			if(commonTransferInAccount == null) {
				bill.setTransferIn("现金");
				bill.setTransferInAccountId(-1);
			}else {
				bill.setTransferIn(commonTransferInAccount.getName());
				bill.setTransferInAccountId(commonTransferInAccount.getIdx());
			}
			
			if(commonTransferOutAccount == null) {
				bill.setTransferOut("储蓄");
				bill.setTransferOutAccountId(-1);
			}else {
				bill.setTransferOut(commonTransferOutAccount.getName());
				bill.setTransferOutAccountId(commonTransferOutAccount.getIdx());
			}

			if(viewFlipper.getDisplayedChild() != 2) {
				refreshAccountTextView();
			}else {
				refreshTransferAccountTextView();
			}
			initCalculator();
		}else if("template".equals(flag)) {
			bill = new Bill();
			

			BillTemplate template = (BillTemplate) this.getIntent().getSerializableExtra("template");
			Log.i("a", "template = " + template.toString());
			bill.setAccountid(template.getAccountid());
			bill.setOutCatagory(template.getOutCatagoryName());
			bill.setOutCatagoryChildId(template.getOutCatagoryChildId());
			bill.setOutCatagoryParentId(template.getOutCatagoryParentId());
			bill.setInCatagory(template.getInCatagoryName());
			bill.setMember(template.getMember());
			bill.setCanBaoXiao(template.isCanBaoXiao());
			bill.setTransferInAccountId(template.getTransferInAccountId());
			bill.setTransferOutAccountId(template.getTransferOutAccountId());
			bill.setBillType(template.getBillType());
			switch (template.getBillType()) {
			case 1:
				BillAccountItem item = accountService.findItemById(bill.getAccountid());
				bill.setAccount(item.getName());
				break;
			case 2:
				item = accountService.findItemById(bill.getAccountid());
				bill.setAccount(item.getName());
				break;
			case 3:
				BillAccountItem inAccount = accountService.findItemById(bill.getTransferInAccountId());
				BillAccountItem outAccount = accountService.findItemById(bill.getTransferOutAccountId());
				bill.setTransferIn(inAccount.getName());
				bill.setTransferOut(outAccount.getName());
				break;
			}
			
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			bill.setDate(sdf.format(d));
			bill.setDateYMD(DateFormater.getInstatnce().getY_M_D());

			
			findViews();
			initViewsByTemplate(bill.getBillType());
			initCalculator();
			
		}

		outCatagorySelectedId = 0; // 用于记录一级类别列表选中的索引数
		outCatagorySelectedChildId = -1; // 用于记录二级类别列表选中的索引数
		accountGroupSelectedIndex = 0; // 用于记录账户expandablelistview中组的索引
		accountChildSelectedIndex = -1; // 用于记录账户expandablelistview中子的索引
		transferOutGroupSelectedIndex = 0; // 用于记录转出expandablelistview中组的索引
		transferOutChildSelectedIndex = -1; // 用于记录转出expandablelistview中子的索引
		transferInGroupSelectedIndex = 0; // 用于记录转入expandablelistview中组的索引
		transferInChildSelectedIndex = -1; // 用于记录转入expandablelistview中子的索引
		memberSelectedIndex = -1; // 用于记录成员列别中的listview选中索引
		inCatagorySelectedIndex = -1; // 用于记录输入类别的数据索引
		
		// Log.i("a", "BillInputActivity onCreate catagorySelectedId = " +
		// catagorySelectedId + " catagorySelectedChildId = " +
		// catagorySelectedChildId);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i("a", "onResume ......");
		commonAccount = accountService.findItemsForAddViews();
		commonTransferInAccount = accountService.findItemsForAddTransferInViews();
		commonTransferOutAccount = accountService.findItemsForAddViews();
		
		
		
		
		if(commonAccount == null) {
			//没有是现金类型下的账户
			bill.setAccount("无账户");
			bill.setAccountid(-1);
		}
		
		if(commonTransferInAccount == null) {
			bill.setTransferIn("无账户");
			bill.setTransferInAccountId(-1);
		}
		
		if(commonTransferOutAccount == null) {
			bill.setTransferOut("无账户");
			bill.setTransferOutAccountId(-1);
		}
		
		if(viewFlipper.getDisplayedChild() == 2) {
			transferInTextView = (TextView) viewFlipper.getCurrentView().findViewById(R.id.zhuanru);
			transferInTextView.setText(bill.getTransferIn());
			transferOutTextView = (TextView) viewFlipper.getCurrentView().findViewById(R.id.zhuanchu);
			transferOutTextView.setText(bill.getTransferOut());
		}else {
			currentAccountTextView = (TextView) viewFlipper.getCurrentView().findViewById(R.id.zhanghu);
			currentAccountTextView.setText(bill.getAccount());
		}
	}
	
	private void initViewsByViewFlag(int billType) {
		switch (billType) {
		case 1:
			zhichuBtn
					.setBackgroundResource(R.drawable.exit_demo_mode_btn_pressed);
			zhichuBtn.setTextColor(Color.WHITE);
			shouruBtn.setClickable(false);
			zhuanzhangBtn.setClickable(false);
			viewFlipper.setDisplayedChild(0);
			outCatagoryTextView.setText(bill.getOutCatagory());
			currentAccountTextView = (TextView) viewFlipper.getCurrentView()
					.findViewById(R.id.zhanghu);
			currentAccountTextView.setText(bill.getAccount());
			currentMemberTextView = (TextView) viewFlipper.getCurrentView()
					.findViewById(R.id.chengyuan);
			currentMemberTextView.setText(bill.getMember());
			if (bill.isCanBaoXiao()) {
				baoxiaoCb.setChecked(true);
			} else
				baoxiaoCb.setChecked(false);

			break;
		case 2:
			shouruBtn
					.setBackgroundResource(R.drawable.exit_demo_mode_btn_pressed);
			shouruBtn.setTextColor(Color.WHITE);
			zhichuBtn.setClickable(false);
			zhuanzhangBtn.setClickable(false);
			viewFlipper.setDisplayedChild(1);
			inCatagoryTextView.setText(bill.getInCatagory());
			currentAccountTextView = (TextView) viewFlipper.getCurrentView()
					.findViewById(R.id.zhanghu);
			currentAccountTextView.setText(bill.getAccount());
			currentMemberTextView = (TextView) viewFlipper.getCurrentView()
					.findViewById(R.id.chengyuan);
			currentMemberTextView.setText(bill.getMember());
			break;
		case 3:
			zhuanzhangBtn
					.setBackgroundResource(R.drawable.exit_demo_mode_btn_pressed);
			zhuanzhangBtn.setTextColor(Color.WHITE);
			zhichuBtn.setClickable(false);
			shouruBtn.setClickable(false);
			viewFlipper.setDisplayedChild(2);
			transferInTextView.setText(bill.getTransferIn());
			transferOutTextView.setText(bill.getTransferOut());
			break;
		}

		currentJinETextView = (TextView) viewFlipper.getCurrentView()
				.findViewById(R.id.jine);
		currentJinETextView.setText(bill.getJine());
		currentDateTextView = (TextView) viewFlipper.getCurrentView()
				.findViewById(R.id.date);
		currentDateTextView.setText(bill.getDate());
		currentBeizhuTextView = (TextView) viewFlipper.getCurrentView()
				.findViewById(R.id.beizhu);
		currentBeizhuTextView.setText(bill.getBeizhu());

	}
	
	
	private void initViewsByViewChangeFlag(int billType) {
		switch (billType) {
		case 1:
			zhichuBtn
					.setBackgroundResource(R.drawable.exit_demo_mode_btn_pressed);
			zhichuBtn.setTextColor(Color.WHITE);
			shouruBtn.setClickable(false);
			zhuanzhangBtn.setClickable(false);
			viewFlipper.setDisplayedChild(0);
			outCatagoryTextView.setText("常用-打的");
			bill.setOutCatagory("常用-打的");
			bill.setOutCatagoryChildId(10);
			bill.setOutCatagoryParentId(1);
			currentAccountTextView = (TextView) viewFlipper.getCurrentView()
					.findViewById(R.id.zhanghu);
			currentMemberTextView = (TextView) viewFlipper.getCurrentView()
					.findViewById(R.id.chengyuan);
			if(bill.getBillType() == 2) {
				currentAccountTextView.setText(bill.getAccount());  //如果之前是收入类型账单，就显示收入类型账单的账户类型
				currentMemberTextView.setText(bill.getMember());    //如果之前是收入类型账单，就显示收入类型账单的成员类型
				bill.setAccount(bill.getAccount());
			}else {
				currentAccountTextView.setText("现金");               //如果之前是转账类型账单，就显示默认的现金
				currentMemberTextView.setText("自己");                //如果之前是转账类型账单，就显示默认的自己
				bill.setAccount("现金");
				bill.setMember("自己");
			}
			baoxiaoCb.setChecked(false);
			bill.setCanBaoXiao(false);
			break;
		case 2:
			shouruBtn
					.setBackgroundResource(R.drawable.exit_demo_mode_btn_pressed);
			shouruBtn.setTextColor(Color.WHITE);
			zhichuBtn.setClickable(false);
			zhuanzhangBtn.setClickable(false);
			viewFlipper.setDisplayedChild(1);
			inCatagoryTextView.setText("工资");
			bill.setInCatagory("工资");
			currentAccountTextView = (TextView) viewFlipper.getCurrentView()
					.findViewById(R.id.zhanghu);
			currentMemberTextView = (TextView) viewFlipper.getCurrentView()
					.findViewById(R.id.chengyuan);

			if(bill.getBillType() == 1) {
				//如果之前的类型是支出，就将账户类型与成员类型设为之前支出类型的相关属性
				currentAccountTextView.setText(bill.getAccount());
				currentMemberTextView.setText(bill.getMember());
			}else {
				currentAccountTextView.setText("现金");
				currentMemberTextView.setText("自己");
				bill.setAccount("现金");
				bill.setMember("自己");
			}

			break;
		case 3:
			zhuanzhangBtn
					.setBackgroundResource(R.drawable.exit_demo_mode_btn_pressed);
			zhuanzhangBtn.setTextColor(Color.WHITE);
			zhichuBtn.setClickable(false);
			shouruBtn.setClickable(false);
			viewFlipper.setDisplayedChild(2);
			transferInTextView.setText("现金");
			transferOutTextView.setText("储蓄");
			bill.setTransferIn("现金");
			bill.setTransferOut("储蓄");
			break;
		}

		currentJinETextView = (TextView) viewFlipper.getCurrentView()
				.findViewById(R.id.jine);
		currentJinETextView.setText(bill.getJine());
		currentDateTextView = (TextView) viewFlipper.getCurrentView()
				.findViewById(R.id.date);
		currentDateTextView.setText(bill.getDate());
		currentBeizhuTextView = (TextView) viewFlipper.getCurrentView()
				.findViewById(R.id.beizhu);
		currentBeizhuTextView.setText(bill.getBeizhu());
	}
	
	private void initViewsByTemplate(int billType) {
		switch (billType) {
		case 1:
			zhichuBtn
					.setBackgroundResource(R.drawable.exit_demo_mode_btn_pressed);
			zhichuBtn.setTextColor(Color.WHITE);
			shouruBtn.setClickable(false);
			zhuanzhangBtn.setClickable(false);
			viewFlipper.setDisplayedChild(0);
			outCatagoryTextView.setText(bill.getOutCatagory());
			currentAccountTextView = (TextView) viewFlipper.getCurrentView()
					.findViewById(R.id.zhanghu);
			currentMemberTextView = (TextView) viewFlipper.getCurrentView()
					.findViewById(R.id.chengyuan);
			
			
			currentAccountTextView.setText(bill.getAccount());
			currentMemberTextView.setText(bill.getMember());
			
			baoxiaoCb.setChecked(bill.isCanBaoXiao());
			break;
		case 2:
			shouruBtn
					.setBackgroundResource(R.drawable.exit_demo_mode_btn_pressed);
			shouruBtn.setTextColor(Color.WHITE);
			zhichuBtn.setClickable(false);
			zhuanzhangBtn.setClickable(false);
			viewFlipper.setDisplayedChild(1);
			inCatagoryTextView.setText(bill.getInCatagory());
			currentAccountTextView = (TextView) viewFlipper.getCurrentView()
					.findViewById(R.id.zhanghu);
			currentMemberTextView = (TextView) viewFlipper.getCurrentView()
					.findViewById(R.id.chengyuan);

			currentAccountTextView.setText(bill.getAccount());
			currentMemberTextView.setText(bill.getMember());
			
			baoxiaoCb.setChecked(bill.isCanBaoXiao());
			break;
		case 3:
			zhuanzhangBtn
					.setBackgroundResource(R.drawable.exit_demo_mode_btn_pressed);
			zhuanzhangBtn.setTextColor(Color.WHITE);
			zhichuBtn.setClickable(false);
			shouruBtn.setClickable(false);
			viewFlipper.setDisplayedChild(2);
			transferInTextView.setText(bill.getTransferIn());
			transferOutTextView.setText(bill.getTransferOut());
			break;
		}

		currentJinETextView = (TextView) viewFlipper.getCurrentView()
				.findViewById(R.id.jine);
		currentJinETextView.setText("0");
		currentDateTextView = (TextView) viewFlipper.getCurrentView()
				.findViewById(R.id.date);
		currentDateTextView.setText(bill.getDate());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 99) {
			// 有BillCatagorySettingActivity返回
			parentId = data.getIntExtra("parentId", 0);
			catagoryChildId = data.getIntExtra("catagoryid", 0);
			outCatagorySelectedId = data.getIntExtra("catagorySelectedId", 0);
			outCatagorySelectedChildId = data.getIntExtra(
					"catagorySelectedChildId", 0);
			catagoryStr = dbService.getCatagoryStr(catagoryChildId);
			int catagoryChildId = data.getIntExtra("catagoryChildId", 0);
			int catagoryParentId = data.getIntExtra("catagoryParentId", 0);
			Log.i("a", "catagoryChildId = " + catagoryChildId + " catagoryParentId = " + catagoryParentId);
			bill.setOutCatagoryChildId(catagoryChildId);
			bill.setOutCatagoryParentId(catagoryParentId);
			// Log.i("a",
			// "BillInputActivity onActivityResult catagorySelectedId = " +
			// catagorySelectedId + " catagorySelectedChildId = " +
			// catagorySelectedChildId);
			bill.setOutCatagory(catagoryStr);
			outCatagoryTextView.setText(catagoryStr);
		} else if (resultCode == 98) {
			// 由BillAccountSettingActivity返回
			String accountFlag = data.getStringExtra("accountFlag");
			accountStr = data.getStringExtra("accountStr");
			Log.i("a", "resultCode .....");
			int accountid = data.getIntExtra("accountid", 0);
			if (accountFlag.equals(ConstantUtil.ACCOUNT_COMMON)) {
				accountGroupSelectedIndex = data.getIntExtra(
						"currentGroupSelectedIndex", 0);
				accountChildSelectedIndex = data.getIntExtra(
						"currentChildSelectedIndex", 0);
				bill.setAccount(accountStr);
				bill.setAccountid(accountid);
//				currentAccountTextView = (TextView) viewFlipper
//						.getCurrentView().findViewById(R.id.zhanghu);
//				currentAccountTextView.setText(accountStr);
			} else if (accountFlag.equals(ConstantUtil.ACCOUNT_TRANSFER_OUT)) {
				transferOutGroupSelectedIndex = data.getIntExtra(
						"currentGroupSelectedIndex", 0);
				transferOutChildSelectedIndex = data.getIntExtra(
						"currentChildSelectedIndex", 0);
				bill.setTransferOut(accountStr);
				bill.setTransferOutAccountId(accountid);
//				transferOutTextView.setText(accountStr);
			} else if (accountFlag.equals(ConstantUtil.ACCOUNT_TRANSFER_IN)) {
				transferInGroupSelectedIndex = data.getIntExtra(
						"currentGroupSelectedIndex", 0);
				transferInChildSelectedIndex = data.getIntExtra(
						"currentChildSelectedIndex", 0);
				bill.setTransferIn(accountStr);
				bill.setTransferInAccountId(accountid);
//				transferInTextView.setText(accountStr);
			}
			
			if(viewFlipper.getDisplayedChild() != 2) {
				refreshAccountTextView();
			}else {
				refreshTransferAccountTextView();
			}
			// accountGroupSelectedIndex =
			// data.getIntExtra("currentGroupSelectedIndex", 0);
			// accountChildSelectedIndex =
			// data.getIntExtra("currentChildSelectedIndex", 0);
			// accountStr = data.getStringExtra("accountStr");
			// bill.setAccount(accountStr);
			// currentAccountTextView = (TextView)
			// viewFlipper.getCurrentView().findViewById(R.id.zhanghu);
			// currentAccountTextView.setText(accountStr);
		} else if (ConstantUtil.SELECT_MEMEBER_FINISHED == resultCode) {
			// 由BillMemberSelect返回
			memberSelectedIndex = data.getIntExtra("currentSelectedIndex", 0);
			memberStr = data.getStringExtra("member");
			bill.setMember(memberStr);
			currentMemberTextView = (TextView) viewFlipper.getCurrentView()
					.findViewById(R.id.chengyuan);
			currentMemberTextView.setText(memberStr);
		} else if (ConstantUtil.SELECTED_DATE_FINISHED == resultCode) {
			// 选择日期完成
			String dateStr = data.getStringExtra("date");
			String dateYMD = data.getStringExtra("dateYMD");
			// Log.i("a", "dateStr = " + dateStr + " dateYMD = " + dateYMD);
			bill.setDate(dateStr);
			bill.setDateYMD(dateYMD);
			currentDateTextView = (TextView) viewFlipper.getCurrentView()
					.findViewById(R.id.date);
			currentDateTextView.setText(dateStr);
		} else if (ConstantUtil.EDIT_BEIZHU == resultCode) {
			String beizhuStr = data.getStringExtra("content");
			bill.setBeizhu(beizhuStr);
			currentBeizhuTextView = (TextView) viewFlipper.getCurrentView()
					.findViewById(R.id.beizhu);
			currentBeizhuTextView.setText(beizhuStr);
		} else if (ConstantUtil.SELECTED_INCATAGORY_FINISHED == resultCode) {
			String inCatagoryName = data.getStringExtra("catagoryname");
			inCatagorySelectedIndex = data.getIntExtra("currentSelectedIndex",
					0);
			bill.setInCatagory(inCatagoryName);
			inCatagoryTextView.setText(inCatagoryName);
		}
	}

	private void findViews() {
		zhichuBtn = (TextView) this.findViewById(R.id.zhichu);
		shouruBtn = (TextView) this.findViewById(R.id.shouru);
		zhuanzhangBtn = (TextView) this.findViewById(R.id.zhuanzhang);
		baoxiaoCb = (CheckBox) this.findViewById(R.id.baoxiaocb);
		viewFlipper = (ViewFlipper) this.findViewById(R.id.viewFlipper);
		nextLayout = (LinearLayout) this.findViewById(R.id.nextLayout);    //再记按钮
		childView1 = viewFlipper.getChildAt(0);
		childView2 = viewFlipper.getChildAt(1);
		childView3 = viewFlipper.getChildAt(2);
		zhichuJine = (TextView) childView1.findViewById(R.id.jine);
		shouruJine = (TextView) childView2.findViewById(R.id.jine);
		zhuanzhangJine = (TextView) childView3.findViewById(R.id.jine);
		currentBeizhuTextView = (TextView) viewFlipper.getCurrentView()
				.findViewById(R.id.beizhu);
		outCatagoryTextView = (TextView) childView1.findViewById(R.id.leixing);
		inCatagoryTextView = (TextView) childView2.findViewById(R.id.leixing);
		inCatagoryTextView.setText(bill.getInCatagory());
		outCatagoryTextView.setText(bill.getOutCatagory());
		transferInTextView = (TextView) childView3.findViewById(R.id.zhuanru);
		transferOutTextView = (TextView) childView3.findViewById(R.id.zhuanchu);

		popWinParentView = this.findViewById(R.id.popWinParent);
	}

	private void initViews() {
		zhichuBtn.setBackgroundResource(R.drawable.exit_demo_mode_btn_pressed);
		zhichuBtn.setTextColor(Color.WHITE);
		currentDateTextView = (TextView) viewFlipper.getCurrentView()
				.findViewById(R.id.date);
		currentDateTextView.setText(bill.getDate());

	}

	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.zhichu:
			// 支出按钮
			changeBtnBack(0);
			break;
		case R.id.shouru:
			// 收入按钮
			changeBtnBack(1);
			break;
		case R.id.zhuanzhang:
			// 转账按钮
			changeBtnBack(2);
			break;
		case R.id.jinelayout:
			// 金额
			showCalculator();
			break;
		case R.id.leixinglayout:
			// 点击转到类型设置界面
			if (viewFlipper.getDisplayedChild() == 1) {
				// 点击收入界面的类型
				intent = new Intent(BillInputActivity.this,
						BillInCatagorySettingActivity.class);
				intent.putExtra("currentSelectedIndex", inCatagorySelectedIndex);
				this.startActivityForResult(intent, 100);
				overridePendingTransition(R.anim.activity_up,
						R.anim.activity_steady);
			} else {
				// 点击支出界面的类型
				intent = new Intent(BillInputActivity.this,
						BillCatagorySettingActivity.class);
				intent.putExtra("parentId", parentId);
				intent.putExtra("catagorySelectedId", outCatagorySelectedId);
				intent.putExtra("catagorySelectedChildId",
						outCatagorySelectedChildId);
				this.startActivityForResult(intent, 100);
				overridePendingTransition(R.anim.activity_up,
						R.anim.activity_steady);
			}

			break;
		case R.id.zhanghulayout:
			intent = new Intent(BillInputActivity.this,
					BillAccountSettingActivity.class);
			intent.putExtra("currentGroupSelectedIndex",
					accountGroupSelectedIndex);
			intent.putExtra("currentChildSelectedIndex",
					accountChildSelectedIndex);
			intent.putExtra("accountFlag", ConstantUtil.ACCOUNT_COMMON);
			this.startActivityForResult(intent, 100);
			overridePendingTransition(R.anim.activity_up,
					R.anim.activity_steady);

			break;
		case R.id.riqilayout:
			intent = new Intent(BillInputActivity.this,
					BillDataWheelPickerActivity.class);
			startActivityForResult(intent, 100);
			break;
		case R.id.chengyuanlayout:
			intent = new Intent(BillInputActivity.this,
					BillMemberSelectActivity.class);
			intent.putExtra("currentSelectedIndex", memberSelectedIndex);
			startActivityForResult(intent, 100);
			overridePendingTransition(R.anim.activity_up,
					R.anim.activity_steady);
			break;
		case R.id.beizhulayout:
			intent = new Intent(BillInputActivity.this,
					BillTextInputActivity.class);
			intent.putExtra("title", "账单备注");
			intent.putExtra("fenlei", ConstantUtil.EDIT_BEIZHU);
			intent.putExtra("editNum", 100);
			intent.putExtra("content", currentBeizhuTextView.getText()
					.toString());
			startActivityForResult(intent, 100);
			break;
		case R.id.baoxiaolayout:
			if (baoxiaoCb.isChecked()) {
				baoxiaoCb.setChecked(false);
			} else {
				baoxiaoCb.setChecked(true);
			}
			break;

		case R.id.zhuanchulayout:

			intent = new Intent(BillInputActivity.this,
					BillAccountSettingActivity.class);
			intent.putExtra("currentGroupSelectedIndex",
					transferOutGroupSelectedIndex);
			intent.putExtra("currentChildSelectedIndex",
					transferOutChildSelectedIndex);
			intent.putExtra("accountFlag", ConstantUtil.ACCOUNT_TRANSFER_OUT);
			this.startActivityForResult(intent, 100);
			overridePendingTransition(R.anim.activity_up,
					R.anim.activity_steady);

			break;
		case R.id.zhuanrulayout:

			intent = new Intent(BillInputActivity.this,
					BillAccountSettingActivity.class);
			intent.putExtra("currentGroupSelectedIndex",
					transferInGroupSelectedIndex);
			intent.putExtra("currentChildSelectedIndex",
					transferInChildSelectedIndex);
			intent.putExtra("accountFlag", ConstantUtil.ACCOUNT_TRANSFER_IN);
			this.startActivityForResult(intent, 100);
			overridePendingTransition(R.anim.activity_up,
					R.anim.activity_steady);

			break;
		case R.id.back:
			back();
			break;
		case R.id.save:

			clickSaveBtn(2);


			break;
		case R.id.savetemplate:
			intent = new Intent(BillInputActivity.this, BillTemplateNameInputDialogActivity.class);
			
			template = new BillTemplate();
			template.setAccountid(bill.getAccountid());
			template.setCanBaoXiao(baoxiaoCb.isChecked());
			template.setInCatagoryName(bill.getInCatagory());
			template.setOutCatagoryName(bill.getOutCatagory());
			template.setOutCatagoryChildId(bill.getOutCatagoryChildId());
			template.setOutCatagoryParentId(bill.getOutCatagoryParentId());
			template.setMember(bill.getMember());
			template.setTransferInAccountId(bill.getTransferInAccountId());
			template.setTransferOutAccountId(bill.getTransferOutAccountId());
			intent.putExtra("template", template);
			
			switch (viewFlipper.getDisplayedChild()) {
			case 0:
				template.setBillType(1);
				break;
			case 1:
				template.setBillType(2);
				break;
			case 2:
				template.setBillType(3);
				break;
			}
			
			
			
			startActivity(intent);
			break;
		case R.id.next:
			
			
			
			clickSaveBtn(1);
			
			
			
			break;
		}
	}
	
	private void clickSaveBtn(int clickNextBtn) {
		if ("add".equals(flag)) {
			currentJinETextView = (TextView) viewFlipper.getCurrentView()
					.findViewById(R.id.jine);
			if (currentJinETextView.getText().toString() == null
					|| "".equals(currentJinETextView.getText().toString())
					|| "0".equals(currentJinETextView.getText().toString())) {
				Toast.makeText(BillInputActivity.this, "请输入金额!", 0).show();
			} else {
				
				
				
				currentBeizhuTextView = (TextView) viewFlipper
						.getCurrentView().findViewById(R.id.beizhu);
				bill.setBeizhu(currentBeizhuTextView.getText().toString());
				bill.setJine(currentJinETextView.getText().toString());
				bill.setBaoxiaoed(false);
				if (baoxiaoCb.isChecked()) {
					bill.setCanBaoXiao(true);
				} else {
					bill.setCanBaoXiao(false);
				}

				switch (viewFlipper.getDisplayedChild()) {
				case 0:
					// 支出
					bill.setBillType(1);
					if(bill.getAccountid() > 0) {
						showNoticeDialog(bill);
						billService.addOutBill(bill);
						saveOrUpdateOk(clickNextBtn);
					}else {
						Toast.makeText(BillInputActivity.this, "无账户类型，请先添加账户!", 0).show();
						return;
					}
					break;
				case 1:
					// 收入
					bill.setBillType(2);
					if(bill.getAccountid() > 0) {
						showNoticeDialog(bill);
						billService.addInBill(bill);
						saveOrUpdateOk(clickNextBtn);
					}else {
						Toast.makeText(BillInputActivity.this, "无账户类型，请先添加账户!", 0).show();
						return;
					}
					break;
				case 2:
					// 转账
					bill.setBillType(3);
					if(bill.getTransferInAccountId() > 0 && bill.getTransferOutAccountId() > 0) {
						showNoticeDialog(bill);
						billService.addTransferBill(bill);
						saveOrUpdateOk(clickNextBtn);
					}else {
						Toast.makeText(BillInputActivity.this, "无账户类型，请先添加账户!", 0).show();
						return;
					}
					break;
				}
			}

		} else if ("view".equals(flag)) {
			if (currentJinETextView.getText().toString() == null
					|| "".equals(currentJinETextView.getText().toString())
					|| "0".equals(currentJinETextView.getText().toString())) {
				Toast.makeText(BillInputActivity.this, "请输入金额!", 0).show();
			} else {
				
				bill.setJine(currentJinETextView.getText().toString());
				bill.setBeizhu(currentBeizhuTextView.getText().toString());
				

				switch (bill.getBillType()) {
				case 1:
					if (baoxiaoCb.isChecked()) {
						bill.setCanBaoXiao(true);
					} else {
						bill.setCanBaoXiao(false);
					}
					if(bill.getAccountid() > 0) {
						showNoticeDialog(bill);
						billService.updateOutBill(bill);
						saveOrUpdateOk(clickNextBtn);
					}else {
						Toast.makeText(BillInputActivity.this, "无账户类型，请先添加账户!", 0).show();
						return;
					}
					
					break;
				case 2:
					if(bill.getAccountid() > 0) {
						showNoticeDialog(bill);
						billService.updateInBill(bill);
						saveOrUpdateOk(clickNextBtn);
					}else {
						Toast.makeText(BillInputActivity.this, "无账户类型，请先添加账户!", 0).show();
					}
					
					break;
				case 3:
					if(bill.getTransferInAccountId() > 0 && bill.getTransferOutAccountId() > 0) {
						showNoticeDialog(bill);
						billService.updateTransferBill(bill);
						saveOrUpdateOk(clickNextBtn);
					}else {
						Toast.makeText(BillInputActivity.this, "无账户类型，请先添加账户!", 0).show();
						return;
					}
					
					break;
				}
			}
		}else if ("viewchange".equals(flag)) {
			if (currentJinETextView.getText().toString() == null
					|| "".equals(currentJinETextView.getText().toString())
					|| "0".equals(currentJinETextView.getText().toString())) {
				Toast.makeText(BillInputActivity.this, "请输入金额!", 0).show();
			} else {
				
				bill.setJine(currentJinETextView.getText().toString());
				bill.setBeizhu(currentBeizhuTextView.getText().toString());
				switch (bill.getBillType()) {
				case 1:
					if (baoxiaoCb.isChecked()) {
						bill.setCanBaoXiao(true);
					} else {
						bill.setCanBaoXiao(false);
					}
					if(bill.getAccountid() > 0) {
						showNoticeDialog(bill);
						billService.updateOutBill(bill);
						saveOrUpdateOk(clickNextBtn);
					}else {
						Toast.makeText(BillInputActivity.this, "无账户类型，请先添加账户!", 0).show();
						return;
					}
					break;
				case 2:
					if(bill.getAccountid() > 0) {
						showNoticeDialog(bill);
						billService.updateInBill(bill);
						saveOrUpdateOk(clickNextBtn);
					}else {
						Toast.makeText(BillInputActivity.this, "无账户类型，请先添加账户!", 0).show();
						return;
					}
					break;
				case 3:
					if(bill.getTransferInAccountId() > 0 && bill.getTransferOutAccountId() > 0) {
						showNoticeDialog(bill);
						billService.updateTransferBill(bill);
						saveOrUpdateOk(clickNextBtn);
					}else {
						Toast.makeText(BillInputActivity.this, "无账户类型，请先添加账户!", 0).show();
						return;
					}
					break;
					
				}
			}
		}else if("template".equals(flag)) {
			if (currentJinETextView.getText().toString() == null
					|| "".equals(currentJinETextView.getText().toString())
					|| "0".equals(currentJinETextView.getText().toString())) {
				Toast.makeText(BillInputActivity.this, "请输入金额!", 0).show();
			} else {
				
				currentBeizhuTextView = (TextView) viewFlipper
						.getCurrentView().findViewById(R.id.beizhu);
				bill.setBeizhu(currentBeizhuTextView.getText().toString());
				bill.setJine(currentJinETextView.getText().toString());
				bill.setBaoxiaoed(false);
				if (baoxiaoCb.isChecked()) {
					bill.setCanBaoXiao(true);
				} else {
					bill.setCanBaoXiao(false);
				}

				switch (viewFlipper.getDisplayedChild()) {
				case 0:
					// 支出
					bill.setBillType(1);
					if(bill.getAccountid() > 0) {
						showNoticeDialog(bill);
						billService.addOutBill(bill);
						saveOrUpdateOk(clickNextBtn);
					}else {
						Toast.makeText(BillInputActivity.this, "无账户类型，请先添加账户!", 0).show();
						return;
					}
					break;
				case 1:
					// 收入
					bill.setBillType(2);
					if(bill.getAccountid() > 0) {
						showNoticeDialog(bill);
						billService.addInBill(bill);
						saveOrUpdateOk(clickNextBtn);
					}else {
						Toast.makeText(BillInputActivity.this, "无账户类型，请先添加账户!", 0).show();
						return;
					}
					break;
				case 2:
					// 转账
					bill.setBillType(3);
					if(bill.getTransferInAccountId() > 0 && bill.getTransferOutAccountId() > 0) {
						showNoticeDialog(bill);
						billService.addTransferBill(bill);
						saveOrUpdateOk(clickNextBtn);
					}else {
						Toast.makeText(BillInputActivity.this, "无账户类型，请先添加账户!", 0).show();
						return;
					}
					break;
				}
				
			}
		}

		// Log.i("a", bill.toString());

	}
	
	
	private void showNoticeDialog(Bill bill) {
		
		String noticeContent = accountService.ifShowNotice(bill);
//		Log.i("a", "dialog content = " + noticeContent);
		if(noticeContent != null && noticeContent.length() > 0) {
			intent = new Intent(BillInputActivity.this, BillAccountNoticeDialogActivity.class);
			BillAccountItem bai = accountService.findItemDetailById(bill.getAccountid());
			String accountName = bai.getName();
			String accountYue = bai.getNoticeValue() + "";
			intent.putExtra("accountName", accountName);
			intent.putExtra("accountYue", accountYue);
			intent.putExtra("accountId", bai.getIdx());
			intent.putExtra("content", noticeContent);
			startActivity(intent);
		}
		
	}
	
	private void saveOrUpdateOk(int clickSaveNext) {
		if(clickSaveNext == 1) {
			//点击了再存一条
			Toast.makeText(BillInputActivity.this, "账单信息保存成功!", 0).show();
			intent = new Intent(BillInputActivity.this, BillInputActivity.class);
			intent.putExtra("flag", "add");
			startActivity(intent);
			BillInputActivity.this.finish();
			overridePendingTransition(R.anim.activity_steady,
					R.anim.activity_down);
		}else {
			Toast.makeText(BillInputActivity.this, "账单信息保存成功!", 0).show();
			BillInputActivity.this.finish();
			overridePendingTransition(R.anim.activity_steady,
					R.anim.activity_down);
		}
		
	}

	private void changeBtnBack(int index) {
		switch (index) {
		case 0:
			// 支出
			zhichuBtn
					.setBackgroundResource(R.drawable.exit_demo_mode_btn_pressed);
			zhichuBtn.setTextColor(Color.WHITE);
			shouruBtn
					.setBackgroundResource(R.drawable.exit_demo_mode_btn_normal);
			shouruBtn.setTextColor(Color.BLACK);
			zhuanzhangBtn
					.setBackgroundResource(R.drawable.exit_demo_mode_btn_normal);
			zhuanzhangBtn.setTextColor(Color.BLACK);

			if (viewFlipper.getDisplayedChild() != 0) {
				if (viewFlipper.getDisplayedChild() == 1) {
					viewFlipper.showPrevious();
				} else if (viewFlipper.getDisplayedChild() == 2) {
					viewFlipper.showPrevious();
					viewFlipper.showPrevious();
				}
			}

			break;
		case 1:
			// 收入
			shouruBtn
					.setBackgroundResource(R.drawable.exit_demo_mode_btn_pressed);
			shouruBtn.setTextColor(Color.WHITE);
			zhuanzhangBtn
					.setBackgroundResource(R.drawable.exit_demo_mode_btn_normal);
			zhuanzhangBtn.setTextColor(Color.BLACK);
			zhichuBtn
					.setBackgroundResource(R.drawable.exit_demo_mode_btn_normal);
			zhichuBtn.setTextColor(Color.BLACK);

			if (viewFlipper.getDisplayedChild() != 1) {
				if (viewFlipper.getDisplayedChild() == 0) {
					viewFlipper.showNext();
				} else if (viewFlipper.getDisplayedChild() == 2) {
					viewFlipper.showPrevious();
				}
			}

			break;
		case 2:
			// 转账
			zhuanzhangBtn
					.setBackgroundResource(R.drawable.exit_demo_mode_btn_pressed);
			zhuanzhangBtn.setTextColor(Color.WHITE);
			zhichuBtn
					.setBackgroundResource(R.drawable.exit_demo_mode_btn_normal);
			zhichuBtn.setTextColor(Color.BLACK);
			shouruBtn
					.setBackgroundResource(R.drawable.exit_demo_mode_btn_normal);
			shouruBtn.setTextColor(Color.BLACK);

			if (viewFlipper.getDisplayedChild() != 2) {
				if (viewFlipper.getDisplayedChild() == 0) {
					viewFlipper.showNext();
					viewFlipper.showNext();
				} else if (viewFlipper.getDisplayedChild() == 1) {
					viewFlipper.showNext();
				}
			}
			break;
		}
		refreshJinETextView();
		refreshCatagoryTextView();
		refreshDateTextView();
		refreshBeizhuTextView();
		if (viewFlipper.getDisplayedChild() != 2) {
			refreshMemberTextView();
			refreshAccountTextView();
		}else {
			refreshTransferAccountTextView();
		}
	}

	LinearLayout cal_equal;
	LinearLayout cal_del;
	ImageView cal_sure;

	// 初始化计算器 popwindow
	private void initCalculator() {
		View contentView = inflater.inflate(R.layout.popwindow_bill_counter,
				null);
		calculator = new PopupWindow(contentView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		TextView cal_add = (TextView) contentView.findViewById(R.id.cal_add);
		TextView cal_minus = (TextView) contentView
				.findViewById(R.id.cal_minus);
		TextView cal_multiply = (TextView) contentView
				.findViewById(R.id.cal_multiply);
		TextView cal_divide = (TextView) contentView
				.findViewById(R.id.cal_divide);
		TextView cal_one = (TextView) contentView.findViewById(R.id.cal_one);
		TextView cal_two = (TextView) contentView.findViewById(R.id.cal_two);
		TextView cal_three = (TextView) contentView
				.findViewById(R.id.cal_three);
		TextView cal_four = (TextView) contentView.findViewById(R.id.cal_four);
		TextView cal_five = (TextView) contentView.findViewById(R.id.cal_five);
		TextView cal_six = (TextView) contentView.findViewById(R.id.cal_six);
		TextView cal_seven = (TextView) contentView
				.findViewById(R.id.cal_seven);
		TextView cal_eight = (TextView) contentView
				.findViewById(R.id.cal_eight);
		TextView cal_nine = (TextView) contentView.findViewById(R.id.cal_nine);
		TextView cal_zero = (TextView) contentView.findViewById(R.id.cal_zero);
		TextView cal_dot = (TextView) contentView.findViewById(R.id.cal_dot);
		cal_equal = (LinearLayout) contentView.findViewById(R.id.cal_equal);
		cal_del = (LinearLayout) contentView.findViewById(R.id.cal_del);
		cal_sure = (ImageView) contentView.findViewById(R.id.cal_sure);
		cal_add.setOnClickListener(new CalculatorBtnClickListener());
		cal_minus.setOnClickListener(new CalculatorBtnClickListener());
		cal_multiply.setOnClickListener(new CalculatorBtnClickListener());
		cal_divide.setOnClickListener(new CalculatorBtnClickListener());
		cal_one.setOnClickListener(new CalculatorBtnClickListener());
		cal_two.setOnClickListener(new CalculatorBtnClickListener());
		cal_three.setOnClickListener(new CalculatorBtnClickListener());
		cal_four.setOnClickListener(new CalculatorBtnClickListener());
		cal_five.setOnClickListener(new CalculatorBtnClickListener());
		cal_six.setOnClickListener(new CalculatorBtnClickListener());
		cal_seven.setOnClickListener(new CalculatorBtnClickListener());
		cal_eight.setOnClickListener(new CalculatorBtnClickListener());
		cal_nine.setOnClickListener(new CalculatorBtnClickListener());
		cal_zero.setOnClickListener(new CalculatorBtnClickListener());
		cal_dot.setOnClickListener(new CalculatorBtnClickListener());
		cal_del.setOnClickListener(new CalculatorBtnClickListener());
		cal_sure.setOnClickListener(new CalculatorBtnClickListener());
		cal_equal.setOnClickListener(new CalculatorBtnClickListener());
		calculator.setFocusable(true);
		calculator.setBackgroundDrawable(new ColorDrawable());
		calculator.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				if (!isEqualBtnClick) {
					BillInputActivity.this.isClickFlag = false; // 当每次关闭计算器时，让是否点击符号复位
					BillInputActivity.this.isFloat = false; // 当每次关闭计算器时，让是否是浮点数复位
					jineTv = (TextView) viewFlipper.getCurrentView()
							.findViewById(R.id.jine);
					jineTv.setText(temp1Str);
					cal_equal.setVisibility(ViewGroup.GONE);
					cal_sure.setVisibility(ViewGroup.VISIBLE);
				}
			}
		});
	}

	private void showCalculator() {
		calculator.showAtLocation(popWinParentView, Gravity.BOTTOM, 0, 0);
	}

	private class CalculatorBtnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
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
				if (calculator.isShowing()) {
					calculator.dismiss();
				}
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
			}
		}
	}

	private void refreshJinETextView() {
		if (bill.getJine() == null || "".equals(bill.getJine())) {
			jie_txt = "0";
		} else {
			jie_txt = bill.getJine();
		}
		zhichuJine.setText(jie_txt);
		shouruJine.setText(jie_txt);
		zhuanzhangJine.setText(jie_txt);
	}

	private void refreshCatagoryTextView() {
		outCatagoryTextView.setText(bill.getOutCatagory());
		inCatagoryTextView.setText(bill.getInCatagory());
	}

	private void refreshAccountTextView() {
		currentAccountTextView = (TextView) viewFlipper.getCurrentView()
				.findViewById(R.id.zhanghu);
		currentAccountTextView.setText(bill.getAccount());
	}

	private void refreshTransferAccountTextView() {
		transferInTextView = (TextView) viewFlipper.getCurrentView().findViewById(R.id.zhuanru);
		transferInTextView.setText(bill.getTransferIn());
		transferOutTextView = (TextView) viewFlipper.getCurrentView().findViewById(R.id.zhuanchu);
		transferOutTextView.setText(bill.getTransferOut());
	}
	
	private void refreshMemberTextView() {
		currentMemberTextView = (TextView) viewFlipper.getCurrentView()
				.findViewById(R.id.chengyuan);
		currentMemberTextView.setText(bill.getMember());
	}

	private void refreshDateTextView() {
		currentDateTextView = (TextView) viewFlipper.getCurrentView()
				.findViewById(R.id.date);
		currentDateTextView.setText(bill.getDate());
	}

	private void refreshBeizhuTextView() {
		currentBeizhuTextView = (TextView) viewFlipper.getCurrentView()
				.findViewById(R.id.beizhu);
		currentBeizhuTextView.setText(bill.getBeizhu());
	}

	// 点击了计算器上的数字键
	private void onClickNum(int numStr) {
		jineTv = (TextView) viewFlipper.getCurrentView()
				.findViewById(R.id.jine);
		String jie_str = jineTv.getText().toString();
		Float jie_float = 0f;
		Long jie_long = 0l;
		if (jie_str.contains(".")) {
			// 当前值是浮点型
			if (jie_str.substring(jie_str.indexOf("."), jie_str.length())
					.length() < 3) {
				// 保存2位小数位
				jie_float = Float.parseFloat(jie_str);
				jie_txt = jie_float + "" + numStr;
			}
		} else {
			// 当前值是整数
			jie_long = Long.parseLong(jie_str);
			if (isFloat) {
				// 点击了小数点
				if (jie_long == 0) {
					jie_txt = "0." + numStr;
				} else {
					jie_txt = jie_str + "." + numStr;
				}
			} else {
				// 未点击小数点
				if (jie_str.length() < 7) {
					// 整数位保留到百万
					if (jie_long == 0) {
						jie_txt = "" + numStr;
					} else {
						jie_txt = jineTv.getText().toString() + numStr;
					}
				}
			}
		}
		jineTv.setText(jie_txt);
		bill.setJine(jie_txt);
	}

	// 点击了计算器上的小数点
	private void onClickDot() {
		isFloat = true;
	}

	// 点击了计算器上的删除
	private void onClickDel() {
		jineTv = (TextView) viewFlipper.getCurrentView()
				.findViewById(R.id.jine);
		String jie_str = jineTv.getText().toString();
		// Log.i("a", "jine length = " + jie_str.length());
		if (jie_str.length() > 1) {
			jie_txt = jie_str.substring(0, jie_str.length() - 1); // 去掉最后一个字符
			if (jie_txt.endsWith(".")) {
				// 如果以小数点结尾
				jie_txt = jie_txt.substring(0, jie_txt.length() - 1);
				isFloat = false;
			}
			
			if("-".equals(jie_txt)) {
				jie_txt = "0";
			}
			
		} else {
			jie_txt = "0";
		}
		jineTv.setText(jie_txt);
		bill.setJine(jie_txt);
	}

	// 点击了计算器上的加减乘除
	private void onClickFlag(int flagId) {
		cal_sure.setVisibility(ViewGroup.GONE);
		cal_equal.setVisibility(ViewGroup.VISIBLE);

		if (!isClickFlag) {
			jineTv = (TextView) viewFlipper.getCurrentView().findViewById(
					R.id.jine);
			temp1Str = jineTv.getText().toString();
			jineTv.setText("0");
			isClickFlag = true; // 点击了加减乘除按钮
		}
		this.flagId = flagId;
		this.isFloat = false; // 点击符号按钮后将是否是浮点数标记为复位，不然再点击数字时会是小数
	}

	// 点击了计算器上的等号
	private void onClickEqual() {
		cal_sure.setVisibility(ViewGroup.VISIBLE);
		cal_equal.setVisibility(ViewGroup.GONE);

		jineTv = (TextView) viewFlipper.getCurrentView()
				.findViewById(R.id.jine);
		String jie_str = jineTv.getText().toString();
		if (jie_str.length() > 0 && !"0".equals(jie_str)) {
			// 输入了数字，并且不是0
			DecimalFormat df = new DecimalFormat("0.00");
			temp2Str = jie_str;
			String resultStr;
			Float temp1Float = 0f;
			Float temp2Float = 0f;
			Float resultFloat = 0f;
			switch (this.flagId) {
			case 0:
				// 加
				temp1Float = Float.parseFloat(temp1Str);
				temp2Float = Float.parseFloat(temp2Str);
				resultFloat = temp1Float + temp2Float;
				resultStr = resultStrDeleteZero(df.format(resultFloat));
				jineTv.setText(resultStr);
				// Log.i("a", "temp1Float = " + temp1Float + "   temp2Float = "
				// + temp2Float + "   result = " + resultFloat);
				bill.setJine(resultStr);

				break;
			case 1:
				// 减
				temp1Float = Float.parseFloat(temp1Str);
				temp2Float = Float.parseFloat(temp2Str);
				resultFloat = temp1Float - temp2Float;
				resultStr = resultStrDeleteZero(df.format(resultFloat));
				jineTv.setText(resultStr);
				// Log.i("a", "temp1Float = " + temp1Float + "   temp2Float = "
				// + temp2Float + "   result = " + resultFloat);
				bill.setJine(resultStr);
				break;
			case 2:
				// 乘
				temp1Float = Float.parseFloat(temp1Str);
				temp2Float = Float.parseFloat(temp2Str);
				resultFloat = temp1Float * temp2Float;
				resultStr = resultStrDeleteZero(df.format(resultFloat));
				jineTv.setText(resultStr);
				// Log.i("a", "temp1Float = " + temp1Float + "   temp2Float = "
				// + temp2Float + "   result = " + resultFloat);
				bill.setJine(resultStr);
				break;
			case 3:
				// 除
				temp1Float = Float.parseFloat(temp1Str);
				temp2Float = Float.parseFloat(temp2Str);
				resultFloat = temp1Float / temp2Float;
				resultStr = resultStrDeleteZero(df.format(resultFloat));
				jineTv.setText(resultStr);
				// Log.i("a", "temp1Float = " + temp1Float + "   temp2Float = "
				// + temp2Float + "   result = " + resultFloat);
				bill.setJine(resultStr);
				break;
			}

		} else {
			jineTv.setText(temp1Str); // 如果点击完符号后在没有输入数字或者输入的数字为0，此时按等号时，jineTv的值就是之前缓存的值temp1Str
		}
		isClickFlag = false;
	}

	// 将最后的金额字符型结果去掉小数位的0，例如12.30， 去掉0后成12.3，再如12.00去零成为12
	private String resultStrDeleteZero(String resultStr) {
		String str = null;
		str = resultStr.substring(resultStr.lastIndexOf(".") + 1,
				resultStr.length());
		if (str.length() == 1) {
			if (str.equals("0")) {
				return resultStr.substring(0, resultStr.length() - 2);
			}
		} else {
			String a = str.substring(1, 2); // 01 中的1
			String b = str.substring(0, 1); // 01中的0
			if (a.equals("0")) {
				resultStr = resultStr.substring(0, resultStr.length() - 1);
				if (b.equals("0")) {
					return resultStr.substring(0, resultStr.length() - 2);
				}
			}
		}
		return resultStr;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbService.closeDB();
		billService.closeDB();
		dbService = null;
		billService = null;
	}

	private void back() {
		BillInputActivity.this.finish();
		overridePendingTransition(R.anim.activity_steady, R.anim.activity_down);
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
