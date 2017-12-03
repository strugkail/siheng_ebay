/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.entity;

import com.oigbuy.jeesite.common.persistence.DataEntity;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatformSite;
import com.oigbuy.jeesite.modules.ebay.purchase.dto.PurchaseConfigInfo;
import com.oigbuy.jeesite.modules.ebay.purchase.dto.PurchaseSource;
import com.oigbuy.jeesite.modules.ebay.tags.entity.Tags;

import org.apache.commons.lang3.StringEscapeUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;
import java.util.List;

/**
 * 产品表
 * @author tony.liu
 */
public class Product extends DataEntity<Product> {
	
	private static final long serialVersionUID = 1L;
	private String developmentType;		// 开发类型
	private String developmentChannel;  //开发渠道
	private String sysParentCode;		// 系统母代码
	private String cnName;		// 中文报关名称
	private String enName;		// 英文报关名称
	private String name;		// 中文名
	private String tags;		// tags
	private Double costPrice;		// 成本价
	private Double profitMargin;		// 利润率
	private Double declaredValue;		// 申报价格
	private Double sellingPrice;		// 售价
	private Double defaultWeight;		// 默认重量
	private String weightType;		// 重量标识(0:使用默认重量，1使用子代码自己的重量)
	private String remark;		// 备注
	private String casing;		// 包装
	private Double freight;		// 运费
	private String texture;		// 材质
	private String specification;  //规格
	private String description;		// description
	private String imgUrl;		// 图片地址
	private String imgLink;		// 原图片链接地址
	private String keyName;			//关键词
	private String startName;		//句首
	private String endName;			//句尾
	private String secondName;		//第二关键词维护
	private String colorName;		//颜色
	private String sizeName;		//尺寸
	private String wishColorName;   //wish颜色
	private String wishSizeName;    //wish尺寸
	private String productTitle; // 产品标题
	private String title;//生成的title
	private String code;//生成title的组合code
	private String codeHistory;
	private String type; // 0:草稿箱   1：正式
	private int maxCode; // 当前最大的合成码code
	private String calType; // 利润测算方式(目前支持两种1：马邮，2：其他邮政)
	private String productType;  //产品类型
	private String procurementChannel;  //采购渠道
	private int titleNum;
	private int compositeImgNum;
	private String finishedProductQuantity; //完成产品数量补充标识

	private String createId;		// 开发人id
	private String createName;		// 开发人name
	private Date createTime;  // create_time
	
	private String updateId;
	private String updateName;
	private Date updateTime;
	
	private String speTags;   //规格标签
	private String proTags;   //产品标签
	
//	Data identifying
	private int identifying;
	private String extraImagesUrl;
//	Image Type(Main image/Composite image)
	private String imgType;
//	Child code
	private String sysSku;
//	Child code selling price
	private Double publishPrice;
//	Child code transform price
	private Double publishTransPrice;
//	Child code size
	private String size;
//	Child code color
	private String color;
	private String siteId;
	
	private List<String> mainProductImgList;		//主图和刊登图链接
	private List<String> detailProductImgList;		//细节图链接
	private List<String> compositeImageList;    //合成图链接
	private List<String> puzzleProductImgList;   //拼图链接

	private List<Tags> selectedTags; // 选择的产品tags
	private List<Tags> selectedSpeTags;//选择的规则标签
//	private List<String> tagsList; 			//产品标签id集合
//	private List<String> speTagsList;       //规格标签集合
	private List<String> competitorId;		//
	private List<String> procurementData;	//采购信息
	private List<PurchaseSource> purchaseSourceList; // 采购源列表
	private List<PurchaseConfigInfo> purchaseConfigInfoList; // 采购配置信息
	private List<ProductProperty> propertyList;// 产品属性
	private String productUrl;// 商品链接
	private String categoryName;// 分类1
	private String subCategoryName;// 分类2
	private List<PlatformSite> siteList;
	private Date dateStart;        //查询开始时间
	private Date dateEnd;          //查询结束时间
	private String sign;         // 1 已签收 2 未签收
	
