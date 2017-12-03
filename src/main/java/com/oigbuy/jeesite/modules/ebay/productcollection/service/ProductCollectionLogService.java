/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.productcollection.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.productcollection.dao.ProductCollectionLogDao;
import com.oigbuy.jeesite.modules.ebay.productcollection.entity.ProductCollectionLog;

/**
 * 竞品采集记录日志Service
 * @author bill.xu
 * @version 2017-09-07
 */
@Service
@Transactional(readOnly = true)
public class ProductCollectionLogService extends CrudService<ProductCollectionLogDao, ProductCollectionLog> {

	public ProductCollectionLog get(String id) {
		return super.get(id);
	}
	
	public List<ProductCollectionLog> findList(ProductCollectionLog productCollectionLog) {
		return super.findList(productCollectionLog);
	}
	
	public Page<ProductCollectionLog> findPage(Page<ProductCollectionLog> page, ProductCollectionLog productCollectionLog) {
		return super.findPage(page, productCollectionLog);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductCollectionLog productCollectionLog) {
		super.save(productCollectionLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductCollectionLog productCollectionLog) {
		super.delete(productCollectionLog);
	}
	
}