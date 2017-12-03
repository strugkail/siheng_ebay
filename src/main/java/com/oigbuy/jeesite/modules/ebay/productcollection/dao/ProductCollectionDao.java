/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.productcollection.dao;

import org.apache.ibatis.annotations.Param;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.productcollection.entity.ProductCollection;

/**
 * 竞品采集DAO接口
 * @author 王佳点
 * @version 2017-09-04
 */
@MyBatisDao
public interface ProductCollectionDao extends CrudDao<ProductCollection> {

	/**
	 * 通过关联的 推荐id 查询
	 * 
	 * @param id
	 * @return
	 */
	ProductCollection findByRecomId(@Param("recomId") String id);
	
	/**
	 * 通过 产品ID 查询竞品ID
	 * 
	 * @param ProductId
	 * @return
	 */
	String findCollectIdByProductId(@Param("productId") String productId);
	
}