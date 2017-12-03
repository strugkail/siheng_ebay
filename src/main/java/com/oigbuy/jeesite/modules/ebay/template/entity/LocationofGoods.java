package com.oigbuy.jeesite.modules.ebay.template.entity;

import java.util.Date;

import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * 商品所在地实体
 * @author yuxiang.xiong
 * 2017年9月5日 下午1:58:53
 */
public class LocationofGoods extends DataEntity<LocationofGoods>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
	private String templateName;   //模板名称
	private String siteId;    // 站点id
	private String saleType;   // 销售类型
	private String goodsAddr;  // 商品地址
	private String postCode;   // 邮编
	private String country;  // 国家
	private Date createTime;  //创建时间
	private String createName;
	private Date updateTime;
	private String updateName;
	private String flag;
	
	/***
	 * 用户或则 店铺 限制的 用户账户
	 */
	private String sellerName;
	
	
	
	public LocationofGoods() {
		super();
	}
	public LocationofGoods(String siteId, String saleType) {
		super();
		this.siteId = siteId;
		this.saleType = saleType;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getSaleType() {
		return saleType;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
	public String getGoodsAddr() {
		return goodsAddr;
	}
	public void setGoodsAddr(String goodsAddr) {
		this.goodsAddr = goodsAddr;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	
	
}
