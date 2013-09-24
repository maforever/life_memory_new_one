package com.example.lifememory.utils;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;

import com.example.lifememory.activity.model.BillTemplate;

public class BillTemplateCreator {
	
	public void initTemplate(SQLiteDatabase db) {
		List<BillTemplate> templates = new ArrayList<BillTemplate>();
		BillTemplate template = new BillTemplate();
		//支出 模版信息
		template.setName("逛街购物");
		template.setOutCatagoryName("购物-衣服鞋帽");
		template.setOutCatagoryChildId(33);
		template.setOutCatagoryParentId(4);
		template.setAccountid(1);
		template.setMember("自己");
		template.setCanBaoXiao(false);
		template.setBillType(1);
		templates.add(template);
		
	    template = new BillTemplate();
		template.setName("午饭");
		template.setOutCatagoryName("餐饮-午饭");
		template.setOutCatagoryChildId(12);
		template.setOutCatagoryParentId(2);
		template.setAccountid(1);
		template.setMember("自己");
		template.setCanBaoXiao(false);
		template.setBillType(1);
		templates.add(template);
		
		template = new BillTemplate();
		template.setName("外出打车");
		template.setOutCatagoryName("交通-打的");
		template.setAccountid(1);
		template.setOutCatagoryChildId(10);
		template.setOutCatagoryParentId(3);
		template.setMember("自己");
		template.setCanBaoXiao(false);
		template.setBillType(1);
		templates.add(template);
		
		//收入模版信息
		template = new BillTemplate();
		template.setName("工资");
		template.setInCatagoryName("工资");
		template.setAccountid(3);
		template.setMember("自己");
		template.setCanBaoXiao(false);
		template.setBillType(2);
		templates.add(template);
		
		
		//转账模版信息
		template = new BillTemplate();
		template.setName("取现");
		template.setTransferInAccountId(1);
		template.setTransferOutAccountId(3);
		template.setBillType(3);
		templates.add(template);
		
		for(BillTemplate t : templates) {
			db.execSQL(
					"insert into bill_template (name, incatagoryname, outcatagoryname,outcatagorychildid , outcatagoryparentid , accountid, member, canbaoxiao, transferinaccountdid, transferoutaccountid, billtype) values (?,?,?,?,?,?,?,?,?,?,?)",
					new String[] { t.getName(), t.getInCatagoryName(), t.getOutCatagoryName(),
							String.valueOf(t.getOutCatagoryChildId()), String.valueOf(t.getOutCatagoryParentId()),
							String.valueOf(t.getAccountid()),
							t.getMember(),
							String.valueOf(t.isCanBaoXiao()),
							String.valueOf(t.getTransferInAccountId()),
							String.valueOf(t.getTransferOutAccountId()),
							String.valueOf(t.getBillType()) });
		}
	}
}
