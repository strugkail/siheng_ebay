package com.oigbuy.jeesite.modules.ebay.productcollection.dto;
/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *//*
package com.oigbuy.jeesite.modules.ebay.productcollection.dto;

import org.hibernate.validator.constraints.Length;

import com.oigbuy.jeesite.common.persistence.DataEntity;

*//**
 * 站点Entity
 * @author mashuai
 * @version 2017-05-22
 *//*
public class PlatformSite extends DataEntity<PlatformSite> {
	
	private static final long serialVersionUID = 1L;
	private Long platformId;		// 平台Id
	private String siteName;		// 站点名称
	private String currency;		// 货币
	private String siteShortName;		// 站点名称缩写
	private String paypalAccount;		// paypal卖家账号
	private String remark;
	
	public PlatformSite() {
		super();
	}

	public PlatformSite(String id){
		super(id);
	}

	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}
	
	@Length(min=0, max=128, message="站点名称长度必须介于 0 和 128 之间")
	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	
	@Length(min=0, max=16, message="货币长度必须介于 0 和 16 之间")
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	@Length(min=0, max=16, message="站点名称缩写长度必须介于 0 和 16 之间")
	public String getSiteShortName() {
		return siteShortName;
	}

	public void setSiteShortName(String siteShortName) {
		this.siteShortName = siteShortName;
	}
	
	@Length(min=0, max=32, message="paypal卖家账号长度必须介于 0 和 32 之间")
	public String getPaypalAccount() {
		return paypalAccount;
	}

	public void setPaypalAccount(String paypalAccount) {
		this.paypalAccount = paypalAccount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}*/