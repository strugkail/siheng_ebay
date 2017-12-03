/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.listing.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.modules.ebay.listing.dao.EbayListingPriceDao;
import com.oigbuy.jeesite.modules.ebay.listing.entity.EbayListingPrice;
import com.oigbuy.jeesite.modules.ebay.product.dto.ProductDetail;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductCodeManager;

/**
 * ebay  listing 价格Service
 * @author bill.xu
 * @version 2017-09-22
 */
@Service
@Transactional(readOnly = true)
public class EbayListingPriceService extends CrudService<EbayListingPriceDao, EbayListingPrice> {

	public EbayListingPrice get(String id) {
		return super.get(id);
	}
	
	public List<EbayListingPrice> findList(EbayListingPrice ebayListingPrice) {
		return super.findList(ebayListingPrice);
	}
	
	public Page<EbayListingPrice> findPage(Page<EbayListingPrice> page, EbayListingPrice ebayListingPrice) {
		return super.findPage(page, ebayListingPrice);
	}
	
	@Transactional(readOnly = false)
	public void save(EbayListingPrice ebayListingPrice) {
		super.save(ebayListingPrice);
	}
	
	@Transactional(readOnly = false)
	public void delete(EbayListingPrice ebayListingPrice) {
		super.delete(ebayListingPrice);
	}

	
	@Transactional(readOnly = false)
	public void saveListingPrice(ProductDetail productDetail, Long listingId, String siteId) {
		if(CollectionUtils.isEmpty(productDetail.getCodeManagerList())){
			return ;
		}
		List<ProductCodeManager> managerList = productDetail.getCodeManagerList();
		List<EbayListingPrice> priceList = new ArrayList<EbayListingPrice>();
		for (ProductCodeManager productCodeManager : managerList) {
			if(productCodeManager==null || StringUtils.isBlank(productCodeManager.getId())){
				continue;
			}
			priceList.add(new EbayListingPrice(Global.getID().toString(), productCodeManager.getListingSkuCode(), Long.valueOf(siteId), listingId, String.valueOf(productCodeManager.getPublishPrice())));
		}
		if(CollectionUtils.isNotEmpty(priceList)){
			this.dao.insertList(priceList);
		}
	}

	
	/***
	 * 通过 listingId 和 sku  查询
	 * 
	 * @param listingId
	 * @param sku
	 * @return
	 */
	public EbayListingPrice findByListingIdAndSku(Long listingId, String sku) {
		
		EbayListingPrice ebayListingPrice = null;
		
		List<EbayListingPrice> priceList = this.findList(new EbayListingPrice(sku, listingId));
		if(CollectionUtils.isNotEmpty(priceList)){
			Iterator<EbayListingPrice> iterator = priceList.iterator();
			ebayListingPrice = iterator.next();
		}
		return ebayListingPrice;
	}

	
	/**
	 * 批量更新 价格
	 * 
	 * @param priceList
	 */
	@Transactional(readOnly=false)
	public void updateBatch(List<EbayListingPrice> priceList) {
		this.dao.updateBatch(priceList);
	}

	
	/***
	 * 批量插入
	 * 
	 * @param priceList
	 */
	@Transactional(readOnly=false)
	public void insertList(List<EbayListingPrice> priceList) {
		this.dao.insertList(priceList);
	}
}