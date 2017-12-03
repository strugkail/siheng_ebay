/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.purchase.dao;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.purchase.dto.PurchaseSource;
import com.oigbuy.jeesite.modules.ebay.purchase.dto.PurchaseSourceCode;
import com.oigbuy.jeesite.modules.ebay.purchase.entity.Supplier;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 供应商信息DAO接口
 * @author 马志明
 * @version 2017-06-18
 */
@MyBatisDao
public interface SupplierDao extends CrudDao<Supplier> {
	
	/**
	 * 根据供应商名称获取供应商
	 * 
	 * @param supplierName
	 * @return
	 */
	Supplier getSupplierBySupplierName(@Param("supplierName") String supplierName);
	
	PurchaseSource getSourceByUrlAndProductId(@Param("productId") String productId, @Param("sourceUrl") String sourceUrl);
	
	void insertPurchaseSource(PurchaseSource purchaseSource);
	
	List<PurchaseSource> getAllPurchaseSourceByProductId(@Param("productId") String productId);
	
	void deletePurchaseSourceById(@Param("sourceId") String sourceId);
	
	/**
	 * 插入采购源与产品代码关系表
	 * @param purchaseSourceCodeList
	 */
	void insertPurchaseSourceCodeList(List<PurchaseSourceCode> purchaseSourceCodeList);
	void deletePurchaseSourceBycodeId(@Param("CODE_ID") String CODE_ID);
	
	List<Supplier> querySupplierBySupplierName(@Param("supplierName") String supplierName);
	
	int getCountForPurchaseSourceCodeBySourceId(@Param("sourceId") String sourceId);

	List<Supplier> getSupplierByMap(Map<String,String> map);
	int deleteByProductId(@Param("productId") String productId);
}