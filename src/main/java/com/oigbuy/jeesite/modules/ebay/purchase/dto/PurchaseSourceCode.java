package com.oigbuy.jeesite.modules.ebay.purchase.dto;

/**
 * 采购源与产品代码关系
 * 
 * @author ming.ma
 *
 */
public class PurchaseSourceCode {
	
	private String sourceCodeId; // 源码ID
	private String codeId; // 代码ID
	private String sourceId; // 采购源ID
	private String productId; // 产品ID

	public PurchaseSourceCode() {
	}

	public PurchaseSourceCode(String sourceCodeId, String codeId,
							  String sourceId, String productId) {
		this.sourceCodeId = sourceCodeId;
		this.codeId = codeId;
		this.sourceId = sourceId;
		this.productId = productId;
	}

	public String getSourceCodeId() {
		return sourceCodeId;
	}

	public void setSourceCodeId(String sourceCodeId) {
		this.sourceCodeId = sourceCodeId;
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
}
