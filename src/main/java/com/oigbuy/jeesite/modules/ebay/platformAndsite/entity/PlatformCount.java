/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.platformAndsite.entity;

import org.hibernate.validator.constraints.Length;

import com.oigbuy.jeesite.common.persistence.DataEntity;
/**
 * 统计站点、店铺数量实体
 * @author yuxiang.xiong
 * 
 */
public class PlatformCount extends DataEntity<PlatformCount> {

	private static final long serialVersionUID = 1L;
	private String name; // 平台名称
	private String countSite; // 站点数量
	private String countShop; // 店铺数量
	private String id;
	private String remark;

	public PlatformCount() {
		super();
	}

	public PlatformCount(String id) {
		super(id);
	}

	@Length(min = 0, max = 128, message = "平台名称长度必须介于 0 和 128 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountSite() {
		return countSite;
	}

	public void setCountSite(String countSite) {
		this.countSite = countSite;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
    
}