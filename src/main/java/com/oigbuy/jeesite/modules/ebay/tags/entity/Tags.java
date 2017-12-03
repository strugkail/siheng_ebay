/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.tags.entity;

import com.oigbuy.jeesite.common.persistence.DataEntity;

import org.hibernate.validator.constraints.Length;

/**
 * 标签Entity
 * @author mashuai
 * @version 2017-05-22
 */
public class Tags extends DataEntity<Tags> {
	
	private static final long serialVersionUID = 1L;
	private String typeId;		    // 类型Id
	private String tagsName;		// 标签名称
	private String description;		// 标签备注
	
	private String typeName;		// 类型名称
	private String typeFlag;        //类型标识
	
	public Tags() {
		super();
	}

	public Tags(String id){
		super(id);
	}

	@Length(min=0, max=20, message="类型Id长度必须介于 0 和 20 之间")
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String tagsType) {
		this.typeId = tagsType;
	}
	
	@Length(min=0, max=32, message="标签名称长度必须介于 0 和 32 之间")
	public String getTagsName() {
		return tagsName;
	}

	public void setTagsName(String tagsName) {
		this.tagsName = tagsName;
	}
	
	@Length(min=0, max=300, message="标签备注长度必须介于 0 和 300 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeFlag() {
		return typeFlag;
	}

	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}

	@Override
	public String toString() {
		return "Tags [id=" + id + ", typeId=" + typeId + ", tagsName=" + tagsName
				+ ", description=" + description + "]";
	}
	
}