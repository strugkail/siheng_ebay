package com.oigbuy.jeesite.modules.ebay.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.product.dao.PropertyDao;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductProperty;

/**
 * 
 * @author tony.liu
 */
@Service
@Transactional(readOnly = true)
public class PropertyService extends CrudService<PropertyDao, ProductProperty> {
	@Autowired
	private PropertyDao propertyDao;

	public List<ProductProperty> findPropertyListById(String productId){
	return  propertyDao.findPropertyListById(productId);
	}
	public List<ProductProperty> findPropertyValueListById(ProductProperty property){
	return propertyDao.findPropertyValueListById(property);
	}
	public int addProperty(ProductProperty property){
		return propertyDao.addProperty(property);
	}

	public int deletePropertyById(ProductProperty property){
		return propertyDao.deletePropertyById(property);
	}
	public void insertList(List<ProductProperty> list){
		propertyDao.insertList(list);
	}
	public List<ProductProperty> findListByProperty(ProductProperty property){
		return	propertyDao.findListByProperty(property);
	}
	public List<ProductProperty> findByProductId(String productId){
		return propertyDao.findByProductId(productId);
	}

}