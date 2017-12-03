package com.oigbuy.jeesite.modules.ebay.purchase.dto;

/**
 * 采购源dto
 * 
 * @author ming.ma
 *
 */
public class PurchaseSource {

	private String sourceId; // 采购源ID
	private String supplierId; // 供应商ID
	private String supplierName; // 供应商名称
	private String productId; // 产品ID
	private String sourceUrl; // 采购URL
	private String remark;   //备注

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String pruductId) {
		this.productId = pruductId;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
