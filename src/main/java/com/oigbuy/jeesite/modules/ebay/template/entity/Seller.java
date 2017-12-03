package com.oigbuy.jeesite.modules.ebay.template.entity;

import java.util.Date;

import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * 卖家账户Entity实体类
 * 
 * @author jalyn.zhang
 *
 */
public class Seller extends DataEntity<Seller> {
	
	private static final long serialVersionUID = 1L;
	
	private String id;	//模板ID
	private String sellerName;	//卖家账号
	private Long siteId;	//主站点ID
	private Long subSiteId;	//分站点ID
	private String operator;	// 操作人
	private Date operateTime;	// 操作时间
	
	private String sname;	//卖家账号简写
	private String palAccount;	//PayPal账户  大 
	private String subPalAccount;	//PayPal账户2
	
	private Long shopId;//店铺ID
	
	
	
	public Seller() {
		super();
	}
	public Seller(Long siteId, Long shopId) {
		super();
		this.siteId = siteId;
		this.shopId = shopId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getPalAccount() {
		return palAccount;
	}
	public void setPalAccount(String palAccount) {
		this.palAccount = palAccount;
	}
	public String getSubPalAccount() {
		return subPalAccount;
	}
	public void setSubPalAccount(String subPalAccount) {
		this.subPalAccount = subPalAccount;
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
	public Long getSubSiteId() {
		return subSiteId;
	}
	public void setSubSiteId(Long subSiteId) {
		this.subSiteId = subSiteId;
	}

}
