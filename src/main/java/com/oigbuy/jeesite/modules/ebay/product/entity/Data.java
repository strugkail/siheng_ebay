package com.oigbuy.jeesite.modules.ebay.product.entity;

import java.io.Serializable;



public class Data implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Product product;
	private ParentSku parentSku;
	private MessureMmsParam messureMmsParam;
	
	
	
	public Data() {
		super();
	}
	public Data(Product product, ParentSku parentSku,
			MessureMmsParam messureMmsParam) {
		super();
		this.product = product;
		this.parentSku = parentSku;
		this.messureMmsParam = messureMmsParam;
	}
	public MessureMmsParam getMessureMmsParam() {
		return messureMmsParam;
	}
	public void setMessureMmsParam(MessureMmsParam messureMmsParam) {
		this.messureMmsParam = messureMmsParam;
	}
	public ParentSku getParentSku() {
		return parentSku;
	}
	public void setParentSku(ParentSku parentSku) {
		this.parentSku = parentSku;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
}
