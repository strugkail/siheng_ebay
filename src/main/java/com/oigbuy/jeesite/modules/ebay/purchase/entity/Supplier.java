/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.purchase.entity;

import com.oigbuy.jeesite.common.persistence.DataEntity;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 供应商信息Entity
 * @author 马志明
 * @version 2017-06-18
 */
public class Supplier extends DataEntity<Supplier> {
	
	private static final long serialVersionUID = 1L;
	
	private String supplierId;		// 供应商ID
	private String supplierName;		// 供应商名称
	
	public Supplier() {
		super();
	}

	public Supplier(String id){
		super(id);
	}

	@NotNull(message="供应商ID不能为空")
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
	@Length(min=0, max=255, message="供应商名称长度必须介于 0 和 255 之间")
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
}