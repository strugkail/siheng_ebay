/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.modules.ebay.product.dao.TitleKeyDao;
import com.oigbuy.jeesite.modules.ebay.product.dto.ProductDto;
import com.oigbuy.jeesite.modules.ebay.product.entity.TitleKey;

/**
 * 标题关键字Service
 * @author bill.xu
 * @version 2017-09-22
 */
@Service
@Transactional(readOnly = true)
public class TitleKeyService extends CrudService<TitleKeyDao, TitleKey> {

	public TitleKey get(String id) {
		return super.get(id);
	}
	
	public List<TitleKey> findList(TitleKey titleKey) {
		return super.findList(titleKey);
	}
	
	public Page<TitleKey> findPage(Page<TitleKey> page, TitleKey titleKey) {
		return super.findPage(page, titleKey);
	}
	
	@Transactional(readOnly = false)
	public void save(TitleKey titleKey) {
		super.save(titleKey);
	}
	
	@Transactional(readOnly = false)
	public void delete(TitleKey titleKey) {
		super.delete(titleKey);
	}

	
	/**
	 * 保存 产品的标题关键字
	 * @param productDto
	 */
	@Transactional(readOnly=false)
	public void saveProductTitleKey(ProductDto productDto) {
      
		String saleType = productDto.getDevelopmentType();
		
		if(Global.SALE_TYPE_OVERSEAS_STOREHOUSE.equals(saleType) || Global.SALE_TYPE_VIRTUAL_OVERSEAS_STOREHOUSE.equals(saleType)){
			// 精品的没有相对应的关键字  这里不做任何操作 
		
		}else{
			String productId = productDto.getId();
			// 先删除  ebay  所有的 key 关键字
			this.dao.deletByProductId(productId,Global.ONE);
			
			List<TitleKey>  list = new  ArrayList<TitleKey>();
			//主标题下的 必须关键字保存
			list.addAll(saveKeyWord(productDto.getKeyWord1(), productId,Global.ZERO_INTEGER,Global.ZERO,null));
			
			//主标题下的 非关键字保存
			list.addAll(saveKeyWord(productDto.getOtherKeyWord1(),productId,Global.ONE_INTEGER,Global.ZERO,null));
			
			//副标题下的 必须关键字保存
			list.addAll(saveKeyWord(productDto.getKeyWord2(), productId,Global.ZERO_INTEGER,Global.ONE,null));
			
			//副标题下的 非关键字保存
			list.addAll(saveKeyWord(productDto.getOtherKeyWord2(),productId,Global.ONE_INTEGER,Global.ONE,null));
			
			if(CollectionUtils.isNotEmpty(list)){
				this.dao.insertList(list);
			}
		}
		
	}

	private List<TitleKey> saveKeyWord(String keyword, String productId,Integer titleType,String type,Long titleId) {
		List<TitleKey> keyList = new ArrayList<TitleKey>();
		if(StringUtils.isNotBlank(keyword)){
			String[] key1 = keyword.split(Global.SEPARATE_6);
			for (int i = 0; i < key1.length; i++) {
				keyList.add(new TitleKey(Global.getID().toString(),Long.valueOf(productId), titleType,i,key1[i], Global.ONE, type, titleId));
			}
		}
		return keyList;
	}
	
}