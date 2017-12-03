package com.oigbuy.jeesite.modules.ebay.listing.dto;

import java.math.BigDecimal;
import java.util.List;

import com.oigbuy.jeesite.modules.ebay.listing.entity.EbayListingImg;
import com.oigbuy.jeesite.modules.ebay.listing.entity.ListingProperty;


/***
 * listing  下的 code manager 
 * 
 * @author bill.xu
 *
 */
public class ListingCodeManagerDto {

	
	/**
	 * id 
	 */
	private String id;
	
	/***
	 *codeManagerId
	 */
	private String codeManagerId;
	
	/***
	 * listing code manager 刊登价格（根据站点计算出来 不能重复）
	 */
	private BigDecimal publishPrice;
	
	/**
	 * listing  下的 code manager 的图片（根据 listingid 和 codemanagerId 查询得到）
	 */
	private EbayListingImg codeManagerImage;
	
	
	/***
	 * code manager 的 多属性
	 */
	private List<ListingProperty> productPropertyList; 

	
	/**
	 * 对应刊登的 UPC/EAN
	 */
	private String productCode;
	
	
	/***
	 * 采办数量（listing 中作为 库存数量）
	 */
	private int recommendNumber;
	
	
	/**
	 * 多属性的 sku
	 */
	private String sku;
	
	
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getSku() {
		return sku;
	}


	public void setSku(String sku) {
		this.sku = sku;
	}


	public int getRecommendNumber() {
		return recommendNumber;
	}


	public void setRecommendNumber(int recommendNumber) {
		this.recommendNumber = recommendNumber;
	}


	public String getProductCode() {
		return productCode;
	}


	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}


	public List<ListingProperty> getProductPropertyList() {
		return productPropertyList;
	}


	public void setProductPropertyList(List<ListingProperty> productPropertyList) {
		this.productPropertyList = productPropertyList;
	}


	public BigDecimal getPublishPrice() {
		return publishPrice;
	}


	public void setPublishPrice(BigDecimal publishPrice) {
		this.publishPrice = publishPrice;
	}


	public EbayListingImg getCodeManagerImage() {
		return codeManagerImage;
	}


	public void setCodeManagerImage(EbayListingImg codeManagerImage) {
		this.codeManagerImage = codeManagerImage;
	}


	public String getCodeManagerId() {
		return codeManagerId;
	}


	public void setCodeManagerId(String codeManagerId) {
		this.codeManagerId = codeManagerId;
	}
	

	
	
}
