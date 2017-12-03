/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.KeyWord;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.product.dao.ProductDescriptionDao;
import com.oigbuy.jeesite.modules.ebay.product.dto.ProductDetail;
import com.oigbuy.jeesite.modules.ebay.product.dto.ProductDto;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductDescription;

/**
 * 产品描述Service
 * @author bill.xu
 * @version 2017-10-19
 */
@Service
@Transactional(readOnly = true)
public class ProductDescriptionService extends CrudService<ProductDescriptionDao, ProductDescription> {

	public ProductDescription get(String id) {
		return super.get(id);
	}
	
	public List<ProductDescription> findList(ProductDescription productDescription) {
		return super.findList(productDescription);
	}
	
	public Page<ProductDescription> findPage(Page<ProductDescription> page, ProductDescription productDescription) {
		return super.findPage(page, productDescription);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductDescription productDescription) {
		super.save(productDescription);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductDescription productDescription) {
		super.delete(productDescription);
	}

	
	/***
	 * 保存产品描述
	 * 
	 * @param productId
	 * @param productDetail 
	 */
	@Transactional(readOnly=false)
	public void saveProductDescription(String  productId, ProductDetail productDetail) {
		//保存的时候要先删除其他的描述内容
		this.dao.deleteByProductId(productId, Global.ONE);
		
		List<ProductDescription> descriptionList = new ArrayList<ProductDescription>();
		//页面传递过来的 描述内容 分为 原始的和经过翻译的 
		/***
		 * 描述 翻译后的 集合
		 */
		List<String> productDescritions = productDetail.getProductDescritions();
		if(CollectionUtils.isNotEmpty(productDescritions)){
			/***
			 * 描述 用户输入的内容
			 */
			List<String> productDescritionsT = productDetail.getProductDescritionsT();
			for(int i=0;i<productDescritions.size();i++){
				String name1 ="";
				if(i<productDescritionsT.size()){
					name1 = productDescritionsT.get(i);
				}
				String name2 = productDescritions.get(i);
				ProductDescription e = new ProductDescription(String.valueOf(Global.getID()),Long.valueOf(productId), name1, name2, Global.ONE, Global.ONE);
				descriptionList.add(e);
			}
			
		}
		
		/***
		 * 描述2 翻译后的
		 */
		String productDescritions2 =productDetail.getProductDescritions2();
		/***
		 * 描述2
		 */
		String productDescritions2T = productDetail.getProductDescritions2T();
		ProductDescription e2 = new ProductDescription(String.valueOf(Global.getID()),Long.valueOf(productId), productDescritions2T, productDescritions2, Global.ONE, Global.TWO);
		descriptionList.add(e2);
		
		
		if(CollectionUtils.isNotEmpty(descriptionList)){
			this.dao.insertList(descriptionList);
		}
		
	}
	
	/**
	 * 查询该产品的 描述信息
	 * @param id  产品id
	 * @param productDto
	 * @return
	 */
	public ProductDto getProductDescription(String id,ProductDto productDto) {
		//查询所有的
		List<ProductDescription> list = this.dao.findList(new ProductDescription(Long.valueOf(id), Global.ONE));
		if(CollectionUtils.isNotEmpty(list)){
			Iterator<ProductDescription> it = list.iterator();
			while (it.hasNext()) {
				ProductDescription type = it.next();
				if(Global.TWO.equals(type.getType())){//2是手动输入的
					productDto.setProductDescription2(type);
					it.remove();
				}
			}
			productDto.setProductDescriptions(list);
		}
		return productDto;
	}

}