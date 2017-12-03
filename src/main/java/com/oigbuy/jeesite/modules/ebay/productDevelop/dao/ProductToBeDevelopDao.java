package com.oigbuy.jeesite.modules.ebay.productDevelop.dao;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.productDevelop.entity.ProductToBeDevelop;

/**
 * 产品开发Dao
 * @author strugkail.li
 * @version 2017-09-04
 */
@MyBatisDao
public interface ProductToBeDevelopDao extends CrudDao<ProductToBeDevelop> {
    int update(ProductToBeDevelop collect);
    ProductToBeDevelop get(String collectId);
}
