/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.platformAndsite.entity;

import org.hibernate.validator.constraints.Length;

import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * 平台Entity
 * @author mashuai
 * @version 2017-05-22
 */
public class Platform extends DataEntity<Platform> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 平台名称
	private String code;		// 平台码
	private String id;
	private String remark;
	
	public Platform() {
		super();
	}

	public Platform(String id){
		super(id);
	}

	@Length(min=0, max=128, message="平台名称长度必须介于 0 和 128 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=16, message="平台码长度必须介于 0 和 16 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}