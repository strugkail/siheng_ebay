/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.productDevelop.dao;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.productDevelop.entity.ProductTags;
import com.oigbuy.jeesite.modules.ebay.tags.entity.Tags;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 产品和标签的关系表DAO接口
 * @author mashuai
 * @version 2017-05-23
 */
@MyBatisDao
public interface ProductTagsDao extends CrudDao<ProductTags> {

	void insertList(List<ProductTags> productTagsList);

	List<Tags> findTagsListByProductId(String productId);
	List<Tags> findTagsListByProductIdAndTypeFlag(@Param("productId") String productId, @Param("typeFlag") String typeFlag);
	
	void deleteAllByProductId(@Param("productId") String productId);
	
}