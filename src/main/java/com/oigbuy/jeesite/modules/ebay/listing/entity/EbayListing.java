/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.listing.entity;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.oigbuy.jeesite.common.persistence.DataEntity;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.Platform;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatformSite;
import com.oigbuy.jeesite.modules.ebay.product.entity.Product;
import com.oigbuy.jeesite.modules.ebay.template.entity.Seller;

/**
 * ebay listingEntity
 * @author bill.xu
 * @version 2017-09-22
 */
public class EbayListing extends DataEntity<EbayListing> {
	
	private static final long serialVersionUID = 1L;
	private Long productId;		// 产品id
	private Long shopId;		// 店铺id
	private Long siteId;		// 站点id
	private Long platformId;		// 平台id
	private Long titleId;		// 标题id
	private Long subTitleId;		// 副标题id
	private String title;		// 标题
	private String subTitle;		// 副标题
	private String parentSku;		// 平台母代码
	private String sysParentSku;		// 系统母代码
	private String description;		// 描述
	private String productCode;		// 刊登到平台的 productID，UPCEAN
	private String saleType;		// 销售类型（1、一口价 2、拍卖价）
	private String categoryStatus;		// 物品状况，和刊登分类有关
	private String conditionID;		// 和物品状况有关
	private String number;		// 数量
	private String lotSize;		// lot size
	private String shelfTime;		// 上架时间，拍卖和一口价不同 ，单位天，1 代表 1天，空 表示卖完为止
	private BigDecimal upsetPrice;		// 拍卖底价
	private String windowDisplayPicture;		// 橱窗展示图片  Feature  Gallery  Plus 和 空
	private String counter;		// 计数器
	private String style;		// 风格
	private String personalBelongings;		// 私人物品  1、使用  2、不使用
	private String listingCategory;		// listing 分类
	private String autoComplementOnlineQuantity;		// 自动补在线数量  1、卖多少补多少   2、不补
	private String modeCategory;		// 模板分类  1、全部
	private String paymentMethod;		// 收款方式默认是  paypal
	private String paypalType;		// 收款PayPal方式    1小额  2大额
	private String immediatePay;		// 立即付款  默认要求买家不立即付款1，可页面选择立即付款 2
	private String paymentDesc;		// 付款描述
	private String status;		// listing 状态，（ 0、listing 新建状态，1、待刊登状态  2、已刊登状态  3、刊登状态待审核，4、刊登状态已批准 ,5、刊登状态被拒绝）
	
	
	
	private Long publishStyleId;		// 刊登风格模板id
	private Long logisticsTemplateId;		// 物流模板id
	private Long locationId;		// 商品所在地模板id
	private Long returnId;		// 退货模板id
	private Long buyerLimitId;		// 买家模板id
	
	
	private String publishDescription;//刊登描述
	private BigDecimal sellingPrice;//一口价
	
	private String otherRemark;//其他备注
	
	/***
	 * 是不是多属性
	 */
	private boolean multiAttribute;
	
	/***
	 * 刊登成功之后回写 listing itemId
	 */
	private String itemId;
	
	/***
	 * listing 页面查询操作
	 */
	private Product product;
	/**
	 *刊登店铺
	 */
	private Seller seller;
	/***
	 * 站点信息
	 */
	private PlatformSite platformSite;
	
	/***
	 * 平台信息
	 */
	private Platform platform;
	
	private List<String> paymentMethods;
	
	
	
	public boolean isMultiAttribute() {
		return multiAttribute;
	}

