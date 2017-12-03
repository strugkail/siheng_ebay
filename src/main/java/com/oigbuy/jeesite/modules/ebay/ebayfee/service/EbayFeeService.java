/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.ebayfee.service;

import java.util.List;

import com.oigbuy.jeesite.common.config.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.ebayfee.dao.EbayFeeDao;
import com.oigbuy.jeesite.modules.ebay.ebayfee.entity.EbayFee;

/**
 * ebay最小费率Service
 * @author strugkail
 * @version 2017-09-25
 */
@Service
@Transactional(readOnly = true)
public class EbayFeeService extends CrudService<EbayFeeDao, EbayFee> {
	@Autowired
	private EbayFeeDao feeDao;
	public EbayFee get(String id) {
		return super.get(id);
	}
	public EbayFee getBySite(EbayFee fee){return feeDao.getBySite(fee);}
	public EbayFee getBySiteAndCategory(EbayFee fee){
		if(fee.getCategoryName()!=null && fee.getCategoryName().contains(Global.SEPARATE_14)){
			fee.setCategoryName(fee.getCategoryName().split(Global.SEPARATE_14)[0]);
		}else{
			throw new RuntimeException("此分类："+fee.getCategoryName()+"未包含分隔符:"+Global.SEPARATE_14);
		}
		return feeDao.getBySiteAndCategory(fee);
	}
	public List<EbayFee> findList(EbayFee ebayFee) {
		return super.findList(ebayFee);
	}
	
	public Page<EbayFee> findPage(Page<EbayFee> page, EbayFee ebayFee) {
		return super.findPage(page, ebayFee);
	}
	
	@Transactional(readOnly = false)
	public void save(EbayFee ebayFee) {
		super.save(ebayFee);
	}
	
	@Transactional(readOnly = false)
	public void delete(EbayFee ebayFee) {
		super.delete(ebayFee);
	}
	
}