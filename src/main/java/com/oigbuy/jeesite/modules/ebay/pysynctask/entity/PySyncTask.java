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
public class PySyncTask extends DataEntity<PySyncTask> {
	
	private static final long serialVersionUID = 1L;
	private String taskId;         // 任务ID
	private String sourceId;		// 数据源ID
	private String recordId;	
	private String operType;		// 操作类型
	private String content;			// 内容
	private Date createTime;		// 创建时间
	
	public PySyncTask() {
		super();
	}
	
	public PySyncTask(String taskId, String sourceId, String operType, String content,
			Date createTime) {
		this.taskId = taskId;
		this.sourceId = sourceId;
		this.operType = operType;
		this.content = content;
		this.createTime = createTime;
	}
	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
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

	@Override
	public String toString() {
		return "PySyncTask{" +
				"taskId='" + taskId + '\'' +
				", sourceId='" + sourceId + '\'' +
				", recordId='" + recordId + '\'' +
				", operType='" + operType + '\'' +
				", content='" + content + '\'' +
				", createTime=" + createTime +
				'}';
	}
}