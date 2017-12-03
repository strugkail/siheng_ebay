/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.product.entity.TitleKey;

/**
 * 标题关键字DAO接口
 * @author bill.xu
 * @version 2017-09-22
 */
@MyBatisDao
public interface TitleKeyDao extends CrudDao<TitleKey> {

	void insertList(List<TitleKey> keyList);

	
	/***
	 * 产品id 删除 ebay 下的 标题关键字
	 * 
	 * @param productId
	 * @param platformType
	 */
	void deletByProductId(@Param("productId")String productId, @Param("platformType")String platformType);
	
}