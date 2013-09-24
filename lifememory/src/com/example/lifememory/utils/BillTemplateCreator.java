package com.example.lifememory.utils;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;

import com.example.lifememory.activity.model.BillTemplate;

public class BillTemplateCreator {
	
	public void initTemplate(SQLiteDatabase db) {
		List<BillTemplate> templates = new ArrayList<BillTemplate>();
		BillTemplate template = new BillTemplate();
		//֧�� ģ����Ϣ
		template.setName("��ֹ���");
		template.setOutCatagoryName("����-�·�Ьñ");
		template.setOutCatagoryChildId(33);
		template.setOutCatagoryParentId(4);
		template.setAccountid(1);
		template.setMember("�Լ�");
		template.setCanBaoXiao(false);
		template.setBillType(1);
		templates.add(template);
		
	    template = new BillTemplate();
		template.setName("�緹");
		template.setOutCatagoryName("����-�緹");
		template.setOutCatagoryChildId(12);
		template.setOutCatagoryParentId(2);
		template.setAccountid(1);
		template.setMember("�Լ�");
		template.setCanBaoXiao(false);
		template.setBillType(1);
		templates.add(template);
		
		template = new BillTemplate();
		template.setName("�����");
		template.setOutCatagoryName("��ͨ-���");
		template.setAccountid(1);
		template.setOutCatagoryChildId(10);
		template.setOutCatagoryParentId(3);
		template.setMember("�Լ�");
		template.setCanBaoXiao(false);
		template.setBillType(1);
		templates.add(template);
		
		//����ģ����Ϣ
		template = new BillTemplate();
		template.setName("����");
		template.setInCatagoryName("����");
		template.setAccountid(3);
		template.setMember("�Լ�");
		template.setCanBaoXiao(false);
		template.setBillType(2);
		templates.add(template);
		
		
		//ת��ģ����Ϣ
		template = new BillTemplate();
		template.setName("ȡ��");
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
