/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.parcel.entity;

import org.hibernate.validator.constraints.Length;

import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * 包裹表Entity
 * @author handong.wang
 * @version 2017-09-07
 */
public class Parcel extends DataEntity<Parcel> {
	
	private static final long serialVersionUID = 1L;
	private String parcelType;		// 包裹表
	private String length;		// length
	private String wide;		// wide
	private String high;		// high
	private String maxWeight;		// max_weight
	private String minWeight;		// min_weight
	private String limit;		// limit
	
	public Parcel() {
		super();
	}

	public Parcel(String id){
		super(id);
	}

	@Length(min=0, max=255, message="包裹表长度必须介于 0 和 255 之间")
	public String getParcelType() {
		return parcelType;
	}

	public void setParcelType(String parcelType) {
		this.parcelType = parcelType;
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
	
	public String getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(String maxWeight) {
		this.maxWeight = maxWeight;
	}
	
	public String getMinWeight() {
		return minWeight;
	}

	public void setMinWeight(String minWeight) {
		this.minWeight = minWeight;
	}
	
	@Length(min=0, max=255, message="limit长度必须介于 0 和 255 之间")
	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}
	
}