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
		item = new BillCatagoryItem("����", R.drawable.changyong, 0);
		items.add(item);
		item = new BillCatagoryItem("����", R.drawable.canyin, 0);
		items.add(item);
		item = new BillCatagoryItem("��ͨ", R.drawable.jiaotong, 0);
		items.add(item);
		item = new BillCatagoryItem("����", R.drawable.gouwu, 0);
		items.add(item);
		item = new BillCatagoryItem("����", R.drawable.yule, 0);
		items.add(item);
		item = new BillCatagoryItem("ҽ��", R.drawable.yijiao, 0);
		items.add(item);
		item = new BillCatagoryItem("�Ӽ�", R.drawable.jujia, 0);
		items.add(item);
		item = new BillCatagoryItem("Ͷ��", R.drawable.touzi, 0);
		items.add(item);
		item = new BillCatagoryItem("����", R.drawable.renqing, 0);
		items.add(item);

		for (BillCatagoryItem bcItem : items) {
			db.execSQL(
					"insert into bill_catagory (name, imageid, parentid) values (?, ?, ?)",
					new String[] { bcItem.getName(),
							String.valueOf(bcItem.getImageId()),
							String.valueOf(bcItem.getParentId()) });
		}

		// ��ʼ���������б���Ϣ
		items = new ArrayList<BillCatagoryItem>();
		// ����
		item = new BillCatagoryItem("���", R.drawable.dache, 1);
		items.add(item);
		item = new BillCatagoryItem("���", R.drawable.zaocan, 1);
		items.add(item);
		item = new BillCatagoryItem("���", R.drawable.wucan, 1);
		items.add(item);
		item = new BillCatagoryItem("���", R.drawable.wancan, 1);
		items.add(item);
		for (BillCatagoryItem bcItem : items) {
			db.execSQL(
					"insert into bill_catagory (name, imageid, parentid) values (?, ?, ?)",
					new String[] { bcItem.getName(),
							String.valueOf(bcItem.getImageId()),
							String.valueOf(bcItem.getParentId()) });
		}

		// ����
		items = new ArrayList<BillCatagoryItem>();
		item = new BillCatagoryItem("���", R.drawable.zaocan, 2);
		items.add(item);
		item = new BillCatagoryItem("���", R.drawable.wucan, 2);
		items.add(item);
		item = new BillCatagoryItem("���", R.drawable.wancan, 2);
		items.add(item);
		item = new BillCatagoryItem("ҹ��", R.drawable.yexiao, 2);
		items.add(item);
		item = new BillCatagoryItem("����ˮ��", R.drawable.yanjiu, 2);
		items.add(item);
		item = new BillCatagoryItem("��ʳ", R.drawable.lingshi, 2);
		items.add(item);
		item = new BillCatagoryItem("���ԭ��", R.drawable.maicai, 2);
		items.add(item);
		item = new BillCatagoryItem("���ν���", R.drawable.youyanjiangcu, 2);
		items.add(item);
		for (BillCatagoryItem bcItem : items) {
			db.execSQL(
					"insert into bill_catagory (name, imageid, parentid) values (?, ?, ?)",
					new String[] { bcItem.getName(),
							String.valueOf(bcItem.getImageId()),
							String.valueOf(bcItem.getParentId()) });
		}

		// ��ͨ
		items = new ArrayList<BillCatagoryItem>();
		item = new BillCatagoryItem("���", R.drawable.dache, 3);
		items.add(item);
		item = new BillCatagoryItem("��������", R.drawable.gongjiaoditie, 3);
		items.add(item);
		item = new BillCatagoryItem("����", R.drawable.jiayou, 3);
		items.add(item);
		item = new BillCatagoryItem("ͣ����", R.drawable.tingchefei, 3);
		items.add(item);
		item = new BillCatagoryItem("��·����", R.drawable.guoluguoqiao, 3);
		items.add(item);
		item = new BillCatagoryItem("�����⳥", R.drawable.fakuanpeichang, 3);
		items.add(item);
		item = new BillCatagoryItem("����ά��", R.drawable.baoyangweihu, 3);
		items.add(item);
		item = new BillCatagoryItem("��", R.drawable.huoche, 3);
		items.add(item);
		item = new BillCatagoryItem("�����", R.drawable.chekuanchedai, 3);
		items.add(item);
		item = new BillCatagoryItem("����", R.drawable.chexian, 3);
		items.add(item);
		item = new BillCatagoryItem("����", R.drawable.hangkong, 3);
		items.add(item);

		for (BillCatagoryItem bcItem : items) {
			db.execSQL(
					"insert into bill_catagory (name, imageid, parentid) values (?, ?, ?)",
					new String[] { bcItem.getName(),
							String.valueOf(bcItem.getImageId()),
							String.valueOf(bcItem.getParentId()) });
		}

		// ����
		items = new ArrayList<BillCatagoryItem>();
		item = new BillCatagoryItem("�·�Ьñ", R.drawable.yifuxiemao, 4);
		items.add(item);
		item = new BillCatagoryItem("���ðٻ�", R.drawable.riyongbaihuo, 4);
		items.add(item);
		item = new BillCatagoryItem("Ӥ����Ʒ", R.drawable.yingeryongpin, 4);
		items.add(item);
		item = new BillCatagoryItem("�����Ʒ", R.drawable.shumachanpin, 4);
		items.add(item);
		item = new BillCatagoryItem("��ױ����", R.drawable.huazhuanghufu, 4);
		items.add(item);
		item = new BillCatagoryItem("����", R.drawable.shoushi, 4);
		items.add(item);
		item = new BillCatagoryItem("�̾�", R.drawable.yanjiu, 4);
		items.add(item);
		item = new BillCatagoryItem("����", R.drawable.dianqi, 4);
		items.add(item);
		item = new BillCatagoryItem("�Ҿ�", R.drawable.jiaju, 4);
		items.add(item);
		item = new BillCatagoryItem("�����鼮", R.drawable.baokanshuji, 4);
		items.add(item);
		item = new BillCatagoryItem("���", R.drawable.wanju, 4);
		items.add(item);
		item = new BillCatagoryItem("��Ӱ", R.drawable.sheying, 4);
		items.add(item);

		for (BillCatagoryItem bcItem : items) {
			db.execSQL(
					"insert into bill_catagory (name, imageid, parentid) values (?, ?, ?)",
					new String[] { bcItem.getName(),
							String.valueOf(bcItem.getImageId()),
							String.valueOf(bcItem.getParentId()) });
		}

		// ����
		items = new ArrayList<BillCatagoryItem>();
		item = new BillCatagoryItem("����OK", R.drawable.kalaok, 5);
		items.add(item);
		item = new BillCatagoryItem("��Ӱ", R.drawable.dianying, 5);
		items.add(item);
		item = new BillCatagoryItem("���ε���", R.drawable.wangyoudianwan, 5);
		items.add(item);
		item = new BillCatagoryItem("�˶�����", R.drawable.yundongjianshen, 5);
		items.add(item);
		item = new BillCatagoryItem("ϴԡ��ԡ", R.drawable.xiyuzuyu, 5);
		items.add(item);
		item = new BillCatagoryItem("��ƿ���", R.drawable.chajiukafei, 5);
		items.add(item);
		item = new BillCatagoryItem("���ζȼ�", R.drawable.luyoudujia, 5);
		items.add(item);

		for (BillCatagoryItem bcItem : items) {
			db.execSQL(
					"insert into bill_catagory (name, imageid, parentid) values (?, ?, ?)",
					new String[] { bcItem.getName(),
							String.valueOf(bcItem.getImageId()),
							String.valueOf(bcItem.getParentId()) });
		}

		// ҽ��
		items = new ArrayList<BillCatagoryItem>();
		item = new BillCatagoryItem("��ҽ��ҩ", R.drawable.qiuyimaiyao, 6);
		items.add(item);
		item = new BillCatagoryItem("��ѵ����", R.drawable.peixunkaoshi, 6);
		items.add(item);
		item = new BillCatagoryItem("ѧϰ�̲�", R.drawable.xuezajiaocai, 6);
		items.add(item);
		item = new BillCatagoryItem("�ҽ̲�ϰ", R.drawable.jiajiaobuxi, 6);
		items.add(item);
		for (BillCatagoryItem bcItem : items) {
			db.execSQL(
					"insert into bill_catagory (name, imageid, parentid) values (?, ?, ?)",
					new String[] { bcItem.getName(),
							String.valueOf(bcItem.getImageId()),
							String.valueOf(bcItem.getParentId()) });
		}

		// �Ӽ�
		items = new ArrayList<BillCatagoryItem>();
		item = new BillCatagoryItem("��������", R.drawable.meirongmeifa, 7);
		items.add(item);
		item = new BillCatagoryItem("�ֻ�����", R.drawable.shoujihuafei, 7);
		items.add(item);
		item = new BillCatagoryItem("�������", R.drawable.kuandai, 7);
		items.add(item);
		item = new BillCatagoryItem("���ݴ���", R.drawable.fangwudaikuan, 7);
		items.add(item);
		item = new BillCatagoryItem("ˮ��ȼ��", R.drawable.shuidianranqi, 7);
		items.add(item);
		item = new BillCatagoryItem("��ҵ", R.drawable.wuye, 7);
		items.add(item);
		item = new BillCatagoryItem("ס�޷���", R.drawable.zhusuzufang, 7);
		items.add(item);
		item = new BillCatagoryItem("���շ�", R.drawable.baoxianfei, 7);
		items.add(item);
		item = new BillCatagoryItem("���Ѵ���", R.drawable.xiaofeidaikuan, 7);
		items.add(item);
		item = new BillCatagoryItem("˰��", R.drawable.shuifei, 7);
		items.add(item);
		item = new BillCatagoryItem("��������", R.drawable.jiazhengfuwu, 7);
		items.add(item);
		item = new BillCatagoryItem("�������", R.drawable.kuaidiyouji, 7);
		items.add(item);
		for (BillCatagoryItem bcItem : items) {
			db.execSQL(
					"insert into bill_catagory (name, imageid, parentid) values (?, ?, ?)",
					new String[] { bcItem.getName(),
							String.valueOf(bcItem.getImageId()),
							String.valueOf(bcItem.getParentId()) });
		}

		// Ͷ��
		items = new ArrayList<BillCatagoryItem>();
		item = new BillCatagoryItem("֤���ڻ�", R.drawable.zhengquanqihuo, 8);
		items.add(item);
		item = new BillCatagoryItem("����", R.drawable.baoxian, 8);
		items.add(item);
		item = new BillCatagoryItem("���", R.drawable.waihui, 8);
		items.add(item);
		item = new BillCatagoryItem("�ƽ�ʵ��", R.drawable.huangjinshiwu, 8);
		items.add(item);
		item = new BillCatagoryItem("�黭����", R.drawable.shuhuayishu, 8);
		items.add(item);
		for (BillCatagoryItem bcItem : items) {
			db.execSQL(
					"insert into bill_catagory (name, imageid, parentid) values (?, ?, ?)",
					new String[] { bcItem.getName(),
							String.valueOf(bcItem.getImageId()),
							String.valueOf(bcItem.getParentId()) });
		}

		// ����
		items = new ArrayList<BillCatagoryItem>();
		item = new BillCatagoryItem("���", R.drawable.lijin, 9);
		items.add(item);
		item = new BillCatagoryItem("����", R.drawable.lipin, 9);
		items.add(item);
		item = new BillCatagoryItem("���ƾ��", R.drawable.cishanjuankuan, 9);
		items.add(item);
		item = new BillCatagoryItem("������", R.drawable.daifukuan, 9);
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
