package com.example.lifememory.db;

import com.example.lifememory.utils.BillAccountCreator;
import com.example.lifememory.utils.BillCatagoryCreator;
import com.example.lifememory.utils.BillInCatagoryCreator;
import com.example.lifememory.utils.BillMemberCreator;
import com.example.lifememory.utils.BillTemplateCreator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PregnancyDiaryOpenHelper extends SQLiteOpenHelper {

	public PregnancyDiaryOpenHelper(Context context) {
		super(context, "pregnancy_diary.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		/**
		 * diary_jishiben ���ڼ��±����ݿ� diary_luyin ����¼�����ݿ�
		 */
		db.execSQL("create table if not exists diary_jishiben(idx integer primary key autoincrement, "
				+ "title text, content text, textcolorindex integer, textsizeindex integer, createdate text, updatedate text, createymd text, updateymd text, createym text, updateym text, ismodyfied integer, imageid int"
				+ ")");

		db.execSQL("create table if not exists diary_luyin("
				+ "idx integer primary key autoincrement, title text, audiopath text, createdate text, createymd, createym"
				+ ")");
		/**
		 * baby_jishiben �������±����ݿ� baby_luyin ����¼�����ݿ�
		 */
		db.execSQL("create table if not exists baby_jishiben(idx integer primary key autoincrement, "
				+ "title text, content text, textcolorindex integer, textsizeindex integer, createdate text, updatedate text, createymd text, updateymd text, createym text, updateym text, ismodyfied integer, imageid int"
				+ ")");

		db.execSQL("create table if not exists baby_luyin("
				+ "idx integer primary key autoincrement, title text, audiopath text, createdate text, createymd, createym"
				+ ")");

		/**
		 * bill_info      �˵���Ϣ���ݿ�
		 * bill_incatagory ��������������ݿ�
		 * bill_catagory  ����֧��������ݿ�
		 * bill_account   �����˻����ݿ�
		 * bill_member    ���˳�Ա���ݿ�
		 * catagoryname ������� �ֽ����ÿ����������֧��   1,2,3,4
		 * bizhong     ����
		 * dangqianyue   ��ǰ���
		 * isNotice   �Ƿ����þ�����
		 * noticeValue  �����߽��
		 */
		db.execSQL("create table if not exists bill_info(idx integer primary key autoincrement, jine text, incatagory text, outcatagory text , outcatagorychildid integer, outcatagoryparentid integer, account text, accountid integer, date text, dateymd text, member text, beizhu text, isCanBaoXiao text, isBaoxiaoed text, transferIn text, transferInAccountId integer, transferOut text, transferOutAccountId, billType integer)");
		db.execSQL("create table if not exists bill_incatagory(idx integer primary key autoincrement, name text)");
		db.execSQL("create table if not exists bill_catagory(idx integer primary key autoincrement, name text, imageid integer, budget text,  parentid integer)");
		db.execSQL("create table if not exists bill_account(idx integer primary key autoincrement, catagoryname text ,name text, bizhong text, dangqianyue text, isnotice text, noticevalue text, imageid integer, beizhu text)");
		db.execSQL("create table if not exists bill_member(idx integer primary key autoincrement, name text)");
		db.execSQL("create table if not exists bill_template(idx integer primary key autoincrement, name text,incatagoryname text, outcatagoryname text, outcatagorychildid integer, outcatagoryparentid integer, accountid integer, member text, canbaoxiao, transferinaccountdid integer, transferoutaccountid integer, billtype integer)");
		
		
		//����ط����ʹ���̻߳ᱨ�� database is locked
		BillCatagoryCreator creator = new BillCatagoryCreator();
		creator.initCatagoryDatas(db);
		
		BillAccountCreator accountCreator = new BillAccountCreator();
		accountCreator.initAccountDatas(db);
		
		BillMemberCreator memberCreator = new BillMemberCreator();
		memberCreator.initMember(db);
		
		BillInCatagoryCreator inCatagoryCreator = new BillInCatagoryCreator();
		inCatagoryCreator.initInCatagorys(db);
		
		BillTemplateCreator templateCreator = new BillTemplateCreator();
		templateCreator.initTemplate(db);
		
		Log.i("a", "db onCreate");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
