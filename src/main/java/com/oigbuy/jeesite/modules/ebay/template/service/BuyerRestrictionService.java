package com.oigbuy.jeesite.modules.ebay.template.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.template.dao.BuyerRestrictionDao;
import com.oigbuy.jeesite.modules.ebay.template.entity.BuyerRestriction;


@Service
@Transactional(readOnly=true)
public class BuyerRestrictionService extends CrudService<BuyerRestrictionDao, BuyerRestriction> {
    
	@Autowired
	private BuyerRestrictionDao buyerRestrictionDao;
	
	/**
	 * 通过模板名称查询数量
	 * @author yuxiang.xiong
	 * 2017年9月6日 下午3:14:53
	 * @param templateName
	 * @return
	 */
	public int findCountBytemplateName(String templateName){
		return buyerRestrictionDao.findCountBytemplateName(templateName);
	}

	/***
	 * 自主选择  买家限制 模板
	 * 
	 * @return
	 */
	public BuyerRestriction autoSelectBuyerRestriction() {
		BuyerRestriction buyerRestriction = null;
		List<BuyerRestriction> list = this.findList(null);
		if(CollectionUtils.isEmpty(list)){
			return buyerRestriction;
		}
		return list.iterator().next();
	}
}
