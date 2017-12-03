//package com.oigbuy.jeesite.modules.ebay.listing.dto.req;
//
//import java.io.Serializable;
///**
// * 物流设置
// * 
// * @author jalyn.zhang
// *
// */
//public class ShippingDetail implements Serializable{
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	
//	private String shippingServiceModel;//运输模板
//	
//	private String shippingStrategyId;//运输策略ID
//	private String shippingStrategy;//运输策略
//	
//
//	private ShippingServiceDto service1;
//	private ShippingServiceDto service2;
//	private ShippingServiceDto service3;
//	private ShippingServiceDto service4;
//	private ShippingServiceDto service5;
//	
//	private String[] exCountries;//屏蔽目的地  英文的  TODO 
//	
//	private String readyTime;//备货时间  1天 2天 数字
//	
//	private String buyingTime;//商品采购周期  天数 1 
//	
//	private boolean globalShippling;//    TODO 
//	
//	private String localPickup;//    TODO 
//	
//	
//	public String getLocalPickup() {
//		return localPickup;
//	}
//	public void setLocalPickup(String localPickup) {
//		this.localPickup = localPickup;
//	}
//	public String getShippingServiceModel() {
//		return shippingServiceModel;
//	}
//	public void setShippingServiceModel(String shippingServiceModel) {
//		this.shippingServiceModel = shippingServiceModel;
//	}
//	public String getShippingStrategy() {
//		return shippingStrategy;
//	}
//	public void setShippingStrategy(String shippingStrategy) {
//		this.shippingStrategy = shippingStrategy;
//	}
//	public boolean isGlobalShippling() {
//		return globalShippling;
//	}
//	public void setGlobalShippling(boolean globalShippling) {
//		this.globalShippling = globalShippling;
//	}
//	
//	
//	public String[] getExCountries() {
//		return exCountries;
//	}
//	public void setExCountries(String[] exCountries) {
//		this.exCountries = exCountries;
//	}
//	public String getReadyTime() {
//		return readyTime;
//	}
//	public void setReadyTime(String readyTime) {
//		this.readyTime = readyTime;
//	}
//	public String getBuyingTime() {
//		return buyingTime;
//	}
//	public void setBuyingTime(String buyingTime) {
//		this.buyingTime = buyingTime;
//	}
//	public String getShippingStrategyId() {
//		return shippingStrategyId;
//	}
//	public void setShippingStrategyId(String shippingStrategyId) {
//		this.shippingStrategyId = shippingStrategyId;
//	}
//	public ShippingServiceDto getService1() {
//		return service1;
//	}
//	public void setService1(ShippingServiceDto service1) {
//		this.service1 = service1;
//	}
//	public ShippingServiceDto getService2() {
//		return service2;
//	}
//	public void setService2(ShippingServiceDto service2) {
//		this.service2 = service2;
//	}
//	public ShippingServiceDto getService3() {
//		return service3;
//	}
//	public void setService3(ShippingServiceDto service3) {
//		this.service3 = service3;
//	}
//	public ShippingServiceDto getService4() {
//		return service4;
//	}
//	public void setService4(ShippingServiceDto service4) {
//		this.service4 = service4;
//	}
//	public ShippingServiceDto getService5() {
//		return service5;
//	}
//	public void setService5(ShippingServiceDto service5) {
//		this.service5 = service5;
//	}
//
//	
//}
