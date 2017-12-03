/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.dto;

import java.util.List;

import com.oigbuy.jeesite.modules.ebay.logistics.entity.LogisticsMode;
import com.oigbuy.jeesite.modules.ebay.mode.entity.PublishStyleMode;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatformSite;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductCodeManager;
import com.oigbuy.jeesite.modules.ebay.template.entity.BuyerRestriction;
import com.oigbuy.jeesite.modules.ebay.template.entity.LocationofGoods;
import com.oigbuy.jeesite.modules.ebay.template.entity.ReturnPurchase;


/**
 * 产品详细（页面编辑）
 * 
 * @author tony.liu
 */
public class ProductDetail {
	
	/***
	 * 站点信息
	 */
	private PlatformSite platformSite;
	
	/***
	 * 产品model
	 */
	private  ProductDto product;
//	
//	/***
//	 * item Specifics
//	 */
//	private List<ItemSpecifics> itemSpecificsList;
	
	/***
	 * 产品下 sku 信息
	 */
	private List<ProductCodeManager>  codeManagerList;
	
	/***
	 * 刊登风格
	 */
	private PublishStyleMode publishStyleMode;
	
	/***
	 * 物流模板
	 */
	private LogisticsMode logisticsMode;
	
	/***
	 * 商品所在地
	 */
	private LocationofGoods locationofGoods;
	
	/***
	 * 退货模板
	 */
	private ReturnPurchase returnPurchase;
	
	/**其他   end  */
	/***
	 * 买家限制
	 */
	private BuyerRestriction buyerRestriction;
	

	/**
	 * 自主追加的 item specifics 
	 */
	private String[] itemSpecificsNames;		
	private String[] itemSpecificsValues;	
	private String[] itemSpecificsRemarks;	
	
	/**
	 * 分类 一 下的细节
	 */
	private String[] itemSpecificsNames1;		
	private String[] itemSpecificsValues1;		
	private String[] itemSpecificsRemarks1;
	
	
	/**
	 *分类 二 下的细节
	 */
	private String[] itemSpecificsNames2;		
	private String[] itemSpecificsValues2;		
	private String[] itemSpecificsRemarks2;
	
	
	
	/***
	 * 主 g 图的id集合
	 */
	private 	String []  mainImageId1;
	private 	String []  mainImageUrl1;
	/***
	 * 细节 图的id集合
	 */
	private 	String []  mainImageId2;
	private 	String []  mainImageUrl2;
	/***
	 * 特效 图的id集合
	 */
	private 	String []  mainImageId5;
	private 	String []  mainImageUrl5;
	
	/***
	 * 卖家账号id
	 */
	private  String sellerAccount;
	
	/***
	 * 站点id
	 */
	private String siteId;
	
	/***
	 * 描述 翻译后的
	 */
	private List<String> productDescritions;
	/***
	 * 描述 
	 */
	private List<String> productDescritionsT;
	/***
	 * 描述2 翻译后的
	 */
	private String productDescritions2;
	/***
	 * 描述2
	 */
	private String productDescritions2T;
	
	
	/**
	 * 自定义的属性 name 集合
	 */
	private List<String> productPropertyNames;
	
	
	/***
	 * 通过 itemid 查询分类得到该分类下的 是不是多属性（多 sku ）
	 */
	private String multiAttribute;
	
	
	public List<String> getProductPropertyNames() {
		return productPropertyNames;
	}

	public void setProductPropertyNames(List<String> productPropertyNames) {
		this.productPropertyNames = productPropertyNames;
	}

	public List<String> getProductDescritionsT() {
		return productDescritionsT;
	}

	public void setProductDescritionsT(List<String> productDescritionsT) {
		this.productDescritionsT = productDescritionsT;
	}

	public String getProductDescritions2T() {
		return productDescritions2T;
	}

	public void setProductDescritions2T(String productDescritions2T) {
		this.productDescritions2T = productDescritions2T;
	}

	public List<String> getProductDescritions() {
		return productDescritions;
	}

	public void setProductDescritions(List<String> productDescritions) {
		this.productDescritions = productDescritions;
	}

	public String getProductDescritions2() {
		return productDescritions2;
	}

