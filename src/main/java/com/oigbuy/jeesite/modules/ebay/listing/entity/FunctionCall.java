/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.listing.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * 接口调用Entity
 * @author mashuai
 * @version 2017-05-25
 */
public class FunctionCall extends DataEntity<FunctionCall> {
	
	private static final long serialVersionUID = 1L;
	private String listingId;		// ListingId
	private String shopsId;		// 店铺Id
	private String platformId;		// 平台Ids
	private String operationType;		// 操作类型  add update del select 等 
	private String content;		// 操作内容  
	private String status;		// 操作状态  操作状态(0:待刊登，1:正在刊登，2:刊登完成，刊登异常)
	private String message;		// 信息 
	private String errorSku;	// 异常的sku
	private Date createTime;   // 创建时间
	private Date executeTime;  // 执行时间
	private String step; // 步骤 
	private int taskNum; // 任务数量（待处理的图片数量）
	private String recordId;
	
	private String platformType;//平台标志（0表示 wish  1 表示 ebay）
	
	
	
	private String productId;
	
	
	
	
	
	public FunctionCall(String listingId, String platformType) {
		super();
		this.listingId = listingId;
		this.platformType = platformType;
	}


	public FunctionCall(String id, String listingId, String shopsId, String platformId,
			String operationType, String content, String status, String step,
			String platformType) {
		super();
		super.id=id;
		this.listingId = listingId;
		this.shopsId = shopsId;
		this.platformId = platformId;
		this.operationType = operationType;
		this.content = content;
		this.status = status;
		this.step = step;
		this.platformType = platformType;
	}


	public String getPlatformType() {
		return platformType;
	}


	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}



	public FunctionCall() {
	}

	
	
	public FunctionCall(String id, String listingId, String shopsId, String platformId,
			String operationType, String content, String status, Date createTime, Date executeTime, String step, int taskNum) {
		super();
		super.id=id;
		this.listingId = listingId;
		this.shopsId = shopsId;
		this.platformId = platformId;
		this.operationType = operationType;
		this.content = content;
		this.status = status;
		this.createTime = createTime;
		this.executeTime = executeTime;
		this.step = step;
		this.taskNum = taskNum;
	}

	public FunctionCall(String id){
		super(id);
	}

	@Length(min=0, max=20, message="ListingId长度必须介于 0 和 20 之间")
	public String getListingId() {
		return listingId;
	}
	
	public String getErrorSku() {
		return errorSku;
	}

	public void setErrorSku(String errorSku) {
		this.errorSku = errorSku;
	}

	public void setListingId(String listingId) {
		this.listingId = listingId;
	}
	
	@Length(min=0, max=20, message="店铺Id长度必须介于 0 和 20 之间")
	public String getShopsId() {
		return shopsId;
	}

	public void setShopsId(String shopsId) {
		this.shopsId = shopsId;
	}
	
	@Length(min=0, max=20, message="平台Id长度必须介于 0 和 20 之间")
	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}
	
	@Length(min=0, max=11, message="操作类型长度必须介于 0 和 11 之间")
	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	
	@Length(min=0, max=300, message="操作内容长度必须介于 0 和 300 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=11, message="操作状态长度必须介于 0 和 11 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(Date executeTime) {
		this.executeTime = executeTime;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public int getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(int taskNum) {
		this.taskNum = taskNum;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
}