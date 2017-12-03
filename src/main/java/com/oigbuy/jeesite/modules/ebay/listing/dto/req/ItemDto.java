//package com.oigbuy.jeesite.modules.ebay.listing.dto.req;
//
//import java.io.Serializable;
//import java.util.List;
//
//
//
//public class ItemDto implements Serializable {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	
//	
//	private String conditionID;
//	
//	private String site;//站点 UK大写
//	
//	private String sellerName;//卖家账号
//	
//	private String productId;//UPC/EAN
//	
//	private String conditionStatus;//物品状况
//	
//	private String conditionDescription;//物品描述
//	
//	
//	private List<ItemSpecificsDto> itemSpecifics;//细节
//	
//	private List<VariationDto> variations;//多属性设置
//	
//	private String productCode;//产品母代码
//	
//	private String titile;//刊登标题
//	
//	private String subTitle;//刊登副标题
//	
//	private String categoryId;//刊登分类
//	
//	private String categoryName;//刊登分类名称
//	
//	private String subCategoryId;
//	
//	private String subCategoryName;//二级分类
//	
//	private String style;//刊登风格  TODO 
//	
//	private Integer quantity;//数量
//	
//	private Integer quantityThreshold;//限制数量  TODO 
//	
//	private String pictureDescription;//TODO 
//	
//	private PictureDto pictures;//主图
//	
//	private List<PictureDto> itemPictures;//细节图
//	
//	private List<PictureDto> specialPictures;//特效图
//	
//	private Integer lotSize;
//	
//	private String currency;//货币
//	
//	private String description;//TODO 
//	
//	private Integer dispatchTimeMax;//发货期限  TODO 
//	
//	private String listingDuration;//上市活跃的天数 上架时间 
//	
//	private double startPrice;//一口价
//	
//	private double floorPrice;//拍卖底价
//	
//	private ShippingDetail shippingLocalDetail;//境内物流设置
//	
//	private ShippingDetail shippingOutsideDetail;//境外物流设置
//	
//	private String productModelName;//商品模板名称
//	private String productLocation;//商品地址
//	
//	private String zipCode;//邮编
//	
//	private String productCountry;//国家  UK  TODO 
//	
//	private RefundDetailDto refundDetail;//退货设置
//	
//	private PayMentDetailDto payDetail;//收款选项
//	
//	private BuyerDetailDto buyer;//买家选项
//	
//	private String galleryType;//橱窗展示图片类型  
//	
//	private String hitCounter;//计数器
//	
//	private String modelType;//其他 模板分类  默认都是全部，全部  TODO  all ? 
//	
//	private String note;//其他 备注
//	
//	private String listingType;//listing分类   默认都是全部，全部  TODO  all ? 
//	
//	private boolean privateListing; //私人物品
//	
//	private String otherStyle; //其他样式   BoldTitle
//	
//	private boolean autoComplementOnlineQuantity; //自动补在线数量
//	
//	
//	public String getOtherStyle() {
//		return otherStyle;
//	}
//
//	public void setOtherStyle(String otherStyle) {
//		this.otherStyle = otherStyle;
//	}
//
//	public boolean isAutoComplementOnlineQuantity() {
//		return autoComplementOnlineQuantity;
//	}
//
//	public void setAutoComplementOnlineQuantity(boolean autoComplementOnlineQuantity) {
//		this.autoComplementOnlineQuantity = autoComplementOnlineQuantity;
//	}
//
//	public String getConditionID() {
//		return conditionID;
//	}
//
//	public void setConditionID(String conditionID) {
//		this.conditionID = conditionID;
//	}
//
//	public String getSite() {
//		return site;
//	}
//
//	public void setSite(String site) {
//		this.site = site;
//	}
//
//	public String getSellerName() {
//		return sellerName;
//	}
//
//	public void setSellerName(String sellerName) {
//		this.sellerName = sellerName;
//	}
//
//	public String getProductId() {
//		return productId;
//	}
//
//	public void setProductId(String productId) {
//		this.productId = productId;
//	}
//
//	public String getConditionStatus() {
//		return conditionStatus;
//	}
//
//	public void setConditionStatus(String conditionStatus) {
//		this.conditionStatus = conditionStatus;
//	}
//
//	public String getConditionDescription() {
//		return conditionDescription;
//	}
//
//	public void setConditionDescription(String conditionDescription) {
//		this.conditionDescription = conditionDescription;
//	}
//
//	
//
//	public List<ItemSpecificsDto> getItemSpecifics() {
//		return itemSpecifics;
//	}
//
//	public void setItemSpecifics(List<ItemSpecificsDto> itemSpecifics) {
//		this.itemSpecifics = itemSpecifics;
//	}
//
//	public String getProductCode() {
//		return productCode;
//	}
//
//	public void setProductCode(String productCode) {
//		this.productCode = productCode;
//	}
//
//	public String getTitile() {
//		return titile;
//	}
//
//	public void setTitile(String titile) {
//		this.titile = titile;
//	}
//
//	public String getSubTitle() {
//		return subTitle;
//	}
//
//	public void setSubTitle(String subTitle) {
//		this.subTitle = subTitle;
//	}
//
//	public String getCategoryId() {
//		return categoryId;
//	}
//
//	public void setCategoryId(String categoryId) {
//		this.categoryId = categoryId;
//	}
//
//	public String getCategoryName() {
//		return categoryName;
//	}
//
//	public void setCategoryName(String categoryName) {
//		this.categoryName = categoryName;
//	}
//
//	public String getSubCategoryId() {
//		return subCategoryId;
//	}
//
//	public void setSubCategoryId(String subCategoryId) {
//		this.subCategoryId = subCategoryId;
//	}
//
//	public String getSubCategoryName() {
//		return subCategoryName;
//	}
//
//	public void setSubCategoryName(String subCategoryName) {
//		this.subCategoryName = subCategoryName;
//	}
//
//	public String getStyle() {
//		return style;
//	}
//
//	public void setStyle(String style) {
//		this.style = style;
//	}
//
//	public Integer getQuantity() {
//		return quantity;
//	}
//
//	public void setQuantity(Integer quantity) {
//		this.quantity = quantity;
//	}
//
//	public Integer getQuantityThreshold() {
//		return quantityThreshold;
//	}
//
//	public void setQuantityThreshold(Integer quantityThreshold) {
//		this.quantityThreshold = quantityThreshold;
//	}
//
//	public String getPictureDescription() {
//		return pictureDescription;
//	}
//
//	public void setPictureDescription(String pictureDescription) {
//		this.pictureDescription = pictureDescription;
//	}
//
//	public PictureDto getPictures() {
//		return pictures;
//	}
//
//	public void setPictures(PictureDto pictures) {
//		this.pictures = pictures;
//	}
//
//	public List<PictureDto> getItemPictures() {
//		return itemPictures;
//	}
//
//	public void setItemPictures(List<PictureDto> itemPictures) {
//		this.itemPictures = itemPictures;
//	}
//
//	public List<PictureDto> getSpecialPictures() {
//		return specialPictures;
//	}
//
//	public void setSpecialPictures(List<PictureDto> specialPictures) {
//		this.specialPictures = specialPictures;
//	}
//
//	public Integer getLotSize() {
//		return lotSize;
//	}
//
//	public void setLotSize(Integer lotSize) {
//		this.lotSize = lotSize;
//	}
//
//	public String getCurrency() {
//		return currency;
//	}
//
//	public void setCurrency(String currency) {
//		this.currency = currency;
//	}
//
//	public String getDescription() {
//		return description;
//	}
//
//	public void setDescription(String description) {
//		this.description = description;
//	}
//
//	public Integer getDispatchTimeMax() {
//		return dispatchTimeMax;
//	}
//
//	public void setDispatchTimeMax(Integer dispatchTimeMax) {
//		this.dispatchTimeMax = dispatchTimeMax;
//	}
//
//	public String getListingDuration() {
//		return listingDuration;
//	}
//
//	public void setListingDuration(String listingDuration) {
//		this.listingDuration = listingDuration;
//	}
//
//
//	public double getStartPrice() {
//		return startPrice;
//	}
//
//	public void setStartPrice(double startPrice) {
//		this.startPrice = startPrice;
//	}
//
//	public double getFloorPrice() {
//		return floorPrice;
//	}
//
//	public void setFloorPrice(double floorPrice) {
//		this.floorPrice = floorPrice;
//	}
//
//	public ShippingDetail getShippingLocalDetail() {
//		return shippingLocalDetail;
//	}
//
//	public void setShippingLocalDetail(ShippingDetail shippingLocalDetail) {
//		this.shippingLocalDetail = shippingLocalDetail;
//	}
//
//	public ShippingDetail getShippingOutsideDetail() {
//		return shippingOutsideDetail;
//	}
//
//	public void setShippingOutsideDetail(ShippingDetail shippingOutsideDetail) {
//		this.shippingOutsideDetail = shippingOutsideDetail;
//	}
//
//	public String getProductModelName() {
//		return productModelName;
//	}
//
//	public void setProductModelName(String productModelName) {
//		this.productModelName = productModelName;
//	}
//
//	public String getProductLocation() {
//		return productLocation;
//	}
//
//	public void setProductLocation(String productLocation) {
//		this.productLocation = productLocation;
//	}
//
//	public String getZipCode() {
//		return zipCode;
//	}
//
//	public void setZipCode(String zipCode) {
//		this.zipCode = zipCode;
//	}
//
//	public String getProductCountry() {
//		return productCountry;
//	}
//
//	public void setProductCountry(String productCountry) {
//		this.productCountry = productCountry;
//	}
//
//	public RefundDetailDto getRefundDetail() {
//		return refundDetail;
//	}
//
//	public void setRefundDetail(RefundDetailDto refundDetail) {
//		this.refundDetail = refundDetail;
//	}
//
//	public PayMentDetailDto getPayDetail() {
//		return payDetail;
//	}
//
//	public void setPayDetail(PayMentDetailDto payDetail) {
//		this.payDetail = payDetail;
//	}
//
//	public BuyerDetailDto getBuyer() {
//		return buyer;
//	}
//
//	public void setBuyer(BuyerDetailDto buyer) {
//		this.buyer = buyer;
//	}
//
//	public String getGalleryType() {
//		return galleryType;
//	}
//
//	public void setGalleryType(String galleryType) {
//		this.galleryType = galleryType;
//	}
//
//	public String getHitCounter() {
//		return hitCounter;
//	}
//
//	public void setHitCounter(String hitCounter) {
//		this.hitCounter = hitCounter;
//	}
//
//	public String getModelType() {
//		return modelType;
//	}
//
//	public void setModelType(String modelType) {
//		this.modelType = modelType;
//	}
//
//	public String getNote() {
//		return note;
//	}
//
//	public void setNote(String note) {
//		this.note = note;
//	}
//
//	public String getListingType() {
//		return listingType;
//	}
//
//	public void setListingType(String listingType) {
//		this.listingType = listingType;
//	}
//
//	public boolean isPrivateListing() {
//		return privateListing;
//	}
//
//	public void setPrivateListing(boolean privateListing) {
//		this.privateListing = privateListing;
//	}
//
//	public List<VariationDto> getVariations() {
//		return variations;
//	}
//
//	public void setVariations(List<VariationDto> variations) {
//		this.variations = variations;
//	}
//
//	
//	
//}
