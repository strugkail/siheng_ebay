/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.dao;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductQueue;

/**
 * 对消息的增删改查DAO接口
 * @author AA
 * @version 2017-10-27
 */
@MyBatisDao
public interface ProductQueueDao extends CrudDao<ProductQueue> {
	
}