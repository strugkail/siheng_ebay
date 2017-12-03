/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.purchase.entity;

import com.oigbuy.jeesite.common.persistence.DataEntity;

import org.hibernate.validator.constraints.Length;

/**
 * 产品采购Entity
 * @author 马志明
 * @version 2017-06-21
 */
public class ProductPurchase extends DataEntity<ProductPurchase> {
	
	private static final long serialVersionUID = 1L;
	private String purchaseId;		// 采购ID
	private String sourceCodeId;		// 源码ID
	private String productId;		// 产品Id
	private String taskId;		// 任务Id
	private Integer count;		// 采购数量
	
	public ProductPurchase() {
		super();
	}

	public ProductPurchase(String id){
		super(id);
	}
	
	public ProductPurchase(String purchaseId, String sourceCodeId,
			String productId, String taskId, Integer count) {
		super();
		this.purchaseId = purchaseId;
		this.sourceCodeId = sourceCodeId;
		this.productId = productId;
		this.taskId = taskId;
		this.count = count;
	}

	@Length(min=1, max=20, message="采购ID长度必须介于 1 和 20 之间")
	public String getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}
	
	@Length(min=0, max=20, message="源码ID长度必须介于 0 和 20 之间")
	public String getSourceCodeId() {
		return sourceCodeId;
	}

	public void setSourceCodeId(String sourceCodeId) {
		this.sourceCodeId = sourceCodeId;
	}
	
	@Length(min=0, max=20, message="产品Id长度必须介于 0 和 20 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=20, message="任务Id长度必须介于 0 和 20 之间")
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "ProductPurchase{" +
				"purchaseId='" + purchaseId + '\'' +
				", sourceCodeId='" + sourceCodeId + '\'' +
				", productId='" + productId + '\'' +
				", taskId='" + taskId + '\'' +
				", count=" + count +
				'}';
	}
}