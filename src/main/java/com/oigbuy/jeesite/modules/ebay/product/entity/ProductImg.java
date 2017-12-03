/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.entity;

import com.oigbuy.jeesite.common.persistence.DataEntity;

import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 产品图片Entity
 * @author mashuai
 * @version 2017-05-23
 */
public class ProductImg extends DataEntity<ProductImg> {
	
	private static final long serialVersionUID = 1L;
	private String productId;		// 产品Id
	private String code;		// 图片码（自增长码,越短越好）
	private String imgType;		// 图片类型（1：主图，2：非主图）
	private String imgUrl;		// 图片地址
	private String id;
	private String indexSeq;
	private String parentId;	//父图ID
	private String codeManagerId;    //子代码ID
	private String templateIden;	//模板标识(0：非模板图片  1：模板图片)
	private String platformFlag;  //平台标识（product_Img表中没有，关联product_img_platform表中的属性）
	
	/***
	 * 图片关联的产品子代码（sku）信息
	 */
	private ProductCodeManager productCodeManager;
	
	
	/***
	 *  sku 图片 集合 
	 *  
	 */
	private List<ProductImg> skuImageList;
	
	/**
	 * 合成图片的合成码
	 */
	private String compositeCode; 


	
	public ProductImg(String productId, String imgUrl, String id,String platformFlag) {
		super();
		this.productId = productId;
		this.imgUrl = imgUrl;
		this.id = id;
		this.platformFlag = platformFlag;
	}

	public ProductCodeManager getProductCodeManager() {
		return productCodeManager;
	}

	public void setProductCodeManager(ProductCodeManager productCodeManager) {
		this.productCodeManager = productCodeManager;
	}

	public List<ProductImg> getSkuImageList() {
		return skuImageList;
	}

	public void setSkuImageList(List<ProductImg> skuImageList) {
		this.skuImageList = skuImageList;
	}

	public ProductImg() {
		super();
	}

	public ProductImg(String id){
		super(id);
	}

	@Length(min=0, max=20, message="产品Id长度必须介于 0 和 20 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=11, message="图片码（自增长码,越短越好）长度必须介于 0 和 11 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=11, message="图片类型（1：主图，2：非主图）长度必须介于 0 和 11 之间")
	public String getImgType() {
		return imgType;
	}

	public void setImgType(String imgType) {
		this.imgType = imgType;
	}
	
	@Length(min=0, max=40, message="图片地址长度必须介于 0 和 40 之间")
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ProductImg [id=" + id + ",imgUrl=" + imgUrl + "]";
	}

	public String getIndexSeq() {
		return indexSeq;
	}

	public void setIndexSeq(String indexSeq) {
		this.indexSeq = indexSeq;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCodeManagerId() {
		return codeManagerId;
	}

	public void setCodeManagerId(String codeManagerId) {
		this.codeManagerId = codeManagerId;
	}

	public String getTemplateIden() {
		return templateIden;
	}

	public void setTemplateIden(String templateIden) {
		this.templateIden = templateIden;
	}

	public String getPlatformFlag() {
		return platformFlag;
	}

	public void setPlatformFlag(String platformFlag) {
		this.platformFlag = platformFlag;
	}

	public String getCompositeCode() {
		return compositeCode;
	}

	public void setCompositeCode(String compositeCode) {
		this.compositeCode = compositeCode;
	}
	
	
}