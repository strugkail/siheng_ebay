/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.sellgroup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.sellgroup.dao.GroupDao;
import com.oigbuy.jeesite.modules.ebay.sellgroup.entity.Group;
import com.oigbuy.jeesite.modules.ebay.sellgroup.entity.GroupSeller;
import com.oigbuy.jeesite.modules.ebay.sellgroup.entity.GroupUser;
import com.oigbuy.jeesite.modules.ebay.template.entity.Seller;
import com.oigbuy.jeesite.modules.sys.service.SystemService;

/**
 * 销售组Service
 * @author handong.wang
 * @version 2017-10-27
 */
@Service
@Transactional(readOnly = true)
public class GroupService extends CrudService<GroupDao, Group> {
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private GroupDao groupDao;
	
	public Group get(String id) {
		return super.get(id);
	}
	
	public List<Group> findList(Group group) {
		return super.findList(group);
	}
	
	public Page<Group> findPage(Page<Group> page, Group group) {
		return super.findPage(page, group);
	}
	
	@Transactional(readOnly = false)
	public void save(Group group) {
		super.save(group);
	}
	
	@Transactional(readOnly = false)
	public void saveGroupSeller(List<GroupSeller> groupSellers) {
		for(GroupSeller groupSeller : groupSellers){
			groupDao.saveGroupSeller(groupSeller);
		}
	}
	
	@Transactional(readOnly = false)
	public void saveGroupUser(List<GroupUser> groupUsers) {
		for(GroupUser groupUser : groupUsers){
			groupDao.saveGroupUser(groupUser);
		}
	}
	
	@Transactional(readOnly = false)
	public void updateAll(Group group){
		groupDao.update(group);
		groupDao.deleteGroupSeller(group.getGroupId());
		groupDao.deleteGroupUser(group.getGroupId());
	}
	
	@Transactional(readOnly = false)
	public void deleteAll(Integer groupId){
		groupDao.deleteGroup(groupId);
		groupDao.deleteGroupSeller(groupId);
		groupDao.deleteGroupUser(groupId);
	}
	
	@Transactional(readOnly = false)
	public void deleteGroup(Integer groupId){
		groupDao.deleteGroup(groupId);
	}
	
	@Transactional(readOnly = false)
	public void deleteGroupSeller(Integer groupId){
		groupDao.deleteGroupSeller(groupId);
	}
	
	@Transactional(readOnly = false)
	public void deleteGroupUser(Integer groupId){
		groupDao.deleteGroupUser(groupId);
	}
	
	
	@Transactional(readOnly = false)
	public void saveAll(Group group) {
		save(group);
	}

	
	/***
	 * 查询该 用户下的 分组集合，以及分组  集合下的 可刊登店铺 集合
	 * @param  userId
	 * @return
	 */
	public List<Seller> getCanUseSellerList(String userId) {
		List<Seller> sellerList = this.groupDao.findSellerIdList(userId);
		return sellerList;
	}
	
	/**
	 * 根据销售组groupId获取关联的用户列表
	 */
	public List<Integer> getUserList(String groupId){
		return groupDao.getUserList(groupId);
	}
	
	
	/**
	 * 根据销售组groupId获取关联的刊登人员列表
	 */
	public List<Integer> getSellerList(String groupId){
		return groupDao.getSellerList(groupId);
	}
}