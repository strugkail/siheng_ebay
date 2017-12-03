/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.productcollection.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * 竞品采集Entity
 * @author 王佳点
 * @version 2017-09-04
 */
public class ProductCollection extends DataEntity<ProductCollection> {
	
	private static final long serialVersionUID = 1L;
//	private Long collectId;			// 采集产品Id
	private String siteId;			// 站点ID
	private String saleTypeId;		// 销售类型(1.海外仓   2.虚拟海外仓)
	private String saleGroupId;		// 销售组
	private String productName;		// 商品名称
	private String imgUrl;			// 图片Url
	private String imgLink;			// 图片链接
	private String productUrl;		// 参考链接
	private String productNumber;		// 采购量
	private String pictureNumber;		// 制图数量(默认为4张)
	private String remark;			// 销售备注
	private String operator;		// 采集人ID
	private Date createTime;		// 操作时间
	private String status;			// 采集状态  （0.产品采集  1.产品测算  2.产品开发  3.设计制图  4.资料完善 5.review.7.放弃开发 8.暂时放弃开发）
	private String recomId;			// 供应商推荐ID
	private String itemId;			//竞争商品ID
	private String productId;		//关联产品ID
	
	private String siteName;   		 //站点名称
	
	private Date dateStart;        //查询采集开始时间
	private Date dateEnd;          //查询采集结束时间
	
	private String type;			//0.保存  1.开发     
	
	private String saleGroupName;   //销售组名
	
	private String createName;		//采集人姓名
	
	public ProductCollection() {
		super();
	}

	public ProductCollection(String id){
		super(id);
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public String getImgLink() {
		return imgLink;
	}

	public void setImgLink(String imgLink) {
		this.imgLink = imgLink;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

//	public Long getCollectId() {
//		return collectId;
//	}
//
//	public void setCollectId(Long collectId) {
//		this.collectId = collectId;
//	}
	
	@Length(min=0, max=20, message="站点ID长度必须介于 0 和 20 之间")
	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	
	@Length(min=0, max=20, message="销售类型长度必须介于 0 和 20 之间")
	public String getSaleTypeId() {
		return saleTypeId;
	}

	public void setSaleTypeId(String saleTypeId) {
		this.saleTypeId = saleTypeId;
	}
	
	public String getSaleGroupId() {
		return saleGroupId;
	}

	public void setSaleGroupId(String saleGroupId) {
		this.saleGroupId = saleGroupId;
	}

	public String getSaleGroupName() {
		return saleGroupName;
	}

	public void setSaleGroupName(String saleGroupName) {
		this.saleGroupName = saleGroupName;
	}

	@Length(min=0, max=300, message="商品名称长度必须介于 0 和 300 之间")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@Length(min=0, max=600, message="参考链接长度必须介于 0 和 600 之间")
	public String getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}
	
	@Min(value=0,message="采购数量必须是数字")
	@Length(min=0, max=11, message="采购量长度必须介于 0 和 11 之间")
	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}
	
	@Min(value=0,message="制图数量必须是数字")
	@Length(min=0, max=11, message="制图数量(默认为4张)长度必须介于 0 和 11 之间")
	public String getPictureNumber() {
		return pictureNumber;
	}

	public void setPictureNumber(String pictureNumber) {
		this.pictureNumber = pictureNumber;
	}
	
	@Length(min=0, max=300, message="销售备注长度必须介于 0 和 300 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Length(min=0, max=50, message="操作人长度必须介于 0 和 50 之间")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=1, message="采集状态长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=32, message="供应商推荐ID长度必须介于 0 和 32 之间")
	public String getRecomId() {
		return recomId;
	}

	public void setRecomId(String recomId) {
		this.recomId = recomId;
	}

	@Length(min=0, max=32, message="竞争商品ID长度必须介于0和32之间")
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}
	
	
}