package com.oigbuy.jeesite.modules.ebay.product.dao;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.product.entity.Product;

import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @author tony.liu
 */
@MyBatisDao
public interface ProductDao extends CrudDao<Product> {

	void updateById(Product product);
	
	void updateProductUpdateTimeById(@Param("id") String productId, @Param("updateTime") Date updateTime);
	
	void updateCodeHistoryById(@Param("id")String productId, @Param("codeHistory") String codeHistory);
	int insert(Product product);
//	Product get(String id);
	void updatePropertyAndPropertyContentById(Product product);

	/**
	 * 新增线下数据到Product中
	 * @param product
	 */
	void addExcelData(Product product);

	void addProduct(Product product);
	
	/**
	 * 更新已完成产品数量补充标识
	 * @param productId
	 * @param finishedProductQuantity
	 */
	void updateFinishedProductQuantity(@Param("id") String productId, @Param("finishedProductQuantity") String finishedProductQuantity);


}