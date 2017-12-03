package com.oigbuy.jeesite.modules.ebay.product.entity;

import java.io.Serializable;

public class MessureMmsParam implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String processNum;			//流程号
	private String siteName;			//站点名称
	private String categoryName;		//Ebay分类名称		
	private String subCategoryName;		//Ebay二级分类名称
	private Double startPrice;			//售价
	private Double postage;				//平台运费
	private String productUrl;			//商品url
	private String title;				//产品标题
	private String itemId;				//竞争对手id
	private String sellerGroupName;		//销售组名称
	private String name;				//中文名称
	private String picture;				//主图片(base64加密)
	private String collectionQuantity;   //竞品采购数量
	private String description;			//描述
	private String tariff;					//关税

	
	public MessureMmsParam() {
		super();
	}


	public String getProcessNum() {
		return processNum;
	}


	public void setProcessNum(String processNum) {
		this.processNum = processNum;
	}


	public String getSiteName() {
		return siteName;
	}


	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}


	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public String getSubCategoryName() {
		return subCategoryName;
	}


	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}


	public Double getStartPrice() {
		return startPrice;
	}


	public void setStartPrice(Double startPrice) {
		this.startPrice = startPrice;
	}


	public Double getPostage() {
		return postage;
	}


	public void setPostage(Double postage) {
		this.postage = postage;
	}


	public String getProductUrl() {
		return productUrl;
	}


	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getItemId() {
		return itemId;
	}


	public void setItemId(String itemId) {
		this.itemId = itemId;
	}


	public String getSellerGroupName() {
		return sellerGroupName;
	}


	public void setSellerGroupName(String sellerGroupName) {
		this.sellerGroupName = sellerGroupName;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPicture() {
		return picture;
	}


	public void setPicture(String picture) {
		this.picture = picture;
	}


	public String getCollectionQuantity() {
		return collectionQuantity;
	}


	public void setCollectionQuantity(String collectionQuantity) {
		this.collectionQuantity = collectionQuantity;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public String getTariff() {
		return tariff;
	}

	public void setTariff(String tariff) {
		this.tariff = tariff;
	}
}
