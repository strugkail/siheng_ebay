/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.entity;

import org.hibernate.validator.constraints.Length;

import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * 标题关键字Entity
 * @author bill.xu
 * @version 2017-09-22
 */
public class TitleKey extends DataEntity<TitleKey> {
	
	private static final long serialVersionUID = 1L;
	private Long productId;		// 产品Id
	private Integer titleType;		// 类型（0：必选，1：非必选）
	private Integer code;		// 标题码
	private String titleKey;		// 关键词
	
	private String platformType; //0 表示 wish 1表示 ebay
	
	private String type;//0 表示 主标题关键字  1 副标题关键字
	
	/***
	 *标题id
	 */
	private Long titleId;
	
	
	
	
	public TitleKey(String id ,Long productId, Integer titleType, Integer code,
			String titleKey, String platformType, String type, Long titleId) {
		super();
		this.id=id;
		this.productId = productId;
		this.titleType = titleType;
		this.code = code;
		this.titleKey = titleKey;
		this.platformType = platformType;
		this.type = type;
		this.titleId = titleId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public TitleKey(Long productId, String platformType,String type) {
		super();
		this.productId = productId;
		this.platformType = platformType;
		this.type = type;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public TitleKey() {
		super();
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public Integer getTitleType() {
		return titleType;
	}

	public void setTitleType(Integer titleType) {
		this.titleType = titleType;
	}
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	
	@Length(min=0, max=100, message="关键词长度必须介于 0 和 100 之间")
	public String getTitleKey() {
		return titleKey;
	}

	public void setTitleKey(String titleKey) {
		this.titleKey = titleKey;
	}

	public Long getTitleId() {
		return titleId;
	}

	public void setTitleId(Long titleId) {
		this.titleId = titleId;
	}
	
}