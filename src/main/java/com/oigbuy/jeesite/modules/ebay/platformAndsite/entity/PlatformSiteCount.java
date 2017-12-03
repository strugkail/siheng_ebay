/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.platformAndsite.entity;
import com.oigbuy.jeesite.common.persistence.DataEntity;
/**
 * 统计站点、店铺数量实体
 * @author yuxiang.xiong
 * 
 */
public class PlatformSiteCount extends DataEntity<PlatformSiteCount> {

	private static final long serialVersionUID = 1L;
	private String siteName; // 站点名称
	private String countShop; // 店铺数量
	private String id;
    private String platformId;
    private String remark;
    
	public PlatformSiteCount() {
		super();
	}

	public PlatformSiteCount(String id) {
		super(id);
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getCountShop() {
		return countShop;
	}

	public void setCountShop(String countShop) {
		this.countShop = countShop;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
    
}