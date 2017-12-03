/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.listing.entity;

import org.hibernate.validator.constraints.Length;

import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * ebay sku 映射Entity
 * @author bill.xu
 * @version 2017-09-22
 */
public class EbaySkuMapping extends DataEntity<EbaySkuMapping> {
	
	private static final long serialVersionUID = 1L;
	private String sku;		// 平台sku
	private Long productId;		// 产品id
	private Long listingId;		// listing  id
	private Long codeManagerId;		// code manager id
	
	private String productCode; //对应刊登的 UPC或则是EAN 码
	
	
	/***
	 * 对应 listing 的库存数量
	 */
	private Integer quantity;
	
	

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public EbaySkuMapping(String id,String sku, Long productId, Long listingId,Long codeManagerId, String productCode) {
		super();
		this.id = id;
		this.sku = sku;
		this.productId = productId;
		this.listingId = listingId;
		this.codeManagerId = codeManagerId;
		this.productCode = productCode;
	}

	public EbaySkuMapping(Long productId, Long listingId) {
		super();
		this.productId = productId;
		this.listingId = listingId;
	}

	public EbaySkuMapping() {
		super();
	}

	public EbaySkuMapping(String id){
		super(id);
	}

	@Length(min=0, max=40, message="平台sku长度必须介于 0 和 40 之间")
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public Long getListingId() {
		return listingId;
	}

	public void setListingId(Long listingId) {
		this.listingId = listingId;
	}
	
	public Long getCodeManagerId() {
		return codeManagerId;
	}

	public void setCodeManagerId(Long codeManagerId) {
		this.codeManagerId = codeManagerId;
	}
	
}