	public void setMultiAttribute(boolean multiAttribute) {
		this.multiAttribute = multiAttribute;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public List<String> getPaymentMethods() {
		return paymentMethods;
	}

	public void setPaymentMethods(List<String> paymentMethods) {
		this.paymentMethods = paymentMethods;
	}

	public String getOtherRemark() {
		return otherRemark;
	}

	public void setOtherRemark(String otherRemark) {
		this.otherRemark = otherRemark;
	}

	public BigDecimal getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(BigDecimal sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public String getPublishDescription() {
		return publishDescription;
	}

	public void setPublishDescription(String publishDescription) {
		this.publishDescription = publishDescription;
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

	

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public EbayListing(Long productId, Long shopId, Long siteId) {
		super();
		this.productId = productId;
		this.shopId = shopId;
		this.siteId = siteId;
	}

	public EbayListing() {
		super();
	}

	public EbayListing(String id){
		super(id);
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	
	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	
	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}
	
	public Long getTitleId() {
		return titleId;
	}

	public void setTitleId(Long titleId) {
		this.titleId = titleId;
	}
	
	public Long getSubTitleId() {
		return subTitleId;
	}

	public void setSubTitleId(Long subTitleId) {
		this.subTitleId = subTitleId;
	}
	
	@Length(min=0, max=1000, message="标题长度必须介于 0 和 1000 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=1000, message="副标题长度必须介于 0 和 1000 之间")
	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	
	@Length(min=0, max=40, message="平台母代码长度必须介于 0 和 40 之间")
	public String getParentSku() {
		return parentSku;
	}

	public void setParentSku(String parentSku) {
		this.parentSku = parentSku;
	}
	
	@Length(min=0, max=40, message="系统母代码长度必须介于 0 和 40 之间")
	public String getSysParentSku() {
		return sysParentSku;
	}

	public void setSysParentSku(String sysParentSku) {
		this.sysParentSku = sysParentSku;
	}
	
	@Length(min=0, max=3000, message="描述长度必须介于 0 和 3000 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=40, message="刊登到平台的 productID，UPCEAN长度必须介于 0 和 40 之间")
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	@Length(min=0, max=1, message="销售类型（1、一口价 2、拍卖价）长度必须介于 0 和 1 之间")
	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
	
	@Length(min=0, max=200, message="物品状况，和刊登分类有关长度必须介于 0 和 200 之间")
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

	@Length(min=0, max=8, message="数量长度必须介于 0 和 8 之间")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@Length(min=0, max=8, message="lot size长度必须介于 0 和 8 之间")
	public String getLotSize() {
		return lotSize;
	}

	public void setLotSize(String lotSize) {
		this.lotSize = lotSize;
	}
	
	@Length(min=0, max=20, message="上架时间，拍卖和一口价不同 ，单位天，1 代表 1天，空 表示卖完为止长度必须介于 0 和 20 之间")
	public String getShelfTime() {
		return shelfTime;
	}

	public void setShelfTime(String shelfTime) {
		this.shelfTime = shelfTime;
	}
	
	public BigDecimal getUpsetPrice() {
		return upsetPrice;
	}

	public void setUpsetPrice(BigDecimal upsetPrice) {
		this.upsetPrice = upsetPrice;
	}
	
	@Length(min=0, max=200, message="橱窗展示图片  Feature  Gallery  Plus 和 空长度必须介于 0 和 200 之间")
	public String getWindowDisplayPicture() {
		return windowDisplayPicture;
	}

	public void setWindowDisplayPicture(String windowDisplayPicture) {
		this.windowDisplayPicture = windowDisplayPicture;
	}
	
	@Length(min=0, max=200, message="计数器长度必须介于 0 和 200 之间")
	public String getCounter() {
		return counter;
	}

	public void setCounter(String counter) {
		this.counter = counter;
	}
	
	@Length(min=0, max=200, message="风格长度必须介于 0 和 200 之间")
	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
	
	@Length(min=0, max=2, message="私人物品  1、使用  2、不使用长度必须介于 0 和 2 之间")
	public String getPersonalBelongings() {
		return personalBelongings;
	}

	public void setPersonalBelongings(String personalBelongings) {
		this.personalBelongings = personalBelongings;
	}
	
	@Length(min=0, max=200, message="listing 分类长度必须介于 0 和 200 之间")
	public String getListingCategory() {
		return listingCategory;
	}

	public void setListingCategory(String listingCategory) {
		this.listingCategory = listingCategory;
	}
	
	@Length(min=0, max=2, message="自动补在线数量  1、卖多少补多少   2、不补长度必须介于 0 和 2 之间")
	public String getAutoComplementOnlineQuantity() {
		return autoComplementOnlineQuantity;
	}

	public void setAutoComplementOnlineQuantity(String autoComplementOnlineQuantity) {
		this.autoComplementOnlineQuantity = autoComplementOnlineQuantity;
	}
	
	@Length(min=0, max=2, message="模板分类  1、全部长度必须介于 0 和 2 之间")
	public String getModeCategory() {
		return modeCategory;
	}

	public void setModeCategory(String modeCategory) {
		this.modeCategory = modeCategory;
	}
	
	@Length(min=0, max=200, message="收款方式默认是  paypal长度必须介于 0 和 200 之间")
	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	@Length(min=0, max=2, message="收款PayPal方式    1小额  2大额长度必须介于 0 和 2 之间")
	public String getPaypalType() {
		return paypalType;
	}

	public void setPaypalType(String paypalType) {
		this.paypalType = paypalType;
	}
	
	@Length(min=0, max=2, message="立即付款  默认要求买家不立即付款1，可页面选择立即付款 2长度必须介于 0 和 2 之间")
	public String getImmediatePay() {
		return immediatePay;
	}

	public void setImmediatePay(String immediatePay) {
		this.immediatePay = immediatePay;
	}
	
	@Length(min=0, max=300, message="付款描述长度必须介于 0 和 300 之间")
	public String getPaymentDesc() {
		return paymentDesc;
	}

	public void setPaymentDesc(String paymentDesc) {
		this.paymentDesc = paymentDesc;
	}
	
	@Length(min=0, max=10, message="listing 状态，（ 0、listing 新建状态，1、待刊登状态  2、已刊登状态  3、刊登状态待审核，4、刊登状态已批准 ,5、刊登状态被拒绝）长度必须介于 0 和 10 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public PlatformSite getPlatformSite() {
		return platformSite;
	}

	public void setPlatformSite(PlatformSite platformSite) {
		this.platformSite = platformSite;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}
	
	
	
}