package com.example.lifememory.activity.model;

import java.io.Serializable;


/**
 * idx integer primary key autoincrement, 
 * name text,catagoryname text,
 *  accountid integer, member text,
 *   canbaoxiao, transferinaccountdid integer,
 *    transferoutaccountid integer, billtype integer
 *
 */
public class BillTemplate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idx;
	private String name;
	private String inCatagoryName;
	private String outCatagoryName;
	private int outCatagoryChildId;        //支出类型的子id
	private int outCatagoryParentId;	   //支出类型的父id
	private int accountid;
	private String member;
	private boolean canBaoXiao;
	private int transferInAccountId;
	private int transferOutAccountId;
	private int billType;
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getInCatagoryName() {
		return inCatagoryName;
	}
	public void setInCatagoryName(String inCatagoryName) {
		this.inCatagoryName = inCatagoryName;
	}
	public String getOutCatagoryName() {
		return outCatagoryName;
	}
	public void setOutCatagoryName(String outCatagoryName) {
		this.outCatagoryName = outCatagoryName;
	}
	
	public int getOutCatagoryChildId() {
		return outCatagoryChildId;
	}
	public void setOutCatagoryChildId(int outCatagoryChildId) {
		this.outCatagoryChildId = outCatagoryChildId;
	}
	public int getOutCatagoryParentId() {
		return outCatagoryParentId;
	}
	public void setOutCatagoryParentId(int outCatagoryParentId) {
		this.outCatagoryParentId = outCatagoryParentId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getAccountid() {
		return accountid;
	}
	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}
	public String getMember() {
		return member;
	}
	public void setMember(String member) {
		this.member = member;
	}
	public boolean isCanBaoXiao() {
		return canBaoXiao;
	}
	public void setCanBaoXiao(boolean canBaoXiao) {
		this.canBaoXiao = canBaoXiao;
	}
	public int getTransferInAccountId() {
		return transferInAccountId;
	}
	public void setTransferInAccountId(int transferInAccountId) {
		this.transferInAccountId = transferInAccountId;
	}
	public int getTransferOutAccountId() {
		return transferOutAccountId;
	}
	public void setTransferOutAccountId(int transferOutAccountId) {
		this.transferOutAccountId = transferOutAccountId;
	}
	public int getBillType() {
		return billType;
	}
	public void setBillType(int billType) {
		this.billType = billType;
	}
	@Override
	public String toString() {
		return "BillTemplate [idx=" + idx + ", name=" + name
				+ ", inCatagoryName=" + inCatagoryName + ", outCatagoryName="
				+ outCatagoryName + ", outCatagoryChildId="
				+ outCatagoryChildId + ", outCatagoryParentId="
				+ outCatagoryParentId + ", accountid=" + accountid
				+ ", member=" + member + ", canBaoXiao=" + canBaoXiao
				+ ", transferInAccountId=" + transferInAccountId
				+ ", transferOutAccountId=" + transferOutAccountId
				+ ", billType=" + billType + "]";
	}
	
	
	
	
}
