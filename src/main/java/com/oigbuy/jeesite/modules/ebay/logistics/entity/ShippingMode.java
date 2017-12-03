/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.logistics.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * 运输模板Entity
 * @author handong.wang
 * @version 2017-09-07
 */
public class ShippingMode extends DataEntity<ShippingMode> {
	
	private static final long serialVersionUID = 1L;
	private String modeId;		// mode_id
	private String shippingType;		// shipping_type
	private String ebayTransportStrategy;		// ebay_transport_strategy
	private float firstTimeFee;		// first_time_fee_usd
	private float firstTimeFeeCny;		// first_time_fee_cny
	private float renewalFee;		// renewal_fee_usd
	private float renewalFeeCny;		// renewal_fee_cny
	private String country;		// country
	private String type;		// type
	private String sort;		// sort
	private float akHiPr;		// ak_hi_pr_usd
	private float akHiPrCny;		// ak_hi_pr_cny
	private String description;		//运输方式描述
	private List<String> selectCountryList; //选中的country
	private List<SendToCountryParam> sendToCountryList;
	private List<ShippingTypeParam> shippingTypeList;
	private List<ShippingTypeParam> shippingTypeInsideList;
	private List<ShippingTypeParam> shippingTypeOutsideList;

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<SendToCountryParam> getSendToCountryList() {
		return sendToCountryList;
	}
	public void setSendToCountryList(List<SendToCountryParam> sendToCountryList) {
		this.sendToCountryList = sendToCountryList;
	}
	public List<ShippingTypeParam> getShippingTypeList() {
		return shippingTypeList;
	}
	public void setShippingTypeList(List<ShippingTypeParam> shippingTypeList) {
		this.shippingTypeList = shippingTypeList;
	}
	public List<String> getSelectCountryList() {
		return selectCountryList;
	}
	public void setSelectCountryList(List<String> selectCountryList) {
		this.selectCountryList = selectCountryList;
	}
	public ShippingMode() {
		super();
	}
	public ShippingMode(String id){
		super(id);
	}
	@Length(min=1, max=11, message="mode_id长度必须介于 1 和 11 之间")
	public String getModeId() {
		return modeId;
	}

	public void setModeId(String modeId) {
		this.modeId = modeId;
	}
	
	@Length(min=0, max=255, message="shipping_type长度必须介于 0 和 255 之间")
	public String getShippingType() {
		return shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}
	
	@Length(min=0, max=255, message="ebay_transport_strategy长度必须介于 0 和 255 之间")
	public String getEbayTransportStrategy() {
		return ebayTransportStrategy;
	}

	public void setEbayTransportStrategy(String ebayTransportStrategy) {
		this.ebayTransportStrategy = ebayTransportStrategy;
	}
	
	
	
	@Length(min=0, max=255, message="country长度必须介于 0 和 255 之间")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Length(min=0, max=50, message="type长度必须介于 0 和 50 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=50, message="sort长度必须介于 0 和 50 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	public float getFirstTimeFee() {
		return firstTimeFee;
	}
	public void setFirstTimeFee(float firstTimeFee) {
		this.firstTimeFee = firstTimeFee;
	}
	public float getFirstTimeFeeCny() {
		return firstTimeFeeCny;
	}
	public void setFirstTimeFeeCny(float firstTimeFeeCny) {
		this.firstTimeFeeCny = firstTimeFeeCny;
	}
	public float getRenewalFee() {
		return renewalFee;
	}
	public void setRenewalFee(float renewalFee) {
		this.renewalFee = renewalFee;
	}
	public float getRenewalFeeCny() {
		return renewalFeeCny;
	}
	public void setRenewalFeeCny(float renewalFeeCny) {
		this.renewalFeeCny = renewalFeeCny;
	}
	public float getAkHiPr() {
		return akHiPr;
	}
	public void setAkHiPr(float akHiPr) {
		this.akHiPr = akHiPr;
	}
	public float getAkHiPrCny() {
		return akHiPrCny;
	}
	public void setAkHiPrCny(float akHiPrCny) {
		this.akHiPrCny = akHiPrCny;
	}
	public List<ShippingTypeParam> getShippingTypeInsideList() {
		return shippingTypeInsideList;
	}
	public void setShippingTypeInsideList(
			List<ShippingTypeParam> shippingTypeInsideList) {
		this.shippingTypeInsideList = shippingTypeInsideList;
	}
	public List<ShippingTypeParam> getShippingTypeOutsideList() {
		return shippingTypeOutsideList;
	}
	public void setShippingTypeOutsideList(
			List<ShippingTypeParam> shippingTypeOutsideList) {
		this.shippingTypeOutsideList = shippingTypeOutsideList;
	}

}