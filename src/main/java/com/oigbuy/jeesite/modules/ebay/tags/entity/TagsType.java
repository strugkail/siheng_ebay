/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.tags.entity;

import com.oigbuy.jeesite.common.persistence.DataEntity;

import org.hibernate.validator.constraints.Length;

/**
 * 标签类型Entity
 * @author mashuai
 * @version 2017-05-22
 */
public class TagsType extends DataEntity<TagsType> {
	
	private static final long serialVersionUID = 1L;
	private String typeName;		// 类型名称
	private String description;		// 类型描述
	private String typeFlag;        //类型标识  （1.产品标签 2.规格标签 3.店铺标签..）
	
	public TagsType() {
		super();
	}

	public TagsType(String id){
		super(id);
	}

	@Length(min=1, max=32, message="类型名称长度必须介于 1 和 32 之间")
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	@Length(min=0, max=300, message="类型描述长度必须介于 0 和 300 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTypeFlag() {
		return typeFlag;
	}

	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}
	
	
	
}