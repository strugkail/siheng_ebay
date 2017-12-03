package com.oigbuy.jeesite.modules.ebay.product.entity;

import java.io.Serializable;

public class ChildSku implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cnName;
	private String enName;
	private String sysSku;
	private Double length;
	private Double wide;
	private Double high;
	private Double weight;
	private Double costPrice; //成本价
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getSysSku() {
		return sysSku;
	}
	public void setSysSku(String sysSku) {
		this.sysSku = sysSku;
	}
	public Double getLength() {
		return length;
	}
	public void setLength(Double length) {
		this.length = length;
	}
	public Double getWide() {
		return wide;
	}
	public void setWide(Double wide) {
		this.wide = wide;
	}
	public Double getHigh() {
		return high;
	}
	public void setHigh(Double high) {
		this.high = high;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Double getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}
	
	
}
