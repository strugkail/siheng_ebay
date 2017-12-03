/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.mode.entity;

import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * 屏蔽目的地模板Entity
 * @author bill.xu
 * @version 2017-09-05
 */
public class ShieldCountryMode extends DataEntity<ShieldCountryMode> {
	
	private static final long serialVersionUID = 1L;
	private String modeName;		// 模板名称
	private String siteId;		// 站点id
	private String saleTypeId;		// 销售类型（可使用枚举或者是关联表待定）
	//private String deliverTypeId;		// 发货方式编码或者id
	
	private String countryId;		// 屏蔽的国家id（多个用逗号分隔开）
	
	private String countryName;		//屏蔽的国家名称（额外添加的属性）
	
	
	private String 	deliveryWarehouse; //发货仓库
	
	
	

	public ShieldCountryMode() {
		super();
	}

	public ShieldCountryMode(String siteId, String saleTypeId,
			String deliveryWarehouse) {
		super();
		this.siteId = siteId;
		this.saleTypeId = saleTypeId;
		this.deliveryWarehouse = deliveryWarehouse;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	
	

	public ShieldCountryMode(String id){
		super(id);
	}

	public String getModeName() {
		return modeName;
	}

	public void setModeName(String modeName) {
		this.modeName = modeName;
	}
	
	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	
	public String getSaleTypeId() {
		return saleTypeId;
	}

	public void setSaleTypeId(String saleTypeId) {
		this.saleTypeId = saleTypeId;
	}
	
	public String getDeliveryWarehouse() {
		return deliveryWarehouse;
	}

	public void setDeliveryWarehouse(String deliveryWarehouse) {
		this.deliveryWarehouse = deliveryWarehouse;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	
}