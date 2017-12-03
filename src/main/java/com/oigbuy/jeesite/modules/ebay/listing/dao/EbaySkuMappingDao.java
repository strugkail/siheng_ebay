/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.listing.dao;

import java.util.List;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.listing.entity.EbaySkuMapping;

/**
 * ebay sku 映射DAO接口
 * @author bill.xu
 * @version 2017-09-22
 */
@MyBatisDao
public interface EbaySkuMappingDao extends CrudDao<EbaySkuMapping> {

	void insertList(List<EbaySkuMapping> list);

	/***
	 * 批量更新  刊登的 EAN/UPC
	 * 
	 * @param skuMappingList
	 */
	void updateBatch(List<EbaySkuMapping> skuMappingList);
	
}