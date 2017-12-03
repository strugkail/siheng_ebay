/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.listing.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.listing.entity.ListingProperty;

/**
 * listing 属性DAO接口
 * @author bill.xu
 * @version 2017-10-25
 */
@MyBatisDao
public interface ListingPropertyDao extends CrudDao<ListingProperty> {

	/***
	 * 批量新增
	 * @param listingProperties
	 */
	void insertList(List<ListingProperty> listingProperties);

	/***
	 * 批量更新
	 * @param productPropertyList
	 */
	void updateBatch(List<ListingProperty> productPropertyList);

	/***
	 * 通过 分组查询 listing 的属性名称
	 * 
	 * @param listingId
	 * @return
	 */
	List<String> findListingPropertyNames(@Param("listingId")String listingId);

	
	/***
	 * 通过 listingId 和 属性name 获取 对应的 values
	 * 
	 * @param listingId
	 * @param name
	 * @return
	 */
	List<String> findListingPropertyValues(@Param("listingId")String listingId, @Param("name")String name);

	
	/***
	 * 通过 listing id 删除该属性 
	 * 
	 * @param listingId
	 */
	void deleteByListingId(@Param("listingId") String listingId);
	
}