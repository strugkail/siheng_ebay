/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.productDevelop.dao;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.KeyWord;
import com.oigbuy.jeesite.modules.ebay.product.entity.Product;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductProperty;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductPropertyValue;
import com.oigbuy.jeesite.modules.ebay.productDevelop.entity.ProductTags;
import com.oigbuy.jeesite.modules.ebay.purchase.dto.PurchaseConfigInfo;
import com.oigbuy.jeesite.modules.ebay.tags.entity.Tags;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 产品和标签的关系表DAO接口
 * @author strugkail.li
 * @version
 */
@MyBatisDao
public interface ProductDevelopDao extends CrudDao<ProductTags> {

    List<PurchaseConfigInfo> getPurchaseConfigInfoListByProductId(@Param("productId") String productId);

    List<PurchaseConfigInfo> getInfoListByProductId(@Param("productId") String productId);

    void insert(Product product);

    void updateById(Product product);

    List<ProductProperty> findPropertyListById(String productId);

    List<ProductProperty> findPropertyValueListById(ProductProperty property);

    int addProperty(ProductProperty property);

    int deletePropertyById(ProductProperty property);

    void insertList(List<ProductProperty> list);

    void insertKeyWord(List<KeyWord> insertKeyWord);
}