package com.oigbuy.jeesite.modules.ebay.product.dto;

import java.util.List;

import com.oigbuy.jeesite.modules.ebay.product.entity.Product;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductDescription;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductImg;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductTitle;
import com.oigbuy.jeesite.modules.ebay.product.entity.TProductImgPlatform;

public class ProductDto extends Product {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7465043187281523898L;
	
	
	private Long publishStyleId;		// 刊登风格模板id
	private Long logisticsTemplateId;		// 物流模板id
	private Long locationId;		// 商品所在地模板id
	private Long returnId;		// 退货模板id
	private Long buyerLimitId;		// 买家模板id
	
	private String productStatus;		// 产品状态，1、新建状态  2、编辑状态 3、已刊登状态 4、其他
	
//	private Long siteId;		// 站点id，通过该id和刊登分类可动态取 item_specifics
	private String saleType;		// 1、一口价 2、拍卖价
	private String category1;		// 刊登分类1
	private String categoryCode1;		// 刊登分类1的id
	private String category2;		// 刊登分类2
	private String categoryCode2;		// 刊登分类2的id
	private String productCode;		// 刊登到平台的 productID，UPCEAN
	private String categoryStatus;		// 物品状况，和刊登分类有关
	private String conditionID;		// 和物品状况相关
	private String keyWord1;		// 刊登标题的必须 关键字
	private String otherKeyWord1;		// 刊登标题的  其他关键字
	private String subtitle;		// 刊登副标题
	private String keyWord2;		// 刊登副标题的必须 关键字
	private String otherKeyWord2;		// 刊登副标题的  其他关键字
	private String number;		// 数量
	private String lotSize;		// ebay  lotSize
	private String shelfTime;		// 上架时间，拍卖和一口价不同 ，单位天，1 代表 1天，空 表示卖完为止
	private String upsetPrice;		// 拍卖底价，和商品销售类型有关
	private String windowDisplayPicture;		// 橱窗展示图片  Feature  Gallery  Plus 和 空
	private String counter;		// 计数器   BasicStyle RetoStyle
	private String style;		// 样式  BoldTitle
	private String personalBelongings;		// 私人物品  1、使用  2、不使用
	private String listingCategory;		// listing 分类
	private String autoComplementOnlineQuantity;		// 自动补在线数量  1、卖多少补多少   2、不补
	private String modeCategory;		// 模板分类  1、全部
	
	private String[] paymentMethod;		// 收款方式默认是  paypal,可以多选
	
	private String paypalType;		// 收款PayPal方式    1小额  2大额  对应的大账号小账号
	
	private String immediatePay;		// 立即付款  默认要求买家不立即付款1，可页面选择立即付款 2
	private String paymentDesc;		// 付款描述
	
	
	
	/***
	 * 主标题的 title  code 
	 */
	private String titleCode;
	
	
	/**
	 * 副标题的 title code 
	 */
	private  String subTitleCode;
	
	
	
	/***
	 * 主标题集合
	 */
	private List<ProductTitle> mainTitleList;
	
	/***
	 * 副标题集合
	 */
	private List<ProductTitle> subTitleList;
	
	
	/***
	 * 产品主图集合
	 */
	private  List<TProductImgPlatform> mainProductImage;
	/***
	 * 产品细节图集合 
	 */
	private  List<TProductImgPlatform> detailProductImage;
	/***
	 * 产品特效图集合
	 */
	private  List<TProductImgPlatform> specialProductImage;
	
	
	/***
	 * 产品描述集合
	 */
	private List<ProductDescription> productDescriptions;
	
	/***
	 * 类型为 手动输入的产品描述
	 */
	private ProductDescription productDescription2;
	
	
	/***
	 * 该产品下的所有图片
	 */
	private List<ProductImg> allImageList;
	
	
	
