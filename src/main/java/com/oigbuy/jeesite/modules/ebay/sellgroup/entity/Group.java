/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.sellgroup.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.oigbuy.jeesite.common.persistence.DataEntity;
import com.oigbuy.jeesite.modules.ebay.template.entity.Seller;
import com.oigbuy.jeesite.modules.sys.entity.User;

/**
 * 销售组Entity
 * @author handong.wang
 * @version 2017-10-27
 */
public class Group extends DataEntity<Group> {
	
	private static final long serialVersionUID = 1L;
	private Integer groupId;		// 销售组id
	private String sellerGroupName;		// 组名
	private List<Integer> userList;
	private List<Integer> sellerList;
	
	public Group() {
		super();
	}
	public Group(String id){
		super(id);
	}
	
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public String getSellerGroupName() {
		return sellerGroupName;
	}
	public void setSellerGroupName(String sellerGroupName) {
		this.sellerGroupName = sellerGroupName;
	}
	public List<Integer> getUserList() {
		return userList;
	}
	public void setUserList(List<Integer> userList) {
		this.userList = userList;
	}
	public List<Integer> getSellerList() {
		return sellerList;
	}
	public void setSellerList(List<Integer> sellerList) {
		this.sellerList = sellerList;
	}
	
	
	

	
	
	
	
	
}