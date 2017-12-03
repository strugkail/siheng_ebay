/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.platformAndsite.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * 店铺Entity
 * @author mashuai
 * @version 2017-05-22
 */
public class Shops extends DataEntity<Shops> {
	
	private static final long serialVersionUID = 1L;
	private Long platformId;		// 平台ID
	private Long siteId;		// 站点ID
	private String name;		// 店铺名称
	private String account;		// 店铺登录名称
	private String password;		// 店铺登录密码
	private String description;		// 描述
	private Date createTime;     //店铺创建时间
	private Date updateTime; 	//更新
	private Date refreshTokenTime; // 刷新token时间
	private String platformName;   //平台名称
	private String siteName;
	private String shopTagsName;      //店铺标签名称
	private String shopTagsId;      //店铺标签ID
	private String codePreFix; //平台母代码前缀
	private List<String> countryIds;
	private String flag;
	
	private String shopStatus; // 0新店铺1老店铺
	
	private Date dateStart;        //查询开始时间
	private Date dateEnd;          //查询结束时间
	
	private String token;
	private String groupId;
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Shops() {
		super();
	}

	public Shops(String id){
		super(id);
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	
	@Length(min=0, max=32, message="店铺名称长度必须介于 0 和 32 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=32, message="店铺登录名称长度必须介于 0 和 32 之间")
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	@Length(min=0, max=32, message="店铺登录密码长度必须介于 0 和 32 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Length(min=0, max=300, message="描述长度必须介于 0 和 300 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getRefreshTokenTime() {
		return refreshTokenTime;
	}

	public void setRefreshTokenTime(Date refreshTokenTime) {
		this.refreshTokenTime = refreshTokenTime;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getShopTagsName() {
		return shopTagsName;
	}

	public void setShopTagsName(String shopTagsName) {
		this.shopTagsName = shopTagsName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getShopTagsId() {
		return shopTagsId;
	}

	public void setShopTagsId(String shopTagsId) {
		this.shopTagsId = shopTagsId;
	}

	public String getCodePreFix() {
		return codePreFix;
	}

	public void setCodePreFix(String codePreFix) {
		this.codePreFix = codePreFix;
	}

	public List<String> getCountryIds() {
		return countryIds;
	}

	public void setCountryIds(List<String> countryIds) {
		this.countryIds = countryIds;
	}

	public String getShopStatus() {
		return shopStatus;
	}

	public void setShopStatus(String shopStatus) {
		this.shopStatus = shopStatus;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	
	
}