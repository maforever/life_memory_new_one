package com.example.lifememory.activity.model;


public class BillCatagoryItem {
	
	private int idx;
	private String name;
	private int imageId;
	private double bugget;           //预算值
	private int parentId; 
	private double spendValue;       //以消费值
	
	public BillCatagoryItem() {};
	
	public BillCatagoryItem(String name, int imageId, int parentId) {
		super();
		this.name = name;
		this.imageId = imageId;
		this.parentId = parentId;
	}
	
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
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	public double getBugget() {
		return bugget;
	}
	public void setBugget(double bugget) {
		this.bugget = bugget;
	}

	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public double getSpendValue() {
		return spendValue;
	}

	public void setSpendValue(double spendValue) {
		this.spendValue = spendValue;
	}

	@Override
	public String toString() {
		return "BillCatagoryItem [idx=" + idx + ", name=" + name + ", imageId="
				+ imageId + ", bugget=" + bugget + ", parentId=" + parentId
				+ ", spendValue=" + spendValue + "]";
	}

	
}
