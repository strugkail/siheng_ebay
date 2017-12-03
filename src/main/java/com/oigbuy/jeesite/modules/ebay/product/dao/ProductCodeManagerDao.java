/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.dao;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductCodeManager;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 产品代码管理表DAO接口
 * @author mashuai
 * @version 2017-05-23
 */
@MyBatisDao
public interface ProductCodeManagerDao extends CrudDao<ProductCodeManager> {
	
	List<ProductCodeManager> findListByProductId(String productId);
	void deleteProductCodeManagersByProductId(@Param("productId") String productId);
	/***
	 * 批量新增
	 * 
	 * @param productCodeManagerList
	 */
	void insertList(List<ProductCodeManager> productCodeManagerList);
	/***
	 * 批量更新
	 * 
	 * @param codeList
	 */
	void updateBatch(List<ProductCodeManager> codeList);

	/**
	 * 通过code查询productCodeManager
	 * @param sysParentSku
	 * @return
	 */
	List<ProductCodeManager> getProductCodeManagerByCode(String sysParentSku);

	/**
	 * 通过code更新ProductCodeManager
	 * @param code
	 */
	void UpdateBycode(ProductCodeManager productCodeManager);
	
	/**
	 * 通过子代码Id更新推荐采购数量
	 * @param productCodeManager
	 */
	void updateNumBycodeManagerId(ProductCodeManager productCodeManager);
}