	public void setProductDescritions2(String productDescritions2) {
		this.productDescritions2 = productDescritions2;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getSellerAccount() {
		return sellerAccount;
	}

	public void setSellerAccount(String sellerAccount) {
		this.sellerAccount = sellerAccount;
	}

	public String[] getItemSpecificsNames() {
		return itemSpecificsNames;
	}

	public void setItemSpecificsNames(String[] itemSpecificsNames) {
		this.itemSpecificsNames = itemSpecificsNames;
	}

	public String[] getItemSpecificsValues() {
		return itemSpecificsValues;
	}

	public void setItemSpecificsValues(String[] itemSpecificsValues) {
		this.itemSpecificsValues = itemSpecificsValues;
	}

	public String[] getItemSpecificsRemarks() {
		return itemSpecificsRemarks;
	}

	public void setItemSpecificsRemarks(String[] itemSpecificsRemarks) {
		this.itemSpecificsRemarks = itemSpecificsRemarks;
	}

	
	public String[] getItemSpecificsNames1() {
		return itemSpecificsNames1;
	}

	public void setItemSpecificsNames1(String[] itemSpecificsNames1) {
		this.itemSpecificsNames1 = itemSpecificsNames1;
	}

	public String[] getItemSpecificsValues1() {
		return itemSpecificsValues1;
	}

	public void setItemSpecificsValues1(String[] itemSpecificsValues1) {
		this.itemSpecificsValues1 = itemSpecificsValues1;
	}

	public String[] getItemSpecificsRemarks1() {
		return itemSpecificsRemarks1;
	}

	public void setItemSpecificsRemarks1(String[] itemSpecificsRemarks1) {
		this.itemSpecificsRemarks1 = itemSpecificsRemarks1;
	}

	public String[] getItemSpecificsNames2() {
		return itemSpecificsNames2;
	}

	public void setItemSpecificsNames2(String[] itemSpecificsNames2) {
		this.itemSpecificsNames2 = itemSpecificsNames2;
	}

	public String[] getItemSpecificsValues2() {
		return itemSpecificsValues2;
	}

	public void setItemSpecificsValues2(String[] itemSpecificsValues2) {
		this.itemSpecificsValues2 = itemSpecificsValues2;
	}

	public String[] getItemSpecificsRemarks2() {
		return itemSpecificsRemarks2;
	}

	public void setItemSpecificsRemarks2(String[] itemSpecificsRemarks2) {
		this.itemSpecificsRemarks2 = itemSpecificsRemarks2;
	}

	public BuyerRestriction getBuyerRestriction() {
		return buyerRestriction;
	}

	public void setBuyerRestriction(BuyerRestriction buyerRestriction) {
		this.buyerRestriction = buyerRestriction;
	}

	public ReturnPurchase getReturnPurchase() {
		return returnPurchase;
	}

	public void setReturnPurchase(ReturnPurchase returnPurchase) {
		this.returnPurchase = returnPurchase;
	}

	public LogisticsMode getLogisticsMode() {
		return logisticsMode;
	}

	public void setLogisticsMode(LogisticsMode logisticsMode) {
		this.logisticsMode = logisticsMode;
	}


//	public List<ItemSpecifics> getItemSpecificsList() {
//		return itemSpecificsList;
//	}
//
//	public void setItemSpecificsList(List<ItemSpecifics> itemSpecificsList) {
//		this.itemSpecificsList = itemSpecificsList;
//	}

	public ProductDto getProduct() {
		return product;
	}

	public void setProduct(ProductDto product) {
		this.product = product;
	}

	public PublishStyleMode getPublishStyleMode() {
		return publishStyleMode;
	}

	public void setPublishStyleMode(PublishStyleMode publishStyleMode) {
		this.publishStyleMode = publishStyleMode;
	}

	public LocationofGoods getLocationofGoods() {
		return locationofGoods;
	}

	public void setLocationofGoods(LocationofGoods locationofGoods) {
		this.locationofGoods = locationofGoods;
	}

	public PlatformSite getPlatformSite() {
		return platformSite;
	}

	public void setPlatformSite(PlatformSite platformSite) {
		this.platformSite = platformSite;
	}
	

	public List<ProductCodeManager> getCodeManagerList() {
		return codeManagerList;
	}

	public void setCodeManagerList(List<ProductCodeManager> codeManagerList) {
		this.codeManagerList = codeManagerList;
	}

	public String[] getMainImageId1() {
		return mainImageId1;
	}

	public void setMainImageId1(String[] mainImageId1) {
		this.mainImageId1 = mainImageId1;
	}

	public String[] getMainImageId2() {
		return mainImageId2;
	}

	public void setMainImageId2(String[] mainImageId2) {
		this.mainImageId2 = mainImageId2;
	}

	public String[] getMainImageId5() {
		return mainImageId5;
	}

	public void setMainImageId5(String[] mainImageId5) {
		this.mainImageId5 = mainImageId5;
	}

	public String[] getMainImageUrl1() {
		return mainImageUrl1;
	}

	public void setMainImageUrl1(String[] mainImageUrl1) {
		this.mainImageUrl1 = mainImageUrl1;
	}

	public String[] getMainImageUrl2() {
		return mainImageUrl2;
	}

	public void setMainImageUrl2(String[] mainImageUrl2) {
		this.mainImageUrl2 = mainImageUrl2;
	}

	public String[] getMainImageUrl5() {
		return mainImageUrl5;
	}

	public void setMainImageUrl5(String[] mainImageUrl5) {
		this.mainImageUrl5 = mainImageUrl5;
	}

	public String getMultiAttribute() {
		return multiAttribute;
	}

	public void setMultiAttribute(String multiAttribute) {
		this.multiAttribute = multiAttribute;
	}


}