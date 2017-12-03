/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.platformAndsite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.dao.PlatformDao;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.Platform;

/**
 * 平台Service
 * @author mashuai
 * @version 2017-05-22
 */
@Service
@Transactional(readOnly = true)
public class PlatformService extends CrudService<PlatformDao, Platform> {
    
	@Autowired
	private PlatformDao platformDao;
	
	public Platform get(String id) {
		return super.get(id);
	}
	
	public List<Platform> findList(Platform platform) {
		return super.findList(platform);
	}
	
	public Page<Platform> findPage(Page<Platform> page, Platform platform) {
		return super.findPage(page, platform);
	}
	
	@Transactional(readOnly = false)
	public void save(Platform platform) {
		super.save(platform);
	}
	
	@Transactional(readOnly = false)
	public void delete(Platform platform) {
		super.delete(platform);
	}
	public List<Platform> findPlatByplatformId(Platform platform) {
		return platformDao.findPlatByplatformId(platform);
	}
	public List<Platform> getinfoByplatformName(String platformName){
		return platformDao.getinfoByplatformName(platformName);
	}
}