/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.entity;

import org.hibernate.validator.constraints.Length;

import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * 产品描述Entity
 * @author bill.xu
 * @version 2017-10-19
 */
public class ProductDescription extends DataEntity<ProductDescription> {
	
	private static final long serialVersionUID = 1L;
	private Long productId;		// 产品id
	private String content;		// 描述内容
	private String translateContent;		// 描述对应的英文翻译描述
	private String platformType;		// 平台标志  0 表示 wish，1 表示 ebay
	private String type;		// 标识  1、 自主输入一个  2、多个组合
	
	
	
	
	public ProductDescription(Long productId, String platformType) {
		super();
		this.productId = productId;
		this.platformType = platformType;
	}

	public ProductDescription(String id,Long productId, String content,
			String translateContent, String platformType, String type) {
		super();
		super.id = id;
		this.productId = productId;
		this.content = content;
		this.translateContent = translateContent;
		this.platformType = platformType;
		this.type = type;
	}

	public ProductDescription() {
		super();
	}

	public ProductDescription(String id){
		super(id);
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=3000, message="描述内容长度必须介于 0 和 3000 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	public String getTranslateContent() {
		return translateContent;
	}

	public void setTranslateContent(String translateContent) {
		this.translateContent = translateContent;
	}

	@Length(min=0, max=2, message="平台标志  0 表示 wish，1 表示 ebay长度必须介于 0 和 2 之间")
	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}
	
	@Length(min=0, max=2, message="标识  1、 自主输入一个  2、多个组合长度必须介于 0 和 2 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}