package com.oigbuy.jeesite.modules.ebay.listing.dto;

import java.util.List;

import com.oigbuy.api.domain.ebay.UsualDto;
import com.oigbuy.jeesite.modules.ebay.listing.entity.EbayListing;
import com.oigbuy.jeesite.modules.ebay.listing.entity.EbayListingImg;
import com.oigbuy.jeesite.modules.ebay.logistics.entity.LogisticsMode;
import com.oigbuy.jeesite.modules.ebay.mode.entity.PublishStyleMode;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatformSite;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductCategory;
import com.oigbuy.jeesite.modules.ebay.template.entity.BuyerRestriction;
import com.oigbuy.jeesite.modules.ebay.template.entity.LocationofGoods;
import com.oigbuy.jeesite.modules.ebay.template.entity.ReturnPurchase;

/***
 * 
 * 
 * @author bill.xu
 *
 */
public class ListingDetailDto extends  EbayListing {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4387355541520509380L;

	/***
	 * 站点
	 */
	private PlatformSite platformSite;
	
	/***
	 * listing 主 g 图
	 */
	private EbayListingImg mainListingImg;

	/**
	 * listing 细节图
	 */
	private List<EbayListingImg> detailListingImg;
	
	/***
	 * listing 特效图 
	 */
	private List<EbayListingImg> specialListingImg;
	
	/***
	 * listing 下的 code manager 集合,其中有 图片 价格信息，属性
	 */
	private List<ListingCodeManagerDto> listingCodeManagerList;
	
	
	/***
	 * listing  分类 该信息 也要实时的从 ebay 上取，如果数据库中存在自定义的值，也要能加载出来
	 */
	private List<ProductCategory> productCategoryList;
	
	/***
	 * 分类下的 conditions 
	 */
	private List<UsualDto> conditions;
	
	
	/***
	 * listing 的 物流模板
	 */
	private LogisticsMode logisticsMode;
	
	/***
	 * 商品所在地模板 
	 */
	private  LocationofGoods locationofGoods;

	/**
	 * 退货模板
	 */
	private ReturnPurchase returnPurchase;
	
	/***
	 * 买家限制
	 */
	private BuyerRestriction buyerRestriction;
	


	/***
	 * 刊登模板
	 */
	private PublishStyleMode publishStyleMode;
	
	
	/**
	 * 自定义的 item specifics  name
	 */
	private List<String> itemSpecificsNames;
	
	/**
	 * 自定义的 item specifics  value
	 */
	private List<String> itemSpecificsValues;
	
	/**
	 * 自定义的 item specifics  remark
	 */
	private List<String> itemSpecificsRemarks;
	

	/***
	 * listing 属性的 name 集合
	 */
	private List<String>  listingPropertyNames;
	
	
	
	
	
	
	public PublishStyleMode getPublishStyleMode() {
		return publishStyleMode;
	}

	public void setPublishStyleMode(PublishStyleMode publishStyleMode) {
		this.publishStyleMode = publishStyleMode;
	}

	public List<String> getListingPropertyNames() {
		return listingPropertyNames;
	}

	public void setListingPropertyNames(List<String> listingPropertyNames) {
		this.listingPropertyNames = listingPropertyNames;
	}

	public List<String> getItemSpecificsNames() {
		return itemSpecificsNames;
	}

	public void setItemSpecificsNames(List<String> itemSpecificsNames) {
		this.itemSpecificsNames = itemSpecificsNames;
	}

	public List<String> getItemSpecificsValues() {
		return itemSpecificsValues;
	}

	public void setItemSpecificsValues(List<String> itemSpecificsValues) {
		this.itemSpecificsValues = itemSpecificsValues;
	}

	public List<String> getItemSpecificsRemarks() {
		return itemSpecificsRemarks;
	}

	public void setItemSpecificsRemarks(List<String> itemSpecificsRemarks) {
		this.itemSpecificsRemarks = itemSpecificsRemarks;
	}

	public List<UsualDto> getConditions() {
		return conditions;
	}

	public void setConditions(List<UsualDto> conditions) {
		this.conditions = conditions;
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

	public LocationofGoods getLocationofGoods() {
		return locationofGoods;
	}

	public void setLocationofGoods(LocationofGoods locationofGoods) {
		this.locationofGoods = locationofGoods;
	}

	public LogisticsMode getLogisticsMode() {
		return logisticsMode;
	}

	public void setLogisticsMode(LogisticsMode logisticsMode) {
		this.logisticsMode = logisticsMode;
	}

	public List<ProductCategory> getProductCategoryList() {
		return productCategoryList;
	}

	public void setProductCategoryList(List<ProductCategory> productCategoryList) {
		this.productCategoryList = productCategoryList;
	}

	public PlatformSite getPlatformSite() {
		return platformSite;
	}

	public void setPlatformSite(PlatformSite platformSite) {
		this.platformSite = platformSite;
	}

	public EbayListingImg getMainListingImg() {
		return mainListingImg;
	}

	public void setMainListingImg(EbayListingImg mainListingImg) {
		this.mainListingImg = mainListingImg;
	}

	public List<EbayListingImg> getDetailListingImg() {
		return detailListingImg;
	}

	public void setDetailListingImg(List<EbayListingImg> detailListingImg) {
		this.detailListingImg = detailListingImg;
	}

	public List<EbayListingImg> getSpecialListingImg() {
		return specialListingImg;
	}

	public void setSpecialListingImg(List<EbayListingImg> specialListingImg) {
		this.specialListingImg = specialListingImg;
	}

	public List<ListingCodeManagerDto> getListingCodeManagerList() {
		return listingCodeManagerList;
	}

	public void setListingCodeManagerList(List<ListingCodeManagerDto> listingCodeManagerList) {
		this.listingCodeManagerList = listingCodeManagerList;
	}

	public void setReturnPurchase(ReturnPurchase returnPurchase) {
		this.returnPurchase = returnPurchase;
	}

	
}
