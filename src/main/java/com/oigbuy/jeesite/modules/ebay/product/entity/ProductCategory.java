/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.entity;

import java.util.List;

import com.oigbuy.api.domain.ebay.UsualDto;
import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * 产品分类Entity
 * @author bill.xu
 * @version 2017-09-22
 */
public class ProductCategory extends DataEntity<ProductCategory> {
	
	private static final long serialVersionUID = 1L;
	
	private Long productId;		// 产品id
	private Long siteId;		// 站点id
	private Long platformId;	// 平台id
	private String categoryName;		// 分类名称
	private String orderNum;		// 排序说明   1 为分类 1  2为分类 2  0表示自定义分类
	
	private String categoryCode;//对应 ebay 的产品分类id
	
	/***
	 * listing Id 
	 */
	private Long listingId;
	
	/***
	 * 该分类下的 分类细节
	 */
	private List<ItemSpecifics> itemSpecificsList;
	
	/***
	 * 分类下的 conditions
	 */
	private List<UsualDto> conditions;
	
	

	/***
	 * 通过分类获取该分类下是不是多属性产品
	 */
	private String mutilAttribute;

	public ProductCategory(String orderNum, Long listingId) {
		super();
		this.orderNum = orderNum;
		this.listingId = listingId;
	}



	public ProductCategory(String categoryName, String categoryCode) {
		super();
		this.categoryName = categoryName;
		this.categoryCode = categoryCode;
	}



	public List<UsualDto> getConditions() {
		return conditions;
	}



	public void setConditions(List<UsualDto> conditions) {
		this.conditions = conditions;
	}



	public ProductCategory(String id,Long productId, Long siteId, String categoryName,String orderNum) {
		super();
		this.id = id;
		this.productId = productId;
		this.siteId = siteId;
		this.categoryName = categoryName;
		this.orderNum = orderNum;
	}

	
	
	public String getCategoryCode() {
		return categoryCode;
	}



	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}



	public List<ItemSpecifics> getItemSpecificsList() {
		return itemSpecificsList;
	}

	public void setItemSpecificsList(List<ItemSpecifics> itemSpecificsList) {
		this.itemSpecificsList = itemSpecificsList;
	}

	public ProductCategory(Long productId, Long siteId) {
		super();
		this.productId = productId;
		this.siteId = siteId;
	}

	public ProductCategory() {
		super();
	}

	public ProductCategory(String id){
		super(id);
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	
	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Long getListingId() {
		return listingId;
	}

	public void setListingId(Long listingId) {
		this.listingId = listingId;
	}
	public String getMutilAttribute() {
		return mutilAttribute;
	}
	public void setMutilAttribute(String mutilAttribute) {
		this.mutilAttribute = mutilAttribute;
	}
}