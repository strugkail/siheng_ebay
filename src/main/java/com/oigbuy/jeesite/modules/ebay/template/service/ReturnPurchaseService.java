package com.oigbuy.jeesite.modules.ebay.template.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.template.dao.ReturnPurchaseDao;
import com.oigbuy.jeesite.modules.ebay.template.entity.ReturnPurchase;

@Service
@Transactional(readOnly = true)
public class ReturnPurchaseService extends CrudService<ReturnPurchaseDao, ReturnPurchase>{

	@Autowired
	private ReturnPurchaseDao returnPurchaseDao;
	
	public ReturnPurchase get(String id) {
		return super.get(id);
	}

	public List<ReturnPurchase> findList(ReturnPurchase retpurchase) {
		return super.findList(retpurchase);
	}

	public Page<ReturnPurchase> findPage(Page<ReturnPurchase> page, ReturnPurchase retpurchase) {
		return super.findPage(page, retpurchase);
	}
	/**
	 * 通过模板名称查询数量
	 * @author yuxiang.xiong
	 * 2017年9月6日 下午3:14:30
	 * @param templateName
	 * @return
	 */
	public int findCountBytemplateName(String templateName){
		return returnPurchaseDao.findCountBytemplateName(templateName);
	}

	/***
	 * 通过 销售类型 和 站点 id 获取 对应的模板  
	 * 
	 * 
	 * @param saleType
	 * @param siteIds
	 * @return
	 */
	public ReturnPurchase autoSelectReturnPurchase(String saleType,String siteIds) {
		List<ReturnPurchase> returnPurchaseList = this.findList(new ReturnPurchase(saleType, siteIds));
		ReturnPurchase returnPurchase = null;
		if(CollectionUtils.isEmpty(returnPurchaseList)){
			return returnPurchase;
		}
		return returnPurchaseList.iterator().next();
	}
}
