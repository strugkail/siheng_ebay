/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.sellgroup.entity;

import org.hibernate.validator.constraints.Length;

import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * 销售组和刊登关联Entity
 * @author handong.wang
 * @version 2017-10-28
 */
public class GroupSeller extends DataEntity<GroupSeller> {
	
	private static final long serialVersionUID = 1L;
	private Integer groupId;		// group_id
	private Integer sellerId;		// seller_id
	
	public GroupSeller() {
		super();
	}

	public GroupSeller(String id){
		super(id);
	}

	@Length(min=1, max=11, message="group_id长度必须介于 1 和 11 之间")
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	@Length(min=1, max=11, message="seller_id长度必须介于 1 和 11 之间")
	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	
	
	
	
}