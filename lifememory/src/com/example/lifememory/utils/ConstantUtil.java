package com.example.lifememory.utils;

import java.util.List;

import com.example.lifememory.R;

public class ConstantUtil {

	/**
	 * 用于记账模块中的startActivityForResult中的resultCode常量
	 */
	public final static int EDIT_NAME_FINISHED = 99; // 名称编辑完成
	public final static int EDIT_NOTICE_FINISHED = 98; // 警戒线编辑完成
	public final static int EDIT_BEIZHU = 97; // 备注编辑完成
	public final static int EDIT_BIZHONGFINISHED = 96; // 币种选择完成
	public final static int EDIT_CATAGORYNAMEFINISHED = 95; // 类别名称选择完成
	public final static int EDIT_NOTICEVALUEFINISHED = 94; // 警戒线金额设置完成
	public final static int EDIT_YUEFINISHED = 93; // 当月余额设置完成
	public final static int SELECT_MEMEBER_FINISHED = 92; //成员选择完成
	public final static int EDIT_MEMBER_FINISHED = 91;     //成员编辑完成
	public final static int SELECTED_DATE_FINISHED = 90;   //选择日期完成
	public final static int SELECTED_INCATAGORY_FINISHED = 89;  //选择收入类别完成
	public final static int EDIT_INCATAGORY_FINISHED = 88;      //编辑收入类别完成
	public final static int EDIT_BUDGET_FINISHED = 87;          //编辑预算值完成
	public final static int REIMBURSEMENT_VALUE_INPUT = 86;     //报销金额设置完成
	public final static int SELECT_ACCOUNT_FINISHED = 85;
	public final static int EDIT_NOCHANGE_BACK = 10; // 直接点击back不保存信息

	/**
	 * 因为BillInputActivity这个界面是由viewflipper构成的，里面有三个子视图，然后在这三个子视图中有三个地方需要用到同一个BillAccountSettingActivity，
	 * 用到的地方有zhanghulayout, zhuanchulayout, zhuanrulayout.所以用accountFlag作为参数传递过去，返回的时候在onactivityforresult中
	 * 根据accountFlag进行分别处理
	 * common   zhanghulayout
	 * transferOut    zhuanchulayout
	 * transferIn     zhuanrulayout
	 */
	public final static String ACCOUNT_COMMON = "COMMON";     
	public final static String ACCOUNT_TRANSFER_IN = "TRANSFERIN";
	public final static String ACCOUNT_TRANSFER_OUT = "TRANSFEROUT";		
	
	
	
	
	/**
	 * BillAccountBiZhongSettingActivity 记账账户币种
	 */
	public final static String[] BILL_ACCOUNT_BIZHONG_NAMES1 = { "人民币", "美元",
			"欧元", "澳大利亚元", "巴西雷亚尔", "加拿大元", "丹麦克朗", "瑞士法郎", "英镑", "港元", "日元",
			"韩元", "澳门元", "马来西亚林吉特", "新西兰元", "挪威克朗", "菲律宾比索", "新加坡元", "瑞典克朗",
			"新台币", "泰铢", "斯里兰卡卢比" };

	public final static String[] BILL_ACCOUNT_BIZHONG_NAMES2 = { "CNY", "USD",
			"EUR", "AUD", "BRL", "CAD", "DKK", "CHF", "GBP", "HKD", "JPY",
			"KRW", "MOP", "MYR", "NZD", "NOK", "PHP", "SGD", "SEK", "TWD",
			"THB", "LKR"

	};

	public final static int[] BILL_ACCOUNT_BIZHONG_IMAGEIDS = {
			R.drawable.currency_icon_cny, R.drawable.currency_icon_usd,
			R.drawable.currency_icon_eur, R.drawable.currency_icon_aud,
			R.drawable.currency_icon_brl, R.drawable.currency_icon_cad,
			R.drawable.currency_icon_dkk, R.drawable.currency_icon_chf,
			R.drawable.currency_icon_gbp, R.drawable.currency_icon_hkd,
			R.drawable.currency_icon_jpy, R.drawable.currency_icon_krw,
			R.drawable.currency_icon_mop, R.drawable.currency_icon_myr,
			R.drawable.currency_icon_nzd, R.drawable.currency_icon_nok,
			R.drawable.currency_icon_php, R.drawable.currency_icon_sgd,
			R.drawable.currency_icon_sek, R.drawable.currency_icon_twd,
			R.drawable.currency_icon_thb, R.drawable.currency_icon_lkr };
}
