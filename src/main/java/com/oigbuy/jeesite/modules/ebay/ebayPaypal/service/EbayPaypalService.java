/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.ebayPaypal.service;

import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.ebayPaypal.dao.EbayPaypalDao;
import com.oigbuy.jeesite.modules.ebay.ebayPaypal.entity.EbayPaypal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ebay最小费率Service
 * @author strugkail
 * @version 2017-09-25
 */
@Service
@Transactional(readOnly = true)
public class EbayPaypalService extends CrudService<EbayPaypalDao, EbayPaypal> {
	@Autowired
	private EbayPaypalDao paypalDao;
	public EbayPaypal get(String id) {
		return super.get(id);
	}
	public EbayPaypal getBySite(EbayPaypal paypal){return paypalDao.getBySite(paypal);}
	public List<EbayPaypal> findList(EbayPaypal ebayFee) {
		return super.findList(ebayFee);
	}
	
	public Page<EbayPaypal> findPage(Page<EbayPaypal> page, EbayPaypal ebayFee) {
		return super.findPage(page, ebayFee);
	}
	
	@Transactional(readOnly = false)
	public void save(EbayPaypal ebayFee) {
		super.save(ebayFee);
	}
	
	@Transactional(readOnly = false)
	public void delete(EbayPaypal ebayFee) {
		super.delete(ebayFee);
	}
	
}