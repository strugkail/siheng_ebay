/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.country.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Description;

import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * 国家管理Entity
 * @author 王佳点
 * @version 2017-06-26
 */
public class Country extends DataEntity<Country> {
	
	private static final long serialVersionUID = 1L;
	// 国家ID
	private String sequence;		// 顺序号
	private String area;		// 大区
	private String code;		// 国家代码
	private String gbName;		// 英文名称
	private String cnName;		// 中文名称
	private String disable;		// 停用
	
	public Country() {
		super();
	}

	public Country(String id){
		super(id);
	}
	
	@Length(min=0, max=6, message="顺序号长度必须介于 0 和 6 之间")
	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	
	@Length(min=0, max=16, message="大区长度必须介于 0 和 16 之间")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	@Length(min=0, max=16, message="国家代码长度必须介于 0 和 16 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=64, message="英文名称长度必须介于 0 和 64 之间")
	public String getGbName() {
		return gbName;
	}

	public void setGbName(String gbName) {
		this.gbName = gbName;
	}
	
	@Length(min=0, max=64, message="中文名称长度必须介于 0 和 64 之间")
	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	
	@Length(min=0, max=1, message="停用长度必须介于 0 和 1 之间")
	public String getDisable() {
		return disable;
	}
    @Description(value = "0 停用  1 启用")
	public void setDisable(String disable) {
		this.disable = disable;
	}
	
}