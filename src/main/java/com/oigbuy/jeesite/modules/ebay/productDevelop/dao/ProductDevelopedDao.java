package com.oigbuy.jeesite.modules.ebay.productDevelop.dao;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.product.entity.Product;

/**
 * 产品开发Dao
 * @author strugkail.li
 * @version 2017-09-07
 */
@MyBatisDao
public interface ProductDevelopedDao extends CrudDao<Product> {
}
