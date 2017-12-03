/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.listing.dao;

import java.util.List;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.listing.entity.EbayListingPrice;

/**
 * ebay  listing 价格DAO接口
 * @author bill.xu
 * @version 2017-09-22
 */
@MyBatisDao
public interface EbayListingPriceDao extends CrudDao<EbayListingPrice> {

	/***
	 * 批量插入新增
	 * 
	 * @param list
	 */
	void insertList(List<EbayListingPrice> list);

	/***
	 * 批量修改
	 * @param priceList
	 */
	void updateBatch(List<EbayListingPrice> priceList);
	
}