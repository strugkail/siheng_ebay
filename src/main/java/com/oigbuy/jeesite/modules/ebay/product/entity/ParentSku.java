package com.oigbuy.jeesite.modules.ebay.product.entity;

import java.io.Serializable;
import java.util.List;

public class ParentSku implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String processNum;
	private String productId;
	private String cnName;
	private String enName;
	private String sysParentSku;
	private Double length;
	private Double wide;
	private Double high;
	private Double weight;
	private List<ChildSku> childSku;
	public String getProcessNum() {
		return processNum;
	}
	public void setProcessNum(String processNum) {
		this.processNum = processNum;
	}
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
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
	public String getSysParentSku() {
		return sysParentSku;
	}
	public void setSysParentSku(String sysParentSku) {
		this.sysParentSku = sysParentSku;
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
	public List<ChildSku> getChildSku() {
		return childSku;
	}
	public void setChildSku(List<ChildSku> childSku) {
		this.childSku = childSku;
	}
	
	
}
