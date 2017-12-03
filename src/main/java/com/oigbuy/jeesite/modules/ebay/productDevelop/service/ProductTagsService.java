/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.productDevelop.service;

import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.productDevelop.dao.ProductTagsDao;
import com.oigbuy.jeesite.modules.ebay.productDevelop.entity.ProductTags;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 产品和标签的关系表Service
 * @author mashuai
 * @version 2017-05-23
 */
@Service
@Transactional(readOnly = true)
public class ProductTagsService extends CrudService<ProductTagsDao, ProductTags> {

	public ProductTags get(String id) {
		return super.get(id);
	}
	
	public List<ProductTags> findList(ProductTags productTags) {
		return super.findList(productTags);
	}
	
	public Page<ProductTags> findPage(Page<ProductTags> page, ProductTags productTags) {
		return super.findPage(page, productTags);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductTags productTags) {
		super.save(productTags);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductTags productTags) {
		super.delete(productTags);
	}
	
}