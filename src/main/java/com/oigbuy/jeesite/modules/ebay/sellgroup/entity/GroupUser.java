/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.sellgroup.entity;

import org.hibernate.validator.constraints.Length;
import com.oigbuy.jeesite.modules.sys.entity.User;
import javax.validation.constraints.NotNull;

import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * 销售组与登陆账户Entity
 * @author handong.wang
 * @version 2017-10-28
 */
public class GroupUser extends DataEntity<GroupUser> {
	
	private static final long serialVersionUID = 1L;
	private Integer groupId;		// group_id
	private Integer userId;		// user_id
	
	public GroupUser() {
		super();
	}

	public GroupUser(String id){
		super(id);
	}

	@Length(min=1, max=11, message="group_id长度必须介于 1 和 11 之间")
	public Integer getGroupId() {
		return groupId;
	}
	
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	@NotNull(message="user_id不能为空")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	

}