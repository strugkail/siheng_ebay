/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.tags.service;

import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.tags.dao.TagsDao;
import com.oigbuy.jeesite.modules.ebay.tags.entity.Tags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 标签Service
 * @author mashuai
 * @version 2017-05-22
 */
@Service
@Transactional(readOnly = true)
public class TagsService extends CrudService<TagsDao, Tags> {
	
	@Autowired
	private TagsDao tagsDao;

	public Tags get(String id) {
		return super.get(id);
	}
	
	public List<Tags> findList(Tags tags) {
		return super.findList(tags);
	}
	
	public Page<Tags> findPage(Page<Tags> page, Tags tags) {
		return super.findPage(page, tags);
	}
	
	@Transactional(readOnly = false)
	public void save(Tags tags) {
		super.save(tags);
	}
	
	@Transactional(readOnly = false)
	public void delete(Tags tags) {
		super.delete(tags);
	}

	/**
	 * 
	 * @author yuxiang.xiong
	 *
	 * @param typeFlag(1,产品标签 2，规格标签  3 店铺标签)
	 * @return 
	 */
	public List<Tags> findTagsListByTypeFlag(String typeFlag) {
		return tagsDao.findTagsListByTypeFlag(typeFlag);
	}
	
}