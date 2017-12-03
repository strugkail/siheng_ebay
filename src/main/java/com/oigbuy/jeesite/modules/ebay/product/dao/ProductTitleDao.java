/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductTitle;

/**
 * 产品标题DAO接口
 * @author bill.xu
 * @version 2017-09-22
 */
@MyBatisDao
public interface ProductTitleDao extends CrudDao<ProductTitle> {

	
	
	/**
	 * 通过  平台标志 和 产品 id 删除 
	 * 
	 * 
	 * @param productId
	 * @param platformType
	 */
	void deleteByProductId(@Param("productId")String productId, @Param("platformType")String platformType);

	void insertList(List<ProductTitle> list);
	
}