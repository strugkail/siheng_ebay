/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * 产品图片Entity
 * @author AA
 * @version 2017-10-26
 */
public class TProductImgPlatform extends DataEntity<TProductImgPlatform> {
	
	private static final long serialVersionUID = 1L;
	private String imgId;		// img_id
	private String platformId;		// platform_id
	private String productId;		// product_id
	private String codeManagerId;		// code_manager_id
	private String imgType;		// img_type
	private Integer index;		// index
	private String parentId;		// parent_id
	private String originalImgId;		// 制图前的图片ID
	private String designImgFlag;		// 是否制图标志
	private String platformFlag;		// 平台标识  
	private String templateIden;		//模板标识(0：非模板图片  1：模板图片)

	
	/**
	 * 非持久化字段   
	 */
	private String imgUrl;
	
	
	/***
	 * 子  g  图的集合 
	 */
	private	List<TProductImgPlatform> tProductImgPlatformList;
	
	
	/***
	 * 图片关联的产品子代码（sku）信息
	 */
	private ProductCodeManager productCodeManager;
	
	
	
	/***
	 * 产品图片
	 */
	private ProductImg productImg;
	
	/**
	 * 拼图Code
	 */
	private String imgCode;  
	
	
	public ProductImg getProductImg() {
		return productImg;
	}

	public void setProductImg(ProductImg productImg) {
		this.productImg = productImg;
	}

	public TProductImgPlatform() {
		super();
	}
	
	public ProductCodeManager getProductCodeManager() {
		return productCodeManager;
	}

	public void setProductCodeManager(ProductCodeManager productCodeManager) {
		this.productCodeManager = productCodeManager;
	}




	public List<TProductImgPlatform> gettProductImgPlatformList() {
		return tProductImgPlatformList;
	}

	public void settProductImgPlatformList(
			List<TProductImgPlatform> tProductImgPlatformList) {
		this.tProductImgPlatformList = tProductImgPlatformList;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public TProductImgPlatform(String id){
		super(id);
	}

	@Length(min=0, max=11, message="img_id长度必须介于 0 和 11 之间")
	public String getImgId() {
		return imgId;
	}

	public void setImgId(String imgId) {
		this.imgId = imgId;
	}
	
	@Length(min=0, max=11, message="platform_id长度必须介于 0 和 11 之间")
	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}
	
	@Length(min=0, max=11, message="product_id长度必须介于 0 和 11 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=11, message="code_manager_id长度必须介于 0 和 11 之间")
	public String getCodeManagerId() {
		return codeManagerId;
	}

	public void setCodeManagerId(String codeManagerId) {
		this.codeManagerId = codeManagerId;
	}
	
	@Length(min=0, max=255, message="img_type长度必须介于 0 和 255 之间")
	public String getImgType() {
		return imgType;
	}

	public void setImgType(String imgType) {
		this.imgType = imgType;
	}
	
	@Length(min=0, max=255, message="index长度必须介于 0 和 255 之间")
	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Length(min=0, max=11, message="制图前的图片ID长度必须介于 0 和 11 之间")
	public String getOriginalImgId() {
		return originalImgId;
	}

	public void setOriginalImgId(String originalImgId) {
		this.originalImgId = originalImgId;
	}
	
	@Length(min=0, max=1, message="是否制图标志长度必须介于 0 和 1 之间")
	public String getDesignImgFlag() {
		return designImgFlag;
	}

	public void setDesignImgFlag(String designImgFlag) {
		this.designImgFlag = designImgFlag;
	}
	
	@Length(min=0, max=2, message="平台标识长度必须介于 0 和 2 之间")
	public String getPlatformFlag() {
		return platformFlag;
	}

	public void setPlatformFlag(String platformFlag) {
		this.platformFlag = platformFlag;
	}

	public String getTemplateIden() {
		return templateIden;
	}

	public void setTemplateIden(String templateIden) {
		this.templateIden = templateIden;
	}

	public String getImgCode() {
		return imgCode;
	}

	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}
	
	
}