/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * 产品代码管理表Entity
 * @author mashuai
 * @version 2017-05-23
 */
public class ProductCodeManager extends DataEntity<ProductCodeManager> {
	
	private static final long serialVersionUID = 1L;
	private String productId;		// 产品Id
	private String imgUrl;		// 图片Id
	private String cnName;		// 中文名称
	private String enName;		// 英文名称
	private String sysParentSku;		// 系统母代码
	private String sysSku;		// 系统子代码
	private Date createTime;		// 编码时间
	private String length;		// 长
	private String wide;		// 宽
	private String high;		// 高
	private String psize;		// 尺寸
	private String color;		// 颜色
	private String wishSize;    //wish尺寸
	private String wishColor;   //wish颜色
	private Integer recommendNumber;		// 推荐采集样本数量
	
	private Double publishPrice; // 刊登价格
	private Double publishTransPrice; // 刊登运费
	private Double costPrice;		// 成本价  
	private Double weight;		// 重量
	private Double profit;      // 利润
	private Double profitRate; // 利润率
	private int codeIndex; // code序号

	private String sourceId;

	private String property;

	/***
	 * 对应页面的 productId  各平台进行刊登的  EAN 或者  UPC 码
	 */
	private  String productCode;
	
	/***
	 * 该 图片的id
	 */
	private String imageId;
	
	
	/**
	 * 是不是  大 paypal，true 表示是，否则不是
	 */
	private  boolean isBigPaypal;
	
	
	/**
	 * 产品（code_manager）属性
	 */
	private List<ProductProperty> productPropertyList;
	
	
	/***
	 * 该 子sku 对应到 listing mapping 中的sku
	 */
	private String listingSkuCode;
	
	/**
	 * 生成 listing 时的 库存数量
	 */
	private Integer quantity;

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getListingSkuCode() {
		return listingSkuCode;
	}

	public void setListingSkuCode(String listingSkuCode) {
		this.listingSkuCode = listingSkuCode;
	}

	public List<ProductProperty> getProductPropertyList() {
		return productPropertyList;
	}

	public void setProductPropertyList(List<ProductProperty> productPropertyList) {
		this.productPropertyList = productPropertyList;
	}

	public boolean isBigPaypal() {
		return isBigPaypal;
	}

	public void setBigPaypal(boolean isBigPaypal) {
		this.isBigPaypal = isBigPaypal;
	}


	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public ProductCodeManager() {
		super();
	}

	public ProductCodeManager(String id, String productId, String cnName, String enName,
			String sysParentSku, String sysSku, Date createTime, String psize,
			String color,String wishSize,String wishColor,String property) {
		super.id=id;
		this.productId = productId;
		this.cnName = cnName;
		this.enName = enName;
		this.sysParentSku = sysParentSku;
		this.sysSku = sysSku;
		this.createTime = createTime;
		this.psize = psize;
		this.color = color;
		this.wishSize = wishSize;
		this.wishColor = wishColor;
		this.property = property;
	}
	
	public ProductCodeManager(String id, String productId, String cnName, String enName,
			String sysParentSku, String sysSku, Date createTime, String psize,
			String color,String wishSize,String wishColor, Double publishPrice, Double publishTransPrice, Double costPrice,
			Double weight, Double profitRate, Integer recommendNumber,String property,String sourceId,Double profit) {
		super.id = id;
		this.productId = productId;
		this.cnName = cnName;
		this.enName = enName;
		this.sysParentSku = sysParentSku;
		this.sysSku = sysSku;
		this.createTime = createTime;
		this.psize = psize;
		this.color = color;
		this.wishSize = wishSize;
		this.wishColor = wishColor;
		this.publishPrice = publishPrice;
		this.publishTransPrice = publishTransPrice;
		this.costPrice = costPrice;
		this.weight = weight;
		this.profitRate = profitRate;
		this.recommendNumber = recommendNumber;
		this.property = property;
		this.sourceId = sourceId;
		this.profit = profit;
	}


	public ProductCodeManager(String id){
		super(id);
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=300, message="图片Id长度必须介于 0 和 300 之间")
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	@Length(min=0, max=128, message="中文名称长度必须介于 0 和 128 之间")
	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	
	@Length(min=0, max=128, message="英文名称长度必须介于 0 和 128 之间")
	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}
	
	@Length(min=0, max=40, message="系统母代码长度必须介于 0 和 40 之间")
	public String getSysParentSku() {
		return sysParentSku;
	}

	public void setSysParentSku(String sysParentSku) {
		this.sysParentSku = sysParentSku;
	}
	
	@Length(min=0, max=40, message="系统子代码长度必须介于 0 和 40 之间")
	public String getSysSku() {
		return sysSku;
	}

	public void setSysSku(String sysSku) {
		this.sysSku = sysSku;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}
	
	public String getWide() {
		return wide;
	}

	public void setWide(String wide) {
		this.wide = wide;
	}
	
	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}
	
	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
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
	
	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	public Double getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(Double profitRate) {
		this.profitRate = profitRate;
	}

	@Length(min=0, max=40, message="尺寸长度必须介于 0 和 40 之间")
	public String getPsize() {
		return psize;
	}

	public void setPsize(String psize) {
		this.psize = psize;
	}
	
	@Length(min=0, max=40, message="颜色长度必须介于 0 和 40 之间")
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	@Length(min=0, max=40, message="wish尺寸长度必须介于 0 和 40 之间")
	public String getWishSize() {
		return wishSize;
	}

	public void setWishSize(String wishSize) {
		this.wishSize = wishSize;
	}

	@Length(min=0, max=40, message="wish颜色长度必须介于 0 和 40 之间")
	public String getWishColor() {
		return wishColor;
	}

	public void setWishColor(String wishColor) {
		this.wishColor = wishColor;
	}

	@Length(min=0, max=11, message="推荐采集样本数量长度必须介于 0 和 11 之间")
	public Integer getRecommendNumber() {
		return recommendNumber;
	}

	public void setRecommendNumber(Integer recommendNumber) {
		this.recommendNumber = recommendNumber;
	}

	public int getCodeIndex() {
		return codeIndex;
	}

	public void setCodeIndex(int codeIndex) {
		this.codeIndex = codeIndex;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}


	@Override
	public String toString() {
		return "ProductCodeManager{" +
				"productId='" + productId + '\'' +
				", imgUrl='" + imgUrl + '\'' +
				", cnName='" + cnName + '\'' +
				", enName='" + enName + '\'' +
				", sysParentSku='" + sysParentSku + '\'' +
				", sysSku='" + sysSku + '\'' +
				", createTime=" + createTime +
				", length='" + length + '\'' +
				", wide='" + wide + '\'' +
				", high='" + high + '\'' +
				", psize='" + psize + '\'' +
				", color='" + color + '\'' +
				", wishSize='" + wishSize + '\'' +
				", wishColor='" + wishColor + '\'' +
				", recommendNumber=" + recommendNumber +
				", publishPrice=" + publishPrice +
				", publishTransPrice=" + publishTransPrice +
				", costPrice=" + costPrice +
				", weight=" + weight +
				", profitRate=" + profitRate +
				", codeIndex=" + codeIndex +
				", property='" + property + '\'' +
				", productCode='" + productCode + '\'' +
				", imageId='" + imageId + '\'' +
				", isBigPaypal=" + isBigPaypal +
				", productPropertyList=" + productPropertyList +
				'}';
	}
}