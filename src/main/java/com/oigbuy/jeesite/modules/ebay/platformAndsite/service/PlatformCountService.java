/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.platformAndsite.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.dao.PlatformCountDao;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatformCount;

/**
 * 平台统计service
 * @author yuxiang.xiong
 *
 */
@Service
@Transactional(readOnly = true)
public class PlatformCountService extends CrudService<PlatformCountDao, PlatformCount> {

	public PlatformCount get(String id) {
		return super.get(id);
	}
	
	public List<PlatformCount> findList(PlatformCount platformcount) {
		return super.findList(platformcount);
	}
	
	public Page<PlatformCount> findPage(Page<PlatformCount> page, PlatformCount platformcount) {
		return super.findPage(page, platformcount);
	}
	
	@Transactional(readOnly = false)
	public void save(PlatformCount platformcount) {
		super.save(platformcount);
	}
	
	@Transactional(readOnly = false)
	public void delete(PlatformCount platformcount) {
		super.delete(platformcount);
	}
	
}