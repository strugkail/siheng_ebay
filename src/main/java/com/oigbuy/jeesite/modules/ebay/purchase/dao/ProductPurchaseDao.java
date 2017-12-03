/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.purchase.dao;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.purchase.entity.ProductPurchase;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 产品采购DAO接口
 * @author 马志明
 * @version 2017-06-21
 */
@MyBatisDao
public interface ProductPurchaseDao extends CrudDao<ProductPurchase> {
	
	void insertList(List<ProductPurchase> list);
	
	void deletePurchaseSourceCodesByProductId(@Param("productId") String productId);
	
}