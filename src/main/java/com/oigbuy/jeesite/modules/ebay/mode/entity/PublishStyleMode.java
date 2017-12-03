/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.mode.entity;

import org.hibernate.validator.constraints.Length;

import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * 模板Entity
 * @author bill.xu
 * @version 2017-09-05
 */
public class PublishStyleMode extends DataEntity<PublishStyleMode> {
	
	private static final long serialVersionUID = 1L;
	private String modeName;		// 风格模板的名称
	private String account;		// ebay账号
	private String accountType;		// 账号类型（0所有，1部分）
	private String content;		// 富文本编辑器的内容html
	
	/***
	 * 站点（具体某一个站点下的风格模板）
	 */
	private String siteId;
	
	

	public PublishStyleMode(String account, String accountType) {
		super();
		this.account = account;
		this.accountType = accountType;
	}

	public PublishStyleMode() {
		super();
	}

	public PublishStyleMode(String id){
		super(id);
	}

	@Length(min=0, max=50, message="风格模板的名称长度必须介于 0 和 50 之间")
	public String getModeName() {
		return modeName;
	}

	public void setModeName(String modeName) {
		this.modeName = modeName;
	}
	
	@Length(min=0, max=300, message="ebay账号长度必须介于 0 和 300 之间")
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	@Length(min=0, max=2, message="账号类型（0所有，1部分）长度必须介于 0 和 2 之间")
	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	
	
	
}