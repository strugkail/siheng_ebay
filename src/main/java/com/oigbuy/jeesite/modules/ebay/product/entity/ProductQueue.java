package com.oigbuy.jeesite.modules.ebay.product.entity;

import com.oigbuy.jeesite.common.persistence.DataEntity;

public class ProductQueue extends DataEntity<ProductQueue> {


	private Integer productId;
	private String productFrom;
	private String productName;
	private String imgUrl;
	private String platformName;
	private String createTime;
	private String status;

	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public String getPlatformName() {
		return platformName;
	}
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getProductFrom() {
		return productFrom;
	}
	public void setProductFrom(String productFrom) {
		this.productFrom = productFrom;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
