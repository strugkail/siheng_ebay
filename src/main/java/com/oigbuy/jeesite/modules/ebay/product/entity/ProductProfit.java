package com.oigbuy.jeesite.modules.ebay.product.entity;

public class ProductProfit {
	
	private Double publishPrice;
	private Double publishTransPrice;
	private Double costPrice;
	private Double weight;
	private Double profitRate; // 产品利润率
	private Double declaredValue; // 申报价格
	
	private String calType;

	public Double getPublishPrice() {
		return publishPrice;
	}

	public void setPublishPrice(Double publishPrice) {
		this.publishPrice = publishPrice;
	}

	public Double getPublishTransPrice() {
		return publishTransPrice;
	}

	public void setPublishTransPrice(Double publishTransPrice) {
		this.publishTransPrice = publishTransPrice;
	}

	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(Double profitRate) {
		this.profitRate = profitRate;
	}

	public String getCalType() {
		return calType;
	}

	public void setCalType(String calType) {
		this.calType = calType;
	}

	public Double getDeclaredValue() {
		return declaredValue;
	}

	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
	}
	
}
