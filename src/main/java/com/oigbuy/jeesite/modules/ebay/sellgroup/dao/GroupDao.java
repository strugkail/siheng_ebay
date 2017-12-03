/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.sellgroup.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.sellgroup.entity.Group;
import com.oigbuy.jeesite.modules.ebay.sellgroup.entity.GroupSeller;
import com.oigbuy.jeesite.modules.ebay.sellgroup.entity.GroupUser;
import com.oigbuy.jeesite.modules.ebay.template.entity.Seller;

/**
 * 销售组DAO接口
 * @author handong.wang
 * @version 2017-10-27
 */
@MyBatisDao
public interface GroupDao extends CrudDao<Group> {
	
	public void saveGroupSeller(GroupSeller groupSeller);
	
	public void saveGroupUser(GroupUser groupUser);
	
	public void deleteGroup(@Param("groupId")Integer groupId);
	
	public void deleteGroupSeller(@Param("groupId")Integer groupId);
	
	public void deleteGroupUser(@Param("groupId")Integer groupId);

	public List<Seller> findSellerIdList(@Param("userId")String id);
	
	public List<Integer> getUserList(String groupId);
	
	public List<Integer> getSellerList(String groupId);
	
}