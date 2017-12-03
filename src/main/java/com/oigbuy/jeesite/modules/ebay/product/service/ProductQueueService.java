/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.service;

import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.product.dao.ProductQueueDao;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductQueue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 对消息的增删改查Service
 * @author AA
 * @version 2017-10-27
 */
@Service
@Transactional(readOnly = true)
public class ProductQueueService extends CrudService<ProductQueueDao, ProductQueue> {

	public ProductQueue get(String id) {
		return super.get(id);
	}
	
	public List<ProductQueue> findList(ProductQueue tProductQueue) {
		return super.findList(tProductQueue);
	}
	
	public Page<ProductQueue> findPage(Page<ProductQueue> page, ProductQueue tProductQueue) {
		return super.findPage(page, tProductQueue);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductQueue tProductQueue) {
		super.save(tProductQueue);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductQueue tProductQueue) {
		super.delete(tProductQueue);
	}
	
}