	/***
	 * 该产品刊登到某一个店铺中 对应的 listing sku
	 */
	private String listingSkuCode;
	
	
	
	
	public String getListingSkuCode() {
		return listingSkuCode;
	}
	public void setListingSkuCode(String listingSkuCode) {
		this.listingSkuCode = listingSkuCode;
	}
	public List<ProductImg> getAllImageList() {
		return allImageList;
	}
	public void setAllImageList(List<ProductImg> allImageList) {
		this.allImageList = allImageList;
	}
	public ProductDescription getProductDescription2() {
		return productDescription2;
	}
	public void setProductDescription2(ProductDescription productDescription2) {
		this.productDescription2 = productDescription2;
	}
	public List<ProductDescription> getProductDescriptions() {
		return productDescriptions;
	}
	public void setProductDescriptions(List<ProductDescription> productDescriptions) {
		this.productDescriptions = productDescriptions;
	}
	public List<ProductTitle> getMainTitleList() {
		return mainTitleList;
	}
	public void setMainTitleList(List<ProductTitle> mainTitleList) {
		this.mainTitleList = mainTitleList;
	}
	public List<ProductTitle> getSubTitleList() {
		return subTitleList;
	}
	public void setSubTitleList(List<ProductTitle> subTitleList) {
		this.subTitleList = subTitleList;
	}
	public String getCategoryCode1() {
		return categoryCode1;
	}
	public void setCategoryCode1(String categoryCode1) {
		this.categoryCode1 = categoryCode1;
	}
	public String getCategoryCode2() {
		return categoryCode2;
	}
	public void setCategoryCode2(String categoryCode2) {
		this.categoryCode2 = categoryCode2;
	}
	public String getTitleCode() {
		return titleCode;
	}
	public void setTitleCode(String titleCode) {
		this.titleCode = titleCode;
	}
	public String getSubTitleCode() {
		return subTitleCode;
	}
	public void setSubTitleCode(String subTitleCode) {
		this.subTitleCode = subTitleCode;
	}
	public Long getPublishStyleId() {
		return publishStyleId;
	}
	public void setPublishStyleId(Long publishStyleId) {
		this.publishStyleId = publishStyleId;
	}
	public Long getLogisticsTemplateId() {
		return logisticsTemplateId;
	}
	public void setLogisticsTemplateId(Long logisticsTemplateId) {
		this.logisticsTemplateId = logisticsTemplateId;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public Long getReturnId() {
		return returnId;
	}
	public void setReturnId(Long returnId) {
		this.returnId = returnId;
	}
	public Long getBuyerLimitId() {
		return buyerLimitId;
	}
	public void setBuyerLimitId(Long buyerLimitId) {
		this.buyerLimitId = buyerLimitId;
	}

	public String getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public String getSaleType() {
		return saleType;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
	public String getCategory1() {
		return category1;
	}
	public void setCategory1(String category1) {
		this.category1 = category1;
	}
	public String getCategory2() {
		return category2;
	}
	public void setCategory2(String category2) {
		this.category2 = category2;
	}

	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getCategoryStatus() {
		return categoryStatus;
	}
	public void setCategoryStatus(String categoryStatus) {
		this.categoryStatus = categoryStatus;
	}
	
	public String getConditionID() {
		return conditionID;
	}
	public void setConditionID(String conditionID) {
		this.conditionID = conditionID;
	}
	public String getKeyWord1() {
		return keyWord1;
	}
	public void setKeyWord1(String keyWord1) {
		this.keyWord1 = keyWord1;
	}
	public String getOtherKeyWord1() {
		return otherKeyWord1;
	}
	public void setOtherKeyWord1(String otherKeyWord1) {
		this.otherKeyWord1 = otherKeyWord1;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getKeyWord2() {
		return keyWord2;
	}
	public void setKeyWord2(String keyWord2) {
		this.keyWord2 = keyWord2;
	}
	public String getOtherKeyWord2() {
		return otherKeyWord2;
	}
	public void setOtherKeyWord2(String otherKeyWord2) {
		this.otherKeyWord2 = otherKeyWord2;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getLotSize() {
		return lotSize;
	}
	public void setLotSize(String lotSize) {
		this.lotSize = lotSize;
	}
	public String getShelfTime() {
		return shelfTime;
	}
	public void setShelfTime(String shelfTime) {
		this.shelfTime = shelfTime;
	}
	public String getUpsetPrice() {
		return upsetPrice;
	}
	public void setUpsetPrice(String upsetPrice) {
		this.upsetPrice = upsetPrice;
	}
	public String getWindowDisplayPicture() {
		return windowDisplayPicture;
	}
	public void setWindowDisplayPicture(String windowDisplayPicture) {
		this.windowDisplayPicture = windowDisplayPicture;
	}
	public String getCounter() {
		return counter;
	}
	public void setCounter(String counter) {
		this.counter = counter;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getPersonalBelongings() {
		return personalBelongings;
	}
	public void setPersonalBelongings(String personalBelongings) {
		this.personalBelongings = personalBelongings;
	}
	public String getListingCategory() {
		return listingCategory;
	}
	public void setListingCategory(String listingCategory) {
		this.listingCategory = listingCategory;
	}
	public String getAutoComplementOnlineQuantity() {
		return autoComplementOnlineQuantity;
	}
	public void setAutoComplementOnlineQuantity(String autoComplementOnlineQuantity) {
		this.autoComplementOnlineQuantity = autoComplementOnlineQuantity;
	}
	public String getModeCategory() {
		return modeCategory;
	}
	public void setModeCategory(String modeCategory) {
		this.modeCategory = modeCategory;
	}
	public String[] getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String[] paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPaypalType() {
		return paypalType;
	}
	public void setPaypalType(String paypalType) {
		this.paypalType = paypalType;
	}
	public String getImmediatePay() {
		return immediatePay;
	}
	public void setImmediatePay(String immediatePay) {
		this.immediatePay = immediatePay;
	}
	public String getPaymentDesc() {
		return paymentDesc;
	}
	public void setPaymentDesc(String paymentDesc) {
		this.paymentDesc = paymentDesc;
	}
	public List<TProductImgPlatform> getMainProductImage() {
		return mainProductImage;
	}
	public void setMainProductImage(List<TProductImgPlatform> mainProductImage) {
		this.mainProductImage = mainProductImage;
	}
	public List<TProductImgPlatform> getDetailProductImage() {
		return detailProductImage;
	}
	public void setDetailProductImage(List<TProductImgPlatform> detailProductImage) {
		this.detailProductImage = detailProductImage;
	}
	public List<TProductImgPlatform> getSpecialProductImage() {
		return specialProductImage;
	}
	public void setSpecialProductImage(List<TProductImgPlatform> specialProductImage) {
		this.specialProductImage = specialProductImage;
	}
	
	
}
