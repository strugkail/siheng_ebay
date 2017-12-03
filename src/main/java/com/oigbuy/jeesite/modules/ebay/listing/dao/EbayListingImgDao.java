/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.listing.dao;

import java.util.List;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.listing.entity.EbayListingImg;

/**
 * ebay listing 图片DAO接口
 * @author bill.xu
 * @version 2017-09-22
 */
@MyBatisDao
public interface EbayListingImgDao extends CrudDao<EbayListingImg> {

	void insertList(List<EbayListingImg> list);
	
}