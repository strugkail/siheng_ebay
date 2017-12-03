package com.oigbuy.jeesite.modules.ebay.purchase.dto;

public class PurchaseConfigInfo {

	private String id;
	private String color; // 颜色
	private String size;  // 尺寸
	private String wishColor; //wish颜色
	private String wishSize;  //wish尺寸
	private String property;// ebay属性
	private String sourceId; // 源ID
	private Integer quantity; // 采购数量
	private Double publishPrice; // 刊登价格
	private Double publishTransPrice; // 刊登运费
	private Double costPrice;		// 成本价
	private Double weight;		// 重量
	private Double profit;     // 利润
	private Double profitRate; // 利润率
	private String sysSku;    //系统子代码
	private String codeManagerId;
	private String supplierName; // 供应商名称


	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	

	public String getWishColor() {
		return wishColor;
	}

	public void setWishColor(String wishColor) {
		this.wishColor = wishColor;
	}

	public String getWishSize() {
		return wishSize;
	}

	public void setWishSize(String wishSize) {
		this.wishSize = wishSize;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

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

	public String getSysSku() {
		return sysSku;
	}

	public void setSysSku(String sysSku) {
		this.sysSku = sysSku;
	}

	public String getCodeManagerId() {
		return codeManagerId;
	}

	public void setCodeManagerId(String codeManagerId) {
		this.codeManagerId = codeManagerId;
	}
	
	
}
