/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductCategory;

/**
 * 产品分类DAO接口
 * @author bill.xu
 * @version 2017-09-22
 */
@MyBatisDao
public interface ProductCategoryDao extends CrudDao<ProductCategory> {

	void deleteByProductIdAndSiteId(@Param("productId")String productId, @Param("siteId")String siteId, @Param("listingId")Long listingId);

	void insertList(List<ProductCategory> list);
	
}