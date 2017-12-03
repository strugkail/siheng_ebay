/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.mode.service;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.modules.ebay.mode.dao.PublishStyleModeDao;
import com.oigbuy.jeesite.modules.ebay.mode.entity.PublishStyleMode;

/**
 * 模板Service
 * @author bill.xu
 * @version 2017-09-05
 */
@Service
@Transactional(readOnly = true)
public class PublishStyleModeService extends CrudService<PublishStyleModeDao, PublishStyleMode> {

	public PublishStyleMode get(String id) {
		return super.get(id);
	}
	
	public List<PublishStyleMode> findList(PublishStyleMode publishStyleMode) {
		return super.findList(publishStyleMode);
	}
	
	public Page<PublishStyleMode> findPage(Page<PublishStyleMode> page, PublishStyleMode publishStyleMode) {
		return super.findPage(page, publishStyleMode);
	}
	
	@Transactional(readOnly = false)
	public void save(PublishStyleMode publishStyleMode) {
		super.save(publishStyleMode);
	}
	
	@Transactional(readOnly = false)
	public void delete(PublishStyleMode publishStyleMode) {
		super.delete(publishStyleMode);
	}

	
	/***
	 * 自主计算 刊登风格模板（目前是 通过 站点选择，后面可能会通过 店铺选择  ）
	 * 
	 * 站点选择不到 就 选择默认的 通用的（没有站点的模板）
	 * 
	 * @param siteIds
	 * @return
	 */
	public PublishStyleMode autoSelectPublishStyleMode(String siteIds) {
		
		List<PublishStyleMode> findList = this.dao.findList(null);
		PublishStyleMode publishStyleMode=null;
		if(CollectionUtils.isEmpty(findList)){
			return publishStyleMode;
		}
		//通用模板
		PublishStyleMode publishStyleModeN = null;
		Iterator<PublishStyleMode> iterator = findList.iterator();
		while (iterator.hasNext()) {
			PublishStyleMode mode =  iterator.next();
			if(StringUtils.isBlank(mode.getSiteId())){
				publishStyleModeN = mode;
			}
			if(StringUtils.isNoneBlank(mode.getSiteId()) && mode.getSiteId().equals(siteIds)){
				publishStyleMode = mode;
				break;
			}
		}
		return publishStyleMode==null?publishStyleModeN:publishStyleMode;
	}
	
}