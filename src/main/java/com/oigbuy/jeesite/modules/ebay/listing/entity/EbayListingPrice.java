/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.listing.entity;

import org.hibernate.validator.constraints.Length;

import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * ebay  listing 价格Entity
 * @author bill.xu
 * @version 2017-09-22
 */
public class EbayListingPrice extends DataEntity<EbayListingPrice> {
	
	private static final long serialVersionUID = 1L;
	private String sku;		// 对应的sku码
	private Long siteId;		// 站点id
	private Long listingId;		// listing id
	private String price;		// 价格
	
	
	
	
	public EbayListingPrice(String sku, Long listingId) {
		super();
		this.sku = sku;
		this.listingId = listingId;
	}

	public EbayListingPrice(String id,String sku, Long siteId, Long listingId,String price) {
		super();
		this.id = id;
		this.sku = sku;
		this.siteId = siteId;
		this.listingId = listingId;
		this.price = price;
	}

	public EbayListingPrice() {
		super();
	}

	public EbayListingPrice(String id){
		super(id);
	}

	@Length(min=0, max=40, message="对应的sku码长度必须介于 0 和 40 之间")
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
	
	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	
	public Long getListingId() {
		return listingId;
	}

	public void setListingId(Long listingId) {
		this.listingId = listingId;
	}
	
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
}