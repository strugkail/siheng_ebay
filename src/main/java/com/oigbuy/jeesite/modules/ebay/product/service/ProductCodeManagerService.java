/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.common.utils.BarCodeUtils;
import com.oigbuy.jeesite.common.utils.ObjectUtils;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.common.utils.enums.SiteCurrencyEnum;
import com.oigbuy.jeesite.common.utils.translate.TranslatorUtil;
import com.oigbuy.jeesite.modules.ebay.product.dao.ProductCodeManagerDao;
import com.oigbuy.jeesite.modules.ebay.product.dao.TProductImgPlatformDao;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductCodeManager;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductProperty;
import com.oigbuy.jeesite.modules.ebay.product.entity.TProductImgPlatform;

/**
 * 产品代码管理表Service
 * @author mashuai
 * @version 2017-05-23
 */
@Service
@Transactional(readOnly = true)
public class ProductCodeManagerService extends CrudService<ProductCodeManagerDao, ProductCodeManager> {
	@Autowired
	private ProductCodeManagerDao productCodeManagerDao;
	
	@Autowired
	private ProductImgService productImgService;
	
	/***
	 * 图片
	 */
	@Autowired
	private TProductImgPlatformDao tProductImgPlatformDao;
	
	/***
	 * 产品属性
	 */
	@Autowired
	private PropertyService propertyService;
	
	public ProductCodeManager get(String id) {
		return super.get(id);
	}
	
	public List<ProductCodeManager> findList(ProductCodeManager productCodeManager) {
		return super.findList(productCodeManager);
	}
	
	public Page<ProductCodeManager> findPage(Page<ProductCodeManager> page, ProductCodeManager productCodeManager) {
		return super.findPage(page, productCodeManager);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductCodeManager productCodeManager) {
		super.save(productCodeManager);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductCodeManager productCodeManager) {
		super.delete(productCodeManager);
	}

	
	
	/****
	 * 通过 产品id  获取对应的  code manager 集合 ，并通过  siteName 翻译 子 sku 的 自定义属性  
	 * 
	 * @param productId
	 * @param siteName
	 * @return
	 */
	public List<ProductCodeManager> findListByProductId(String productId,String siteName,List<TProductImgPlatform> images) {
		 
		List<ProductCodeManager> codeManagerList=productCodeManagerDao.findListByProductId(productId);
		
		ProductProperty property = new ProductProperty(productId);
		// 通过关联表获取属性   
		List<ProductProperty>  propAllList = propertyService.findListByProperty(property);
		Map<String,List<ProductProperty>> propMap = new HashMap<String, List<ProductProperty>>();
		if(CollectionUtils.isNotEmpty(propAllList)){
			for(ProductProperty prop : propAllList){
				List<ProductProperty> propList = propMap.get(prop.getCodeManagerId());
				if(propList == null){
					propList = new ArrayList<ProductProperty>();
				}
				propList.add(prop);
				propMap.put(prop.getCodeManagerId(), propList);
			}
		}
		Map<String,TProductImgPlatform> imagesMap = new HashMap<String, TProductImgPlatform>();
		if(CollectionUtils.isNotEmpty(images)){
			for(TProductImgPlatform image : images){
				imagesMap.put(image.getCodeManagerId(), image);
			}
		}
		if(CollectionUtils.isNotEmpty(codeManagerList)){
		 for (ProductCodeManager productCodeManager : codeManagerList) {
			 if(productCodeManager != null){
				 
				 productCodeManager.setProductCode(BarCodeUtils.getCodeBySite(siteName));
				 
				 TProductImgPlatform image = imagesMap.get(productCodeManager.getId());
				 if(ObjectUtils.notEqual(image, null)){
					 productCodeManager.setImgUrl(image.getImgUrl());
					 productCodeManager.setImageId(image.getId());
					 //productCodeManager.setImageId(image.getImgId());
				 }
				 List<ProductProperty>  productPropertyList = propMap.get(productCodeManager.getId());
				 if (CollectionUtils.isNotEmpty(productPropertyList)) {
					 for (ProductProperty productProperty : productPropertyList) {
						 if(StringUtils.isNotBlank(siteName)){//有站点 则获取对应的各个属性的翻译值 
							 try {
								 // 要翻译的目的语言  暂时先写为
								 String language = SiteCurrencyEnum.getLanguageBySite(siteName);
								 productProperty.setTranslateName(TranslatorUtil.getSimpleContent(productProperty.getPropertyName(),language));
								 productProperty.setTranslateValue(TranslatorUtil.getSimpleContent(productProperty.getPropertyValue(),language));
							 } catch (Exception e) {
								 logger.error("##########  code manager 多属性翻译出错 {}",e.getLocalizedMessage());
							 }
						 }
					 }
				}
			    productCodeManager.setProductPropertyList(productPropertyList);
			    
			  
			    
			 }
//			 ProductImg image = this.productImgService.findByCodeManagerId(productCodeManager.getId(), Global.IMG_TYPE_CHILD_MAIN, productId, Global.ZERO);
		}
		}
		return codeManagerList;
	}
	public void update(ProductCodeManager productCodeManager){
		productCodeManagerDao.update(productCodeManager);
	}


	public List<ProductCodeManager> getProductCodeManagerByCode(String sysParentSku){
		return productCodeManagerDao.getProductCodeManagerByCode(sysParentSku);
	}

	public void UpdateBycode(ProductCodeManager productCodeManager){
		productCodeManagerDao.UpdateBycode(productCodeManager);
	}
	
	
	
	
	/***
	 * 主要更新  价格，EAN 码，推荐采购数量
	 * 
	 * @param codeManagerList
	 */
	@Transactional(readOnly = false)
	public void updateProductCodeManager(List<ProductCodeManager> codeManagerList) {
		
		List<ProductCodeManager> codeList = new ArrayList<ProductCodeManager>();
		
		if(CollectionUtils.isNotEmpty(codeManagerList)){
		    for (ProductCodeManager productCodeManager : codeManagerList) {
		    	if(productCodeManager==null){
		    		continue;
		    		}
		    	ProductCodeManager code = this.get(productCodeManager.getId());
		    	if(code!=null){
		    		code.setRecommendNumber(productCodeManager.getRecommendNumber());
		    		code.setPublishPrice(productCodeManager.getPublishPrice());
		    		codeList.add(code);
		    	}
			}	
		}
		
		if(CollectionUtils.isNotEmpty(codeList)){
			this.dao.updateBatch(codeList);
		}
	}

}