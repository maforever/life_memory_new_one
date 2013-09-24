package com.example.lifememory.activity.model;

import java.util.List;

public class BillAccountExpandableListViewItem {

	private String title;
	private int imageId;
	private List<BillAccountItem> accountItems;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<BillAccountItem> getAccountItems() {
		return accountItems;
	}
	public void setAccountItems(List<BillAccountItem> accountItems) {
		this.accountItems = accountItems;
	}
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	
	
	
}