	private String officeId;
	private String flag;
	private String currencyType;
	private String platformFlag;
	private Double profit;
	private Double profitRate;
	private String siteShortName; // 站点简称
	private String productNumber;// 采购量
	private String processNum;		//流程号
	private String deliveryWareHouse;	//发货仓
	
	public String getDeliveryWareHouse() {
		return deliveryWareHouse;
	}

	public void setDeliveryWareHouse(String deliveryWareHouse) {
		this.deliveryWareHouse = deliveryWareHouse;
	}

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	
	/**
	 * 是不是  大 paypal，true 表示是，否则不是
	 */
	private  boolean isBigPaypal;
	
	
	public boolean isBigPaypal() {
		return isBigPaypal;
	}

	public void setBigPaypal(boolean isBigPaypal) {
		this.isBigPaypal = isBigPaypal;
	}

	public String getSiteShortName() {
		return siteShortName;
	}

	public void setSiteShortName(String siteShortName) {
		this.siteShortName = siteShortName;
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

	public String getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public List<PlatformSite> getSiteList() {
		return siteList;
	}

	public void setSiteList(List<PlatformSite> siteList) {
		this.siteList = siteList;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public List<ProductProperty> getPropertyList() {
		return propertyList;
	}

	public void setPropertyList(List<ProductProperty> propertyList) {
		this.propertyList = propertyList;
	}



	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getPlatformFlag() {
		return platformFlag;
	}

	public void setPlatformFlag(String platformFlag) {
		this.platformFlag = platformFlag;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public Double getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(Double profitRate) {
		this.profitRate = profitRate;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	public String getSpeTags() {
		return speTags;
	}

	public void setSpeTags(String speTags) {
		this.speTags = speTags;
	}

	public String getProTags() {
		return proTags;
	}

	public void setProTags(String proTags) {
		this.proTags = proTags;
	}

	public String getProcurementChannel() {
		return procurementChannel;
	}

	public void setProcurementChannel(String procurementChannel) {
		this.procurementChannel = procurementChannel;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCodeHistory() {
		return codeHistory;
	}

	public void setCodeHistory(String codeHistory) {
		this.codeHistory = codeHistory;
	}

	@NotEmpty(message="标题内容不能为空！") 
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=1, max=1000, message="产品标题长度必须介于 1 和 1000 之间")
	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<String> getProcurementData() {
		return procurementData;
	}

	public void setProcurementData(List<String> procurementData) {
		this.procurementData = procurementData;
	}

	public String getStartName() {
		return startName;
	}

	public void setStartName(String startName) {
		this.startName = startName;
	}

	public String getEndName() {
		return endName;
	}

	public void setEndName(String endName) {
		this.endName = endName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}


	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}
	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	public Double getDeclaredValue() {
		return declaredValue;
	}

	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
	}

	public Double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public Double getDefaultWeight() {
		return defaultWeight;
	}

	public void setDefaultWeight(Double defaultWeight) {
		this.defaultWeight = defaultWeight;
	}

	public Double getFreight() {
		return freight;
	}

	public void setFreight(Double freight) {
		this.freight = freight;
	}


	public List<String> getCompetitorId() {
		return competitorId;
	}

	public void setCompetitorId(List<String> competitorId) {
		this.competitorId = competitorId;
	}

	public List<String> getMainProductImgList() {
		return mainProductImgList;
	}

	public void setMainProductImgList(List<String> mainProductImgList) {
		this.mainProductImgList = mainProductImgList;
	}

	public List<String> getDetailProductImgList() {
		return detailProductImgList;
	}

	public void setDetailProductImgList(List<String> detailProductImgList) {
		this.detailProductImgList = detailProductImgList;
	}

	public List<String> getCompositeImageList() {
		return compositeImageList;
	}

	public void setCompositeImageList(List<String> compositeImageList) {
		this.compositeImageList = compositeImageList;
	}
	
	@NotEmpty(message="关键字不能为空！") 
	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getWishColorName() {
		return wishColorName;
	}

	public void setWishColorName(String wishColorName) {
		this.wishColorName = wishColorName;
	}

	public String getWishSizeName() {
		return wishSizeName;
	}

	public void setWishSizeName(String wishSizeName) {
		this.wishSizeName = wishSizeName;
	}

	public Product() {
		super();
	}

	public Product(String id){
		super(id);
	}

	@Length(min=1, max=40, message="开发类型长度必须介于 1 和 40 之间")
	public String getDevelopmentType() {
		return developmentType;
	}
	
	@Length(min=1, max=40, message="开发渠道长度必须介于 1 和 40 之间")
	public String getDevelopmentChannel() {
		return developmentChannel;
	}

	public void setDevelopmentChannel(String developmentChannel) {
		this.developmentChannel = developmentChannel;
	}

	public void setDevelopmentType(String developmentType) {
		this.developmentType = developmentType;
	}
	
	@Length(min=0, max=40, message="系统母代码长度必须介于 0 和 40 之间")
	public String getSysParentCode() {
		return sysParentCode;
	}

	public void setSysParentCode(String sysParentCode) {
		this.sysParentCode = sysParentCode;
	}
	
	@Length(min=1, max=40, message="中文报关名称长度必须介于 1 和 40 之间")
	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	
	@Length(min=1, max=40, message="英文报关名称长度必须介于 1 和 40 之间")
	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}
	
	@Length(min=1, max=40, message="中文名长度必须介于 1 和 40 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=1000, message="tags长度必须介于 1 和 1000 之间")
	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = StringEscapeUtils.unescapeXml(tags);
	}
	
	
//	@Length(min=1, max=10, message="利润率长度必须介于 1 和 10 之间")
	public Double getProfitMargin() {
		return profitMargin;
	}

	public void setProfitMargin(Double profitMargin) {
		this.profitMargin = profitMargin;
	}
	@Length(min=0, max=11, message="重量标识(0:使用默认重量，1使用子代码自己的重量)长度必须介于 0 和 11 之间")
	public String getWeightType() {
		return weightType;
	}

	public void setWeightType(String weightType) {
		this.weightType = weightType;
	}
	
	@Length(min=0, max=100, message="备注长度必须介于 1 和 100 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Length(min=0, max=40, message="包装长度必须介于 0 和 40 之间")
	public String getCasing() {
		return casing;
	}

	public void setCasing(String casing) {
		this.casing = casing;
	}
	
	
	@Length(min=0, max=40, message="材质长度必须介于 0 和 40 之间")
	public String getTexture() {
		return texture;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}
	
	@Length(min=0, max=40, message="规格长度必须介于 0 和 40 之间")
	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	@Length(min=1, max=4000, message="description长度必须介于 1 和 4000 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}
	
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Length(min=0, max=300, message="图片地址长度必须介于 0 和 300 之间")
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	@Length(min=0, max=800, message="原图片链接地址长度必须介于 0 和 800 之间")
	public String getImgLink() {
		return imgLink;
	}

	public void setImgLink(String imgLink) {
		this.imgLink = imgLink;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getMaxCode() {
		return maxCode;
	}

	public void setMaxCode(int maxCode) {
		this.maxCode = maxCode;
	}
	
	public String getCalType() {
		return calType;
	}

	public void setCalType(String calType) {
		this.calType = calType;
	}
	public List<String> getPuzzleProductImgList() {
		return puzzleProductImgList;
	}

	public void setPuzzleProductImgList(List<String> puzzleProductImgList) {
		this.puzzleProductImgList = puzzleProductImgList;
	}
	
	public int getTitleNum() {
		return titleNum;
	}

	public void setTitleNum(int titleNum) {
		this.titleNum = titleNum;
	}

	public int getCompositeImgNum() {
		return compositeImgNum;
	}

	public void setCompositeImgNum(int compositeImgNum) {
		this.compositeImgNum = compositeImgNum;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	

	public int getIdentifying() {
		return identifying;
	}

	public void setIdentifying(int identifying) {
		this.identifying = identifying;
	}
	
	public String getExtraImagesUrl() {
		return extraImagesUrl;
	}

	public void setExtraImagesUrl(String extraImagesUrl) {
		this.extraImagesUrl = extraImagesUrl;
	}
	
	

	public String getImgType() {
		return imgType;
	}

	public void setImgType(String imgType) {
		this.imgType = imgType;
	}
	

	public String getSysSku() {
		return sysSku;
	}

	public void setSysSku(String sysSku) {
		this.sysSku = sysSku;
	}

	public Double getPublishPrice() {
		return publishPrice;
	}

	public void setPublishPrice(Double publishPrice) {
		this.publishPrice = publishPrice;
	}

	public Double getPublishTransPrice() {
		return publishTransPrice;
	}

	public void setPublishTransPrice(Double publishTransPrice) {
		this.publishTransPrice = publishTransPrice;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public List<Tags> getSelectedTags() {
		return selectedTags;
	}

	public void setSelectedTags(List<Tags> selectedTags) {
		this.selectedTags = selectedTags;
	}

	public List<Tags> getSelectedSpeTags() {
		return selectedSpeTags;
	}

	public void setSelectedSpeTags(List<Tags> selectedSpeTags) {
		this.selectedSpeTags = selectedSpeTags;
	}

	public List<PurchaseSource> getPurchaseSourceList() {
		return purchaseSourceList;
	}

	public void setPurchaseSourceList(List<PurchaseSource> purchaseSourceList) {
		this.purchaseSourceList = purchaseSourceList;
	}

	public List<PurchaseConfigInfo> getPurchaseConfigInfoList() {
		return purchaseConfigInfoList;
	}

	public void setPurchaseConfigInfoList(List<PurchaseConfigInfo> purchaseConfigInfoList) {
		this.purchaseConfigInfoList = purchaseConfigInfoList;
	}

	public String getProcessNum() {
		return processNum;
	}

	public void setProcessNum(String processNum) {
		this.processNum = processNum;
	}
	
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public String getFinishedProductQuantity() {
		return finishedProductQuantity;
	}

	public void setFinishedProductQuantity(String finishedProductQuantity) {
		this.finishedProductQuantity = finishedProductQuantity;
	}

	@Override
	public String toString() {
		return "Product [developmentType=" + developmentType
				+ ", sysParentCode=" + sysParentCode + ", cnName=" + cnName
				+ ", enName=" + enName + ", name=" + name + ", tags=" + tags
				+ ", costPrice=" + costPrice + ", profitMargin=" + profitMargin
				+ ", declaredValue=" + declaredValue + ", sellingPrice="
				+ sellingPrice + ", defaultWeight=" + defaultWeight
				+ ", weightType=" + weightType + ", remark=" + remark
				+ ", casing=" + casing + ", freight=" + freight + ", texture="
				+ texture + ", description=" + description + ", createId="
				+ createId + ", imgUrl=" + imgUrl + ", imgLink=" + imgLink
				+ ", keyName="
				+ keyName + ", startName=" + startName + ", endName=" + endName
				+ ", secondName=" + secondName + ", colorName=" + colorName
				+ ", sizeName=" + sizeName + ", competitorId=" + competitorId
				+ ", mainProductImgList=" + mainProductImgList + ",detailProductImgList=" + detailProductImgList
				+ ", compositeImageList=" + compositeImageList + ", procurementData="
				+ procurementData + ", title="
				+ title + ", code=" + code + ", productId=" + id
				+ ", codeHistory=" + codeHistory + ", type=" + type + ", officeId=" + officeId +", updateId=" + updateId + ", updateTime=" + updateTime +" ]";
	}

	public Product(String id,String sysParentCode,String productType,String tags,String cnName, String enName,Double declaredValue,String name,
				   String procurementChannel,String texture,String specification,
				   Double defaultWeight,Double costPrice) {
		this.id = id;
		this.sysParentCode = sysParentCode;
		this.productType = productType;
		this.tags = tags;
		this.cnName = cnName;
		this.enName = enName;
		this.declaredValue = declaredValue;
		this.name = name;
		this.procurementChannel = procurementChannel;
		this.texture = texture;
		this.specification = specification;
		this.defaultWeight = defaultWeight;
		this.costPrice = costPrice;

	}
	public Product(String id,String name,String title,String sysParentCode,String wishSizeName,String description) {
		this.id = id;
		this.name = name;
		this.title = title;
		this.sysParentCode = sysParentCode;
		this.wishSizeName = wishSizeName;
		this.description = description;
	}
}