/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.ebayfee.entity;

import org.hibernate.validator.constraints.Length;

import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * ebay最小费率Entity
 * @author strugkail
 * @version 2017-09-25
 */
public class EbayFee extends DataEntity<EbayFee> {
	
	private static final long serialVersionUID = 1L;
	private String categoryName;		// category_name
	private String feeRate;		// fee_rate
	private String maxValue;		// max_value
	private String siteShortname;		// site_shortname
	
	public EbayFee() {
		super();
	}

	public EbayFee(String siteShortname){
		this.siteShortname = siteShortname;
	}
	public EbayFee(String categoryName,String siteShortname){
		this.siteShortname = siteShortname;
		this.categoryName = categoryName;
	}

	@Length(min=0, max=100, message="category_name长度必须介于 0 和 100 之间")
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	@Length(min=0, max=10, message="fee_rate长度必须介于 0 和 10 之间")
	public String getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(String feeRate) {
		this.feeRate = feeRate;
	}
	
	@Length(min=0, max=40, message="max_value长度必须介于 0 和 40 之间")
	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}
	
	@Length(min=0, max=40, message="site_shortname长度必须介于 0 和 40 之间")
	public String getSiteShortname() {
		return siteShortname;
	}

	public void setSiteShortname(String siteShortname) {
		this.siteShortname = siteShortname;
	}
	
}