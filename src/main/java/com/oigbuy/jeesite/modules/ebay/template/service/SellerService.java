package com.oigbuy.jeesite.modules.ebay.template.service;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.Shops;
import com.oigbuy.jeesite.modules.ebay.template.dao.SellerDao;
import com.oigbuy.jeesite.modules.ebay.template.entity.Seller;

/**
 * 卖家账户Service
 * 
 * @author jalyn.zhang
 *
 */

@Service
@Transactional(readOnly = true)
public class SellerService extends CrudService<SellerDao,Seller> {

	@Transactional(readOnly = false)
	public void save(Seller seller) {
		super.save(seller);
	}
	
	@Transactional(readOnly = false)
	public void delete(Seller seller) {
		super.delete(seller);
	}
	public Page<Seller> findPage(Page<Seller> page, Seller seller) {
		return super.findPage(page, seller);
	}
	
	public List<Seller> findList(Seller seller) {
		return super.findList(seller);
	}
	
	public Seller get(String id) {
		return super.get(id);
	}
	
	public Seller findByShopId(Long shopId) {
		List<Seller>   sellerList = this.findList(new Seller(null,shopId));
		if(CollectionUtils.isNotEmpty(sellerList)){
			Iterator<Seller> iterator = sellerList.iterator();
			return iterator.next();
		}
		return null;
	}

	public List<Shops> getShopList() {
		return this.dao.getShopList();
	}

}
