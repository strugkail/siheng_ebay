/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.tags.dao;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.tags.entity.TagsType;

/**
 * 标签类型DAO接口
 * @author mashuai
 * @version 2017-05-22
 */
@MyBatisDao
public interface TagsTypeDao extends CrudDao<TagsType> {
	
}