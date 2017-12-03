/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.platformAndsite.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.dao.PlatformSiteCountDao;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatformSiteCount;

/**
 * 站点统计service
 * @author yuxiang.xiong
 *
 */
@Service
@Transactional(readOnly = true)
public class PlatformSiteCountService extends CrudService<PlatformSiteCountDao, PlatformSiteCount> {

	public PlatformSiteCount get(String id) {
		return super.get(id);
	}
	
	public List<PlatformSiteCount> findList(PlatformSiteCount PlatformSiteCount) {
		return super.findList(PlatformSiteCount);
	}
	
	public Page<PlatformSiteCount> findPage(Page<PlatformSiteCount> page, PlatformSiteCount PlatformSiteCount) {
		return super.findPage(page, PlatformSiteCount);
	}
	
	@Transactional(readOnly = false)
	public void save(PlatformSiteCount PlatformSiteCount) {
		super.save(PlatformSiteCount);
	}
	
	@Transactional(readOnly = false)
	public void delete(PlatformSiteCount PlatformSiteCount) {
		super.delete(PlatformSiteCount);
	}
	
}