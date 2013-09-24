package com.example.lifememory.utils;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillCatagoryItem;

public class BillCatagoryCreator {
	BillCatagoryItem item = null;

	public BillCatagoryCreator() {
	};

	public synchronized void initCatagoryDatas(SQLiteDatabase db) {
		List<BillCatagoryItem> items = new ArrayList<BillCatagoryItem>();
		item = new BillCatagoryItem("常用", R.drawable.changyong, 0);
		items.add(item);
		item = new BillCatagoryItem("餐饮", R.drawable.canyin, 0);
		items.add(item);
		item = new BillCatagoryItem("交通", R.drawable.jiaotong, 0);
		items.add(item);
		item = new BillCatagoryItem("购物", R.drawable.gouwu, 0);
		items.add(item);
		item = new BillCatagoryItem("娱乐", R.drawable.yule, 0);
		items.add(item);
		item = new BillCatagoryItem("医教", R.drawable.yijiao, 0);
		items.add(item);
		item = new BillCatagoryItem("居家", R.drawable.jujia, 0);
		items.add(item);
		item = new BillCatagoryItem("投资", R.drawable.touzi, 0);
		items.add(item);
		item = new BillCatagoryItem("人情", R.drawable.renqing, 0);
		items.add(item);

		for (BillCatagoryItem bcItem : items) {
			db.execSQL(
					"insert into bill_catagory (name, imageid, parentid) values (?, ?, ?)",
					new String[] { bcItem.getName(),
							String.valueOf(bcItem.getImageId()),
							String.valueOf(bcItem.getParentId()) });
		}

		// 初始化类别二级列表信息
		items = new ArrayList<BillCatagoryItem>();
		// 常用
		item = new BillCatagoryItem("打的", R.drawable.dache, 1);
		items.add(item);
		item = new BillCatagoryItem("早餐", R.drawable.zaocan, 1);
		items.add(item);
		item = new BillCatagoryItem("午餐", R.drawable.wucan, 1);
		items.add(item);
		item = new BillCatagoryItem("晚餐", R.drawable.wancan, 1);
		items.add(item);
		for (BillCatagoryItem bcItem : items) {
			db.execSQL(
					"insert into bill_catagory (name, imageid, parentid) values (?, ?, ?)",
					new String[] { bcItem.getName(),
							String.valueOf(bcItem.getImageId()),
							String.valueOf(bcItem.getParentId()) });
		}

		// 餐饮
		items = new ArrayList<BillCatagoryItem>();
		item = new BillCatagoryItem("早餐", R.drawable.zaocan, 2);
		items.add(item);
		item = new BillCatagoryItem("午餐", R.drawable.wucan, 2);
		items.add(item);
		item = new BillCatagoryItem("晚餐", R.drawable.wancan, 2);
		items.add(item);
		item = new BillCatagoryItem("夜宵", R.drawable.yexiao, 2);
		items.add(item);
		item = new BillCatagoryItem("饮料水果", R.drawable.yanjiu, 2);
		items.add(item);
		item = new BillCatagoryItem("零食", R.drawable.lingshi, 2);
		items.add(item);
		item = new BillCatagoryItem("买菜原料", R.drawable.maicai, 2);
		items.add(item);
		item = new BillCatagoryItem("油盐酱醋", R.drawable.youyanjiangcu, 2);
		items.add(item);
		for (BillCatagoryItem bcItem : items) {
			db.execSQL(
					"insert into bill_catagory (name, imageid, parentid) values (?, ?, ?)",
					new String[] { bcItem.getName(),
							String.valueOf(bcItem.getImageId()),
							String.valueOf(bcItem.getParentId()) });
		}

		// 交通
		items = new ArrayList<BillCatagoryItem>();
		item = new BillCatagoryItem("打的", R.drawable.dache, 3);
		items.add(item);
		item = new BillCatagoryItem("公交地铁", R.drawable.gongjiaoditie, 3);
		items.add(item);
		item = new BillCatagoryItem("加油", R.drawable.jiayou, 3);
		items.add(item);
		item = new BillCatagoryItem("停车费", R.drawable.tingchefei, 3);
		items.add(item);
		item = new BillCatagoryItem("过路过桥", R.drawable.guoluguoqiao, 3);
		items.add(item);
		item = new BillCatagoryItem("罚款赔偿", R.drawable.fakuanpeichang, 3);
		items.add(item);
		item = new BillCatagoryItem("保养维修", R.drawable.baoyangweihu, 3);
		items.add(item);
		item = new BillCatagoryItem("火车", R.drawable.huoche, 3);
		items.add(item);
		item = new BillCatagoryItem("车款车贷", R.drawable.chekuanchedai, 3);
		items.add(item);
		item = new BillCatagoryItem("车险", R.drawable.chexian, 3);
		items.add(item);
		item = new BillCatagoryItem("航空", R.drawable.hangkong, 3);
		items.add(item);

		for (BillCatagoryItem bcItem : items) {
			db.execSQL(
					"insert into bill_catagory (name, imageid, parentid) values (?, ?, ?)",
					new String[] { bcItem.getName(),
							String.valueOf(bcItem.getImageId()),
							String.valueOf(bcItem.getParentId()) });
		}

		// 购物
		items = new ArrayList<BillCatagoryItem>();
		item = new BillCatagoryItem("衣服鞋帽", R.drawable.yifuxiemao, 4);
		items.add(item);
		item = new BillCatagoryItem("日用百货", R.drawable.riyongbaihuo, 4);
		items.add(item);
		item = new BillCatagoryItem("婴幼用品", R.drawable.yingeryongpin, 4);
		items.add(item);
		item = new BillCatagoryItem("数码产品", R.drawable.shumachanpin, 4);
		items.add(item);
		item = new BillCatagoryItem("化妆护肤", R.drawable.huazhuanghufu, 4);
		items.add(item);
		item = new BillCatagoryItem("首饰", R.drawable.shoushi, 4);
		items.add(item);
		item = new BillCatagoryItem("烟酒", R.drawable.yanjiu, 4);
		items.add(item);
		item = new BillCatagoryItem("电器", R.drawable.dianqi, 4);
		items.add(item);
		item = new BillCatagoryItem("家具", R.drawable.jiaju, 4);
		items.add(item);
		item = new BillCatagoryItem("报刊书籍", R.drawable.baokanshuji, 4);
		items.add(item);
		item = new BillCatagoryItem("玩具", R.drawable.wanju, 4);
		items.add(item);
		item = new BillCatagoryItem("摄影", R.drawable.sheying, 4);
		items.add(item);

		for (BillCatagoryItem bcItem : items) {
			db.execSQL(
					"insert into bill_catagory (name, imageid, parentid) values (?, ?, ?)",
					new String[] { bcItem.getName(),
							String.valueOf(bcItem.getImageId()),
							String.valueOf(bcItem.getParentId()) });
		}

		// 娱乐
		items = new ArrayList<BillCatagoryItem>();
		item = new BillCatagoryItem("卡拉OK", R.drawable.kalaok, 5);
		items.add(item);
		item = new BillCatagoryItem("电影", R.drawable.dianying, 5);
		items.add(item);
		item = new BillCatagoryItem("网游电玩", R.drawable.wangyoudianwan, 5);
		items.add(item);
		item = new BillCatagoryItem("运动健身", R.drawable.yundongjianshen, 5);
		items.add(item);
		item = new BillCatagoryItem("洗浴足浴", R.drawable.xiyuzuyu, 5);
		items.add(item);
		item = new BillCatagoryItem("茶酒咖啡", R.drawable.chajiukafei, 5);
		items.add(item);
		item = new BillCatagoryItem("旅游度假", R.drawable.luyoudujia, 5);
		items.add(item);

		for (BillCatagoryItem bcItem : items) {
			db.execSQL(
					"insert into bill_catagory (name, imageid, parentid) values (?, ?, ?)",
					new String[] { bcItem.getName(),
							String.valueOf(bcItem.getImageId()),
							String.valueOf(bcItem.getParentId()) });
		}

		// 医教
		items = new ArrayList<BillCatagoryItem>();
		item = new BillCatagoryItem("求医买药", R.drawable.qiuyimaiyao, 6);
		items.add(item);
		item = new BillCatagoryItem("培训考试", R.drawable.peixunkaoshi, 6);
		items.add(item);
		item = new BillCatagoryItem("学习教材", R.drawable.xuezajiaocai, 6);
		items.add(item);
		item = new BillCatagoryItem("家教补习", R.drawable.jiajiaobuxi, 6);
		items.add(item);
		for (BillCatagoryItem bcItem : items) {
			db.execSQL(
					"insert into bill_catagory (name, imageid, parentid) values (?, ?, ?)",
					new String[] { bcItem.getName(),
							String.valueOf(bcItem.getImageId()),
							String.valueOf(bcItem.getParentId()) });
		}

		// 居家
		items = new ArrayList<BillCatagoryItem>();
		item = new BillCatagoryItem("美容美发", R.drawable.meirongmeifa, 7);
		items.add(item);
		item = new BillCatagoryItem("手机花费", R.drawable.shoujihuafei, 7);
		items.add(item);
		item = new BillCatagoryItem("宽带费用", R.drawable.kuandai, 7);
		items.add(item);
		item = new BillCatagoryItem("房屋贷款", R.drawable.fangwudaikuan, 7);
		items.add(item);
		item = new BillCatagoryItem("水电燃气", R.drawable.shuidianranqi, 7);
		items.add(item);
		item = new BillCatagoryItem("物业", R.drawable.wuye, 7);
		items.add(item);
		item = new BillCatagoryItem("住宿房租", R.drawable.zhusuzufang, 7);
		items.add(item);
		item = new BillCatagoryItem("保险费", R.drawable.baoxianfei, 7);
		items.add(item);
		item = new BillCatagoryItem("消费贷款", R.drawable.xiaofeidaikuan, 7);
		items.add(item);
		item = new BillCatagoryItem("税费", R.drawable.shuifei, 7);
		items.add(item);
		item = new BillCatagoryItem("家政服务", R.drawable.jiazhengfuwu, 7);
		items.add(item);
		item = new BillCatagoryItem("快递邮政", R.drawable.kuaidiyouji, 7);
		items.add(item);
		for (BillCatagoryItem bcItem : items) {
			db.execSQL(
					"insert into bill_catagory (name, imageid, parentid) values (?, ?, ?)",
					new String[] { bcItem.getName(),
							String.valueOf(bcItem.getImageId()),
							String.valueOf(bcItem.getParentId()) });
		}

		// 投资
		items = new ArrayList<BillCatagoryItem>();
		item = new BillCatagoryItem("证劵期货", R.drawable.zhengquanqihuo, 8);
		items.add(item);
		item = new BillCatagoryItem("保险", R.drawable.baoxian, 8);
		items.add(item);
		item = new BillCatagoryItem("外汇", R.drawable.waihui, 8);
		items.add(item);
		item = new BillCatagoryItem("黄金实物", R.drawable.huangjinshiwu, 8);
		items.add(item);
		item = new BillCatagoryItem("书画艺术", R.drawable.shuhuayishu, 8);
		items.add(item);
		for (BillCatagoryItem bcItem : items) {
			db.execSQL(
					"insert into bill_catagory (name, imageid, parentid) values (?, ?, ?)",
					new String[] { bcItem.getName(),
							String.valueOf(bcItem.getImageId()),
							String.valueOf(bcItem.getParentId()) });
		}

		// 人情
		items = new ArrayList<BillCatagoryItem>();
		item = new BillCatagoryItem("礼金", R.drawable.lijin, 9);
		items.add(item);
		item = new BillCatagoryItem("礼物", R.drawable.lipin, 9);
		items.add(item);
		item = new BillCatagoryItem("慈善捐款", R.drawable.cishanjuankuan, 9);
		items.add(item);
		item = new BillCatagoryItem("代付款", R.drawable.daifukuan, 9);
		items.add(item);
		for (BillCatagoryItem bcItem : items) {
			db.execSQL(
					"insert into bill_catagory (name, imageid, parentid) values (?, ?, ?)",
					new String[] { bcItem.getName(),
							String.valueOf(bcItem.getImageId()),
							String.valueOf(bcItem.getParentId()) });
		}

	}
}
