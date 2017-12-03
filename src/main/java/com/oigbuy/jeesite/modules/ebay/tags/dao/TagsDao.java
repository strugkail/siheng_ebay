/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.tags.dao;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.tags.entity.Tags;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 标签DAO接口
 * @author mashuai
 * @version 2017-05-22
 */
@MyBatisDao
public interface TagsDao extends CrudDao<Tags> {

	List<Tags> findTagsListByTypeFlag(String typeFlag);
	Tags queryTagsByTagName(@Param("tagName") String tagName);
	
}