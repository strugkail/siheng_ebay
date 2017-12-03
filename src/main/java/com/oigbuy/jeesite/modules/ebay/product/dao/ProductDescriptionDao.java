/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.dao;

import java.util.List;

import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.KeyWord;
import org.apache.ibatis.annotations.Param;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductDescription;

/**
 * 产品描述DAO接口
 * @author bill.xu
 * @version 2017-10-19
 */
@MyBatisDao
public interface ProductDescriptionDao extends CrudDao<ProductDescription> {

	void insertList(List<ProductDescription> descriptionList);

	void deleteByProductId(@Param("productId")String productId, @Param("platformType")String platformType);


}