package com.example.lifememory.activity.model;

public class Bill {
	
	private int idx;
	private int accountLastIdx;          //用于修改账户当前余额，lastjine存放修改前的accountid，首先将该id的账户金额恢复 ： 当前的加上或减去lashJine，再将
								  //idx所指的账单奖赏或减去
	private String jine;          //本次实际的金额，与数据库管理
	private String baoxiaojine;    //实报金额
	private String lastJine;      //用于修改账户当前余额，lastjine存放修改钱的金额，用lastjine减去jine得到改动的差值，在相应的做加减操作
	private String outCatagory;   //支出类型catagoryname
	private int outCatagoryChildId;        //支出类型的子id
	private int outCatagoryParentId;	   //支出类型的父id
	private String inCatagory;    //收入类型catagoryname;
	private String account;   //accountname
	private int accountid;
	private String member; //memebername
	private String date;
	private String dateYMD;   //yyyy-MM-dd
	private String beizhu;
	private boolean isCanBaoXiao = false;
	private boolean isBaoxiaoed = false;
	private String TransferIn;     //转入     现金
	private int transferInAccountId;
	private int lastTransferInAccountId;
	private int transferOutAccountId;
	private int lastTransferOutAccountId;
	private String TransferOut;    //转出   银行卡
	private int billType;    //1 支出 2 收入 3 转账
	private int lastBillType; //记录变换类型前的类型
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	
	public String getJine() {
		return jine;
	}
	public void setJine(String jine) {
		this.jine = jine;
	}
	
	public String getBaoxiaojine() {
		return baoxiaojine;
	}
	public void setBaoxiaojine(String baoxiaojine) {
		this.baoxiaojine = baoxiaojine;
	}
	public String getOutCatagory() {
		return outCatagory;
	}
	public void setOutCatagory(String outCatagory) {
		this.outCatagory = outCatagory;
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
	public String getInCatagory() {
		return inCatagory;
	}
	public void setInCatagory(String inCatagory) {
		this.inCatagory = inCatagory;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDateYMD() {
		return dateYMD;
	}
	public void setDateYMD(String dateYMD) {
		this.dateYMD = dateYMD;
	}
	public String getBeizhu() {
		return beizhu;
	}
	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}
	public boolean isCanBaoXiao() {
		return isCanBaoXiao;
	}
	public void setCanBaoXiao(boolean isCanBaoXiao) {
		this.isCanBaoXiao = isCanBaoXiao;
	}
	public boolean isBaoxiaoed() {
		return isBaoxiaoed;
	}
	public void setBaoxiaoed(boolean isBaoxiaoed) {
		this.isBaoxiaoed = isBaoxiaoed;
	}
	public String getTransferIn() {
		return TransferIn;
	}
	public void setTransferIn(String transferIn) {
		TransferIn = transferIn;
	}
	public String getTransferOut() {
		return TransferOut;
	}
	public void setTransferOut(String transferOut) {
		TransferOut = transferOut;
	}
	public int getBillType() {
		return billType;
	}
	public void setBillType(int billType) {
		this.billType = billType;
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
	public String getLastJine() {
		return lastJine;
	}
	public void setLastJine(String lastJine) {
		this.lastJine = lastJine;
	}
	public int getAccountLastIdx() {
		return accountLastIdx;
	}
	public void setAccountLastIdx(int accountLastIdx) {
		this.accountLastIdx = accountLastIdx;
	}
	public int getLastTransferInAccountId() {
		return lastTransferInAccountId;
	}
	public void setLastTransferInAccountId(int lastTransferInAccountId) {
		this.lastTransferInAccountId = lastTransferInAccountId;
	}
	public int getLastTransferOutAccountId() {
		return lastTransferOutAccountId;
	}
	public void setLastTransferOutAccountId(int lastTransferOutAccountId) {
		this.lastTransferOutAccountId = lastTransferOutAccountId;
	}
	public int getLastBillType() {
		return lastBillType;
	}
	public void setLastBillType(int lastBillType) {
		this.lastBillType = lastBillType;
	}
	@Override
	public String toString() {
		return "Bill [idx=" + idx + ", accountLastIdx=" + accountLastIdx
				+ ", jine=" + jine + ", baoxiaojine=" + baoxiaojine
				+ ", lastJine=" + lastJine + ", outCatagory=" + outCatagory
				+ ", outCatagoryChildId=" + outCatagoryChildId
				+ ", outCatagoryParentId=" + outCatagoryParentId
				+ ", inCatagory=" + inCatagory + ", account=" + account
				+ ", accountid=" + accountid + ", member=" + member + ", date="
				+ date + ", dateYMD=" + dateYMD + ", beizhu=" + beizhu
				+ ", isCanBaoXiao=" + isCanBaoXiao + ", isBaoxiaoed="
				+ isBaoxiaoed + ", TransferIn=" + TransferIn
				+ ", transferInAccountId=" + transferInAccountId
				+ ", lastTransferInAccountId=" + lastTransferInAccountId
				+ ", transferOutAccountId=" + transferOutAccountId
				+ ", lastTransferOutAccountId=" + lastTransferOutAccountId
				+ ", TransferOut=" + TransferOut + ", billType=" + billType
				+ ", lastBillType=" + lastBillType + ", getIdx()=" + getIdx()
				+ ", getJine()=" + getJine() + ", getBaoxiaojine()="
				+ getBaoxiaojine() + ", getOutCatagory()=" + getOutCatagory()
				+ ", getOutCatagoryChildId()=" + getOutCatagoryChildId()
				+ ", getOutCatagoryParentId()=" + getOutCatagoryParentId()
				+ ", getInCatagory()=" + getInCatagory() + ", getAccount()="
				+ getAccount() + ", getAccountid()=" + getAccountid()
				+ ", getMember()=" + getMember() + ", getDate()=" + getDate()
				+ ", getDateYMD()=" + getDateYMD() + ", getBeizhu()="
				+ getBeizhu() + ", isCanBaoXiao()=" + isCanBaoXiao()
				+ ", isBaoxiaoed()=" + isBaoxiaoed() + ", getTransferIn()="
				+ getTransferIn() + ", getTransferOut()=" + getTransferOut()
				+ ", getBillType()=" + getBillType()
				+ ", getTransferInAccountId()=" + getTransferInAccountId()
				+ ", getTransferOutAccountId()=" + getTransferOutAccountId()
				+ ", getLastJine()=" + getLastJine() + ", getAccountLastIdx()="
				+ getAccountLastIdx() + ", getLastTransferInAccountId()="
				+ getLastTransferInAccountId()
				+ ", getLastTransferOutAccountId()="
				+ getLastTransferOutAccountId() + ", getLastBillType()="
				+ getLastBillType() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	

	
	
	
	
	
	
	
	
	
	
}
