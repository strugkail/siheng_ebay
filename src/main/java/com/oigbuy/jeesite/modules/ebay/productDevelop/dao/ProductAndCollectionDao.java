package com.oigbuy.jeesite.modules.ebay.productDevelop.dao;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.productDevelop.entity.ProductToBeDevelop;

import org.apache.ibatis.annotations.Param;

/**
 * 产品开发Dao
 * @author strugkail.li
 * @version 2017-09-04
 */
@MyBatisDao
public interface ProductAndCollectionDao extends CrudDao<ProductToBeDevelop> {
    int insert(ProductToBeDevelop collect);
    int updateById(ProductToBeDevelop collect);
    String findByCollectId(@Param(value="collectId")String collectId);
    String findByProductId(@Param(value="productId")String productId);
    int deleteByProductId(String productId);
}
