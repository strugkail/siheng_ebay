/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.product.dao.ItemSpecificsDao;
import com.oigbuy.jeesite.modules.ebay.product.entity.ItemSpecifics;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductCategory;

/**
 * 分类细节Service
 * @author bill.xu
 * @version 2017-09-22
 */
@Service
@Transactional(readOnly = true)
public class ItemSpecificsService extends CrudService<ItemSpecificsDao, ItemSpecifics> {

	
	@Autowired
	private  ProductCategoryService productCategoryService; 
	
	
	public ItemSpecifics get(String id) {
		return super.get(id);
	}
	
	public List<ItemSpecifics> findList(ItemSpecifics itemSpecifics) {
		return super.findList(itemSpecifics);
	}
	
	public Page<ItemSpecifics> findPage(Page<ItemSpecifics> page, ItemSpecifics itemSpecifics) {
		return super.findPage(page, itemSpecifics);
	}
	
	@Transactional(readOnly = false)
	public void save(ItemSpecifics itemSpecifics) {
		super.save(itemSpecifics);
	}
	
	@Transactional(readOnly = false)
	public void delete(ItemSpecifics itemSpecifics) {
		super.delete(itemSpecifics);
	}

	
	
	/***
	 * 通过name 和 productID 精确查询
	 * 
	 * @param productId
	 * @param name
	 * @return
	 */
	public ItemSpecifics findByProductAndName(Long productId, String name) {
		
		return this.dao.findByProductAndName(productId,name);
	}

	
	/***
	 * 删除 该站点下 分类的 item specifics  
	 * 
	 * @param id   产品id 
	 * @param siteId  站点id 
	 * @param listingId  listing  id 
	 */
	
	@Transactional(readOnly=false)
	public void deleteByProductIdAndCategoryId(String id, String siteId,Long listingId) {
		
		ProductCategory productCategory = new ProductCategory();
		productCategory.setListingId(listingId);
		productCategory.setSiteId(Long.valueOf(siteId));
		productCategory.setProductId(Long.valueOf(id));
		
		List<String> categoryIds = new ArrayList<String>();
		List<ProductCategory> categoryList = productCategoryService.findList(productCategory );
		if(CollectionUtils.isNotEmpty(categoryList)){
			for (ProductCategory ca : categoryList) {
				categoryIds.add(ca.getId());
			}
		}
		if(CollectionUtils.isNotEmpty(categoryIds)){
			this.dao.deleteByCategoryIds(categoryIds);
		}
	}
	
}