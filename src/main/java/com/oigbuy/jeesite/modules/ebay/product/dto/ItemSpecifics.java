package com.oigbuy.jeesite.modules.ebay.product.dto;

public class ItemSpecifics{
	
	/**
	 * item_specifics name 
	 */
	private String name;
	/**
	 * item_specifics value 
	 */
	private String value;
	/**
	 * item_specifics remarks 
	 */
	private String remarks;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public ItemSpecifics(String name, String value, String remarks) {
		super();
		this.name = name;
		this.value = value;
		this.remarks = remarks;
	}
	public ItemSpecifics() {
		super();
	}
	
}