/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.productcollection.dao;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.productcollection.entity.ProductCollectionLog;

/**
 * 竞品采集记录日志DAO接口
 * @author bill.xu
 * @version 2017-09-07
 */
@MyBatisDao
public interface ProductCollectionLogDao extends CrudDao<ProductCollectionLog> {
	
}