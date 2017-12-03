/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.designdrawing.entity;

import java.util.Date;

import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * 设计作图Entity
 * @author 王佳点
 * @version 2017-09-04
 */
public class DesignDrawing extends DataEntity<DesignDrawing> {
	
	private static final long serialVersionUID = 1L;
	private String productId;		// 产品ID
	private String productName;		// 商品名称
	private String imgUrl;			// 示意图片本地url
	private String imgLink;			// 示意图片url
	private String productLink;		// 商品链接
	private String operator;		// 开发人
	private Date createTime;		// 开发时间
	private String type;			//（0.虚拟仓设计作图   1.海外仓设计作图）
	private String sysParentCode;	//系统母代码
	private String saleType;		//销售类型
	
	private Date dateStart;        //查询开始时间
	private Date dateEnd;          //查询结束时间
	
	private String sign;         // 1 已签收 2 未签收
	
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
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
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
	public String getProductLink() {
		return productLink;
	}
	public void setProductLink(String productLink) {
		this.productLink = productLink;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSysParentCode() {
		return sysParentCode;
	}
	public void setSysParentCode(String sysParentCode) {
		this.sysParentCode = sysParentCode;
	}
	public String getSaleType() {
		return saleType;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
}

	
