/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.entity;

import org.hibernate.validator.constraints.Length;

import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * 产品标题Entity
 * @author bill.xu
 * @version 2017-09-22
 */
public class ProductTitle extends DataEntity<ProductTitle> {
	
	private static final long serialVersionUID = 1L;
	private String titleId;		// 标题Id
	private String productId;		// 产品Id
	private String content;		// 内容
	private String code;		// 合成title码
	
	private String type;		// 标题类型  ，0 默认为主标题  ，1 为副标题 
	
	private String platformType; // 0表示 wish  1表示 ebay
	
	/***
	 * 必须关键字
	 */
	private String mainKeyContent;
	
	/***
	 * 非必须关键字
	 */
	private String subKeyContent;
	
	
	
	
	/***
	 * 主标题  副标题信息
	 */
	private String mainTitle;
	private String mainTitleId;
	private String subTitle;
	private String subTitleId;
	
	
	public ProductTitle(String titleId, String productId, String content,
			String code, String type, String platformType) {
		super();
		this.titleId = titleId;
		this.productId = productId;
		this.content = content;
		this.code = code;
		this.type = type;
		this.platformType = platformType;
	}

	public String getMainKeyContent() {
		return mainKeyContent;
	}

	public void setMainKeyContent(String mainKeyContent) {
		this.mainKeyContent = mainKeyContent;
	}

	public String getSubKeyContent() {
		return subKeyContent;
	}

	public void setSubKeyContent(String subKeyContent) {
		this.subKeyContent = subKeyContent;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public ProductTitle() {
		super();
	}

	public ProductTitle(String productId, String type) {
		super();
		this.productId = productId;
		this.type = type;
	}

	public ProductTitle(String id){
		super(id);
	}

	@Length(min=1, max=20, message="标题Id长度必须介于 1 和 20 之间")
	public String getTitleId() {
		return titleId;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}
	
	@Length(min=0, max=20, message="产品Id长度必须介于 0 和 20 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=300, message="内容长度必须介于 0 和 300 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=100, message="合成title码长度必须介于 0 和 100 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMainTitle() {
		return mainTitle;
	}

	public void setMainTitle(String mainTitle) {
		this.mainTitle = mainTitle;
	}

	public String getMainTitleId() {
		return mainTitleId;
	}

	public void setMainTitleId(String mainTitleId) {
		this.mainTitleId = mainTitleId;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getSubTitleId() {
		return subTitleId;
	}

	public void setSubTitleId(String subTitleId) {
		this.subTitleId = subTitleId;
	}
	
	
	
}