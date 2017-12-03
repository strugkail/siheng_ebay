/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.tags.service;

import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.tags.dao.TagsTypeDao;
import com.oigbuy.jeesite.modules.ebay.tags.entity.TagsType;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 标签类型Service
 * @author mashuai
 * @version 2017-05-22
 */
@Service
@Transactional(readOnly = true)
public class TagsTypeService extends CrudService<TagsTypeDao, TagsType> {

	public TagsType get(String id) {
		return super.get(id);
	}
	
	public List<TagsType> findList(TagsType tagsType) {
		return super.findList(tagsType);
	}
	
	public Page<TagsType> findPage(Page<TagsType> page, TagsType tagsType) {
		return super.findPage(page, tagsType);
	}
	
	@Transactional(readOnly = false)
	public void save(TagsType tagsType) {
		super.save(tagsType);
	}
	
	@Transactional(readOnly = false)
	public void delete(TagsType tagsType) {
		super.delete(tagsType);
	}
	
}