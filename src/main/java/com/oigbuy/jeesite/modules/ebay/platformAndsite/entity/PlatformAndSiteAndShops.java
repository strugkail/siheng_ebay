package com.oigbuy.jeesite.modules.ebay.platformAndsite.entity;

/**
 * 此类映射查询语句到实体类的映射，只包含平台ID和店铺ID
 * */
public class PlatformAndSiteAndShops {

	private String platformId;
	private String siteId;
	private String shopsId;
	
	public PlatformAndSiteAndShops() {
		super();
	}
	

	public PlatformAndSiteAndShops(String platformId ,String siteId, String shopsId) {
		super();
		this.platformId = platformId;
		this.shopsId = shopsId;
		this.siteId = siteId;
	}


	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getPlatformId() {
		return platformId;
	}
	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}
	public String getShopsId() {
		return shopsId;
	}
	public void setShopsId(String shopsId) {
		this.shopsId = shopsId;
	}
	
	
	
}
