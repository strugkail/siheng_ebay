/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.productDevelop.entity;

import com.oigbuy.jeesite.common.persistence.DataEntity;

import javax.validation.constraints.NotNull;

/**
 * 产品和标签的关系表Entity
 * @author
 * @version 2017-09-07
 */
public class ProductTags extends DataEntity<ProductTags> {
	
	private static final long serialVersionUID = 1L;
	private Long productId;		// 产品Id
	private Long tagsId;		// 标签Id
	
	private String typeFlag;    //标签标识
	
	public ProductTags() {
		super();
	}

	
	public ProductTags(Long productId, Long tagsId) {
		super();
		this.productId = productId;
		this.tagsId = tagsId;
	}


	public ProductTags(String id){
		super(id);
	}

	@NotNull(message="产品Id不能为空")
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	@NotNull(message="标签Id不能为空")
	public Long getTagsId() {
		return tagsId;
	}

	public void setTagsId(Long tagsId) {
		this.tagsId = tagsId;
	}


	public String getTypeFlag() {
		return typeFlag;
	}


	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}
	
	
}