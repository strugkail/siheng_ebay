/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.listing.entity;

import org.hibernate.validator.constraints.Length;

import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * listing 属性Entity
 * @author bill.xu
 * @version 2017-10-25
 */
public class ListingProperty extends DataEntity<ListingProperty> {
	
	private static final long serialVersionUID = 1L;
	private Long listingId;		// listing  id
	private String name;		// 属性名称
	private String value;		// 属性值
	private Long siteId;		// 站点id
	private Long codeManagerId;		// 子 sku 代码id code manager id
	
	
	
	public ListingProperty(Long listingId, Long codeManagerId) {
		super();
		this.listingId = listingId;
		this.codeManagerId = codeManagerId;
	}

	public ListingProperty() {
		super();
	}

	public ListingProperty(String id){
		super(id);
	}

	public Long getListingId() {
		return listingId;
	}

	public void setListingId(Long listingId) {
		this.listingId = listingId;
	}
	
	@Length(min=0, max=300, message="属性名称长度必须介于 0 和 300 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=300, message="属性值长度必须介于 0 和 300 之间")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	
	public Long getCodeManagerId() {
		return codeManagerId;
	}

	public void setCodeManagerId(Long codeManagerId) {
		this.codeManagerId = codeManagerId;
	}
	
}