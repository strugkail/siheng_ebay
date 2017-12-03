/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.purchase.service;

import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.purchase.dao.ProductPurchaseDao;
import com.oigbuy.jeesite.modules.ebay.purchase.entity.ProductPurchase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 产品采购Service
 * @author 马志明
 * @version 2017-06-21
 */
@Service
@Transactional(readOnly = true)
public class ProductPurchaseService extends CrudService<ProductPurchaseDao, ProductPurchase> {

	public ProductPurchase get(String id) {
		return super.get(id);
	}
	
	public List<ProductPurchase> findList(ProductPurchase productPurchase) {
		return super.findList(productPurchase);
	}
	
	public Page<ProductPurchase> findPage(Page<ProductPurchase> page, ProductPurchase productPurchase) {
		return super.findPage(page, productPurchase);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductPurchase productPurchase) {
		super.save(productPurchase);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductPurchase productPurchase) {
		super.delete(productPurchase);
	}
	
}