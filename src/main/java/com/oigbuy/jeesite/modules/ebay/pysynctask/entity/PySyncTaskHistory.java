/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.pysynctask.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oigbuy.jeesite.common.persistence.DataEntity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 普源同步任务Entity
 * @author mzm
 * @version 2017-07-10
 */
public class PySyncTaskHistory extends DataEntity<PySyncTaskHistory> {
	
	private static final long serialVersionUID = 1L;
	private String taskId;		// 普元任务ID
	private String sourceId;		// 数据源ID
	private String operType;		// 操作类型
	private String content;
	private Date createTime;		// 创建时间
	
	public PySyncTaskHistory() {
		super();
	}

	public PySyncTaskHistory(String id){
		super(id);
	}

	@Length(min=1, max=20, message="普元任务ID长度必须介于 1 和 20 之间")
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	@Length(min=0, max=20, message="数据源ID长度必须介于 0 和 20 之间")
	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
	@Length(min=0, max=40, message="操作类型长度必须介于 0 和 40 之间")
	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}