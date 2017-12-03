/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.listing.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.listing.dao.ListingPropertyDao;
import com.oigbuy.jeesite.modules.ebay.listing.entity.ListingProperty;
import com.oigbuy.jeesite.modules.ebay.product.dto.ProductDetail;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductCodeManager;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductProperty;

/**
 * listing 属性Service
 * @author bill.xu
 * @version 2017-10-25
 */
@Service
@Transactional(readOnly = true)
public class ListingPropertyService extends CrudService<ListingPropertyDao, ListingProperty> {

	public ListingProperty get(String id) {
		return super.get(id);
	}
	
	public List<ListingProperty> findList(ListingProperty listingProperty) {
		return super.findList(listingProperty);
	}
	
	public Page<ListingProperty> findPage(Page<ListingProperty> page, ListingProperty listingProperty) {
		return super.findPage(page, listingProperty);
	}
	
	@Transactional(readOnly = false)
	public void save(ListingProperty listingProperty) {
		super.save(listingProperty);
	}
	
	@Transactional(readOnly = false)
	public void delete(ListingProperty listingProperty) {
		super.delete(listingProperty);
	}

	
	/**
	 * 保存 listing 的属性数据 （从产品属性到 listing 属性）
	 * 
	 * 
	 * 
	 * @param productDetail
	 * @param listingId
	 * @param siteId
	 */
	public void saveListingProperty(ProductDetail productDetail,Long listingId, String siteId) {
		
		List<String> names = productDetail.getProductPropertyNames();
		if(CollectionUtils.isEmpty(names) || CollectionUtils.isEmpty(productDetail.getCodeManagerList())){
			return ;
		}
		List<ListingProperty> listingProperties = new ArrayList<ListingProperty>();  
		List<ProductCodeManager> codeManagers = productDetail.getCodeManagerList();
				
		for (ProductCodeManager productCodeManager : codeManagers) {
			 if(productCodeManager==null || CollectionUtils.isEmpty(productCodeManager.getProductPropertyList())){
				   continue;
			   }
			List<ProductProperty> properties = productCodeManager.getProductPropertyList();
			
			int sum = properties.size();
			ListingProperty listingProperty  = null;
			for (int i = 0; i < sum; i++) {
				ProductProperty productProperty = properties.get(i);
					listingProperty = new ListingProperty();
					listingProperty.setCodeManagerId(Long.valueOf(productProperty.getCodeManagerId()));
					listingProperty.setId(String.valueOf(Global.getID()));
					listingProperty.setListingId(listingId);
					listingProperty.setName(names.get(i));
					listingProperty.setSiteId(Long.valueOf(siteId));
					listingProperty.setValue(productProperty.getPropertyValue());
				listingProperties.add(listingProperty);
			  }
			}
						
		   if(CollectionUtils.isNotEmpty(listingProperties)){
				this.dao.insertList(listingProperties);
			}
	}

	
	/***
	 * 批量更新  属性 name 和  value  
	 * 
	 * @param productPropertyList
	 */
	@Transactional(readOnly=false)
	public void updateBatch(List<ListingProperty> productPropertyList) {
		this.dao.updateBatch(productPropertyList);
	}

	/***
	 * 查询  listing 的 属性，根据属性名称 分组
	 * 
	 * @param listingId
	 * @return
	 */
	@Transactional(readOnly=false)
	public Map<String, List<String>> findListingVariation(String listingId) {
		Map<String, List<String>> resultMap = new HashMap<String, List<String>>();
		List<String> names = this.dao.findListingPropertyNames(listingId);
		if(!CollectionUtils.isNotEmpty(names)){
			return resultMap;
		}
		Iterator<String> iterator = names.iterator();
		while(iterator.hasNext()){
				String name = iterator.next();
				List<String> values = this.dao.findListingPropertyValues(listingId,name);
				if(CollectionUtils.isNotEmpty(values)){
					resultMap.put(name, values);
				}
			}
		return resultMap;
	}

	public void deleteByListingId(String listingId) {
		this.dao.deleteByListingId(listingId);
	}

	public void insertList(List<ListingProperty> propertyList) {
         this.dao.insertList(propertyList);		
	}
	
	List<String> findListingPropertyNames(String listingId){
		return this.dao.findListingPropertyNames(listingId);
	}
	
	
}