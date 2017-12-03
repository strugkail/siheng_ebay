/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.logistics.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oigbuy.jeesite.common.persistence.DataEntity;
import com.oigbuy.jeesite.modules.ebay.mode.entity.ShieldCountryMode;

/**
 * 物流设置Entity
 * @author 王汉东
 * @version 2017-09-05
 */
public class LogisticsMode extends DataEntity<LogisticsMode> {
	
	private static final long serialVersionUID = 1L;
	private String modelName;		// model_name
	private String salesType;		// sales_type
	private String siteId;		// site
	private float minPrice;		// price
	private float maxPrice;		// price
	private String parcelType;		// parcel_type
	private float minWeight;		// min_weight
	private float maxWeight;		// max_weight
	private float minLength;		// length
	private float maxLength;		// length
	private float minWide;		// wide
	private float maxWide;		// wide
	private float minHigh;		// high
	private float maxHigh;		// high
	private String shieldDestination;		// shield_destination  
	private String stockUpTime;		// stock_up_time  备货时间
	private String commondityProcurementCycle;		// commondity_procurement_cycle  商品采购周期
	private float tax;		// tax 
	private float taxRate;		// tax_rate
	
	private String rule;		// rule
	private String createId;		// create_id
	private Date createTime;		// create_time
	private String warehouse;		// warehouse
	private List<ShippingMode> interiorList;
	private List<ShippingMode> externalList;
	private ShippingMode shippingMode;
	private String localPickup;

	/***
	 *屏蔽目的地模板
	 */
	private ShieldCountryMode shieldCountryMode;
	
	/***
	 * 包裹信息  是否不规则
	 */
	private boolean notRegular;
	/***
	 * 运费是否加税
	 */
	private boolean addTax;
	
	
	/***
	 * 体积的计算公式  如 要求 长宽高乘积小于 0.23立方米 ，并且 有 重量在  100-200 g 之间  仓库等于  '中国直发仓',如果没有限制 则 可以为空 
	 * 
	 * 则可表示为  C*K*G<= 2300 && Z<=200 && Z>=100 && WH=='中国直发仓'
	 * 
	 * 如果是   海外仓精品 ，站点在  美国  英国  德国  该字段要设值为 必填字段
	 */
	private String formula;
	
	
	
	
	
	public boolean isNotRegular() {
		return notRegular;
	}

	public void setNotRegular(boolean notRegular) {
		this.notRegular = notRegular;
	}

	public boolean isAddTax() {
		return addTax;
	}

	public void setAddTax(boolean addTax) {
		this.addTax = addTax;
	}

	public String getLocalPickup() {
		return localPickup;
	}

	public void setLocalPickup(String localPickup) {
		this.localPickup = localPickup;
	}

	public ShieldCountryMode getShieldCountryMode() {
		return shieldCountryMode;
	}

	public void setShieldCountryMode(ShieldCountryMode shieldCountryMode) {
		this.shieldCountryMode = shieldCountryMode;
	}

	public ShippingMode getShippingMode() {
		return shippingMode;
	}

	public void setShippingMode(ShippingMode shippingMode) {
		this.shippingMode = shippingMode;
	}

	public List<ShippingMode> getInteriorList() {
		return interiorList;
	}

	public void setInteriorList(List<ShippingMode> interiorList) {
		this.interiorList = interiorList;
	}

	public List<ShippingMode> getExternalList() {
		return externalList;
	}

	public void setExternalList(List<ShippingMode> externalList) {
		this.externalList = externalList;
	}

	public LogisticsMode() {
		super();
	}

	public LogisticsMode(String id){
		super(id);
	}

	@Length(min=0, max=50, message="model_name长度必须介于 0 和 50 之间")
	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	@Length(min=0, max=200, message="shield_destination长度必须介于 0 和 200 之间")
	public String getShieldDestination() {
		return shieldDestination;
	}

	public void setShieldDestination(String shieldDestination) {
		this.shieldDestination = shieldDestination;
	}
	
	@Length(min=0, max=50, message="stock_up_time长度必须介于 0 和 50 之间")
	public String getStockUpTime() {
		return stockUpTime;
	}

	public void setStockUpTime(String stockUpTime) {
		this.stockUpTime = stockUpTime;
	}
	
	@Length(min=0, max=50, message="commondity_procurement_cycle长度必须介于 0 和 50 之间")
	public String getCommondityProcurementCycle() {
		return commondityProcurementCycle;
	}

	public void setCommondityProcurementCycle(String commondityProcurementCycle) {
		this.commondityProcurementCycle = commondityProcurementCycle;
	}
	
	@Length(min=0, max=8, message="rule长度必须介于 0 和 8 之间")
	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}
	
	
	
	@Length(min=0, max=50, message="create_id长度必须介于 0 和 50 之间")
	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=50, message="sales_type长度必须介于 0 和 50 之间")
	public String getSalesType() {
		return salesType;
	}

	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}
	
	@Length(min=0, max=100, message="site_id长度必须介于 0 和 100 之间")
	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	
	@Length(min=0, max=100, message="warehouse长度必须介于 0 和 100 之间")
	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	

	@Length(min=0, max=100, message="parcel_type长度必须介于 0 和 100 之间")
	public String getParcelType() {
		return parcelType;
	}

	public void setParcelType(String parcelType) {
		this.parcelType = parcelType;
	}

	public float getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(float minPrice) {
		this.minPrice = minPrice;
	}

	public float getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(float maxPrice) {
		this.maxPrice = maxPrice;
	}

	public float getMinWeight() {
		return minWeight;
	}

	public void setMinWeight(float minWeight) {
		this.minWeight = minWeight;
	}

	public float getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(float maxWeight) {
		this.maxWeight = maxWeight;
	}

	public float getMinLength() {
		return minLength;
	}

	public void setMinLength(float minLength) {
		this.minLength = minLength;
	}

	public float getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(float maxLength) {
		this.maxLength = maxLength;
	}

	public float getMinWide() {
		return minWide;
	}

	public void setMinWide(float minWide) {
		this.minWide = minWide;
	}

	public float getMaxWide() {
		return maxWide;
	}

	public void setMaxWide(float maxWide) {
		this.maxWide = maxWide;
	}

	public float getMinHigh() {
		return minHigh;
	}

	public void setMinHigh(float minHigh) {
		this.minHigh = minHigh;
	}

	public float getMaxHigh() {
		return maxHigh;
	}

	public void setMaxHigh(float maxHigh) {
		this.maxHigh = maxHigh;
	}

	public float getTax() {
		return tax;
	}

	public void setTax(float tax) {
		this.tax = tax;
	}

	public float getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(float taxRate) {
		this.taxRate = taxRate;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}
	
	
}