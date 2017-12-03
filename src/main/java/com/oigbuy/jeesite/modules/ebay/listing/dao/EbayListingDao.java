/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.listing.dao;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.listing.entity.EbayListing;

/**
 * ebay listingDAO接口
 * @author bill.xu
 * @version 2017-09-22
 */
@MyBatisDao
public interface EbayListingDao extends CrudDao<EbayListing> {
	
}